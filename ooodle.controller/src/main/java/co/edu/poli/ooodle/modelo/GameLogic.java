package co.edu.poli.ooodle.modelo;

import java.time.LocalDateTime;
import java.util.*;
import co.edu.poli.ooodle.servicios.PuzzleManager;

/**
 * Clase que contiene toda la lógica del juego Oooodle. Maneja la generación de
 * puzzles, validación de respuestas y feedback.
 */
public class GameLogic {

	private static final int MAX_INTENTOS = 6;
	private static final int NUMEROS_POR_ECUACION = 4;

	// Estado actual del juego
	private int[] respuestaCorrecta; // Los 4 números solución [a, b, c, d]
	private int intentoActual; // Contador de intentos (0-6)
	private boolean juegoGanado;
	private boolean juegoPerdido;
	private List<Intento> intentosRealizados; // Historial completo de intentos
	private PuzzleManager puzzleManager;
	private Puzzle puzzleActual;

	/**
	 * Constructor: Inicia una nueva partida
	 */
	public GameLogic() {
		this.intentosRealizados = new ArrayList<>();
		this.intentoActual = 0;
		this.juegoGanado = false;
		this.juegoPerdido = false;
		this.puzzleManager = new PuzzleManager();
		generarPuzzle();
	}

	/**
	 * Genera un nuevo puzzle de la lista predefinida
	 */
	private void generarPuzzle() {
		puzzleActual = puzzleManager.siguientePuzzle();
		respuestaCorrecta = puzzleActual.getNumeros();
		System.out.println("📋 Puzzle generado: " + puzzleActual);
	}

	/**
	 * Procesa un intento del usuario
	 * 
	 * @param intento - Array de 4 números ingresados por el usuario [a, b, c, d]
	 * @return Objeto Intento con el feedback completo
	 */
	public Intento procesarIntento(int[] intento) {
		// Validaciones
		if (juegoGanado || juegoPerdido) {
			System.out.println("⚠️ El juego ya ha terminado");
			return null;
		}

		if (intento == null || intento.length != NUMEROS_POR_ECUACION) {
			System.out.println("❌ Debes ingresar exactamente 4 números");
			return null;
		}

		// Incrementar contador
		intentoActual++;

		// Generar feedback
		String feedback = generarFeedback(intento);

		// Crear objeto Intento
		Intento nuevoIntento = new Intento(1, // idPartida (se actualiza después desde el controller)
				intentoActual, intento, feedback, LocalDateTime.now());

		intentosRealizados.add(nuevoIntento);

		// Verificar victoria
		if (feedback.equals("GGGG")) {
			juegoGanado = true;
			System.out.println("🎉 ¡GANASTE! Lo hiciste en " + intentoActual + " intentos");
		}

		// Verificar derrota
		if (intentoActual >= MAX_INTENTOS && !juegoGanado) {
			juegoPerdido = true;
			System.out.println("😞 PERDISTE. La respuesta era: " + Arrays.toString(respuestaCorrecta));
		}

		return nuevoIntento;
	}

	/**
	 * Genera el feedback para un intento G = Verde (número correcto en posición
	 * correcta) Y = Amarillo (número correcto en posición incorrecta) X = Gris
	 * (número no en la respuesta)
	 */
	private String generarFeedback(int[] intento) {
		StringBuilder feedback = new StringBuilder();
		boolean[] usados = new boolean[NUMEROS_POR_ECUACION];

		// Primera pasada: encontrar coincidencias exactas (Verde - G)
		for (int i = 0; i < NUMEROS_POR_ECUACION; i++) {
			if (intento[i] == respuestaCorrecta[i]) {
				feedback.append("G");
				usados[i] = true;
			} else {
				feedback.append("_"); // Placeholder temporal
			}
		}

		// Segunda pasada: encontrar números en posición incorrecta (Amarillo - Y / Gris
		// - X)
		for (int i = 0; i < NUMEROS_POR_ECUACION; i++) {
			if (feedback.charAt(i) == '_') {
				boolean encontrado = false;
				for (int j = 0; j < NUMEROS_POR_ECUACION; j++) {
					if (!usados[j] && intento[i] == respuestaCorrecta[j]) {
						feedback.setCharAt(i, 'Y');
						usados[j] = true;
						encontrado = true;
						break;
					}
				}
				if (!encontrado) {
					feedback.setCharAt(i, 'X');
				}
			}
		}

		return feedback.toString();
	}

	/**
	 * Reinicia el juego generando un nuevo puzzle
	 */
	public void reiniciarJuego() {
		this.intentosRealizados.clear();
		this.intentoActual = 0;
		this.juegoGanado = false;
		this.juegoPerdido = false;
		generarPuzzle();
	}

	/**
	 * Calcula el puntaje basado en intentos usados Fórmula: (6 - intentosUsados) *
	 * 10
	 */
	public int calcularPuntaje() {
		if (juegoGanado) {
			return (MAX_INTENTOS - intentoActual) * 10;
		}
		return 0;
	}

	// =============== GETTERS ===============

	public int getIntento() {
		return intentoActual;
	}

	public int getMaxIntentos() {
		return MAX_INTENTOS;
	}

	public boolean isJuegoGanado() {
		return juegoGanado;
	}

	public boolean isJuegoPerdido() {
		return juegoPerdido;
	}

	public List<Intento> getIntentosRealizados() {
		return new ArrayList<>(intentosRealizados);
	}

	public int[] getRespuestaCorrecta() {
		return respuestaCorrecta;
	}

	public int getIntentosRestantes() {
		return MAX_INTENTOS - intentoActual;
	}

	public boolean juegoTerminado() {
		return juegoGanado || juegoPerdido;
	}

	public Puzzle getPuzzleActual() {
		return puzzleActual;
	}

	public String getPatronEcuacion() {
		return puzzleActual.getPatron();
	}
}