package co.edu.poli.ooodle.servicios;

import java.util.List;

/**
 * Interfaz genérica que define las operaciones
 * básicas de acceso a datos (CRUD).
 *
 * @param <T> tipo de objeto sobre el cual
 *            se realizarán las operaciones
 */
public interface CRUD<T> {

    /**
     * Crea y almacena un nuevo objeto.
     *
     * @param t objeto a registrar
     * @return mensaje o estado del resultado de la operación
     * @throws Exception si ocurre un error durante el proceso
     */
    String create(T t) throws Exception;

    /**
     * Obtiene un único objeto a partir de su identificador.
     *
     * @param <K> tipo del identificador
     * @param id identificador del objeto a buscar
     * @return objeto encontrado o {@code null} si no existe
     * @throws Exception si ocurre un error durante la consulta
     */
    <K> T readone(K id) throws Exception;

    /**
     * Obtiene todos los objetos registrados.
     *
     * @return lista de objetos almacenados
     * @throws Exception si ocurre un error durante la consulta
     */
    List<T> readall() throws Exception;
}