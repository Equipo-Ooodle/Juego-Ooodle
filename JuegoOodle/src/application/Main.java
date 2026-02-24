package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        mostrarRegistro();
    }

    public void mostrarRegistro() {
        Registrarse registro = new Registrarse(this);
        stage.setScene(new Scene(registro.getView(), 400, 300));
        stage.setTitle("Registro");
        stage.show();
    }

    public void mostrarInicioSesion() {
        IniciarSesion login = new IniciarSesion(this);
        stage.setScene(new Scene(login.getView(), 400, 300));
        stage.setTitle("Login");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
