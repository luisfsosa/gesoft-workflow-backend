package cl.com.gesoft.workflow.security;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import cl.com.gesoft.workflow.dao.UserRepository;
import cl.com.gesoft.workflow.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class WorkflowAuthManagerImpl implements WorkflowAuthManager {

    static final long EXPIRATIONTIME = 86_400_000;

    @Autowired
    private UserRepository userRepository;

    @Value("${session.secret}")
    protected String jwtSecret;

    public WorkflowAuthManagerImpl(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    @Override
    public AuthResponse requestAuthKey(String username, String password, AuthenticationListener authenticationListener)
            throws Exception {
        try {
            if (authenticationListener == null) {
                authenticationListener = new AuthenticationListener() {
                    @Override
                    public void authResponseCreated(AuthResponse authResponse) {

                    }
                };
            }

            User user = userRepository.findByEmailIgnoreCaseAndPassword(username, password);
            if (user == null) {
                throw new Exception("Usuario/Password inválido o Usuario no existe en el CRM");
            }

            final Map<String, Object> aplicaciones = new LinkedHashMap<>();
            Long now = System.currentTimeMillis();

            AuthResponse authResponse = new AuthResponse();

            authResponse.setUserId(user.getId().toString());
            authResponse.setClientId(user.getClientId());
            authResponse.setRol(user.getRol());
            authResponse.setCreationDate(new Date(now));
            authResponse.setExpirationDate(new Date(now + EXPIRATIONTIME));

            authenticationListener.authResponseCreated(authResponse);

            String jwt = Jwts.builder().setClaims(aplicaciones).setSubject(user.getId().toString())
                    .setExpiration(authResponse.getExpirationDate())
                    .setHeaderParam("sessionId", authResponse.getSessionId()).setHeaderParam("username", username).setHeaderParam("rol", user.getRol())
                    .setHeaderParam("status", authResponse.getStatus()).setHeaderParam("clientId", user.getClientId())
                    .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
            authResponse.setAuthKey(jwt);
            return authResponse;

        } catch (Exception e) {
            throw new Exception("No se puede ingresar, aguarde unos minutos");
        }
    }
}