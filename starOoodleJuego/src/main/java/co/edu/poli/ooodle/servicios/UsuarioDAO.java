package co.edu.poli.ooodle.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import co.edu.poli.ooodle.modelo.Usuario;


public class UsuarioDAO implements CRUD<Usuario> {
	

	public boolean registrarUsuario(String nombre, String contraseña) {
	    String sql = "INSERT INTO usuario (nombre, contrasena) VALUES (?, ?)";

	    try (Connection conn = ConexionBD.getInstancia().getConexion();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, nombre);

	        // 🔐 HASH directo con BCrypt
	        String hash = BCrypt.hashpw(contraseña, BCrypt.gensalt(12));
	        stmt.setString(2, hash);

	        stmt.executeUpdate();
	        return true;

	    } catch (java.sql.SQLIntegrityConstraintViolationException e) {
	        return false;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

    public boolean existeUsuario(String nombre) {
        String sql = "SELECT * FROM usuario WHERE nombre = ?";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validarLogin(String nombre, String contraseña) {
        String sql = "SELECT contrasena FROM usuario WHERE nombre = ?";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hashBD = rs.getString("contrasena");

                // 🔐 verificación directa BCrypt
                return BCrypt.checkpw(contraseña, hashBD);
            }

            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int obtenerIdUsuario(String nombre, String contraseña) {
        String sql = "SELECT id, contrasena FROM usuario WHERE nombre = ?";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hashBD = rs.getString("contrasena");

                if (BCrypt.checkpw(contraseña, hashBD)) {
                    return rs.getInt("id");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public String create(Usuario u) throws Exception {

        String sql = "INSERT INTO usuario (nombre, contrasena) VALUES (?, ?)";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getNombre());

            // 🔐 BCrypt aquí (integrado en DAO)
            String hash = BCrypt.hashpw(u.getContraseña(), BCrypt.gensalt(12));
            stmt.setString(2, hash);

            stmt.executeUpdate();

            return "OK";

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    @Override
    public Usuario readone(Object id) throws Exception {

        Integer userId = (Integer) id;

        String sql = "SELECT * FROM usuario WHERE id = ?";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

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