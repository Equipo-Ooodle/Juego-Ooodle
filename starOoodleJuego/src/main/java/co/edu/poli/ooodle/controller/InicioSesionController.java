package co.edu.poli.ooodle.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import co.edu.poli.ooodle.modelo.Usuario;
import co.edu.poli.ooodle.servicios.Encriptacion;
import co.edu.poli.ooodle.servicios.UsuarioDAO;
import co.edu.poli.ooodle.vista.Principal;

/**
 * Controlador encargado del inicio de sesión de los usuarios.
 * <p>
 * Esta clase gestiona la autenticación de usuarios mediante la
 * verificación de credenciales ingresadas en la interfaz gráfica.
 * También permite navegar hacia la ventana de registro.
 * </p>
 * 
 * Utiliza:
 * <ul>
 *     <li>{@link UsuarioDAO} para consultar usuarios en la base de datos.</li>
 *     <li>{@link Encriptacion} para verificar contraseñas cifradas.</li>
 *     <li>{@link Principal} para cambiar entre vistas.</li>
 * </ul>
 * 
 * @author Mia
 */
public class InicioSesionController {

    /**
     * Objeto de acceso a datos para operaciones relacionadas con usuarios.
     */
    private UsuarioDAO usuarioDAO;

    /**
     * Referencia a la clase principal de la aplicación.
     */
    private Principal main;

    /**
     * Campo de texto donde el usuario ingresa su nombre.
     */
    @FXML
    private TextField txtNombre;

    /**
     * Campo de contraseña donde el usuario ingresa su clave.
     */
    @FXML
    private PasswordField txtContrasena;

    /**
     * Establece la referencia a la clase principal.
     *
     * @param main instancia principal de la aplicación.
     */
    public void setMain(Principal main) {
        this.main = main;
    }

    /**
     * Establece el DAO de usuarios.
     *
     * @param usuarioDAO objeto encargado de gestionar usuarios en la BD.
     */
    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    /**
     * Maneja el evento de inicio de sesión.
     * <p>
     * Obtiene los datos ingresados por el usuario, intenta autenticarlos
     * y, dependiendo del resultado, permite el acceso o muestra un error.
     * </p>
     */
    @FXML
    private void handleLogin() {

        Usuario usuario = autenticar(
                txtNombre.getText(),
                txtContrasena.getText()
        );

        if (usuario != null) {

            main.setUsuarioActual(usuario);

            mostrarInfo("Bienvenido/a " + usuario.getNombre());

            main.mostrarMenu();

        } else {
            mostrarError("Usuario o contraseña incorrectos");
        }
    }

    /**
     * Navega hacia la vista de registro de usuarios.
     */
    @FXML
    private void handleIrRegistro() {
        main.mostrarRegistro();
    }

    /**
     * Autentica un usuario verificando nombre y contraseña.
     *
     * @param nombre nombre del usuario ingresado.
     * @param contraseña contraseña ingresada por el usuario.
     * @return el objeto {@link Usuario} autenticado si las credenciales
     *         son correctas; de lo contrario, retorna {@code null}.
     */
    public Usuario autenticar(String nombre, String contraseña) {

        Usuario u = usuarioDAO.buscarPorNombre(nombre);

        if (u != null && Encriptacion.verificar(contraseña, u.getContraseña())) {
            return u;
        }

        return null;
    }

    /**
     * Muestra una alerta de error.
     *
     * @param mensaje mensaje que será mostrado al usuario.
     */
    private void mostrarError(String mensaje) {
        new Alert(Alert.AlertType.ERROR, mensaje).showAndWait();
    }

    /**
     * Muestra una alerta informativa.
     *
     * @param mensaje mensaje que será mostrado al usuario.
     */
    private void mostrarInfo(String mensaje) {
        new Alert(Alert.AlertType.INFORMATION, mensaje).showAndWait();
    }
}