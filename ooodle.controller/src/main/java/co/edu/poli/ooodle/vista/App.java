package co.edu.poli.ooodle.vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import co.edu.poli.ooodle.controller.ControladorLogin;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/ooodle/vista/ShowLogin.fxml"));
    	Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/Estilo.css").toExternalForm()); // Conexión css
		//String css = this.getClass().getResource("Estilo.css").toExternalForm();
		//scene.getStylesheets.add(css);
		primaryStage.setScene(scene);
		ControladorLogin controller = loader.getController();
		controller.setStage(primaryStage);
		primaryStage.show();
    }

    static void setRoot(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
	}

	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}

	public static void main(String[] args) {
		launch();
	}
}