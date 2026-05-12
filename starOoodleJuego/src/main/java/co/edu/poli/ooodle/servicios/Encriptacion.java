package co.edu.poli.ooodle.servicios;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Clase utilitaria encargada de realizar
 * operaciones de encriptación y verificación
 * de contraseñas utilizando BCrypt.
 * <p>
 * Permite generar hashes seguros para almacenar
 * contraseñas y validar contraseñas ingresadas
 * por los usuarios.
 * </p>
 */
public class Encriptacion {
    
    /**
     * Genera un hash seguro de una contraseña
     * utilizando el algoritmo BCrypt.
     *
     * @param password contraseña en texto plano
     * @return contraseña encriptada
     */
    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    /**
     * Verifica si una contraseña en texto plano
     * coincide con un hash almacenado.
     *
     * @param passwordPlano contraseña ingresada en texto plano
     * @param hashGuardado hash almacenado previamente
     * @return {@code true} si la contraseña coincide,
     *         {@code false} en caso contrario
     */
    public static boolean verificar(String passwordPlano, String hashGuardado) {
        return BCrypt.checkpw(passwordPlano, hashGuardado);
    }

}