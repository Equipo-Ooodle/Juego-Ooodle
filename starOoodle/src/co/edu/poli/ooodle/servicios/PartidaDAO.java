package co.edu.poli.ooodle.servicios;

import java.util.ArrayList;
import java.util.List;

import co.edu.poli.ooodle.modelo.Partida;

public class PartidaDAO {

    private final List<String> historial = new ArrayList<>();

    public boolean guardarPartida(
            Partida partida,
            String resultado
    ) {

        String registro =
                "Resultado: " + resultado +
                " | Intentos: " + partida.getIntentos() +
                " | Solución: " + partida.getSolucion();

        historial.add(registro);
        return true;
    }

    public String obtenerHistorial() {
        return String.join("\n", historial);
    }

    public int obtenerIntentos(Partida partida) {
        return partida.getIntentos();
    }

    public String obtenerMejorTiempo() {
        return "No implementado";
    }
}