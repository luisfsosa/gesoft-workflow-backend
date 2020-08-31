import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
public class JwtTest {

    @Test
    public void generateJWT() {

        final Map<String, Object> aplicaciones = new LinkedHashMap<>();
            Calendar expirationDate = Calendar.getInstance();
            expirationDate.set(2090, 12, 31);

            String secretKey= "secret";

            String jwt = Jwts.builder()
                    .setClaims(aplicaciones)
                    .setSubject("1")
                    .setExpiration(expirationDate.getTime())
                    .setHeaderParam("username", "luisfsosa@gmail.com")
                    .setHeaderParam("clienteId", 1)
                    .setHeaderParam("sessionId", 11515)
                    .setHeaderParam("rol", "SUPER_USUARIO")
                    .setHeaderParam("alg", "HS512")
                    .signWith(SignatureAlgorithm.HS512.HS512, secretKey)
                    .compact();
            System.out.println("El token es: " + jwt);


            Jws<Claims> jws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt);

            String username =  (String) jws.getHeader().get("username");
            String applicationId = jws.getBody().getSubject();
            Integer clientId = (Integer) jws.getHeader().get("clienteId");

            System.out.println("La expiración es: " + expirationDate.getTime());

            System.out.println("El usuario es: " + username);
            System.out.println("el id es: " + applicationId);
            System.out.println("El clientId es: " + clientId);

            String token= "eyJ1c2VybmFtZSI6Imx1aXNmc29zYUBnbWFpbC5jb20iLCJjbGllbnRlSWQiOjEsInNlc3Npb25JZCI6MTE1MTUsInJvbCI6IlNVUEVSX1VTVUFSSU8iLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiZXhwIjozODIxMTI2NjU2fQ.9nSD4dKSs6qXpcN3rbX9yIHApzJPop6Q3nZJXYxM4qh7IOCIpIJka-J3QCsuraq_3iCaYoO2tOQ8HTeaJQ89Rg";
            Jws<Claims> old  = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            username =  (String) old.getHeader().get("username");
            applicationId = old.getBody().getSubject();
            clientId = (Integer) jws.getHeader().get("clienteId");

            System.out.println("El usuario es:: " + username);
            System.out.println("el id es: " + applicationId);
            System.out.println("El clientId es: " + clientId);

        }

}
