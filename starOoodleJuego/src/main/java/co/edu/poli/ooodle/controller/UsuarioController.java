package co.edu.poli.ooodle.controller;

import org.mindrot.jbcrypt.BCrypt;

import co.edu.poli.ooodle.modelo.Usuario;
import co.edu.poli.ooodle.servicios.UsuarioDAO;

public class UsuarioController {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean registrar(String nombre, String contraseña) {

        if (usuarioDAO.buscarPorNombre(nombre) != null) {
            return false;
        }

        String hash = BCrypt.hashpw(contraseña, BCrypt.gensalt(12));

        Usuario u = new Usuario(0, nombre, hash);

        try {
            usuarioDAO.create(u);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Usuario login(String nombre, String contraseña) {

        Usuario u = usuarioDAO.buscarPorNombre(nombre);

        if (u == null) return null;

        if (BCrypt.checkpw(contraseña, u.getContraseña())) {
            return u;
        }

        return null;
    }
}