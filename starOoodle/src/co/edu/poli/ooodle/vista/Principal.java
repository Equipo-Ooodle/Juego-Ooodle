package co.edu.poli.ooodle.vista;

import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import co.edu.poli.ooodle.modelo.UsuarioDAO;
import co.edu.poli.ooodle.modelo.Registrarse;
import co.edu.poli.ooodle.servicios.InicioSesion;

public class Principal extends Application {

    private Stage stage;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

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
        InicioSesion login = new InicioSesion(this);
        stage.setScene(new Scene(login.getView(), 400, 300));
        stage.setTitle("Login");
    }

    public static void main(String[] args) {
        launch(args);
    }
}