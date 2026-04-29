package co.edu.poli.ooodle.controller;

import org.mindrot.jbcrypt.BCrypt;

import co.edu.poli.ooodle.modelo.Usuario;
import co.edu.poli.ooodle.servicios.UsuarioDAO;

public class RegistroController {

    private UsuarioDAO usuarioDAO;

    public RegistroController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public CasosRegistro registrar(String nombre, String contraseña) {

        if (nombre == null || nombre.trim().isEmpty()) {
            return CasosRegistro.NombreVacio;
        }

        if (contraseña == null || contraseña.trim().isEmpty()) {
            return CasosRegistro.ContrasenaVacia;
        }

        if (usuarioDAO.existeUsuario(nombre)) {
            return CasosRegistro.UsuarioYaExiste;
        }

        try {
            
            String hash = BCrypt.hashpw(contraseña, BCrypt.gensalt(12));

            Usuario nuevo = new Usuario(0, nombre, hash);

            String resultado = usuarioDAO.create(nuevo);

            if (resultado.equals("OK")) {
                return CasosRegistro.RegistroExitoso;
            } else {
                return CasosRegistro.Error;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return CasosRegistro.Error;
        }
    }
}
