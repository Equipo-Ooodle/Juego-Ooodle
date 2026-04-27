package co.edu.poli.ooodle.vista;



import co.edu.poli.ooodle.controller.InicioSesionController;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class InicioSesionVista {

    private VBox view;

    public InicioSesionVista(Principal main) {
    	
    	InicioSesionController controller = new InicioSesionController(main.getUsuarioDAO());

        TextField nombre = new TextField();
        nombre.setPromptText("Nombre");

        PasswordField contraseña = new PasswordField();
        contraseña.setPromptText("Contraseña");

        Button login = new Button("Iniciar Sesión");
        Button volver = new Button("Registrarse");
        
        

        login.setOnAction(e -> {

            int id = controller.obtenerId(
                    nombre.getText(),
                    contraseña.getText()
            );

            if (id != -1) {

                main.setUsuarioActualId(id); // 👈 AQUÍ está la clave

                Alert ok = new Alert(Alert.AlertType.INFORMATION,
                        "Bienvenido/a " + nombre.getText());
                ok.showAndWait();

                main.mostrarMenu();

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
