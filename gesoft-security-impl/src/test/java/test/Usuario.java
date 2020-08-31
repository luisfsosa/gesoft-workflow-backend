package test;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Usuario implements Authentication {
    public String username;
    public Map<String, String> roles = new LinkedHashMap<>();
    public Map<String, Boolean> permisos = new LinkedHashMap<>();
    public Map<String,UsuarioCliente> clientes = new LinkedHashMap<>();

    public Usuario(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, String> getRoles() {
        return roles;
    }

    public Map<String, Boolean> getPermisos() {
        return permisos;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRoles(Map<String, String> roles) {
        this.roles = roles;
    }

    public void setPermisos(Map<String, Boolean> permisos) {
        this.permisos = permisos;
    }

    public Map<String, UsuarioCliente> getClientes() {
        return clientes;
    }

    public void setClientes(Map<String, UsuarioCliente> clientes) {
        this.clientes = clientes;
    }
}
