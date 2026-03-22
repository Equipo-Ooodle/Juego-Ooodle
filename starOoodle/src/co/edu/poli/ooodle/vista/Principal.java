package co.edu.poli.ooodle.vista;

import java.io.*;
import java.util.*;

import co.edu.poli.ooodle.servicios.UsuarioDAO;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        RegistroVista registro = new RegistroVista(this);
        stage.setScene(new Scene(registro.getView(), 400, 300));
        stage.setTitle("Registro");
        stage.show();
    }

    public void mostrarInicioSesion() {
        InicioSesionVista login = new InicioSesionVista(this);
        stage.setScene(new Scene(login.getView(), 400, 300));
        stage.setTitle("Login");
    }

    public static void main(String[] args) {
        launch(args);
    }
}