package co.edu.poli.ooodle.vista;

import co.edu.poli.ooodle.controller.*;
import co.edu.poli.ooodle.modelo.Usuario;
import co.edu.poli.ooodle.servicios.PartidaDAO;
import co.edu.poli.ooodle.servicios.UsuarioDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Principal extends Application {

    private Stage stage;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private PartidaDAO partidaDAO = new PartidaDAO();
    private Usuario usuarioActual;

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        mostrarRegistro(); // o mostrarInicioSesion()
    }

    // 🟣 REGISTRO
    public void mostrarRegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/co/edu/poli/ooodle/vista/registro.fxml")
            );

            Parent root = loader.load();

            RegistroController controller = loader.getController();
            controller.setMain(this);
            controller.setUsuarioDAO(usuarioDAO);

            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Registro");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔵 LOGIN
    public void mostrarInicioSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/co/edu/poli/ooodle/vista/inicioSesion.fxml")
            );

            Parent root = loader.load();

            InicioSesionController controller = loader.getController();
            controller.setMain(this);
            controller.setUsuarioDAO(usuarioDAO);

            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Login");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🟢 MENÚ
    public void mostrarMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/co/edu/poli/ooodle/vista/menu.fxml")
            );

            Parent root = loader.load();

            MenuController controller = loader.getController();
            controller.setMain(this);
            controller.setPartidaDAO(partidaDAO);

            stage.setScene(new Scene(root, 1000, 700));
            stage.setTitle("Menú Principal");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔴 PARTIDA
    public void mostrarPartida(String modo) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/co/edu/poli/ooodle/vista/partida.fxml")
            );

            Parent root = loader.load();

            PartidaController controller = loader.getController();
            controller.setMain(this);
            controller.inicializar(partidaDAO, usuarioActual, modo);
            controller.cargarDatos();

            stage.setScene(new Scene(root, 1000, 600));
            stage.setTitle("Partida");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}