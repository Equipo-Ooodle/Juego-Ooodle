package co.edu.poli.ooodle.vista;

import co.edu.poli.ooodle.controller.InicioSesionController;
import co.edu.poli.ooodle.modelo.Usuario;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class InicioSesionVista {

    private VBox view;

    public InicioSesionVista(Principal main, InicioSesionController controller) {

        TextField nombre = new TextField();
        nombre.setPromptText("Nombre");

        PasswordField contraseña = new PasswordField();
        contraseña.setPromptText("Contraseña");

        Button login = new Button("Iniciar Sesión");
        Button volver = new Button("Registrarse");

        login.setOnAction(e -> {

            Usuario usuario = controller.autenticar(
                    nombre.getText(),
                    contraseña.getText()
            );

            if (usuario != null) {

                main.setUsuarioActual(usuario);

                mostrarInfo("Bienvenido/a " + usuario.getNombre());

                main.mostrarMenu();

            } else {
                mostrarError("Usuario o contraseña incorrectos");
            }
        });

        volver.setOnAction(e -> main.mostrarRegistro());

        view = new VBox(10, nombre, contraseña, login, volver);
    }

    public VBox getView() {
        return view;
    }

    private void mostrarError(String mensaje) {
        new Alert(Alert.AlertType.ERROR, mensaje).showAndWait();
    }

    private void mostrarInfo(String mensaje) {
        new Alert(Alert.AlertType.INFORMATION, mensaje).showAndWait();
    }
}
