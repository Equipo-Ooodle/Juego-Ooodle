package co.edu.poli.ooodle.servicios;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import co.edu.poli.ooodle.modelo.Usuario;

//Clase ImplemetacionCRUD:
public class ImplementacionCRUDUsuario implements CRUD<Usuario, Integer>, OperacionArchivo<Usuario> {

	private List<Usuario> usuarios;

	public ImplementacionCRUDUsuario() {
		usuarios = new ArrayList<>();
	}

	@Override
	public String create(Usuario t) {
		usuarios.add(t);
		return "Usuario agregado exitosamente.";
	}

	@Override
	public Usuario read(Integer identificador) {
		for (Usuario e : usuarios) {
			if (e != null && e.getId() == identificador) {
				return e;
			}
		}
		return null;
	}

	@Override
	public List<Usuario> readAll() {
		return usuarios;
	}

	@Override
	public String update(Integer identificador, Usuario t) {
		for (int i = 0; i < usuarios.size(); i++) {
			Usuario e = usuarios.get(i);
			if (e != null && e.getId() == identificador) {
				usuarios.set(i, t);
				return "Usuario actualizado exitosamente.";
			}
		}
		return "El Usuario con id " + identificador + " no ha sido encontrado.";
	}

	@Override
	public String delete(Integer identificador) {
		Iterator<Usuario> it = usuarios.iterator();
		while (it.hasNext()) {
			Usuario e = it.next();
			if (e != null && e.getId() == identificador) {
				it.remove();
				return "Usuario eliminado exitosamente.";
			}
		}
		return "Usuario con id " + identificador + " no ha sido encontrado.";
	}

	// Serialización de la lista de usuarios
	@Override
	public String serializar(List<Usuario> elementos, String path, String name) {
		try (FileOutputStream fos = new FileOutputStream(path + name);
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(elementos);
			return "Archivo guardado exitosamente.";
		} catch (IOException ioe) {
			return "Error al guardar archivo: " + ioe.getMessage();
		}
	}

	// Deserialización de la lista de Usuarios
	@Override
	@SuppressWarnings("unchecked") // Cast seguro porque sabemos que el archivo contiene una List<Usuario>
	public List<Usuario> deserializar(String path, String name) {
		try (FileInputStream fis = new FileInputStream(path + name);
				ObjectInputStream ois = new ObjectInputStream(fis)) {
			List<Usuario> leidos = (List<Usuario>) ois.readObject();
			this.usuarios = leidos;
			return leidos;
		} catch (FileNotFoundException e) {
			// Archivo no existe
			System.out.println("Archivo no encontrado: " + path + name);
			return null; // <- Esto permite distinguir el caso en el controlador
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	// Permite establecer la lista de estudiantes desde fuera, por ejemplo tras
	// deserializar
	public void setUsuarios(List<Usuario> usuariosDes) {
		this.usuarios = usuariosDes;
	}

	// Si necesitas obtener la lista directamente
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	/**
	 * Busca un estudiante por su ID y contraseña.
	 * 
	 * @param id         El ID del usuario.
	 * @param contrasena La contraseña del usuario.
	 * @return El usuario si existe, o null si no se encuentra.
	 */
	public Usuario buscarPorIdYContrasena(int id, String contrasena) {
		for (Usuario e : usuarios) {
			if (e.getId() == id && e.getContrasena().equals(contrasena)) {
				return e;
			}
		}
		return null;
	}

	// Verifica si ya existe un usuarios con ese ID
	public boolean existeId(int id) {
		for (Usuario e : usuarios) {
			if (e.getId() == id) {
				return true;
			}
		}
		return false;
	}

	// Agrega un usuarios a la lista interna
	public void agregarUsuario(Usuario usuario) {
		usuarios.add(usuario);
	}

}
