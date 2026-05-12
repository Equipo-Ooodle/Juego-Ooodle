package co.edu.poli.ooodle.controller;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import co.edu.poli.ooodle.modelo.Partida;
import co.edu.poli.ooodle.servicios.PartidaDAO;
import co.edu.poli.ooodle.vista.Principal;

/**
 * Controlador encargado de gestionar las acciones del menú principal.
 * <p>
 * Esta clase permite al usuario:
 * </p>
 * <ul>
 *     <li>Iniciar una partida en modo práctica.</li>
 *     <li>Iniciar una partida diaria.</li>
 *     <li>Consultar el historial de partidas.</li>
 *     <li>Cerrar sesión.</li>
 * </ul>
 * 
 * También interactúa con:
 * <ul>
 *     <li>{@link Principal} para cambiar entre vistas.</li>
 *     <li>{@link PartidaDAO} para consultar el historial de partidas.</li>
 * </ul>
 * 
 * @author Mia
 */
public class MenuController {

    /**
     * Objeto encargado del acceso a datos de partidas.
     */
    private PartidaDAO partidaDAO;

    /**
     * Referencia a la clase principal de la aplicación.
     */
    private Principal main;

    /**
     * Establece la referencia a la clase principal.
     *
     * @param main instancia principal de la aplicación.
     */
    public void setMain(Principal main) {
        this.main = main;
    }

    /**
     * Establece el DAO de partidas.
     *
     * @param partidaDAO objeto encargado de gestionar partidas en la base de datos.
     */
    public void setPartidaDAO(PartidaDAO partidaDAO) {
        this.partidaDAO = partidaDAO;
    }

    /**
     * Maneja el inicio de una partida en modo práctica.
     */
    @FXML
    private void handlePractica() {
        main.mostrarPartida("practica");
    }

    /**
     * Maneja el inicio de una partida en modo diaria.
     */
    @FXML
    private void handleDiaria() {
        main.mostrarPartida("diaria");
    }

    /**
     * Muestra el historial de partidas del usuario actual.
     * <p>
     * Obtiene la información desde la base de datos y la presenta
     * mediante una ventana emergente.
     * </p>
     */
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

    /**
     * Cierra la sesión actual y regresa a la pantalla de inicio de sesión.
     */
    @FXML
    private void handleCerrarSesion() {
        main.mostrarInicioSesion();
    }

    /**
     * Obtiene el historial de partidas de un usuario.
     *
     * @param usuarioId identificador del usuario.
     * @return una cadena con el historial de partidas registradas,
     *         o un mensaje indicando que no existen partidas.
     */
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