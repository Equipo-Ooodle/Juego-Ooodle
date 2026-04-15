package co.edu.poli.ooodle.vista;

import co.edu.poli.ooodle.servicios.PartidaDAO;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MenuVista {

    private VBox view;

    public MenuVista(Principal main) {

        Label titulo = new Label("Menú Principal");

        Button practica = new Button("Modo práctica");
        Button diaria = new Button("Partida diaria");
        Button historial = new Button("Historial de partidas");
        Button cerrarSesion = new Button("Cerrar sesión");

        practica.setOnAction(e -> {
            main.mostrarPartida("practica");
        });

        diaria.setOnAction(e -> {
            main.mostrarPartida("diaria");
        });

        historial.setOnAction(e -> {

            PartidaDAO partidaDao = main.getPartidaDao();

            Alert historialPopup = new Alert(
                    Alert.AlertType.INFORMATION
            );

            historialPopup.setTitle("Historial");
            historialPopup.setHeaderText("Historial de partidas");

            historialPopup.setContentText(
                    partidaDao.obtenerHistorial()
            );

            historialPopup.showAndWait();
        });

        cerrarSesion.setOnAction(e -> {
            main.mostrarInicioSesion();
        });

        view = new VBox(
                10,
                titulo,
                practica,
                diaria,
                historial,
                cerrarSesion
        );
    }

    public VBox getView() {
        return view;
    }
}

