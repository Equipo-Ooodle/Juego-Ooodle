module co.edu.poli.ooodle.controller {
	requires javafx.controls;
	requires javafx.fxml;
	

	opens co.edu.poli.ooodle.vista to javafx.fxml;
	opens co.edu.poli.ooodle.controller to javafx.fxml;

	exports co.edu.poli.ooodle.vista;
	exports co.edu.poli.ooodle.controller;

}
