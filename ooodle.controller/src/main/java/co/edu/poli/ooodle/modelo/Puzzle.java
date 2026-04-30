package co.edu.poli.ooodle.modelo;

/**
 * Representa un puzzle del juego Oooodle Contiene los 4 números solución y el
 * patrón de ecuación
 */
public class Puzzle {
	private int[] numeros; // [a, b, c, d]
	private String patron; // "a-b+c*d"
	private int respuestaEsperada; // Resultado de evaluar la ecuación

	public Puzzle(int[] numeros, String patron) {
		this.numeros = numeros;
		this.patron = patron;
		this.respuestaEsperada = evaluarEcuacion();
	}

	/**
	 * Evalúa la ecuación con los números dados Patrón: a - b + c * d Orden de
	 * operaciones: * antes que + y -
	 */
	private int evaluarEcuacion() {
		int a = numeros[0];
		int b = numeros[1];
		int c = numeros[2];
		int d = numeros[3];

		// a - b + c * d
		// Primero: c * d
		// Luego: a - b + resultado
		return a - b + (c * d);
	}

	// =============== GETTERS ===============
	public int[] getNumeros() {
		return numeros;
	}

	public String getPatron() {
		return patron;
	}

	public int getRespuestaEsperada() {
		return respuestaEsperada;
	}

	@Override
	public String toString() {
		return "Puzzle{" + "numeros=" + java.util.Arrays.toString(numeros) + ", patron='" + patron + '\''
				+ ", respuesta=" + respuestaEsperada + '}';
	}
}