package co.edu.poli.ooodle.pruebas.unitarias;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import co.edu.poli.ooodle.modelo.Partida;
import co.edu.poli.ooodle.modelo.Usuario;

/**
 * Clase de pruebas unitarias para la clase {@link Partida}.
 * 
 * <p>
 * Esta clase verifica el correcto funcionamiento de la lógica
 * relacionada con las partidas del juego Ooodle.
 * </p>
 * 
 * @author Mia
 * @version 1.0
 */
public class pruebaPartida {

    /**
     * Verifica que una partida se cree correctamente.
     */
    @Test
    void testCrearPartida() {

        Usuario usuario = new Usuario(1, "Mia", "1234");

        Partida partida = new Partida(usuario);

        assertNotNull(partida);
        assertEquals(0, partida.getIntentos());
        assertEquals(4, partida.getSolucion().size());
    }

    /**
     * Verifica que el número máximo de intentos sea 6.
     */
    @Test
    void testMaxIntentos() {

        Usuario usuario = new Usuario(1, "Mia", "1234");

        Partida partida = new Partida(usuario);

        assertEquals(6, partida.getMaxIntentos());
    }

    /**
     * Verifica que el contador de intentos aumente correctamente.
     */
    @Test
    void testSumarIntento() {

        Usuario usuario = new Usuario(1, "Mia", "1234");

        Partida partida = new Partida(usuario);

        partida.sumarIntento();

        assertEquals(1, partida.getIntentos());
    }

    /**
     * Verifica que el método gano retorne verdadero
     * cuando el intento coincide con la solución.
     */
    @Test
    void testGano() {

        Usuario usuario = new Usuario(1, "Mia", "1234");

        List<Integer> solucion = List.of(1, 2, 3, 4);

        Partida partida = new Partida(usuario, solucion, 0, "GANADA");

        List<Integer> intento = List.of(1, 2, 3, 4);

        assertTrue(partida.gano(intento));
    }

    /**
     * Verifica que el método gano retorne falso
     * cuando el intento no coincide con la solución.
     */
    @Test
    void testNoGano() {

        Usuario usuario = new Usuario(1, "Mia", "1234");

        List<Integer> solucion = List.of(1, 2, 3, 4);

        Partida partida = new Partida(usuario, solucion, 0, "PERDIDA");

        List<Integer> intento = List.of(4, 3, 2, 1);

        assertFalse(partida.gano(intento));
    }

    /**
     * Verifica que el método validarNumero
     * retorne VERDE cuando el número y posición son correctos.
     */
    @Test
    void testValidarNumeroVerde() {

        Usuario usuario = new Usuario(1, "Mia", "1234");

        List<Integer> solucion = List.of(1, 2, 3, 4);

        Partida partida = new Partida(usuario, solucion, 0, "GANADA");

        assertEquals("VERDE", partida.validarNumero(0, 1));
    }

    /**
     * Verifica que el método validarNumero
     * retorne AMARILLO cuando el número existe
     * pero está en una posición incorrecta.
     */
    @Test
    void testValidarNumeroAmarillo() {

        Usuario usuario = new Usuario(1, "Mia", "1234");

        List<Integer> solucion = List.of(1, 2, 3, 4);

        Partida partida = new Partida(usuario, solucion, 0, "GANADA");

        assertEquals("AMARILLO", partida.validarNumero(0, 2));
    }

    /**
     * Verifica que el método validarNumero
     * retorne GRIS cuando el número no pertenece a la solución.
     */
    @Test
    void testValidarNumeroGris() {

        Usuario usuario = new Usuario(1, "Mia", "1234");

        List<Integer> solucion = List.of(1, 2, 3, 4);

        Partida partida = new Partida(usuario, solucion, 0, "GANADA");

        assertEquals("GRIS", partida.validarNumero(0, 9));
    }

    /**
     * Verifica que se genere correctamente
     * la ecuación visible de la partida.
     */
    @Test
    void testGetEcuacionVisible() {

        Usuario usuario = new Usuario(1, "Mia", "1234");

        Partida partida = new Partida(usuario);

        String ecuacion = partida.getEcuacionVisible();

        assertNotNull(ecuacion);
        assertTrue(ecuacion.contains("="));
    }
}