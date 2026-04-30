package co.edu.poli.ooodle.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

import co.edu.poli.ooodle.modelo.Usuario;
import co.edu.poli.ooodle.servicios.ImplementacionCRUDPartida;
import co.edu.poli.ooodle.servicios.ImplementacionCRUDUsuario;
import javafx.event.ActionEvent;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorUsuario implements Initializable {

	@FXML
	private Button bttCreate;

	@FXML
	private Button bttanterior;

	@FXML
	private TableColumn<Usuario, String> columnE1;

	@FXML
	private TableColumn<Usuario, String> columnE2;

	@FXML
	private TableColumn<Usuario, String> columnE3;

	@FXML
	private Label lblContrasena;

	@FXML
	private Label lblCorreoInstitucional;

	@FXML
	private Label lblId;

	@FXML
	private Label lblNombre;

	@FXML
	private TableView<Usuario> tblView1;

	@FXML
	private TextField txtContrasena;

	@FXML
	private TextField txtCorreoInstitucional;

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtNombre;

	private ControladorLogin controladorLogin;

	private ImplementacionCRUDUsuario crudUsuario = new ImplementacionCRUDUsuario();
	private ImplementacionCRUDPartida crudPartida = new ImplementacionCRUDPartida();
	private Stage stage;
	// Lista observable de estudiantes, tomada del CRUD
		private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();

	@FXML
	void create(ActionEvent event) {
		 String id = txtId.getText();
	        String nombre = txtNombre.getText();
	        String correo = txtCorreoInstitucional.getText();
	        String contrasena = txtContrasena.getText();
	        
	        List<String> errores = ValidadorDatos.validarUsuario(id, nombre, correo, contrasena);
	        
	        if (!errores.isEmpty()) {
	            Alert alerta = new Alert(Alert.AlertType.ERROR);
	            alerta.setTitle("Error de validación");
	            alerta.setHeaderText("Corrige los siguientes errores:");
	            alerta.setContentText(String.join("\n", errores));
	            alerta.show();
	            return;
	        }

	        int idNum = Integer.parseInt(id);

	        // Verifica si el ID ya existe usando el CRUD
	        if (crudUsuario.existeId(idNum)) {
	            Alert alerta = new Alert(Alert.AlertType.ERROR);
	            alerta.setTitle("ID duplicado");
	            alerta.setHeaderText(null);
	            alerta.setContentText("Ya existe un estudiante con ese ID.");
	            alerta.show();
	            return;
	        }

	        Usuario nuevo = new Usuario(idNum, contrasena, nombre, correo);
	        crudUsuario.agregarUsuario(nuevo);

	        // Actualiza la lista observable y la tabla
	        usuarios.add(nuevo);
	        loadTableUsuarios();

	        clear();
	    }
	

	// Nuevo método init para recibir los CRUDs
		public void init(Stage stage, ControladorLogin controladorLogin, ImplementacionCRUDUsuario crudUsuario, ImplementacionCRUDPartida crudPartida) {
			this.controladorLogin = controladorLogin;
			this.stage = stage;
			this.crudUsuario = crudUsuario;
			this.crudPartida = crudPartida;

			// Inicializa la lista observable desde el CRUD
			usuarios.setAll(crudUsuario.getUsuarios());
			loadTableUsuarios();
		}

		public void refrescarUsuarios() {
			usuarios.setAll(crudUsuario.getUsuarios());
			
		}

		@FXML
		void ShowLogin(ActionEvent event) {
			controladorLogin.show();
			stage.close();
		}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	private void loadTableUsuarios() {
		if (usuarios.isEmpty()) {
			System.out.println("La lista de estudiantes está vacía");
		} else {
			System.out.println("Cargando estudiantes...");
		}

		columnE1.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId())));
		columnE2.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNombre()));
		columnE3.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCorreoInstitucional()));

		tblView1.setItems(usuarios);
	}

	private void clear() {
		txtId.clear();
		txtContrasena.clear();
		txtNombre.clear();
		txtCorreoInstitucional.clear();

	}

}
