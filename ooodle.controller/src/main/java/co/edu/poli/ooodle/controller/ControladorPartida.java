package co.edu.poli.ooodle.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import co.edu.poli.ooodle.modelo.GameLogic;
import co.edu.poli.ooodle.modelo.Intento;
import co.edu.poli.ooodle.modelo.Partida;
import co.edu.poli.ooodle.modelo.Usuario;
import co.edu.poli.ooodle.servicios.ImplementacionCRUDPartida;
import co.edu.poli.ooodle.servicios.ImplementacionCRUDUsuario;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

public class ControladorPartida {

	// ===================== PANELES =====================
	@FXML
	private AnchorPane AhpInformacionEstudiante;

	@FXML
	private AnchorPane ahpBotonesOpciones;

	@FXML
	private AnchorPane ahpPartida;

	// ===================== INFORMACIÓN DE USUARIO =====================
	@FXML
	private Label lblNombreUsuarioRt;

	@FXML
	private Label lblIdUsuarioRt;

	@FXML
	private Label lblInformacionP;

	// ===================== BOTONES DE NAVEGACIÓN =====================
	@FXML
	private Button bttIniciarPartida;

	@FXML
	private Button bttHistorialPartidas;

	@FXML
	private Button bttSalir;

	@FXML
	private Button btnHome;

	@FXML
	private Button btnRestart;

	@FXML
	private Button btnMostrarReglas;

	// ===================== TECLADO NUMÉRICO (12 botones) =====================
	@FXML
	private GridPane gridTeclado;

	@FXML
	private Button btn1, btn2, btn3, btn4, btn5, btn6;

	@FXML
	private Button btn7, btn8, btn9, btn10, btn11, btn12;

	@FXML
	private Button btnCheck; // Botón para verificar

	@FXML
	private Button btnDelete; // Botón para eliminar último número

	// ===================== TABLERO (6x4) =====================
	@FXML
	private GridPane gridTablero;

	// Fila 0
	@FXML
	private TextField txt_0_0, txt_0_1, txt_0_2, txt_0_3;

	// Fila 1
	@FXML
	private TextField txt_1_0, txt_1_1, txt_1_2, txt_1_3;

	// Fila 2
	@FXML
	private TextField txt_2_0, txt_2_1, txt_2_2, txt_2_3;

	// Fila 3
	@FXML
	private TextField txt_3_0, txt_3_1, txt_3_2, txt_3_3;

	// Fila 4
	@FXML
	private TextField txt_4_0, txt_4_1, txt_4_2, txt_4_3;

	// Fila 5
	@FXML
	private TextField txt_5_0, txt_5_1, txt_5_2, txt_5_3;

	// ===================== RESULTADOS (6 labels) =====================
	@FXML
	private Label lblResultado0, lblResultado1, lblResultado2;

	@FXML
	private Label lblResultado3, lblResultado4, lblResultado5;

	// ===================== ATRIBUTOS DE INSTANCIA =====================
	private Usuario usuarioActual;
	private Stage loginStage;
	private Stage stagePartida;

	private ImplementacionCRUDUsuario crudUsuario;
	private ImplementacionCRUDPartida crudPartida;

	private ObservableList<Partida> partidas = FXCollections.observableArrayList();
	private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();

	// ===================== LÓGICA DEL JUEGO =====================
	private GameLogic gameLogic;
	private Partida partidaActual;
	private TextField[][] campos;
	private Label[] resultados;
	private int filaActual; // Fila en la que se está escribiendo

	//
	private static int contadorPartidas = 0;

	// ===================== INICIALIZACIÓN =====================

	/**
	 * Se llama cuando el controller es creado por FXMLLoader
	 */
	public void initialize() {
		inicializarMatrices();
		configurarTecladoNumerico();
	}

	/**
	 * Inicializa las matrices de TextFields y Labels
	 */
	private void inicializarMatrices() {
		campos = new TextField[][] { { txt_0_0, txt_0_1, txt_0_2, txt_0_3 }, { txt_1_0, txt_1_1, txt_1_2, txt_1_3 },
				{ txt_2_0, txt_2_1, txt_2_2, txt_2_3 }, { txt_3_0, txt_3_1, txt_3_2, txt_3_3 },
				{ txt_4_0, txt_4_1, txt_4_2, txt_4_3 }, { txt_5_0, txt_5_1, txt_5_2, txt_5_3 } };

		resultados = new Label[] { lblResultado0, lblResultado1, lblResultado2, lblResultado3, lblResultado4,
				lblResultado5 };

		// Hacer los campos read-only y sin editable
		for (TextField[] fila : campos) {
			for (TextField campo : fila) {
				campo.setEditable(false);
				campo.getStyleClass().add("tablero-campo");
			}
		}

		filaActual = 0;
	}

	/**
	 * Configura los botones del teclado numérico
	 */
	private void configurarTecladoNumerico() {
		// Botones 1-12
		Button[] botones = { btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12 };

		for (int i = 0; i < botones.length; i++) {
			final int numero = i + 1;
			botones[i].setOnAction(e -> agregarNumero(numero));
		}

		// Botón Check (rojo)
		btnCheck.setOnAction(e -> verificarIntento());

		// Botón Delete
		btnDelete.setOnAction(e -> eliminarUltimo(e));
		
		// Botón Mostrar Reglas
        btnMostrarReglas.setOnAction(e -> mostrarReglas(e));
	}

	/**
	 * Recibe el usuario y servicios desde ControladorLogin
	 */
	public void init(Usuario usuario, ImplementacionCRUDPartida crudPartida, ImplementacionCRUDUsuario crudUsuario) {
		this.usuarioActual = usuario;
		this.crudPartida = crudPartida;
		this.crudUsuario = crudUsuario;

		usuarios.setAll(crudUsuario.getUsuarios());

		if (usuario != null) {
			lblNombreUsuarioRt.setText(usuario.getNombre());
			lblIdUsuarioRt.setText(String.valueOf(usuario.getId()));
		}

		// Asegurar que el panel principal esté visible
		AhpInformacionEstudiante.setVisible(true);
		ahpBotonesOpciones.setVisible(true);
		ahpPartida.setVisible(false);
	}

	// ===================== LÓGICA DE JUEGO =====================

	/**
	 * Inicia una nueva partida
	 */
	@FXML
	void iniciarPartida(ActionEvent event) {
		// Crear nueva instancia de GameLogic
		gameLogic = new GameLogic();
		filaActual = 0;

		// Limpiar el tablero
		limpiarTablero();

		// Crear la partida con ID secuencial
		contadorPartidas++;
		String codigoPartida = String.format("PARTIDA_%03d", contadorPartidas);

		partidaActual = new Partida(codigoPartida, usuarioActual, 0, usuarioActual.getId(), LocalDateTime.now(), 0,
				false, new java.util.ArrayList<>());

		// Mostrar el panel de juego
		ahpBotonesOpciones.setVisible(false);
		AhpInformacionEstudiante.setVisible(true);
		ahpPartida.setVisible(true);

		System.out.println("🎮 Nueva partida iniciada: " + codigoPartida);
		System.out.println("📋 Patrón de ecuación: " + gameLogic.getPatronEcuacion());

		// Inicializar el primer label resultado
		int[] respuesta = gameLogic.getRespuestaCorrecta();
		int resultado = respuesta[0] - respuesta[1] + (respuesta[2] * respuesta[3]);

		resultados[0].setText(String.valueOf(resultado));
		resultados[0].setStyle("-fx-font-size: 16px; " + "-fx-font-weight: bold; " + "-fx-text-fill: #333333;");

		habilitarTeclado();
	}

	/**
	 * Agrega un número a la fila actual
	 */
	private void agregarNumero(int numero) {
		if (gameLogic == null || gameLogic.juegoTerminado()) {
			mostrarAlerta("Juego terminado", "La partida ya ha finalizado");
			return;
		}

		// Buscar la primera celda vacía en la fila actual
		for (int col = 0; col < 4; col++) {
			if (campos[filaActual][col].getText().isEmpty()) {
				campos[filaActual][col].setText(String.valueOf(numero));
				campos[filaActual][col].getStyleClass().add("numero-ingresado");
				return;
			}
		}

		mostrarAlerta("Fila completa", "Ya ingresaste 4 números en esta fila");
	}

	/**
	 * Elimina el último número ingresado en la fila actual
	 */
	@FXML
	void eliminarUltimo(ActionEvent event) {
		// Buscar la última celda con número de derecha a izquierda
		for (int col = 3; col >= 0; col--) {
			if (!campos[filaActual][col].getText().isEmpty()) {
				campos[filaActual][col].setText("");
				campos[filaActual][col].getStyleClass().remove("numero-ingresado");
				return;
			}
		}
	}

	/**
	 * Verifica si la fila está completa y procesa el intento
	 */
	@FXML
	private void verificarIntento() {
		// Verificar que la fila esté completa
		int[] intento = new int[4];
		for (int col = 0; col < 4; col++) {
			String texto = campos[filaActual][col].getText();
			if (texto.isEmpty()) {
				mostrarAlerta("Fila incompleta", "Completa los 4 números antes de verificar");
				return;
			}
			intento[col] = Integer.parseInt(texto);
		}

		// Procesar el intento en GameLogic
		Intento intentoResultado = gameLogic.procesarIntento(intento);

		if (intentoResultado == null) {
			mostrarAlerta("Error", "No se pudo procesar el intento");
			return;
		}

		// Actualizar la partida
		intentoResultado.setIdPartida(contadorPartidas);
		partidaActual.getIntentos().add(intentoResultado);
		partidaActual.setIntentosUsados(gameLogic.getIntento());

		// Colorear la fila según el feedback
		colorearFila(filaActual, intentoResultado.getFeedback());

		// Mostrar resultado
		mostrarResultado(filaActual, intentoResultado);

		// Verificar si ganó o perdió
		if (gameLogic.isJuegoGanado()) {
			finalizarPartida(true);
		} else if (gameLogic.isJuegoPerdido()) {
			finalizarPartida(false);
		} else {
			// Pasar a la siguiente fila
			filaActual++;
			limpiarEstilosFilaActual();
		}
	}

	/**
	 * Colorea la fila según el feedback G = Verde, Y = Amarillo, X = Gris
	 */
	private void colorearFila(int fila, String feedback) {
		for (int col = 0; col < 4; col++) {
			char c = feedback.charAt(col);
			TextField campo = campos[fila][col];

			// Remover estilos anteriores
			campo.getStyleClass().removeAll("feedback-verde", "feedback-amarillo", "feedback-gris");

			// Aplicar nuevo estilo
			switch (c) {
			case 'G':
				campo.getStyleClass().add("feedback-verde");
				break;
			case 'Y':
				campo.getStyleClass().add("feedback-amarillo");
				break;
			case 'X':
				campo.getStyleClass().add("feedback-gris");
				break;
			}
		}
	}

	/**
	 * Muestra el resultado del intento en el Label correspondiente Muestra el
	 * resultado de evaluar la ecuación a-b+c*d
	 */
	private void mostrarResultado(int fila, Intento intento) {
		int[] respuesta = gameLogic.getRespuestaCorrecta();
		// Calcular: a - b + c * d
		int resultado = respuesta[0] - respuesta[1] + (respuesta[2] * respuesta[3]);

		resultados[fila].setText(String.valueOf(resultado));
		resultados[fila].setStyle("-fx-font-size: 16px; " + "-fx-font-weight: bold; " + "-fx-text-fill: #333333;");
	}

	/**
	 * Finaliza la partida (victoria o derrota)
	 */
	private void finalizarPartida(boolean ganada) {
		partidaActual.setGanada(ganada);
		partidaActual.setPuntajeFinal(gameLogic.calcularPuntaje());

		if (ganada) {
			mostrarAlerta("🎉 ¡GANASTE!", "Lo hiciste en " + gameLogic.getIntento() + " intentos\n" + "Puntaje: "
					+ gameLogic.calcularPuntaje());
		} else {
			mostrarAlerta("😞 PERDISTE",
					"La respuesta era: " + java.util.Arrays.toString(gameLogic.getRespuestaCorrecta()));
		}

		// Guardar la partida
		crudPartida.create(partidaActual);

		// Deshabilitar entrada
		deshabilitarTeclado();
	}

	/**
	 * Limpia el tablero
	 */
	private void limpiarTablero() {
		for (TextField[] fila : campos) {
			for (TextField campo : fila) {
				campo.setText("");
				campo.getStyleClass().clear();
				campo.getStyleClass().add("tablero-campo");
			}
		}

		for (Label resultado : resultados) {
			resultado.setText("");
			resultado.getStyleClass().remove("resultado-visible");
		}
	}

	/**
	 * Limpia los estilos de la fila actual para que esté lista
	 */
	private void limpiarEstilosFilaActual() {
		for (int col = 0; col < 4; col++) {
			campos[filaActual][col].getStyleClass().removeAll("numero-ingresado");
		}
	}

	/**
	 * Deshabilita los botones del teclado
	 */
	private void deshabilitarTeclado() {
		Button[] botones = { btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12 };

		for (Button boton : botones) {
			boton.setDisable(true);
		}

		btnCheck.setDisable(true);
		btnDelete.setDisable(true);
	}

	/**
	 * Reinicia el juego
	 */
	@FXML
	void btnRestartClicked(ActionEvent event) {
		if (gameLogic != null) {
			gameLogic.reiniciarJuego();
			filaActual = 0;
			limpiarTablero();
			habilitarTeclado();

			// ✅ AGREGAR ESTO: Reinicializar el primer label resultado
			int[] respuesta = gameLogic.getRespuestaCorrecta();
			int resultado = respuesta[0] - respuesta[1] + (respuesta[2] * respuesta[3]);

			resultados[0].setText(String.valueOf(resultado));
			resultados[0].setStyle("-fx-font-size: 16px; " + "-fx-font-weight: bold; " + "-fx-text-fill: #333333;");

			System.out.println("🔄 Partida reiniciada");
		}
	}

	/**
	 * Habilita los botones del teclado
	 */
	private void habilitarTeclado() {
		Button[] botones = { btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12 };

		for (Button boton : botones) {
			boton.setDisable(false);
		}

		btnCheck.setDisable(false);
		btnDelete.setDisable(false);
	}

	/**
	 * Vuelve al menú de inicio
	 */
	@FXML
	void btnHomeClicked(ActionEvent event) {
		ahpPartida.setVisible(false);
		AhpInformacionEstudiante.setVisible(true);
		ahpBotonesOpciones.setVisible(true);
	}

	/**
	 * Muestra el historial de partidas del usuario
	 */
	@FXML
	void historialPartidas(ActionEvent event) {
		List<Partida> partidasDelUsuario = crudPartida.getPartidas().stream()
				.filter(p -> p.getIdUsuario() == usuarioActual.getId()).collect(Collectors.toList());

		StringBuilder historial = new StringBuilder("Historial de partidas:\n\n");
		for (Partida p : partidasDelUsuario) {
			historial.append("Código: ").append(p.getCodigoPartida()).append("\n");
			historial.append("Ganada: ").append(p.isGanada() ? "Sí ✅" : "No ❌").append("\n");
			historial.append("Puntaje: ").append(p.getPuntajeFinal()).append("\n");
			historial.append("---\n");
		}

		mostrarAlerta("Historial", historial.toString());
	}

	/**
	 * Muestra las reglas del juego
	 */
	@FXML
	void mostrarReglas(ActionEvent event) {
		String reglas = "REGLAS DE OOOODLE\n\n" + "🎯 OBJETIVO:\n"
				+ "Encuentra los 4 números que resuelven la ecuación.\n\n" + "ECUACIÓN:\n"
				+ "a - b + c × d = (resultado mostrado a la derecha)\n\n" + "CÓMO JUGAR:\n"
				+ "1. Usa los botones del 1 al 12 para ingresar 4 números\n"
				+ "2. Presiona 'Check' para verificar tu respuesta\n" + "3. Observa los colores del feedback:\n\n"
				+ "VERDE = Número correcto en posición correcta\n"
				+ "AMARILLO = Número correcto en posición incorrecta\n" + "GRIS = Número no está en la solución\n\n"
				+ "⏱️ INTENTOS:\n" + "Tienes máximo 6 intentos para resolver el puzzle.\n\n" + "PUNTAJE:\n"
				+ "Se calcula según cuántos intentos uses:\n" + "Menos intentos = Mayor puntaje\n\n" + "CONTROLES:\n"
				+ "• Del = Eliminar último número\n" + "• Restart = Reiniciar con un nuevo puzzle\n"
				+ "• Home = Volver al menú principal";

		mostrarAlerta("Reglas del Juego", reglas);
	}

	/**
	 * Cierra sesión y vuelve a login
	 */
	@FXML
	void salir(ActionEvent event) {
		stagePartida.close();
		if (loginStage != null) {
			loginStage.show();
		}
	}

	// ===================== UTILIDADES =====================

	public void setLoginStage(Stage stage) {
		this.loginStage = stage;
	}

	public void setStage(Stage stagePartida) {
		this.stagePartida = stagePartida;
	}

	/**
	 * Muestra una alerta al usuario
	 */
	private void mostrarAlerta(String titulo, String mensaje) {
		Alert alerta = new Alert(Alert.AlertType.INFORMATION);
		alerta.setTitle(titulo);
		alerta.setHeaderText(null);
		alerta.setContentText(mensaje);
		alerta.show();
	}
}