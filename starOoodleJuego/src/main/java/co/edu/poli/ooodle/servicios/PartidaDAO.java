package co.edu.poli.ooodle.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import co.edu.poli.ooodle.modelo.Partida;

public class PartidaDAO {

    public boolean guardarPartida(Partida partida, String resultado, int usuarioId) {

        String sql = "INSERT INTO partida (resultado, intentos, solucion, usuario_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, resultado);
            stmt.setInt(2, partida.getIntentos());
            stmt.setString(3, partida.getSolucion().toString());
            stmt.setInt(4, usuarioId);

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String obtenerHistorial(int usuarioId) {

        StringBuilder historial = new StringBuilder();

        String sql = "SELECT resultado, intentos, solucion FROM partida WHERE usuario_id = ?";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                historial.append("Resultado: ")
                        .append(rs.getString("resultado"))
                        .append(" | Intentos: ")
                        .append(rs.getInt("intentos"))
                        .append(" | Solución: ")
                        .append(rs.getString("solucion"))
                        .append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return historial.toString();
    }
}