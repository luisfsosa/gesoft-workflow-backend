package cl.com.gesoft.workflow.dao;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import cl.com.gesoft.workflow.dao.events.CreateSesionCallback;
import cl.com.gesoft.workflow.dao.events.SaveSesionCallback;
import cl.com.gesoft.workflow.model.Session;

public class WorkflowDAOSvcImpl implements WorkflowDAOSvc {

    @Autowired
	private EntityManager entityManager;

    @Override
    public <T> T findById(final Class<T> t, final Object id) throws Exception {
        return null;
    }

    @Override
    public <T> T createSesion(CreateSesionCallback<T> createSesionCallback) throws Exception {
        return createSesionCallback.beforeSave(new SaveSesionCallback(){
            @Override
            public void save(Session sesion) throws Exception {
                try {
                    entityManager.persist(sesion);
                } catch (final Exception e) {
                    throw new Exception("Error al guardar la sessión", e);
                }
            }
        });
    }

    @Override
    public Session logOut(final Integer userId, final Session session) throws Exception {
        return null;
    }

    @Override
    public Session setLastAccess(final Integer sessionId, final Session session) throws Exception {
        return null;
    }
    
}