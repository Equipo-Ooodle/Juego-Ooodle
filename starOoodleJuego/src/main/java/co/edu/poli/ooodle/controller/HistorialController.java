package co.edu.poli.ooodle.controller;

import java.util.List;

import co.edu.poli.ooodle.modelo.Partida;
import co.edu.poli.ooodle.servicios.PartidaDAO;

public class HistorialController {

    private PartidaDAO partidaDAO;

    public HistorialController(PartidaDAO partidaDAO) {
        this.partidaDAO = partidaDAO;
    }

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