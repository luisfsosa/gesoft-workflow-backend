package cl.com.gesoft.workflow.dao.events;

public interface CreateSesionCallback<T> {
    <T> T beforeSave(SaveSesionCallback saveSesionCallback) throws Exception;

}
