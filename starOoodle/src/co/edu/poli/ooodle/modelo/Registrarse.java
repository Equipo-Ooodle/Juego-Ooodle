package co.edu.poli.ooodle.modelo;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import co.edu.poli.ooodle.vista.Principal;
import co.edu.poli.ooodle.modelo.UsuarioDAO;
import co.edu.poli.ooodle.controller.RegistroController;

public class Registrarse {
    
	private VBox view;

	public Registrarse(Principal main) {
		
		RegistroController controller =
		        new RegistroController(main.getUsuarioDAO());

	    UsuarioDAO usuarioDAO = main.getUsuarioDAO();

	    TextField nombre = new TextField();
	    nombre.setPromptText("Nombre");

	    PasswordField contraseña = new PasswordField();
	    contraseña.setPromptText("Contraseña");

	    Button registrar = new Button("Registrar");
	    Button irLogin = new Button("Ir a inicio de sesión");

	    registrar.setOnAction(e -> {

	        boolean registrado = controller.registrar(
	                nombre.getText(),
	                contraseña.getText()
	        );

	        if (registrado) {
	            mostrarInfo("Usuario registrado");
	        } else {
	            mostrarError("Nombre o contraseña inválidos o usuario ya existe");
	        }
	    });

	    irLogin.setOnAction(e -> main.mostrarInicioSesion());

	    view = new VBox(10, nombre, contraseña, registrar, irLogin);
	}
	
	public VBox getView() {
	    return view;
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