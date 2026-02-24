package application;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class IniciarSesion {

    private VBox view;

    public IniciarSesion(Main main) {

        TextField nombre = new TextField();
        nombre.setPromptText("Nombre");

        PasswordField contraseña = new PasswordField();
        contraseña.setPromptText("Contraseña");

        Button login = new Button("Iniciar Sesión");
        Button volver = new Button("Volver a Registro");

        login.setOnAction(e -> {
            for (Usuario u : Registrarse.getUsuarios()) {
                if (u.getNombre().equals(nombre.getText()) &&
                    u.getContraseña().equals(contraseña.getText())) {

                    Alert ok = new Alert(Alert.AlertType.INFORMATION, "Bienvenido " + u.getNombre());
                    ok.showAndWait();
                    return;
                }
            }

            Alert error = new Alert(Alert.AlertType.ERROR, "Usuario o contraseña incorrectos");
            error.showAndWait();
        });

        volver.setOnAction(e -> main.mostrarRegistro());

        view = new VBox(10, nombre, contraseña, login, volver);
    }

    public VBox getView() {
        return view;
    }
}
