package co.edu.poli.ooodle.controller;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import co.edu.poli.ooodle.modelo.Partida;
import co.edu.poli.ooodle.servicios.PartidaDAO;
import co.edu.poli.ooodle.vista.Principal;

public class MenuController {

    private PartidaDAO partidaDAO;
    private Principal main;

    public void setMain(Principal main) {
        this.main = main;
    }

    public void setPartidaDAO(PartidaDAO partidaDAO) {
        this.partidaDAO = partidaDAO;
    }

    @FXML
    private void handlePractica() {
        main.mostrarPartida("practica");
    }

    @FXML
    private void handleDiaria() {
        main.mostrarPartida("diaria");
    }

    @FXML
    private void handleHistorial() {

        String texto = obtenerHistorial(
                main.getUsuarioActual().getId()
        );

        Alert historialPopup = new Alert(Alert.AlertType.INFORMATION);
        historialPopup.setTitle("Historial");
        historialPopup.setHeaderText("Historial de partidas");
        historialPopup.setContentText(texto);

        historialPopup.showAndWait();
    }

    @FXML
    private void handleCerrarSesion() {
        main.mostrarInicioSesion();
    }

    // 🔽 TU LÓGICA ORIGINAL (intacta)
    public String obtenerHistorial(int usuarioId) {

        List<Partida> partidas = partidaDAO.obtenerPorUsuario(usuarioId);

        if (partidas.isEmpty()) {
            return "No hay partidas registradas";
        }

        StringBuilder sb = new StringBuilder();

        for (Partida p : partidas) {
            sb.append("Resultado: ")
              .append(p.getResultado())
              .append(" | Intentos: ")
              .append(p.getIntentos())
              .append(" | Solución: ")
              .append(p.getSolucion())
              .append("\n");
        }

        return sb.toString();
    }
}