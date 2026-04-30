package co.edu.poli.ooodle.modelo;

import java.io.*;
import java.util.*;

public class Usuario {
	private int id; // Indetificado Unico
	private String contrasena;
	private String nombre;
	private String correoInstitucional;

	public Usuario(int id, String contrasena, String nombre, String correoInstitucional) {
		this.id = id;
		this.contrasena = contrasena;
		this.nombre = nombre;
		this.correoInstitucional = correoInstitucional;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreoInstitucional() {
		return correoInstitucional;
	}

	public void setCorreoInstitucional(String correoInstitucional) {
		this.correoInstitucional = correoInstitucional;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", contrasena=" + contrasena + ", nombre=" + nombre + ", correoInstitucional="
				+ correoInstitucional + "]";
	}

}