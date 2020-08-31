package cl.com.gesoft.workflow.dao.events;

import cl.com.gesoft.workflow.model.Session;

public interface SaveSesionCallback {
    void save(Session sesion) throws Exception;
}
