package co.edu.poli.ooodle.controller;

import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import co.edu.poli.ooodle.modelo.Partida;
import co.edu.poli.ooodle.modelo.Usuario;
import co.edu.poli.ooodle.servicios.PartidaDAO;
import co.edu.poli.ooodle.vista.Principal;

public class PartidaController {

    private Partida logica;
    private PartidaDAO partidaDAO;
    private Usuario usuario;
    private String modo;
    private Principal main;

    private Map<Integer, Button> mapaBotones = new HashMap<>();
    private Set<Integer> numerosBloqueados = new HashSet<>();

    // 🔽 FXML
    @FXML private Label lblEcuacion;
    @FXML private Label lblIntentos;
    @FXML private Label lblEstado;
    @FXML private VBox contenedorTablero;
    @FXML private Button btnReintentar;

    // 🔢 teclado
    @FXML private Button btn1, btn2, btn3, btn4, btn5, btn6,
                         btn7, btn8, btn9, btn10, btn11, btn12;

    // 🧠 TABLERO
    private List<List<TextField>> tablero = new ArrayList<>();
    private int filaActual = 0;
    private final int MAX_FILAS = 6;
    private final int COLUMNAS = 4;

    // 🔧 SETTERS
    public void setMain(Principal main) {
        this.main = main;
    }

    public void inicializar(PartidaDAO dao, Usuario usuario, String modo) {
        this.partidaDAO = dao;
        this.usuario = usuario;
        this.modo = modo;
        this.logica = new Partida();
    }

    @FXML
    public void initialize() {
        mapaBotones.put(1, btn1);
        mapaBotones.put(2, btn2);
        mapaBotones.put(3, btn3);
        mapaBotones.put(4, btn4);
        mapaBotones.put(5, btn5);
        mapaBotones.put(6, btn6);
        mapaBotones.put(7, btn7);
        mapaBotones.put(8, btn8);
        mapaBotones.put(9, btn9);
        mapaBotones.put(10, btn10);
        mapaBotones.put(11, btn11);
        mapaBotones.put(12, btn12);
    }

    // 🔥 TABLERO DINÁMICO
    private void crearTablero() {

        contenedorTablero.getChildren().clear();
        tablero.clear();

        List<String> ops = logica.getOperadores();
        int resultado = logica.calcularResultado();

        for (int i = 0; i < MAX_FILAS; i++) {

            HBox fila = new HBox(10);
            fila.setStyle("-fx-alignment: center;");

            List<TextField> camposFila = new ArrayList<>();

            for (int j = 0; j < COLUMNAS; j++) {

                TextField campo = new TextField();
                campo.setPrefSize(75, 75);
                campo.getStyleClass().add("campo");

                campo.setEditable(true);
                campo.setFocusTraversable(false);

                camposFila.add(campo);
                fila.getChildren().add(campo);

                if (j < COLUMNAS - 1) {
                    Label op = new Label(ops.get(j));
                    op.getStyleClass().add("operador");
                    fila.getChildren().add(op);
                }
            }

            Label igual = new Label("=");
            igual.getStyleClass().add("operador");

            Label res = new Label(String.valueOf(resultado));
            res.getStyleClass().add("resultado");

            fila.getChildren().addAll(igual, res);

            tablero.add(camposFila);
            contenedorTablero.getChildren().add(fila);
        }
    }

    public void cargarDatos() {
        crearTablero();
        lblEcuacion.setText(logica.getEcuacionVisible());
        lblIntentos.setText("Intento 1 de " + MAX_FILAS);
    }

    // 🔢 ESCRIBIR
    private void escribirNumero(String numero) {

        List<TextField> fila = tablero.get(filaActual);

        for (TextField campo : fila) {
            if (campo.getText().isEmpty()) {
                campo.setText(numero);
                return;
            }
        }
    }

    @FXML
    private void handleNumero(ActionEvent e) {
        Button btn = (Button) e.getSource();
        escribirNumero(btn.getText());
    }

    // ❌ DELETE
    @FXML
    private void handleDelete() {

        List<TextField> fila = tablero.get(filaActual);

        for (int i = fila.size() - 1; i >= 0; i--) {
            TextField campo = fila.get(i);

            if (!campo.getText().isEmpty()) {
                campo.clear();
                return;
            }
        }
    }

    // 🎨 TECLADO
    private void pintarBoton(int numero, String estado) {

        Button btn = mapaBotones.get(numero);
        if (btn == null) return;

        btn.getStyleClass().removeAll("tecla-verde", "tecla-amarillo", "tecla-gris");

        switch (estado) {

            case "VERDE":
                btn.getStyleClass().add("tecla-verde");
                break;

            case "AMARILLO":
                btn.getStyleClass().add("tecla-amarillo");
                break;

            case "GRIS":
                btn.getStyleClass().add("tecla-gris");
                btn.setDisable(true);
                numerosBloqueados.add(numero);
                break;
        }
    }

    // 🎯 INTENTO
    @FXML
    private void handleIntentar() {

        List<TextField> fila = tablero.get(filaActual);
        Set<Integer> usados = new HashSet<>();

        // VALIDACIÓN
        for (TextField campo : fila) {

            try {
                int valor = Integer.parseInt(campo.getText());

                if (usados.contains(valor)) {
                    lblEstado.setText("No puedes repetir números");
                    return;
                }

                if (numerosBloqueados.contains(valor)) {
                    lblEstado.setText("Número bloqueado: " + valor);
                    return;
                }

                usados.add(valor);

            } catch (Exception e) {
                lblEstado.setText("Completa la fila");
                return;
            }
        }

        boolean todosVerdes = true;

        for (int i = 0; i < COLUMNAS; i++) {

            int valor = Integer.parseInt(fila.get(i).getText());
            String resultado = logica.validarNumero(i, valor);

            pintarBoton(valor, resultado);

            TextField campo = fila.get(i);

            campo.getStyleClass().removeAll("verde", "amarillo", "gris");

            switch (resultado) {

                case "VERDE":
                    campo.getStyleClass().add("verde");
                    campo.setEditable(false);
                    break;

                case "AMARILLO":
                    campo.getStyleClass().add("amarillo");
                    todosVerdes = false;
                    break;

                default:
                    campo.getStyleClass().add("gris");
                    todosVerdes = false;
            }
        }

        logica.sumarIntento();

        if (todosVerdes) {
            lblEstado.setText("GANASTE");
            guardarSiEsDiaria("GANADA");
            btnReintentar.setVisible(true);
            return;
        }

        filaActual++;

        if (filaActual >= MAX_FILAS) {
            lblEstado.setText("PERDISTE - " + logica.getSolucion());
            guardarSiEsDiaria("PERDIDA");
            btnReintentar.setVisible(true);
            return;
        }

        lblIntentos.setText("Intento " + (filaActual + 1) + " de " + MAX_FILAS);
    }

    // 💾 GUARDAR
    private void guardarSiEsDiaria(String resultado) {

        if (!"diaria".equalsIgnoreCase(modo)) return;

        try {
            logica.setUsuario(usuario);
            logica.setResultado(resultado);
            partidaDAO.create(logica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔙 MENU
    @FXML
    private void handleMenu() {
        main.mostrarMenu();
    }

    // 🔄 RESTART
    @FXML
    private void handleReintentar() {
        main.mostrarPartida(modo);
    }
}