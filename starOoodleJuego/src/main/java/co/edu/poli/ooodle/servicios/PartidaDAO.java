package co.edu.poli.ooodle.servicios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import co.edu.poli.ooodle.modelo.Partida;
import co.edu.poli.ooodle.modelo.Usuario;

/**
 * Clase encargada de gestionar las operaciones
 * de acceso a datos relacionadas con las partidas.
 * <p>
 * Implementa la interfaz {@link CRUD} para realizar
 * operaciones de creación y consulta de partidas
 * almacenadas en la base de datos.
 * </p>
 */
public class PartidaDAO implements CRUD<Partida> {

    /**
     * Guarda una nueva partida en la base de datos.
     *
     * @param p partida a registrar
     * @return mensaje indicando el resultado de la operación
     * @throws Exception si ocurre un error durante el registro
     */
    @Override
    public String create(Partida p) throws Exception {

        String sql = "INSERT INTO partida (resultado, intentos, solucion, usuario_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getResultado());
            stmt.setInt(2, p.getIntentos());
            stmt.setString(3, convertirListaAString(p.getSolucion()));
            stmt.setInt(4, p.getUsuario().getId());

            stmt.executeUpdate();
            return "OK";
        }
    }

    /**
     * Obtiene una partida específica mediante su identificador.
     *
     * @param id identificador de la partida
     * @return objeto {@link Partida} encontrado o {@code null}
     * @throws Exception si ocurre un error durante la consulta
     */
    @Override
    public Partida readone(Object id) throws Exception {

        String sql = "SELECT * FROM partida WHERE id = ?";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, (Integer) id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Usuario u = new Usuario();
                u.setId(rs.getInt("usuario_id"));

                Partida p = new Partida(
                    u,
                    convertirStringALista(rs.getString("solucion")),
                    rs.getInt("intentos"),
                    rs.getString("resultado")
                );

                return p;
            }
        }

        return null;
    }

    /**
     * Obtiene todas las partidas registradas
     * en la base de datos.
     *
     * @return lista de partidas
     * @throws Exception si ocurre un error durante la consulta
     */
    @Override
    public List<Partida> readall() throws Exception {

        List<Partida> lista = new ArrayList<>();

        String sql = "SELECT * FROM partida";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Usuario u = new Usuario();
                u.setId(rs.getInt("usuario_id"));

                Partida p = new Partida(
                    u,
                    convertirStringALista(rs.getString("solucion")),
                    rs.getInt("intentos"),
                    rs.getString("resultado")
                );

                lista.add(p);
            }
        }

        return lista;
    }

    /**
     * Obtiene todas las partidas asociadas
     * a un usuario específico.
     *
     * @param usuarioId identificador del usuario
     * @return lista de partidas del usuario
     */
    public List<Partida> obtenerPorUsuario(int usuarioId) {

        List<Partida> lista = new ArrayList<>();

        String sql = "SELECT * FROM partida WHERE usuario_id = ?";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Usuario u = new Usuario();
                u.setId(rs.getInt("usuario_id"));

                Partida p = new Partida(
                    u,
                    convertirStringALista(rs.getString("solucion")),
                    rs.getInt("intentos"),
                    rs.getString("resultado")
                );

                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Convierte una lista de números enteros
     * en una cadena separada por comas.
     *
     * @param lista lista de números
     * @return representación en texto de la lista
     */
    private String convertirListaAString(List<Integer> lista) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < lista.size(); i++) {
            sb.append(lista.get(i));
            if (i < lista.size() - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }

    /**
     * Convierte una cadena separada por comas
     * en una lista de números enteros.
     *
     * @param texto cadena con números separados por comas
     * @return lista de números enteros
     */
    private List<Integer> convertirStringALista(String texto) {

        List<Integer> lista = new ArrayList<>();

        if (texto == null || texto.isEmpty()) return lista;

        texto = texto.replace("[", "").replace("]", "");

        for (String s : texto.split(",")) {
            lista.add(Integer.parseInt(s.trim()));
        }

        return lista;
    }
}