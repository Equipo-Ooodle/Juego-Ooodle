package juego.ooodle;

import javax.swing.JOptionPane;
import java.util.List;

public class IniciarSesion {

    public Usuario iniciarSesion(List<Usuario> usuarios) {

        while (true) {

            String nombre = JOptionPane.showInputDialog("Nombre de usuario:");

            if (nombre == null) {
                return null; // Cancelar
            }

            String contraseña = JOptionPane.showInputDialog("Contraseña:");

            if (contraseña == null) {
                return null; // Cancelar
            }

            for (Usuario u : usuarios) {
                if (u.getNombre().equals(nombre) &&
                    u.getContraseña().equals(contraseña)) {

                    JOptionPane.showMessageDialog(null,
                        "Ha iniciado sesión con éxito. Bienvenido " + u.getNombre());

                    return u;
                }
            }

            JOptionPane.showMessageDialog(null,
                "Datos incorrectos. Intente nuevamente.");
        }
    }
}
