package co.edu.poli.ooodle.controller;

import java.util.logging.Logger;
import co.edu.poli.ooodle.modelo.Usuario;
import co.edu.poli.ooodle.modelo.UsuarioDAO;

public class RegistroController {
	private static final Logger logger = ErroresLog.getLogger();
    private UsuarioDAO usuarioDAO;

    public RegistroController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public boolean registrar(String nombre, String contraseña) {

        if (nombre == null || nombre.trim().isEmpty()) {
            logger.warning("Intento de registro con nombre vacío");
            return false;
        }

        if (contraseña == null || contraseña.trim().isEmpty()) {
            logger.warning("Intento de registro con contraseña vacía para usuario: " + nombre);
            return false;
        }

        boolean registrado = usuarioDAO.registrarUsuario(nombre, contraseña);

        if (!registrado) {
            logger.warning("Intento de registro con usuario ya existente: " + nombre);
        }

        return registrado;
    }
}
