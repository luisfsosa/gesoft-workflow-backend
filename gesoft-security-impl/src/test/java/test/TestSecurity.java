package test;


import cl.com.gesoft.security.api.GesoftACLException;
import cl.com.gesoft.security.api.GesoftAcl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestSecurityConfig.class})
public class TestSecurity {
    @Autowired
    ServiceTest serviceTest;


    @Test
    public void testAnnotations() throws NoSuchMethodException {
        Method method = TestSecurity.class.getMethod("testAnnotations", new Class[]{});
        final GesoftAcl quoACL = AnnotationUtils.findAnnotation(method, GesoftAcl.class);
        System.out.println(quoACL);
    }


    @Test(expected = GesoftACLException.class)
    public void checkPermission() {
        System.out.println("Check!");
        login("ingeneo");
        System.out.println(serviceTest.obtenerRFC("pricemark"));
    }

    @Test
    public void checkValidPermission() {
        System.out.println("Check!");
        login("pricemark");
        System.out.println(serviceTest.obtenerRFC("pricemark"));
    }

    @Test
    public void checkSuperUser() {
        System.out.println("Check!");
        login("superuser");
        System.out.println(serviceTest.obtenerRFC("pricemark"));
    }

    @Test(expected = GesoftACLException.class)
    public void checkNoSuperUser() {
        System.out.println("Check!");
        login("pricemark");
        System.out.println(serviceTest.obtenerRFC("pricemark"));
    }

    @Test
    public void aprobarAsientoDePriceMark() {
        System.out.println("Check!");
        login("pricemark");
        System.out.println(serviceTest.aprobarAsiento("pricemark"));
    }

    @Test(expected = GesoftACLException.class)
    public void superUserApruebaAsientoDePriceMark() {
        System.out.println("Check!");
        login("superuser");
        System.out.println(serviceTest.aprobarAsiento("pricemark"));
    }


    @Test
    public void aprobarAsientoNumeroDePriceMark() {
        System.out.println("Check!");
        login("pricemark");
        System.out.println(serviceTest.aprobarNumeroAsiento("pricemark",""));
    }


    Map<String,Usuario> usuarios = new LinkedHashMap<>();

    private void login(String usuario){
        SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.getContext().setAuthentication(usuarios.get(usuario));
    }

    {
        Usuario usuario = new Usuario("superuser");
        //roles
        usuario.getRoles().put("usuario","Usuario");
        usuario.getRoles().put("superuser","Usuario");


        usuario.getRoles().put("lectura","Lectura");
        //permisos
        usuario.getPermisos().put("aprobarRFC",true);
        //clientes
        {
            UsuarioCliente usuarioCliente = new UsuarioCliente("walmart");
            usuario.getClientes().put(usuarioCliente.getCliente(),usuarioCliente);
        }
        usuarios.put(usuario.getUsername(),usuario);
    }

    {
        Usuario usuario = new Usuario("pricemark");
        //roles
        usuario.getRoles().put("usuario","Usuario");
//        usuario.getRoles().put("superuser","Usuario");


        usuario.getRoles().put("lectura","Lectura");
        //permisos
        usuario.getPermisos().put("aprobarRFC",true);
        //clientes

        {
            UsuarioCliente usuarioCliente = new UsuarioCliente("pricemark");
            usuarioCliente.getPermisos().put("ServiceTest.aprobarAsiento",true);
            usuario.getClientes().put(usuarioCliente.getCliente(),usuarioCliente);
        }
        {
            UsuarioCliente usuarioCliente = new UsuarioCliente("youtube");
            usuario.getClientes().put(usuarioCliente.getCliente(),usuarioCliente);
        }

        usuarios.put(usuario.getUsername(),usuario);
    }



}
