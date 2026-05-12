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

/**
 * Clase principal de la aplicación Ooodle.
 * <p>
 * Esta clase se encarga de iniciar la aplicación JavaFX y gestionar
 * la navegación entre las diferentes vistas del sistema:
 * registro, inicio de sesión, menú principal y partida.
 * </p>
 * 
 * También mantiene la referencia del usuario actualmente autenticado
 * y las instancias de acceso a datos necesarias para la aplicación.
 * 
 * @author Mia
 */
public class Principal extends Application {

    /**
     * Ventana principal de la aplicación.
     */
    private Stage stage;

    /**
     * Objeto de acceso a datos de usuarios.
     */
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Objeto de acceso a datos de partidas.
     */
    private PartidaDAO partidaDAO = new PartidaDAO();

    /**
     * Usuario actualmente autenticado en el sistema.
     */
    private Usuario usuarioActual;

    /**
     * Obtiene el usuario actualmente autenticado.
     * 
     * @return usuario actual del sistema
     */
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Establece el usuario actualmente autenticado.
     * 
     * @param usuario usuario que inició sesión
     */
    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    /**
     * Método inicial de JavaFX.
     * <p>
     * Configura el escenario principal y muestra la ventana
     * de registro al iniciar la aplicación.
     * </p>
     * 
     * @param primaryStage escenario principal de JavaFX
     */
    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        mostrarRegistro(); // o mostrarInicioSesion()
    }

    /**
     * Muestra la vista de registro de usuarios.
     * <p>
     * Carga el archivo FXML correspondiente y configura
     * el controlador asociado.
     * </p>
     */
    public void mostrarRegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/co/edu/poli/ooodle/vista/registro.fxml")
            );

            Parent root = loader.load();

            RegistroController controller = loader.getController();
            controller.setMain(this);
            controller.setUsuarioDAO(usuarioDAO);

            stage.setScene(new Scene(root, 500, 350));
            stage.setTitle("Registro");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Muestra la vista de inicio de sesión.
     * <p>
     * Carga el archivo FXML correspondiente y configura
     * el controlador asociado.
     * </p>
     */
    public void mostrarInicioSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/co/edu/poli/ooodle/vista/inicioSesion.fxml")
            );

            Parent root = loader.load();

            InicioSesionController controller = loader.getController();
            controller.setMain(this);
            controller.setUsuarioDAO(usuarioDAO);

            stage.setScene(new Scene(root, 500, 350));
            stage.setTitle("Login");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Muestra el menú principal del juego.
     * <p>
     * Configura el controlador del menú y carga
     * la interfaz correspondiente.
     * </p>
     */
    public void mostrarMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/co/edu/poli/ooodle/vista/menu.fxml")
            );

            Parent root = loader.load();

            MenuController controller = loader.getController();
            controller.setMain(this);
            controller.setPartidaDAO(partidaDAO);

            stage.setScene(new Scene(root, 500, 350));
            stage.setTitle("Menú Principal");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Muestra la vista de partida del juego.
     * <p>
     * Inicializa el controlador de partida con el usuario
     * actual, el modo de juego seleccionado y el DAO de partidas.
     * </p>
     * 
     * @param modo modo de juego seleccionado
     */
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

    /**
     * Método principal de ejecución de la aplicación.
     * 
     * @param args argumentos de ejecución
     */
    public static void main(String[] args) {
        launch(args);
    }
}