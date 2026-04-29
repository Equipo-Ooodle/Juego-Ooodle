package co.edu.poli.ooodle.controller;

import org.mindrot.jbcrypt.BCrypt;
import co.edu.poli.ooodle.modelo.Usuario;
import co.edu.poli.ooodle.servicios.UsuarioDAO;

public class InicioSesionController {

    private UsuarioDAO usuarioDAO;

    public InicioSesionController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Usuario autenticar(String nombre, String contraseña) {

        Usuario u = usuarioDAO.buscarPorNombre(nombre);

        if (u != null && BCrypt.checkpw(contraseña, u.getContraseña())) {
            return u;
        }

        return null;
    }
}