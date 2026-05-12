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

/**
 * Controlador encargado de gestionar la lógica visual y funcional
 * de las partidas del juego Ooodle.
 * <p>
 * Esta clase administra:
 * </p>
 * <ul>
 *     <li>La creación dinámica del tablero.</li>
 *     <li>La interacción con el teclado numérico.</li>
 *     <li>La validación de intentos.</li>
 *     <li>La actualización visual de resultados.</li>
 *     <li>El almacenamiento de partidas diarias.</li>
 *     <li>La navegación entre vistas.</li>
 * </ul>
 * 
 * Interactúa con:
 * <ul>
 *     <li>{@link Partida} para la lógica del juego.</li>
 *     <li>{@link PartidaDAO} para persistencia de datos.</li>
 *     <li>{@link Principal} para navegación entre escenas.</li>
 * </ul>
 * 
 * @author Mia
 */
public class PartidaController {

    /**
     * Lógica principal de la partida actual.
     */
    private Partida logica;

    /**
     * Objeto de acceso a datos para partidas.
     */
    private PartidaDAO partidaDAO;

    /**
     * Modo actual de juego.
     */
    private String modo;

    /**
     * Referencia a la clase principal de la aplicación.
     */
    private Principal main;

    /**
     * Mapa que relaciona números con botones del teclado.
     */
    private Map<Integer, Button> mapaBotones = new HashMap<>();

    /**
     * Conjunto de números bloqueados.
     */
    private Set<Integer> numerosBloqueados = new HashSet<>();

    // 🔽 FXML

    /**
     * Etiqueta de ecuación visible.
     */
    @FXML private Label lblEcuacion;

    /**
     * Etiqueta de intentos.
     */
    @FXML private Label lblIntentos;

    /**
     * Etiqueta de estado del juego.
     */
    @FXML private Label lblEstado;

    /**
     * Contenedor del tablero dinámico.
     */
    @FXML private VBox contenedorTablero;

    /**
     * Botón para reiniciar la partida.
     */
    @FXML private Button btnReintentar;

    // 🔢 teclado

    /**
     * Botones numéricos del teclado.
     */
    @FXML private Button btn1, btn2, btn3, btn4, btn5, btn6,
                         btn7, btn8, btn9, btn10, btn11, btn12;

    // 🧠 TABLERO

    /**
     * Representación interna del tablero.
     */
    private List<List<TextField>> tablero = new ArrayList<>();

    /**
     * Índice de la fila actual.
     */
    private int filaActual = 0;

    /**
     * Número máximo de filas permitidas.
     */
    private final int MAX_FILAS = 6;

    /**
     * Número de columnas del tablero.
     */
    private final int COLUMNAS = 4;

    /**
     * Establece la referencia principal de la aplicación.
     *
     * @param main instancia principal.
     */
    public void setMain(Principal main) {
        this.main = main;
    }

    /**
     * Inicializa la partida.
     *
     * @param dao DAO de partidas.
     * @param usuario usuario actual.
     * @param modo modo de juego.
     */
    public void inicializar(PartidaDAO dao, Usuario usuario, String modo) {
        this.partidaDAO = dao;
        this.modo = modo;
        this.logica = new Partida(usuario);
    }

    /**
     * Inicializa el teclado y los botones del juego.
     */
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

    /**
     * Crea dinámicamente el tablero de juego.
     * <p>
     * Genera filas, campos de texto, operadores y resultado visible.
     * También valida que únicamente puedan ingresarse números del 1 al 12.
     * </p>
     */
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

                campo.setEditable(i == filaActual);
                campo.setFocusTraversable(false);

                campo.textProperty().addListener((obs, oldVal, newVal) -> {

                    if (newVal.isEmpty()) return;

                    if (!newVal.matches("\\d+")) {
                        campo.setText(oldVal);
                        mostrarAlerta("Solo se permiten números");
                        return;
                    }

                    try {
                        int valor = Integer.parseInt(newVal);

                        if (valor < 1 || valor > 12) {
                            campo.setText(oldVal);
                            mostrarAlerta("Solo números del 1 al 12");
                        }

                    } catch (NumberFormatException e) {
                        campo.setText(oldVal);
                    }
                });

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

    /**
     * Muestra las instrucciones del juego mediante una alerta.
     */
    @FXML
    private void handleInstrucciones() {

        String mensaje = """
                • Usa números del 1 al 12
                • No puedes repetir números
                • Tienes 6 intentos

                COLORES:

                - Verde:
                Número correcto y posición correcta

                - Amarillo:
                Número correcto en posición incorrecta

                - Gris:
                Número no pertenece a la solución
                """;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Instrucciones");
        alert.setHeaderText("¿Cómo jugar Ooodle?");
        alert.setContentText(mensaje);

        alert.showAndWait();
    }

    /**
     * Muestra una alerta de advertencia.
     *
     * @param mensaje mensaje mostrado al usuario.
     */
    private void mostrarAlerta(String mensaje) {
        new Alert(Alert.AlertType.WARNING, mensaje).showAndWait();
    }

    /**
     * Actualiza qué filas pueden editarse.
     */
    private void actualizarFilasEditables() {
        for (int i = 0; i < tablero.size(); i++) {
            for (TextField campo : tablero.get(i)) {
                campo.setEditable(i == filaActual);
            }
        }
    }

    /**
     * Carga los datos iniciales del juego.
     */
    public void cargarDatos() {
        crearTablero();
        lblEcuacion.setText(logica.getEcuacionVisible());
        lblIntentos.setText("Intento 1 de " + MAX_FILAS);
    }

    /**
     * Escribe un número en la fila actual.
     *
     * @param numero número seleccionado.
     */
    private void escribirNumero(String numero) {

        List<TextField> fila = tablero.get(filaActual);

        for (TextField campo : fila) {
            if (campo.getText().isEmpty()) {
                campo.setText(numero);
                return;
            }
        }
    }

    /**
     * Maneja la pulsación de botones numéricos.
     *
     * @param e evento del botón presionado.
     */
    @FXML
    private void handleNumero(ActionEvent e) {
        Button btn = (Button) e.getSource();
        escribirNumero(btn.getText());
    }

    /**
     * Borra el último número ingresado en la fila actual.
     */
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

    /**
     * Pinta visualmente un botón según el resultado obtenido.
     *
     * @param numero número evaluado.
     * @param estado estado correspondiente.
     */
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

    /**
     * Procesa y valida el intento actual del jugador.
     * <p>
     * Verifica restricciones, actualiza colores, controla intentos
     * y determina si el usuario gana o pierde.
     * </p>
     */
    @FXML
    private void handleIntentar() {

        List<TextField> fila = tablero.get(filaActual);
        Set<Integer> usados = new HashSet<>();

        for (TextField campo : fila) {

            try {
                int valor = Integer.parseInt(campo.getText());

                if (valor < 1 || valor > 12) {
                    mostrarAlerta("Solo números del 1 al 12");
                    return;
                }

                if (usados.contains(valor)) {
                    mostrarAlerta("No puedes repetir números");
                    return;
                }

                if (numerosBloqueados.contains(valor)) {
                    mostrarAlerta("Número bloqueado: " + valor);
                    return;
                }

                usados.add(valor);

            } catch (Exception e) {
                mostrarAlerta("Completa la fila");
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

        actualizarFilasEditables();

        lblIntentos.setText("Intento " + (filaActual + 1) + " de " + MAX_FILAS);
    }

    /**
     * Guarda la partida si pertenece al modo diario.
     *
     * @param resultado resultado final de la partida.
     */
    private void guardarSiEsDiaria(String resultado) {

        if (!"diaria".equalsIgnoreCase(modo)) return;

        try {

            logica.setResultado(resultado);
            partidaDAO.create(logica);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Regresa al menú principal.
     */
    @FXML
    private void handleMenu() {
        main.mostrarMenu();
    }

    /**
     * Reinicia la partida actual.
     */
    @FXML
    private void handleReintentar() {
        main.mostrarPartida(modo);
    }
}