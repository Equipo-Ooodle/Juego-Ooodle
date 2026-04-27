package co.edu.poli.ooodle.controller;



import co.edu.poli.ooodle.servicios.UsuarioDAO;

public class RegistroController {

    private UsuarioDAO usuarioDAO;

    public RegistroController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public CasosRegistro registrar (String nombre, String contraseña) {

        if (nombre == null || nombre.trim().isEmpty()) {
            
            return CasosRegistro.NombreVacio;
        }

        if (contraseña == null || contraseña.trim().isEmpty()) {
            
            return CasosRegistro.ContrasenaVacia;
        }

        boolean registrado = usuarioDAO.registrarUsuario(nombre, contraseña);

        if (!registrado) {
            
            return CasosRegistro.UsuarioYaExiste;
        }

        return CasosRegistro.RegistroExitoso;
    }
}
