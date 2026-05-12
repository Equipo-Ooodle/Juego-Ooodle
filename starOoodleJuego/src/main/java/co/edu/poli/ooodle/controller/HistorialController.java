package co.edu.poli.ooodle.controller;

import java.util.List;

import co.edu.poli.ooodle.modelo.Partida;
import co.edu.poli.ooodle.servicios.PartidaDAO;

/**
 * Controlador encargado de gestionar
 * el historial de partidas de un usuario.
 * 
 * Obtiene las partidas almacenadas en la base
 * de datos y genera un historial en formato texto.
 * 
 * @author Mia
 * @version 1.0
 */
public class HistorialController {

    /**
     * Objeto de acceso a datos de partidas.
     */
    private PartidaDAO partidaDAO;

    /**
     * Constructor del controlador de historial.
     * 
     * @param partidaDAO DAO utilizado para consultar partidas
     */
    public HistorialController(PartidaDAO partidaDAO) {
        this.partidaDAO = partidaDAO;
    }

    /**
     * Obtiene el historial de partidas de un usuario.
     * 
     * @param usuarioId identificador del usuario
     * @return historial de partidas en formato texto
     */
    public String obtenerHistorial(int usuarioId) {

        List<Partida> lista = partidaDAO.obtenerPorUsuario(usuarioId);

        if (lista.isEmpty()) {
            return "No hay partidas registradas";
        }

        StringBuilder historial = new StringBuilder();

        for (Partida p : lista) {
            historial.append("Resultado: ")
                    .append(p.getResultado())
                    .append(" | Intentos: ")
                    .append(p.getIntentos())
                    .append(" | Solución: ")
                    .append(p.getSolucion())
                    .append("\n");
        }

        return historial.toString();
    }
}