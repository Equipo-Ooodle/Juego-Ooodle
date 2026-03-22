package co.edu.poli.ooodle.vista;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import co.edu.poli.ooodle.controller.RegistroController;
import co.edu.poli.ooodle.modelo.CasosRegistro;

public class RegistroVista {
    
	private VBox view;

	public RegistroVista(Principal main) {
		
		RegistroController controller =
		        new RegistroController(main.getUsuarioDAO());

	    TextField nombre = new TextField();
	    nombre.setPromptText("Nombre");

	    PasswordField contraseña = new PasswordField();
	    contraseña.setPromptText("Contraseña");

	    Button registrar = new Button("Registrar");
	    Button irLogin = new Button("¿Ya tienes cuenta? Inicia sesión");

	    registrar.setOnAction(e -> {

	        CasosRegistro registrado = controller.registrar(
	                nombre.getText(),
	                contraseña.getText()
	        );

	        if (registrado == CasosRegistro.NombreVacio) {
	            mostrarError("Espacio de nombre vacío, ingrese un nombre");
	            
	        } 
	        
	        else if (registrado == CasosRegistro.ContrasenaVacia){
	            mostrarError("Espacio de contraseña vacío, ingrese una contraseña");
	        }
	        
	        else if (registrado == CasosRegistro.UsuarioYaExiste) {
	        	mostrarError("Este nombre de usuario ya existe. Por favor, seleccione uno nuevo");
	        }
	        
	        else if (registrado == CasosRegistro.RegistroExitoso) {
	        	mostrarInfo("Se ha registrado exitosamente");
	        	nombre.clear();
	        	contraseña.clear();
	        	main.mostrarInicioSesion();
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