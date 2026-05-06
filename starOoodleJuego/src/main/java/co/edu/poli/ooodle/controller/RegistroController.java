package co.edu.poli.ooodle.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.mindrot.jbcrypt.BCrypt;

import co.edu.poli.ooodle.modelo.Usuario;
import co.edu.poli.ooodle.servicios.Encriptacion;
import co.edu.poli.ooodle.servicios.UsuarioDAO;
import co.edu.poli.ooodle.vista.Principal;

public class RegistroController {

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

    @FXML
    private void handleIrLogin() {
        main.mostrarInicioSesion();
    }

    // 🔽 TU LÓGICA ORIGINAL (NO SE TOCA)
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

    private void mostrarError(String mensaje) {
        new Alert(Alert.AlertType.ERROR, mensaje).showAndWait();
    }

    private void mostrarInfo(String mensaje) {
        new Alert(Alert.AlertType.INFORMATION, mensaje).showAndWait();
    }
}
