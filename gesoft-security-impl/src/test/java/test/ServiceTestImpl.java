package test;

public class ServiceTestImpl implements ServiceTest {


    @Override
    public String obtenerRFC(String paramName) {
        return "Vamos!";
    }

    @Override
    public String aprobarAsiento(String grupo) {
        return "Aprobar Asiento [ " + grupo + "]";
    }

    @Override
    public String aprobarNumeroAsiento(String grupo, String numero) {
        return "Aprobar Asiento [ " + grupo + "][" + numero + "]";
    }

    @Override
    public String eliminarUsuario() {
        return "Vamos!";
    }

    @Override
    public String cambiarPassword() {
        return "Vamos!";
    }

    @Override
    public Boolean puedeAprobar(String numero) {
        return null;
    }
}
