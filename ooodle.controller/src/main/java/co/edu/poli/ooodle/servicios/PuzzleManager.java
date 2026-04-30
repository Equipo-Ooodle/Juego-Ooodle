package co.edu.poli.ooodle.servicios;

import java.util.*;
import co.edu.poli.ooodle.modelo.Puzzle;

/**
 * Gestor de puzzles predefinidos. Maneja una lista exhaustiva de puzzles
 * válidos y los distribuye sin repeticiones consecutivas, pareciendo infinita.
 */
public class PuzzleManager {

	private static final String PATRON = "a-b+c*d";
	private List<Puzzle> todosLosPuzzles;
	private List<Puzzle> shuffledPuzzles;
	private int indiceActual;
	private Puzzle ultimoPuzzleUsado;

	public PuzzleManager() {
		this.todosLosPuzzles = crearListaPuzzles();
		this.shuffledPuzzles = new ArrayList<>(todosLosPuzzles);
		Collections.shuffle(shuffledPuzzles);
		this.indiceActual = 0;
		this.ultimoPuzzleUsado = null;
	}

	/**
	 * Retorna el siguiente puzzle sin repetir el anterior
	 */
	public Puzzle siguientePuzzle() {
		// Si llegamos al final, reiniciamos y shuffleamos
		if (indiceActual >= shuffledPuzzles.size()) {
			Collections.shuffle(shuffledPuzzles);
			indiceActual = 0;
		}

		Puzzle puzzle = shuffledPuzzles.get(indiceActual++);

		// Si es el mismo que el anterior, intercambia con el siguiente
		if (ultimoPuzzleUsado != null && Arrays.equals(puzzle.getNumeros(), ultimoPuzzleUsado.getNumeros())) {

			if (indiceActual < shuffledPuzzles.size()) {
				// Intercambia con el siguiente
				Puzzle temp = puzzle;
				puzzle = shuffledPuzzles.get(indiceActual);
				shuffledPuzzles.set(indiceActual - 1, puzzle);
				shuffledPuzzles.set(indiceActual, temp);
			} else {
				// Shuffle y toma el primero
				Collections.shuffle(shuffledPuzzles);
				indiceActual = 0;
				puzzle = shuffledPuzzles.get(indiceActual++);
			}
		}

		ultimoPuzzleUsado = puzzle;
		return puzzle;
	}

	/**
	 * Lista exhaustiva de puzzles válidos con patrón a-b+c*d Validados manualmente
	 * para evitar múltiples soluciones
	 */
	private List<Puzzle> crearListaPuzzles() {
		List<Puzzle> puzzles = new ArrayList<>();

		// Fila 1: Números 1-4
		puzzles.add(new Puzzle(new int[] { 6, 2, 7, 9 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 8, 3, 5, 2 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 7, 4, 6, 3 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 9, 5, 4, 2 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 5, 1, 8, 4 }, PATRON));

		// Fila 2: Números variados
		puzzles.add(new Puzzle(new int[] { 3, 5, 8, 1 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 4, 9, 2, 6 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 7, 1, 3, 8 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 2, 6, 9, 4 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 9, 4, 1, 7 }, PATRON));

		// Fila 3: Números con 10-12
		puzzles.add(new Puzzle(new int[] { 11, 3, 6, 5 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 12, 7, 4, 2 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 10, 2, 8, 3 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 6, 8, 12, 1 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 9, 2, 7, 11 }, PATRON));

		// Fila 4: Más combinaciones
		puzzles.add(new Puzzle(new int[] { 8, 1, 9, 3 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 5, 3, 10, 2 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 7, 6, 5, 4 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 12, 4, 3, 9 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 11, 9, 2, 6 }, PATRON));

		// Fila 5: Variación adicional
		puzzles.add(new Puzzle(new int[] { 4, 2, 11, 7 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 10, 5, 6, 8 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 3, 1, 12, 4 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 9, 7, 8, 2 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 6, 4, 10, 3 }, PATRON));

		// Fila 6: Más variedad
		puzzles.add(new Puzzle(new int[] { 8, 2, 4, 11 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 12, 6, 9, 5 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 5, 9, 7, 3 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 11, 1, 6, 12 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 7, 3, 2, 10 }, PATRON));

		// Fila 7: Más variaciones
		puzzles.add(new Puzzle(new int[] { 10, 8, 11, 4 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 4, 6, 3, 9 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 9, 3, 12, 2 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 6, 5, 1, 8 }, PATRON));
		puzzles.add(new Puzzle(new int[] { 2, 7, 10, 6 }, PATRON));

		return puzzles;
	}

	/**
	 * Retorna el total de puzzles disponibles
	 */
	public int getTotalPuzzles() {
		return todosLosPuzzles.size();
	}

	/**
	 * Retorna información del manager
	 */
	@Override
	public String toString() {
		return "PuzzleManager{" + "totalPuzzles=" + todosLosPuzzles.size() + ", indiceActual=" + indiceActual + '}';
	}
}