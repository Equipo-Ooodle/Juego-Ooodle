package co.edu.poli.ooodle.pruebas.integracion;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import co.edu.poli.ooodle.modelo.Partida;
import co.edu.poli.ooodle.modelo.Usuario;
import co.edu.poli.ooodle.servicios.PartidaDAO;

/**
 * Clase de pruebas de integración para la clase PartidaDAO.
 * 
 * Verifica el correcto funcionamiento de las operaciones
 * relacionadas con la persistencia de partidas en la base de datos.
 * 
 * Las pruebas incluyen:
 * <ul>
 * <li>Creación de partidas</li>
 * <li>Lectura individual de partidas</li>
 * <li>Lectura de todas las partidas</li>
 * <li>Búsqueda de partidas por usuario</li>
 * </ul>
 */
public class pruebaDAOPartida {

    /**
     * Instancia del DAO utilizada para realizar las pruebas.
     */
    private PartidaDAO partidaDAO = new PartidaDAO();

    /**
     * Verifica que una partida pueda almacenarse correctamente
     * en la base de datos.
     * 
     * @throws Exception si ocurre un error durante la inserción
     */
    @Test
    void testCrearPartida() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setId(1); // ID de un usuario existente en la BD

        Partida partida = new Partida(usuario);
        partida.setResultado("GANADA");

        String resultado = partidaDAO.create(partida);

        assertEquals("OK", resultado);
    }

    /**
     * Verifica que una partida pueda obtenerse correctamente
     * a partir de su identificador.
     * 
     * @throws Exception si ocurre un error durante la consulta
     */
    @Test
    void testReadOne() throws Exception {

        Partida partida = partidaDAO.readone(5);

        assertNotNull(partida);
        assertNotNull(partida.getResultado());
    }

    /**
     * Verifica que el método readall retorne
     * una lista válida y no vacía de partidas.
     * 
     * @throws Exception si ocurre un error durante la consulta
     */
    @Test
    void testReadAll() throws Exception {

        List<Partida> partidas = partidaDAO.readall();

        assertNotNull(partidas);
        assertFalse(partidas.isEmpty());
    }

    /**
     * Verifica que puedan obtenerse correctamente
     * las partidas asociadas a un usuario.
     */
    @Test
    void testObtenerPorUsuario() {

        List<Partida> partidas = partidaDAO.obtenerPorUsuario(1);

        assertNotNull(partidas);
    }
}