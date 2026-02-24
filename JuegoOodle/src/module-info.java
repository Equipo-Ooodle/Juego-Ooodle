module JuegoOodle {
    requires javafx.fxml;
	requires java.desktop;
	requires javafx.controls;
	
	opens application to javafx.graphics, javafx.fxml;
}


