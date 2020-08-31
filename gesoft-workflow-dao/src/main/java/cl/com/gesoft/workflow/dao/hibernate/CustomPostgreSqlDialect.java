package cl.com.gesoft.workflow.dao.hibernate;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import org.hibernate.dialect.PostgreSQL94Dialect;

import java.sql.Types;


/**
 * The type Custom postgre sql dialect.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public class CustomPostgreSqlDialect extends PostgreSQL94Dialect {


    /**
     * Instantiates a new Custom postgre sql dialect.
     */
    public CustomPostgreSqlDialect() {
        System.out.println("Register Java Object");
        this.registerColumnType(Types.JAVA_OBJECT, "jsonb");

        this.registerHibernateType(Types.ARRAY, StringArrayType.class.getName());

    }
}