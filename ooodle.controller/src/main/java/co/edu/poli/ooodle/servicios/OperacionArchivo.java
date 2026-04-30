package co.edu.poli.ooodle.servicios;

import java.util.List;

import co.edu.poli.ooodle.modelo.Usuario;

public interface OperacionArchivo<T> {

	String serializar(List<T> elementos, String path, String name);

	List<T> deserializar(String path, String name);

}
