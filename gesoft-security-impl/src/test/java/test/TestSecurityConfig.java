package test;

import cl.com.gesoft.security.api.GesoftAclService;
import cl.com.gesoft.security.impl.SecurityProxyImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

@Configuration
public class TestSecurityConfig {
    @Autowired
    SecurityProxyImpl securityProxy;

    @Bean
    ServiceTest createServiceTest() throws Exception {
        return securityProxy.getInstance(ServiceTest.class, new ServiceTestImpl());
    }

    @Bean
    GesoftAclService createQuoAclService() {
        return new GesoftAclService() {
            @Override
            public boolean acl(String expression, Map<String, Object> params) {
                System.out.println("Validando ACL [" + expression + "]");
                ExpressionParser parser = new SpelExpressionParser();
                StandardEvaluationContext context = new StandardEvaluationContext();

                Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication();

                if (usuario == null){
                    return false;
                }

                context.setVariable("user", new UserWrapper() {
                    public boolean enGrupo(String grupo) {
                        return usuario.getClientes().containsKey(grupo);
                    }

                    public boolean tienePermiso(String grupo, String permiso) {
                        if  (usuario.getClientes().containsKey(grupo)){
                            if (usuario.getClientes().get(grupo).getPermisos().containsKey(permiso)){
                                return usuario.getClientes().get(grupo).getPermisos().get(permiso);
                            }
                        }
                        return false;
                    }
                    public boolean esUsuario(String rol) {
                        return usuario.getRoles().containsKey(rol);
                    }

                });
                if (params != null){
                    for (String key : params.keySet()){
                        context.setVariable(key,params.get(key));
                    }
                }

                org.springframework.expression.Expression exp = parser
                        .parseExpression(expression);
                return (Boolean) exp.getValue(context);

            }

            @Override
            public String getUsername() {
                return "example";
            }
        };
    }


    @Bean
    SecurityProxyImpl createSecurityProxyImpl() {
        return new SecurityProxyImpl();
    }


    abstract class UserWrapper {

    }

}
