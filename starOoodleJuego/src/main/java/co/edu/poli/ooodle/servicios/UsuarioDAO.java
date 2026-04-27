package co.edu.poli.ooodle.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt;


public class UsuarioDAO {
	

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
}