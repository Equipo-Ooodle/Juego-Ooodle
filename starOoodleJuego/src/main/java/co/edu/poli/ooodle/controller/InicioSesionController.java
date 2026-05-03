package co.edu.poli.ooodle.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.mindrot.jbcrypt.BCrypt;

import co.edu.poli.ooodle.modelo.Usuario;
import co.edu.poli.ooodle.servicios.UsuarioDAO;
import co.edu.poli.ooodle.vista.Principal;

public class InicioSesionController {

    private UsuarioDAO usuarioDAO;
    private Principal main;

    @FXML
    private TextField txtNombre;

    @FXML
    private PasswordField txtContrasena;

    public void setMain(Principal main) {
        this.main = main;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

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

    @FXML
    private void handleIrRegistro() {
        main.mostrarRegistro();
    }

    // 🔽 TU LÓGICA ORIGINAL (intacta)
    public Usuario autenticar(String nombre, String contraseña) {

        Usuario u = usuarioDAO.buscarPorNombre(nombre);

        if (u != null && BCrypt.checkpw(contraseña, u.getContraseña())) {
            return u;
        }

        return null;
    }

    private void mostrarError(String mensaje) {
        new Alert(Alert.AlertType.ERROR, mensaje).showAndWait();
    }

    private void mostrarInfo(String mensaje) {
        new Alert(Alert.AlertType.INFORMATION, mensaje).showAndWait();
    }
}