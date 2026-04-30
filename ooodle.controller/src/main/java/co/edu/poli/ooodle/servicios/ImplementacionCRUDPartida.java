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

import co.edu.poli.ooodle.modelo.Partida;

public class ImplementacionCRUDPartida implements CRUD<Partida, String>, OperacionArchivo<Partida> {

	// Usamos ArrayList en lugar de un arreglo fijo
	private List<Partida> partidas;

	public ImplementacionCRUDPartida() {
		this.partidas = new ArrayList<>();
	}

	@Override
	public String create(Partida t) {
		 // Validar unicidad de codigoAsignatura (y opcionalmente idEstudiante)
	    for (Partida m : partidas) {
	        if (m.getCodigoPartida().equals(t.getCodigoPartida()) && m.getIdUsuario() == t.getIdUsuario()) {
	            return "Ya existe una partida con ese código de asignatura para este estudiante.";
	        }
	    }
	    partidas.add(t);
	    return "Partida agregada exitosamente.";
	}
	
	@Override
	public Partida read(String identificador) {
		for (Partida m : partidas) {
			// Se usa equals para comparar Strings
			if (m != null && m.getCodigoPartida().equals(identificador)) {
				return m;
			}
		}
		return null;
	}

	@Override
	public List<Partida> readAll() {
		return partidas;
	}

	@Override
	public String update(String identificador, Partida t) {
		for (int i = 0; i < partidas.size(); i++) {
			Partida m = partidas.get(i);
			if (m != null && m.getCodigoPartida().equals(identificador)) {
				partidas.set(i, t);
				return "Partida actualizada exitosamente.";
			}
		}
		return "La partida con código único " + identificador + " no ha sido encontrada.";
	}

	@Override
	public String delete(String identificador) {
		Iterator<Partida> iter = partidas.iterator();
		while (iter.hasNext()) {
			Partida m = iter.next();
			if (m != null && m.getCodigoPartida().equals(identificador)) {
				iter.remove();
				return "Partida eliminada exitosamente.";
			}
		}
		return "La partida con código único " + identificador + " no ha sido encontrada.";
	}

	// Serialización de la lista de partidas
	@Override
	public String serializar(List<Partida> elementos, String path, String name) {
		try (FileOutputStream fos = new FileOutputStream(path + name);
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(elementos);
			return "Archivo guardado exitosamente.";
		} catch (IOException ioe) {
			return "Error al guardar archivo: " + ioe.getMessage();
		}
	}

	// Deserialización de la lista de materias
	@Override
	@SuppressWarnings("unchecked") // Cast seguro porque sabemos que el archivo contiene una List<Partida>
	public List<Partida> deserializar(String path, String name) {
		try (FileInputStream fis = new FileInputStream(path + name);
				ObjectInputStream ois = new ObjectInputStream(fis)) {
			List<Partida> leidos = (List<Partida>) ois.readObject();
			this.partidas = leidos; // actualiza tu lista interna si corresponde
			return leidos;
		} catch (FileNotFoundException e) {
			// Archivo no existe
			System.out.println("Archivo no encontrado: " + path + name);
			return null; // retorna null si no existe el archivo
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	// Permite establecer la lista de materias desde fuera, por ejemplo tras
	// deserializar
	public void setPartidas(List<Partida> partidasDes) {
		this.partidas = partidasDes;
	}

	// Si necesitas obtener la lista directamente
	public List<Partida> getPartidas() {
		return partidas;
	}

}
