package co.edu.poli.ooodle.servicios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import co.edu.poli.ooodle.modelo.Partida;
import co.edu.poli.ooodle.modelo.Usuario;

public class PartidaDAO implements CRUD<Partida> {

    
    @Override
    public String create(Partida p) throws Exception {

        String sql = "INSERT INTO partida (resultado, intentos, solucion, usuario_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getResultado());
            stmt.setInt(2, p.getIntentos());
            stmt.setString(3, convertirListaAString(p.getSolucion())); // 🔥 CORREGIDO
            stmt.setInt(4, p.getUsuario().getId());

            stmt.executeUpdate();
            return "OK";
        }
    }

    
    @Override
    public Partida readone(Object id) throws Exception {

        String sql = "SELECT * FROM partida WHERE id = ?";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, (Integer) id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Partida p = new Partida();

                p.setResultado(rs.getString("resultado"));
                p.setIntentos(rs.getInt("intentos"));
                p.setSolucion(convertirStringALista(rs.getString("solucion")));

                Usuario u = new Usuario();
                u.setId(rs.getInt("usuario_id"));
                p.setUsuario(u);

                return p;
            }
        }

        return null;
    }

    
    @Override
    public List<Partida> readall() throws Exception {

        List<Partida> lista = new ArrayList<>();

        String sql = "SELECT * FROM partida";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Partida p = new Partida();

                p.setResultado(rs.getString("resultado"));
                p.setIntentos(rs.getInt("intentos"));
                p.setSolucion(convertirStringALista(rs.getString("solucion")));

                Usuario u = new Usuario();
                u.setId(rs.getInt("usuario_id"));
                p.setUsuario(u);

                lista.add(p);
            }
        }

        return lista;
    }

    
    public List<Partida> obtenerPorUsuario(int usuarioId) {

        List<Partida> lista = new ArrayList<>();

        String sql = "SELECT * FROM partida WHERE usuario_id = ?";

        try (Connection conn = ConexionBD.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Partida p = new Partida();

                p.setResultado(rs.getString("resultado"));
                p.setIntentos(rs.getInt("intentos"));
                p.setSolucion(convertirStringALista(rs.getString("solucion")));

                Usuario u = new Usuario();
                u.setId(rs.getInt("usuario_id"));
                p.setUsuario(u);

                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    

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