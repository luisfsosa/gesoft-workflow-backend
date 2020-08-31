package cl.com.gesoft.workflow.security;

public interface GesoftAuthManager {
    AuthResponse requestAuthKey(String username, String password, AuthenticationListener authenticationListener) throws WorkflowAuthenticationException;
}