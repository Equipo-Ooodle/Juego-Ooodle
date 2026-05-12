package co.edu.poli.ooodle.servicios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import co.edu.poli.ooodle.modelo.Usuario;

/**
 * Clase encargada de gestionar las operaciones
 * de acceso a datos relacionadas con los usuarios.
 * <p>
 * Implementa la interfaz {@link CRUD} para realizar
 * operaciones de creación y consulta de usuarios
 * almacenados en la base de datos.
 * </p>
 */
public class UsuarioDAO implements CRUD<Usuario> {

    /**
     * Registra un nuevo usuario en la base de datos.
     *
     * @param u usuario a registrar
     * @return mensaje indicando el resultado de la operación
     * @throws Exception si ocurre un error durante el registro
     */
    @Override
    public String create(Usuario u) throws Exception {

        String sql = "INSERT INTO usuario (nombre, contrasena) VALUES (?, ?)";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getContraseña());

            stmt.executeUpdate();
            return "OK";

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    /**
     * Busca un usuario en la base de datos
     * mediante su nombre.
     *
     * @param nombre nombre del usuario
     * @return objeto {@link Usuario} encontrado
     *         o {@code null} si no existe
     */
    public Usuario buscarPorNombre(String nombre) {

        String sql = "SELECT * FROM usuario WHERE nombre = ?";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("contrasena")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Verifica si un usuario existe en la base de datos.
     *
     * @param nombre nombre del usuario
     * @return {@code true} si el usuario existe,
     *         {@code false} en caso contrario
     */
    public boolean existeUsuario(String nombre) {
        return buscarPorNombre(nombre) != null;
    }

    /**
     * Obtiene un usuario mediante su identificador.
     *
     * @param id identificador del usuario
     * @return objeto {@link Usuario} encontrado
     *         o {@code null} si no existe
     * @throws Exception si ocurre un error durante la consulta
     */
    @Override
    public Usuario readone(Object id) throws Exception {

        String sql = "SELECT * FROM usuario WHERE id = ?";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, (Integer) id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("contrasena")
                );
            }
        }

        return null;
    }

    /**
     * Obtiene todos los usuarios registrados
     * en la base de datos.
     *
     * @return lista de usuarios
     * @throws Exception si ocurre un error durante la consulta
     */
    @Override
    public List<Usuario> readall() throws Exception {

        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT * FROM usuario";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("contrasena")
                ));
            }
        }

        return lista;
    }
}