package co.edu.poli.ooodle.pruebas.unitarias;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import co.edu.poli.ooodle.servicios.Encriptacion;

/**
 * Clase de pruebas unitarias para la clase {@link Encriptacion}.
 * 
 * <p>
 * Esta clase verifica el correcto funcionamiento
 * de los métodos de hash y validación de contraseñas.
 * </p>
 * 
 * @author Mia
 * @version 1.0
 */
public class pruebaEncriptacion {

    /**
     * Verifica que el método hash genere
     * una contraseña encriptada diferente
     * a la original.
     */
    @Test
    void testHash() {

        String password = "12345";

        String hash = Encriptacion.hash(password);

        assertNotNull(hash);
        assertNotEquals(password, hash);
    }

    /**
     * Verifica que el método verificar
     * retorne verdadero cuando la contraseña
     * coincide con el hash generado.
     */
    @Test
    void testVerificarCorrecta() {

        String password = "12345";

        String hash = Encriptacion.hash(password);

        boolean resultado = Encriptacion.verificar(password, hash);

        assertTrue(resultado);
    }

    /**
     * Verifica que el método verificar
     * retorne falso cuando la contraseña
     * no coincide con el hash generado.
     */
    @Test
    void testVerificarIncorrecta() {

        String password = "12345";

        String hash = Encriptacion.hash(password);

        boolean resultado = Encriptacion.verificar("54321", hash);

        assertFalse(resultado);
    }
}