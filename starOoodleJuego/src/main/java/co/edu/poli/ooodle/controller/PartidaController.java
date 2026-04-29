package co.edu.poli.ooodle.controller;

import java.util.HashSet;
import java.util.Set;

import co.edu.poli.ooodle.modelo.Partida;
import co.edu.poli.ooodle.modelo.Usuario;
import co.edu.poli.ooodle.servicios.PartidaDAO;

public class PartidaController {

    private Partida logica;
    private PartidaDAO partidaDAO;
    private Usuario usuario;
    private String modo;

    private Set<Integer> numerosBloqueados = new HashSet<>();

    public PartidaController(PartidaDAO partidaDAO, Usuario usuario, String modo) {
        this.partidaDAO = partidaDAO;
        this.usuario = usuario;
        this.modo = modo;
        this.logica = new Partida();
    }

    // 🔹 UI
    public String getEcuacion() {
        return logica.getEcuacionVisible();
    }

    public int getIntentos() {
        return logica.getIntentos();
    }

    public int getMaxIntentos() {
        return logica.getMaxIntentos();
    }

    // 🔹 reglas
    public boolean numeroBloqueado(int valor) {
        return numerosBloqueados.contains(valor);
    }

    public String validarNumero(int posicion, int valor) {

        String resultado = logica.validarNumero(posicion, valor);

        if (resultado.equals("GRIS")) {
            numerosBloqueados.add(valor);
        }

        return resultado;
    }

    public void sumarIntento() {
        logica.sumarIntento();
    }

    public boolean gano(boolean todosVerdes) {
        return todosVerdes;
    }

    public boolean perdio() {
        return logica.getIntentos() >= logica.getMaxIntentos();
    }

    public String getSolucion() {
        return logica.getSolucion().toString();
    }

    // 🔥 persistencia SOLO aquí
    public void guardarSiEsDiaria(String resultado) {

        if (!modo.equals("diaria")) return;

        try {
            logica.setUsuario(usuario);
            logica.setResultado(resultado);

            partidaDAO.create(logica);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
