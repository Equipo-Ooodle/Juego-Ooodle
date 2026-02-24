package application;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;

public class Registrarse {
    
	private VBox view;
    private static List<Usuario> usuarios = new ArrayList<>();

    public Registrarse(Main main) {

        TextField nombre = new TextField();
        nombre.setPromptText("Nombre");

        PasswordField contraseña = new PasswordField ();
        contraseña.setPromptText("Contraseña");

        Button registrar = new Button("Registrar");
        Button irLogin = new Button("Ir a inicio de sesión");

        registrar.setOnAction(e -> {

            String nombreTexto = nombre.getText().trim();
            String passTexto = contraseña.getText();

            if (nombreTexto.isEmpty()) {
                mostrarError("El nombre no puede estar vacío");
                return;
            }

            // Aquí revisamos si el usuario ya existe
            for (Usuario u : usuarios) {
                if (u.getNombre().equalsIgnoreCase(nombreTexto)) {
                    mostrarError("Ese usuario ya existe");
                    return;
                }
            }

            // Sino existe antes, se registra
            usuarios.add(new Usuario(nombreTexto, passTexto));
            mostrarInfo("Usuario registrado");

            nombre.clear();
            contraseña.clear();
        });
        irLogin.setOnAction(e -> main.mostrarInicioSesion());

        view = new VBox(10, nombre, contraseña, registrar, irLogin);
    }

    public VBox getView() {
        return view;
    }

    public static List<Usuario> getUsuarios() {
        return usuarios;
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensaje);
        alert.showAndWait();
    }

    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mensaje);
        alert.showAndWait();
    }
}