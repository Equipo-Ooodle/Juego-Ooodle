package co.edu.poli.ooodle.modelo;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 
 */
public class Partida {

	
	private String codigoPartida;
	private Usuario usuario;
	private int puntajeFinal;

	// Nuevo campo:
	private int idUsuario;

	private LocalDateTime fechaPartida; // Cuándo jugó
	private int intentosUsados; // Cuántos de 6 usó
	private boolean ganada; // ¿Ganó o perdió?
	private List<Intento> intentos; // Historial de intentos (importante)

	// Constructor vacío (para BD/deserialización)
	public Partida() {
		this.intentos = new ArrayList<>();
	}

	// Constructor con parámetros (para crear partida en juego)
	public Partida(String codigoPartida, Usuario usuario, int puntajeFinal, int idUsuario, LocalDateTime fechaPartida,
			int intentosUsados, boolean ganada, List<Intento> intentos) {
		this.codigoPartida = codigoPartida;
		this.usuario = usuario;
		this.puntajeFinal = puntajeFinal;
		this.idUsuario = idUsuario;
		this.fechaPartida = fechaPartida;
		this.intentosUsados = intentosUsados;
		this.ganada = ganada;
		this.intentos = intentos != null ? intentos : new ArrayList<>();
	}

	public LocalDateTime getFechaPartida() {
		return fechaPartida;
	}

	public void setFechaPartida(LocalDateTime fechaPartida) {
		this.fechaPartida = fechaPartida;
	}

	public int getIntentosUsados() {
		return intentosUsados;
	}

	public void setIntentosUsados(int intentosUsados) {
		if (intentosUsados < 0 || intentosUsados > 6) {
			throw new IllegalArgumentException("Intentos debe estar entre 0 y 6");
		}
		this.intentosUsados = intentosUsados;
	}

	public boolean isGanada() {
		return ganada;
	}

	public void setGanada(boolean ganada) {
		this.ganada = ganada;
	}

	public List<Intento> getIntentos() {
		return intentos;
	}

	public void setIntentos(List<Intento> intentos) {
		this.intentos = intentos;
	}

	public String getCodigoPartida() {
		return codigoPartida;
	}

	public void setCodigoPartida(String codigoPartida) {
		this.codigoPartida = codigoPartida;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getPuntajeFinal() {
		return puntajeFinal;
	}

	public void setPuntajeFinal(int puntajeFinal) {
		this.puntajeFinal = puntajeFinal;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public void finalizarPartida() {
		// TODO implement here
	}

	@Override
	public String toString() {
		return "Partida [codigoPartida=" + codigoPartida + ", usuario=" + usuario + ", puntajeFinal=" + puntajeFinal
				+ ", idUsuario=" + idUsuario + ", fechaPartida=" + fechaPartida + ", intentosUsados=" + intentosUsados
				+ ", ganada=" + ganada + ", intentos=" + intentos + "]";
	}

}