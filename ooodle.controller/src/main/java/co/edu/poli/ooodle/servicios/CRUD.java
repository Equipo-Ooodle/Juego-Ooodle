package co.edu.poli.ooodle.servicios;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.poli.ooodle.modelo.Usuario;

//Interface CRUD:

public interface CRUD<T, I> {

	String create(T t);

	T read(I identificador);

	List<T> readAll();

	String update(I identificador, T t);

	String delete(I identificador);

}
