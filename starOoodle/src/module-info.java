module starOoodle {
	requires javafx.fxml;
	requires java.desktop;
	requires javafx.controls;
	requires java.logging;



    opens co.edu.poli.ooodle.vista to javafx.fxml;
    exports co.edu.poli.ooodle.vista;
}
