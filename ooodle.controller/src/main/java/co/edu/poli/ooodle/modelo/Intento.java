package co.edu.poli.ooodle.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Representa un intento realizado durante una partida de Oooodle. Guarda la
 * respuesta del usuario, el feedback y la marca temporal.
 */
public class Intento implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id; // ID único del intento (para BD)
	private int idPartida; // Referencia a la partida
	private int numeroIntento; // Número del intento (1-6)
	private int[] respuestaUsuario; // [a, b, c, d] ingresados
	private String feedback; // "GGGY", "GXYY", etc.
	private LocalDateTime fechaIntento; // Cuándo se realizó

	/**
	 * Constructor vacío (para deserialización y BD)
	 */
	public Intento() {
	}

	/**
	 * Constructor completo
	 */
	public Intento(int id, int idPartida, int numeroIntento, int[] respuestaUsuario, String feedback,
			LocalDateTime fechaIntento) {
		this.id = id;
		this.idPartida = idPartida;
		this.numeroIntento = numeroIntento;
		this.respuestaUsuario = respuestaUsuario;
		this.feedback = feedback;
		this.fechaIntento = fechaIntento;
	}

	/**
	 * Constructor para crear un intento durante el juego (sin ID, será generado por
	 * la BD)
	 */
	public Intento(int idPartida, int numeroIntento, int[] respuestaUsuario, String feedback,
			LocalDateTime fechaIntento) {
		this.idPartida = idPartida;
		this.numeroIntento = numeroIntento;
		this.respuestaUsuario = respuestaUsuario;
		this.feedback = feedback;
		this.fechaIntento = fechaIntento;
	}

	// =============== GETTERS Y SETTERS ===============

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(int idPartida) {
		this.idPartida = idPartida;
	}

	public int getNumeroIntento() {
		return numeroIntento;
	}

	public void setNumeroIntento(int numeroIntento) {
		if (numeroIntento < 1 || numeroIntento > 6) {
			throw new IllegalArgumentException("Número de intento debe estar entre 1 y 6");
		}
		this.numeroIntento = numeroIntento;
	}

	public int[] getRespuestaUsuario() {
		return respuestaUsuario;
	}

	public void setRespuestaUsuario(int[] respuestaUsuario) {
		if (respuestaUsuario != null && respuestaUsuario.length != 4) {
			throw new IllegalArgumentException("La respuesta debe contener exactamente 4 números");
		}
		this.respuestaUsuario = respuestaUsuario;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		if (feedback != null && !feedback.matches("[GYX]{4}")) {
			throw new IllegalArgumentException("Feedback debe ser 4 caracteres: G, Y o X");
		}
		this.feedback = feedback;
	}

	public LocalDateTime getFechaIntento() {
		return fechaIntento;
	}

	public void setFechaIntento(LocalDateTime fechaIntento) {
		this.fechaIntento = fechaIntento;
	}

	/**
	 * Verifica si este intento fue correcto (todas las posiciones verdes)
	 */
	public boolean esCorrecta() {
		return feedback != null && feedback.equals("GGGG");
	}

	/**
	 * Cuenta cuántos números están en posición correcta
	 */
	public int contarVerdes() {
		return feedback != null ? (int) feedback.chars().filter(c -> c == 'G').count() : 0;
	}

	/**
	 * Cuenta cuántos números están en posición incorrecta
	 */
	public int contarAmarillos() {
		return feedback != null ? (int) feedback.chars().filter(c -> c == 'Y').count() : 0;
	}

	/**
	 * Cuenta cuántos números no están en la respuesta
	 */
	public int contarGrises() {
		return feedback != null ? (int) feedback.chars().filter(c -> c == 'X').count() : 0;
	}

	/**
	 * Retorna una representación del feedback con códigos de color G = Verde, Y =
	 * Amarillo, X = Gris
	 */
	public String getFeedbackVisual() {
		if (feedback == null)
			return "XXXX";
		return feedback; // Solo retorna "GGYX" etc, sin emojis
	}

	/**
	 * Obtiene el color CSS para una posición específica del feedback
	 */
	public String getColorCSS(int posicion) {
		if (feedback == null || posicion < 0 || posicion >= feedback.length()) {
			return "gris"; // Color por defecto
		}

		char c = feedback.charAt(posicion);
		switch (c) {
		case 'G':
			return "verde"; // Código que usarás en CSS
		case 'Y':
			return "amarillo";
		case 'X':
			return "gris";
		default:
			return "gris";
		}
	}

	@Override
	public String toString() {
		return "Intento{" + "id=" + id + ", idPartida=" + idPartida + ", numeroIntento=" + numeroIntento
				+ ", respuesta=" + java.util.Arrays.toString(respuestaUsuario) + ", feedback='" + feedback + '\''
				+ ", fecha=" + fechaIntento + '}';
	}
}