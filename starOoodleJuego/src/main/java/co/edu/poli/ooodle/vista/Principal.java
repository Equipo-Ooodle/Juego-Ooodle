package co.edu.poli.ooodle.vista;

import co.edu.poli.ooodle.controller.InicioSesionController;
import co.edu.poli.ooodle.controller.MenuController;
import co.edu.poli.ooodle.controller.PartidaController;
import co.edu.poli.ooodle.controller.RegistroController;
import co.edu.poli.ooodle.modelo.Usuario;
import co.edu.poli.ooodle.servicios.PartidaDAO;
import co.edu.poli.ooodle.servicios.UsuarioDAO;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Principal extends Application {

    private Stage stage;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private PartidaDAO partidaDao = new PartidaDAO();
    private Usuario usuarioActual;


    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public PartidaDAO getPartidaDao() {
        return partidaDao;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        mostrarRegistro(); 
    }

    
    public void mostrarRegistro() {

        RegistroController controller =
                new RegistroController(usuarioDAO);

        RegistroVista vista =
                new RegistroVista(this, controller);

        stage.setScene(new Scene(vista.getView(), 400, 300));
        stage.setTitle("Registro");
        stage.show();
    }

    
    public void mostrarInicioSesion() {

        InicioSesionController controller =
                new InicioSesionController(usuarioDAO);

        InicioSesionVista vista =
                new InicioSesionVista(this, controller);

        stage.setScene(new Scene(vista.getView(), 400, 300));
        stage.setTitle("Login");
        stage.show();
    }

    
    public void mostrarMenu() {

        MenuController controller =
            new MenuController(partidaDao);

        MenuVista menu =
            new MenuVista(this, controller);

        stage.setScene(new Scene(menu.getView(), 500, 350));
        stage.setTitle("Menú Principal");
    }

    
    public void mostrarPartida(String modo) {

        PartidaController controller =
            new PartidaController(partidaDao, usuarioActual, modo);

        PartidaVista vista =
            new PartidaVista(this, controller, modo);

        vista.mostrar(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}