package test;

import cl.com.gesoft.security.api.GesoftAcl;
import cl.com.gesoft.security.api.GesoftAclParam;

public interface ServiceTest {

    @GesoftAcl(expression = "#user.enGrupo(#grupo) || #user.esUsuario('superuser')")
    public String obtenerRFC(@GesoftAclParam("grupo") String grupo);

    @GesoftAcl(expression = "#user.tienePermiso(#grupo,'ServiceTest.aprobarAsiento')")
    public String aprobarAsiento(@GesoftAclParam("grupo") String grupo);

    @GesoftAcl(expression = "#user.tienePermiso(#grupo,'ServiceTest.aprobarAsiento')")
    public String aprobarNumeroAsiento(@GesoftAclParam("grupo") String grupo, @GesoftAclParam("asiento") String numero);

    @GesoftAcl(expression = "#grupo")
    public String eliminarUsuario();

    @GesoftAcl(expression = "#grupo")
    public String cambiarPassword();


    public Boolean puedeAprobar(String numero);
}
