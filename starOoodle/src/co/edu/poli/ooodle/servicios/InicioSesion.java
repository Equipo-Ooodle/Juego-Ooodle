package co.edu.poli.ooodle.servicios;

import java.io.*;
import java.util.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import co.edu.poli.ooodle.vista.Principal;
import co.edu.poli.ooodle.modelo.UsuarioDAO;

public class InicioSesion {

    private VBox view;

    public InicioSesion(Principal main) {

        UsuarioDAO usuarioDAO = main.getUsuarioDAO();

        TextField nombre = new TextField();
        nombre.setPromptText("Nombre");

        PasswordField contraseña = new PasswordField();
        contraseña.setPromptText("Contraseña");

        Button login = new Button("Iniciar Sesión");
        Button volver = new Button("Registrarse");

        login.setOnAction(e -> {

            boolean valido = usuarioDAO.validarLogin(
                    nombre.getText(),
                    contraseña.getText()
            );

            if (valido) {
                Alert ok = new Alert(Alert.AlertType.INFORMATION,
                        "Bienvenido/a " + nombre.getText());
                ok.showAndWait();
            } else {
                Alert error = new Alert(Alert.AlertType.ERROR,
                        "Usuario o contraseña incorrectos");
                error.showAndWait();
            }
        });

        volver.setOnAction(e -> main.mostrarRegistro());

        view = new VBox(10, nombre, contraseña, login, volver);
    }

    public VBox getView() {
        return view;
    }
}
