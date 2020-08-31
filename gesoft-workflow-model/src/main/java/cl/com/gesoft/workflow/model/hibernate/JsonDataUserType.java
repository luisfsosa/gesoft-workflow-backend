/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.model.hibernate;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.SerializationException;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;


/**
 * The type Json data user type.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public abstract class JsonDataUserType implements UserType {
    /**
     * The Logger.
     */
    Logger logger = null;
    private Class clazz;
    /**
     * The Object mapper.
     */
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Instantiates a new Json data user type.
     *
     * @param clazz the clazz
     */
    public JsonDataUserType(Class clazz) {
        this.clazz = clazz;
        // No tiene que haber error si en el json hay una propiedad que no exista en el bean
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        logger = LoggerFactory.getLogger(this.clazz);
    }

    /**
     * The constant POSTGRES_JSON_TYPE.
     */
    public static final String POSTGRES_JSON_TYPE = "json";

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }

    @Override
    public Class returnedClass() {
        return clazz;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return ObjectUtils.nullSafeEquals(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        if (x == null) {
            return 0;
        }

        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        PGobject pGobject = (PGobject) resultSet.getObject(strings[0]);
        if (!resultSet.wasNull()) {
            try {
                return objectMapper.readValue(pGobject.getValue(), clazz);
            } catch (Exception e) {
                throw new HibernateException(e);
            }
        }
        return null;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object o, int index, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        try {
            if (o == null) {
                st.setNull(index, Types.OTHER);
            } else {
                PGobject json = new PGobject();
                json.setType(POSTGRES_JSON_TYPE);

                String jsonString = objectMapper.writeValueAsString(o);
                logger.debug("Set parameter [" + index + "] with json [" + jsonString + "] from [" + o + "]");
                logger.info("Set parameter [" + index + "] with json [" + jsonString + "] from [" + o + "]");
                json.setValue(jsonString);
                st.setObject(index, json);
            }
        } catch (JsonProcessingException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public Object deepCopy(Object originalValue) throws HibernateException {
        if (originalValue == null) {
            return null;
        }
        try {
            String data = objectMapper.writeValueAsString(originalValue);
            return objectMapper.readValue(data, clazz);
        } catch (JsonProcessingException e) {
            logger.error("Error al leer object [" + clazz.getCanonicalName() + "] [" + originalValue + "]", e);
            throw new HibernateException(e);
        } catch (IOException e) {
            throw new HibernateException(e);
        }

    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        Object copy = deepCopy(value);

        if (copy instanceof Serializable) {
            return (Serializable) copy;
        }

        throw new SerializationException(String.format("Cannot serialize '%s', %s is not Serializable.", value, value.getClass()), null);

    }

    @Override
    public Object assemble(Serializable cached, Object o) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object o1, Object o2) throws HibernateException {
        return deepCopy(original);
    }
}