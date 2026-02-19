package juego.ooodle;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Registrarse {
    
    private List<Usuario> usuarios = new ArrayList<>();
    
    public Usuario registrar (){
        
         while (true) {

        String nombre = JOptionPane.showInputDialog("Ingrese nombre:");

        if (nombre == null) {
            return null; // Si cancela
        }

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "El nombre no puede estar vacío");
            continue;
        }

        String contraseña = JOptionPane.showInputDialog("Ingrese contraseña:");

        if (contraseña == null) {
            return null; // Si cancela
        }

        if (contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "La contraseña no puede estar vacía");
            continue;
        }

        boolean existe = false;

        for (Usuario u : usuarios) {
            if (u.getNombre().equals(nombre)) {
                existe = true;
                break;
            }
        }

        if (existe) {
            JOptionPane.showMessageDialog(null, 
                "El usuario ya existe. Intente con otro nombre.");
            continue; // 🔁 vuelve a empezar
        }

        Usuario nuevo = new Usuario(nombre, contraseña);
        usuarios.add(nuevo);

        JOptionPane.showMessageDialog(null, 
            "Usuario registrado correctamente");

        return nuevo; // ✅ sale del ciclo
    }
    }
    
    public List<Usuario> getUsuarios(){
        return usuarios;
    }
}
