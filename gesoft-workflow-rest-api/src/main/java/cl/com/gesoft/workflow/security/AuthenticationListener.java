package cl.com.gesoft.workflow.security;

public interface AuthenticationListener {
    void authResponseCreated(AuthResponse authResponse) throws Exception;
}
