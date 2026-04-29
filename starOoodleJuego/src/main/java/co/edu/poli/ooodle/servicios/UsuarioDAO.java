package co.edu.poli.ooodle.servicios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import co.edu.poli.ooodle.modelo.Usuario;

public class UsuarioDAO implements CRUD<Usuario> {

    // 🔹 CREATE (registro)
    @Override
    public String create(Usuario u) throws Exception {

        String sql = "INSERT INTO usuario (nombre, contrasena) VALUES (?, ?)";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getContraseña()); // ya viene hasheada

            stmt.executeUpdate();
            return "OK";

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    // 🔹 Buscar por nombre (CLAVE para login)
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

    public boolean existeUsuario(String nombre) {
        return buscarPorNombre(nombre) != null;
    }

    // 🔹 READ ONE
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

    // 🔹 READ ALL
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