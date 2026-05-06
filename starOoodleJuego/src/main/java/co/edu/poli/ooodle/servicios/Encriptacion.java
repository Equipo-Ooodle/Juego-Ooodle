package co.edu.poli.ooodle.servicios;

import org.mindrot.jbcrypt.BCrypt;

public class Encriptacion {
	
	public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    
    public static boolean verificar(String passwordPlano, String hashGuardado) {
        return BCrypt.checkpw(passwordPlano, hashGuardado);
    }

}
