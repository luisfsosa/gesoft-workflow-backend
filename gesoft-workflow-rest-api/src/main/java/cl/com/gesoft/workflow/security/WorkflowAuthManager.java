package cl.com.gesoft.workflow.security;

public interface WorkflowAuthManager {
    AuthResponse requestAuthKey(String username, String password, AuthenticationListener authenticationListener) throws Exception;
}