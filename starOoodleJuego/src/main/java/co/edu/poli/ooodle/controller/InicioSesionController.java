package co.edu.poli.ooodle.controller;

import co.edu.poli.ooodle.servicios.UsuarioDAO;

public class InicioSesionController {

    private UsuarioDAO usuarioDAO;

    public InicioSesionController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public boolean autenticar(String nombre, String contraseña) {
        return usuarioDAO.validarLogin(nombre, contraseña);
    }
    
    public int obtenerId(String nombre, String contraseña) {
        return usuarioDAO.obtenerIdUsuario(nombre, contraseña);
    }
        
}