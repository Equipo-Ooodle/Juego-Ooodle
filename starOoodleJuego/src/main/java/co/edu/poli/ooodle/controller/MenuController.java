package co.edu.poli.ooodle.controller;

import java.util.List;

import co.edu.poli.ooodle.modelo.Partida;
import co.edu.poli.ooodle.servicios.PartidaDAO;

public class MenuController {

    private PartidaDAO partidaDAO;

    public MenuController(PartidaDAO partidaDAO) {
        this.partidaDAO = partidaDAO;
    }

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
