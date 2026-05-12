package co.edu.poli.ooodle.pruebas.integracion;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import co.edu.poli.ooodle.modelo.Usuario;
import co.edu.poli.ooodle.servicios.Encriptacion;
import co.edu.poli.ooodle.servicios.UsuarioDAO;

/**
 * Clase de pruebas de integración para {@code UsuarioDAO}.
 * <p>
 * Esta clase verifica el correcto funcionamiento de las
 * operaciones relacionadas con usuarios en la base de datos,
 * incluyendo registro, búsqueda y validación de existencia.
 * </p>
 * 
 * @author Mia
 */
public class pruebaDAOUsuario {

    /**
     * Objeto de acceso a datos utilizado en las pruebas.
     */
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Verifica que un usuario pueda registrarse correctamente
     * en la base de datos.
     * 
     * @throws Exception si ocurre un error durante la inserción
     */
    @Test
    void testCrearUsuario() throws Exception {

        String nombre = "Antonio";

        String hash = Encriptacion.hash("44444");

        Usuario usuario = new Usuario(0, nombre, hash);

        String resultado = usuarioDAO.create(usuario);

        assertEquals("OK", resultado);
    }

    /**
     * Verifica que un usuario pueda ser encontrado
     * correctamente mediante su nombre.
     */
    @Test
    void testBuscarPorNombre() {

        Usuario usuario = usuarioDAO.buscarPorNombre("Antonio");

        assertNotNull(usuario);

        assertEquals("Antonio", usuario.getNombre());
    }

    /**
     * Verifica que el sistema detecte correctamente
     * la existencia de un usuario registrado.
     */
    @Test
    void testExisteUsuario() {

        boolean existe = usuarioDAO.existeUsuario("Antonio");

        assertTrue(existe);
    }

    /**
     * Verifica que el sistema retorne falso cuando
     * un usuario no existe en la base de datos.
     */
    @Test
    void testUsuarioNoExiste() {

        boolean existe = usuarioDAO.existeUsuario("usuario_inexistente");

        assertFalse(existe);
    }
}