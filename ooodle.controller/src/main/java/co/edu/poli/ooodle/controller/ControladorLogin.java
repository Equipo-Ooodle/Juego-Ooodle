package co.edu.poli.ooodle.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.poli.ooodle.modelo.Partida;
import co.edu.poli.ooodle.modelo.Usuario;
import co.edu.poli.ooodle.servicios.ImplementacionCRUDPartida;
import co.edu.poli.ooodle.servicios.ImplementacionCRUDUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ControladorLogin implements Initializable {

	@FXML
	private Button bttCreateUsuario;

	@FXML
	private Button bttIngresar;

	@FXML
	private Button bttRecuperarDatos;

	@FXML
	private ImageView imwLogin;

	@FXML
	private Label lblContrasena;

	@FXML
	private Label lblId;

	@FXML
	private PasswordField pwdContrasena;

	@FXML
	private TextField txtId;

	private Stage stage;

	private ImplementacionCRUDUsuario crudUsuario = new ImplementacionCRUDUsuario();
	private ImplementacionCRUDPartida crudPartida = new ImplementacionCRUDPartida();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	// Método utilitario para mostrar alertas
	private void mostrarAlerta(String titulo, String mensaje) {
		Alert alerta = new Alert(Alert.AlertType.ERROR);
		alerta.setTitle(titulo);
		alerta.setHeaderText(null);
		alerta.setContentText(mensaje);
		alerta.show();
	}

	public void setStage(Stage primaryStage) {
		this.stage = primaryStage;

	}

	public void show() {
		stage.show();

	}

	@FXML
	void ingresar(ActionEvent event) {
		String idStr = txtId.getText();
		String contrasena = pwdContrasena.getText();

		// Validación básica de campos
		if (idStr.isEmpty() || contrasena.isEmpty()) {
			mostrarAlerta("Campos vacíos", "Por favor ingresa el ID y la contraseña.");
			return;
		}

		int id;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			mostrarAlerta("ID inválido", "El ID debe ser un número entero.");
			return;
		}

		System.out.println("Intentando login con ID: " + id + " Contraseña: " + contrasena);

		// Buscar usuario en el repositorio
		Usuario usuario = crudUsuario.buscarPorIdYContrasena(id, contrasena);
		if (usuario != null) {
			try {
				FXMLLoader loader = new FXMLLoader(
						getClass().getResource("/co/edu/poli/ooodle/vista/ShowPartida.fxml"));

				Parent root = loader.load();
				ControladorPartida controladorPartida = loader.getController();

				// Pasa el stage de login al de partidas
				controladorPartida.setLoginStage(this.stage);

				controladorPartida.init(usuario, crudPartida, crudUsuario);

				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/css/EstiloPartida.css").toExternalForm()); // Conexión
																												// css
				Stage stagePartida = new Stage();
				controladorPartida.setStage(stagePartida); // (opcional, si quieres tener referencia a tu stage de
															// materias)
				stagePartida.setScene(scene);
				stagePartida.show();

				// Limpiar campos después de login exitoso
				limpiarCampos();

				this.stage.hide(); // Puedes usar close() o hide() según prefieras
			} catch (IOException e) {
				mostrarAlerta("Error", "No se pudo abrir la ventana de partida.\n" + e.getMessage());
			}
		} else {
			mostrarAlerta("Acceso denegado", "ID o contraseña incorrectos.");
		}
	}

	@FXML
	void createUsuario(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/ooodle/vista/ShowUsuario.fxml"));
		Parent root = loader.load();
		ControladorUsuario controller = loader.getController();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		controller.init(stage, this, crudUsuario, crudPartida);
		stage.show();
		this.stage.close();
	}

	@FXML
	void deserializar(ActionEvent event) {
		Alert alerta = new Alert(Alert.AlertType.INFORMATION);

		List<Usuario> usuariosLeidos = crudUsuario.deserializar("", "usuarios.bin");
		List<Partida> partidasLeidas = crudPartida.deserializar("", "partidas.bin");

		if (usuariosLeidos == null || partidasLeidas == null) {
			alerta.setAlertType(Alert.AlertType.ERROR);
			alerta.setTitle("Error de deserialización");
			alerta.setHeaderText(null);
			alerta.setContentText("No existen archivos guardados para recuperar.");
		} else {
			crudUsuario.setUsuarios(usuariosLeidos);
			crudPartida.setPartidas(partidasLeidas);

			// Debug opcional
			System.out.println("Usuarios cargados tras deserialización:");
			for (Usuario e : crudUsuario.getUsuarios()) {
				System.out.println("ID: " + e.getId() + " Contraseña: " + e.getContrasena());
			}

			alerta.setTitle("Deserialización exitosa");
			alerta.setHeaderText(null);
			alerta.setContentText("Usuarios y partidas cargados correctamente.");
		}
		alerta.show();
	}

	/**
	 * Limpia los campos de login
	 */
	private void limpiarCampos() {
		txtId.clear();
		pwdContrasena.clear();
	}

}
