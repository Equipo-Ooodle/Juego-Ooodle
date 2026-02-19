/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juego.ooodle;

/**
 *
 * @author Mia
 */
public class Pruebas {
    
    public static void main(String[] args) {

        Registrarse registro = new Registrarse();

        registro.registrar();
        registro.registrar();
        
        IniciarSesion iniciar = new IniciarSesion();
        
        iniciar.iniciarSesion(registro.getUsuarios());
    }
}
