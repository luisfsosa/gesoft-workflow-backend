package test;

import java.util.LinkedHashMap;
import java.util.Map;

public class UsuarioCliente{
    private String cliente;
    private Map<String, Boolean> permisos = new LinkedHashMap<>();

    public UsuarioCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Map<String, Boolean> getPermisos() {
        return permisos;
    }

    public void setPermisos(Map<String, Boolean> permisos) {
        this.permisos = permisos;
    }
}
