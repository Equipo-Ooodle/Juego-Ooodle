package co.edu.poli.ooodle.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import co.edu.poli.ooodle.modelo.Usuario;
import co.edu.poli.ooodle.servicios.Encriptacion;
import co.edu.poli.ooodle.servicios.UsuarioDAO;
import co.edu.poli.ooodle.vista.Principal;

/**
 * Controlador encargado de gestionar el registro de usuarios
 * dentro de la aplicación Ooodle.
 * <p>
 * Esta clase se encarga de validar los datos ingresados
 * por el usuario, verificar si el nombre ya existe
 * y registrar nuevos usuarios en la base de datos.
 * </p>
 */
public class RegistroController {

    /**
     * Objeto encargado de realizar operaciones
     * relacionadas con usuarios en la base de datos.
     */
    private UsuarioDAO usuarioDAO;

    /**
     * Referencia a la clase principal de la aplicación.
     */
    private Principal main;

    /**
     * Campo de texto para ingresar el nombre de usuario.
     */
    @FXML
    private TextField txtNombre;

    /**
     * Campo de texto para ingresar la contraseña.
     */
    @FXML
    private PasswordField txtContrasena;

    /**
     * Establece la referencia a la ventana principal.
     *
     * @param main instancia principal de la aplicación
     */
    public void setMain(Principal main) {
        this.main = main;
    }

    /**
     * Establece el objeto DAO encargado
     * de gestionar usuarios.
     *
     * @param usuarioDAO DAO de usuarios
     */
    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    /**
     * Maneja el evento del botón de registro.
     * <p>
     * Obtiene los datos ingresados,
     * intenta registrar el usuario
     * y muestra mensajes dependiendo del resultado.
     * </p>
     */
    @FXML
    private void handleRegistrar() {

        CasosRegistro registrado = registrar(
                txtNombre.getText(),
                txtContrasena.getText()
        );

        if (registrado == CasosRegistro.NombreVacio) {
            mostrarError("Espacio de nombre vacío, ingrese un nombre");

        } else if (registrado == CasosRegistro.ContrasenaVacia) {
            mostrarError("Espacio de contraseña vacío, ingrese una contraseña");

        } else if (registrado == CasosRegistro.UsuarioYaExiste) {
            mostrarError("Este nombre de usuario ya existe.");

        } else if (registrado == CasosRegistro.RegistroExitoso) {
            mostrarInfo("Se ha registrado exitosamente");
            txtNombre.clear();
            txtContrasena.clear();
            main.mostrarInicioSesion();
        }
    }

    /**
     * Redirige al usuario hacia la pantalla
     * de inicio de sesión.
     */
    @FXML
    private void handleIrLogin() {
        main.mostrarInicioSesion();
    }

    /**
     * Realiza el proceso de registro de usuario.
     * <p>
     * Verifica que los campos no estén vacíos,
     * valida que el usuario no exista previamente
     * y guarda el nuevo usuario en la base de datos.
     * </p>
     *
     * @param nombre nombre del usuario
     * @param contraseña contraseña ingresada
     * @return resultado del registro mediante el enum {@link CasosRegistro}
     */
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
            String hash = Encriptacion.hash(contraseña);
            Usuario nuevo = new Usuario(0, nombre, hash);

            String resultado = usuarioDAO.create(nuevo);

            return resultado.equals("OK") ? CasosRegistro.RegistroExitoso : CasosRegistro.Error;

        } catch (Exception e) {
            e.printStackTrace();
            return CasosRegistro.Error;
        }
    }

    /**
     * Muestra una alerta de error al usuario.
     *
     * @param mensaje mensaje de error a mostrar
     */
    private void mostrarError(String mensaje) {
        new Alert(Alert.AlertType.ERROR, mensaje).showAndWait();
    }

    /**
     * Muestra una alerta informativa al usuario.
     *
     * @param mensaje mensaje informativo a mostrar
     */
    private void mostrarInfo(String mensaje) {
        new Alert(Alert.AlertType.INFORMATION, mensaje).showAndWait();
    }
}