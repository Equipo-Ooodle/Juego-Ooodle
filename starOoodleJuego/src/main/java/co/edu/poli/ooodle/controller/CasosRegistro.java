package co.edu.poli.ooodle.controller;

/**
 * Enumeración que representa los posibles
 * resultados del proceso de registro de usuario.
 * 
 * Se utiliza para controlar validaciones y
 * estados durante el registro en el sistema.
 * 
 * @author Mia
 * @version 1.0
 */
public enum CasosRegistro {
	
	/**
     * El nombre de usuario está vacío.
     */
	NombreVacio,

    /**
     * La contraseña está vacía.
     */
	ContrasenaVacia,

    /**
     * El usuario ya existe en el sistema.
     */
	UsuarioYaExiste,

    /**
     * El registro se realizó exitosamente.
     */
	RegistroExitoso,

    /**
     * Ocurrió un error durante el registro.
     */
	Error

}