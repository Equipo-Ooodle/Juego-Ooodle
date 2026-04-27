package co.edu.poli.ooodle.vista;


import co.edu.poli.ooodle.servicios.PartidaDAO;
import co.edu.poli.ooodle.servicios.UsuarioDAO;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Principal extends Application {

    private Stage stage;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private PartidaDAO partidaDao = new PartidaDAO();
    private int usuarioActualId;

    public void setUsuarioActualId(int id) {
        this.usuarioActualId = id;
    }

    public int getUsuarioActualId() {
        return usuarioActualId;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }
    
    public PartidaDAO getPartidaDao() {
        return partidaDao;
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
    
    public void mostrarMenu() {
        MenuVista menu = new MenuVista(this);
        stage.setScene(new Scene(menu.getView(), 500, 350));
        stage.setTitle("Menú Principal");
    }
    
    public void mostrarPartida(String modo) {
        PartidaVista partida = new PartidaVista(this, modo);
        partida.mostrar(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}