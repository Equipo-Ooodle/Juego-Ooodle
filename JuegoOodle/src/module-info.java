module JuegoOodle {
    requires javafx.fxml;
	requires java.desktop;
	requires javafx.controls;
	requires java.logging;
	
	opens application to javafx.graphics, javafx.fxml;
}


