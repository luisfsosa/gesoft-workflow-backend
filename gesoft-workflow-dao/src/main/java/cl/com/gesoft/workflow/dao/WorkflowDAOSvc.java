package cl.com.gesoft.workflow.dao;

import cl.com.gesoft.workflow.dao.events.CreateSesionCallback;
import cl.com.gesoft.workflow.model.Session;

public interface WorkflowDAOSvc {
    <T> T findById(Class<T> t, Object id) throws Exception;

    <T> T createSesion(CreateSesionCallback<T> createSesionCallback) throws Exception;

    Session logOut(Integer userId, Session session) throws Exception;

    Session setLastAccess(Integer sessionId, Session session) throws Exception;
}