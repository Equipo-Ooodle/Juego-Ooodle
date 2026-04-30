package co.edu.poli.ooodle.controller;

import java.util.ArrayList;
import java.util.List;



public class ValidadorDatos {
	// Validación de Usuario
	public static List<String> validarUsuario(String id, String nombre, String correo, String contraseña) {
		List<String> errores = new ArrayList<>();

		// Validar ID
		if (id == null || id.trim().isEmpty()) {
			errores.add("El campo 'ID' es obligatorio.");
		} else {
			try {
				int valorId = Integer.parseInt(id);
				if (valorId < 1 || valorId > 1000) {
					errores.add("El 'ID' debe ser un número entre 1 y 1000.");
				}
			} catch (NumberFormatException e) {
				errores.add("El campo 'ID' debe ser un número entero.");
			}
		}

		// Validar nombre
		if (nombre == null || nombre.trim().isEmpty()) {
			errores.add("El campo 'Nombre' es obligatorio.");
		}

		// Validar correo institucional
		if (correo == null || correo.trim().isEmpty()) {
			errores.add("El campo 'Correo Institucional' es obligatorio.");
		} else if (!correo.endsWith("@poligran.edu.co")) {
			errores.add("El correo debe terminar en '@poligran.edu.co'.");
		}

		// Validar contraseña 
		if (contraseña == null || contraseña.trim().isEmpty()) {
			errores.add("El campo 'Contraseña' es obligatorio.");
		} else if (!contraseña.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&]).{8,}$")) {
			errores.add(
					"La contraseña debe tener mínimo 8 caracteres, incluir al menos una letra, un número y un símbolo.");
		}

		return errores;
	}
}
