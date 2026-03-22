package co.edu.poli.ooodle.servicios;

import java.util.ArrayList;
import java.util.List;

import co.edu.poli.ooodle.modelo.Usuario;

public class UsuarioDAO {

    private List<Usuario> usuarios = new ArrayList<>();

    public boolean registrarUsuario(String nombre, String contraseña) {
        if (existeUsuario(nombre)) {
            return false;
        }

        usuarios.add(new Usuario(nombre, contraseña));
        return true;
    }

    public boolean existeUsuario(String nombre) {
        for (Usuario u : usuarios) {
            if (u.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    public boolean validarLogin(String nombre, String contraseña) {
        for (Usuario u : usuarios) {
            if (u.getNombre().equalsIgnoreCase(nombre)
                    && u.getContraseña().equals(contraseña)) {
                return true;
            }
        }
        return false;
    }
}