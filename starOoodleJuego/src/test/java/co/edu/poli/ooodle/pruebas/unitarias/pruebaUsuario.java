package co.edu.poli.ooodle.pruebas.unitarias;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import co.edu.poli.ooodle.modelo.Usuario;

/**
 * Clase de pruebas unitarias para la clase {@link Usuario}.
 * 
 * <p>
 * Esta clase verifica el correcto funcionamiento
 * de los constructores, getters y setters
 * de la clase Usuario.
 * </p>
 * 
 * @author Mia
 * @version 1.0
 */
public class pruebaUsuario {

    /**
     * Verifica el funcionamiento del constructor vacío.
     */
    @Test
    void testConstructorVacio() {

        Usuario usuario = new Usuario();

        assertNotNull(usuario);
    }

    /**
     * Verifica el funcionamiento del constructor
     * con todos los parámetros.
     */
    @Test
    void testConstructorCompleto() {

        Usuario usuario = new Usuario(1, "Mia", "1234");

        assertEquals(1, usuario.getId());
        assertEquals("Mia", usuario.getNombre());
        assertEquals("1234", usuario.getContraseña());
    }

    /**
     * Verifica el funcionamiento del constructor
     * con nombre y contraseña.
     */
    @Test
    void testConstructorParcial() {

        Usuario usuario = new Usuario("Mia", "1234");

        assertEquals("Mia", usuario.getNombre());
        assertEquals("1234", usuario.getContraseña());
    }

    /**
     * Verifica el funcionamiento del setter y getter del id.
     */
    @Test
    void testSetGetId() {

        Usuario usuario = new Usuario();

        usuario.setId(10);

        assertEquals(10, usuario.getId());
    }

    /**
     * Verifica el funcionamiento del setter y getter del nombre.
     */
    @Test
    void testSetGetNombre() {

        Usuario usuario = new Usuario();

        usuario.setNombre("Antonio");

        assertEquals("Antonio", usuario.getNombre());
    }

    /**
     * Verifica el funcionamiento del setter y getter de la contraseña.
     */
    @Test
    void testSetGetContrasena() {

        Usuario usuario = new Usuario();

        usuario.setContraseña("abcd");

        assertEquals("abcd", usuario.getContraseña());
    }
}