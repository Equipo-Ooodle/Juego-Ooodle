package co.edu.poli.ooodle.controller;

import org.mindrot.jbcrypt.BCrypt;

import co.edu.poli.ooodle.modelo.Usuario;
import co.edu.poli.ooodle.servicios.UsuarioDAO;

/**
 * Controlador encargado de gestionar las operaciones
 * relacionadas con usuarios en la aplicación Ooodle.
 * <p>
 * Esta clase permite registrar usuarios nuevos
 * y autenticar usuarios existentes mediante
 * verificación de contraseñas encriptadas.
 * </p>
 */
public class UsuarioController {

    /**
     * Objeto encargado de realizar operaciones
     * de acceso a datos de usuarios.
     */
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Registra un nuevo usuario en el sistema.
     * <p>
     * Verifica si el nombre de usuario ya existe.
     * Si no existe, encripta la contraseña
     * utilizando BCrypt y guarda el usuario
     * en la base de datos.
     * </p>
     *
     * @param nombre nombre del usuario
     * @param contraseña contraseña ingresada por el usuario
     * @return {@code true} si el registro fue exitoso,
     *         {@code false} en caso contrario
     */
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

    /**
     * Realiza el proceso de autenticación de un usuario.
     * <p>
     * Busca el usuario por nombre y valida
     * la contraseña utilizando BCrypt.
     * </p>
     *
     * @param nombre nombre del usuario
     * @param contraseña contraseña ingresada
     * @return objeto {@link Usuario} si las credenciales
     *         son válidas, o {@code null} si son incorrectas
     */
    public Usuario login(String nombre, String contraseña) {

        Usuario u = usuarioDAO.buscarPorNombre(nombre);

        if (u == null) return null;

        if (BCrypt.checkpw(contraseña, u.getContraseña())) {
            return u;
        }

        return null;
    }
}