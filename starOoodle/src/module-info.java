module starOoodle {
	requires javafx.fxml;
	requires transitive java.sql;
	requires java.desktop;
	requires javafx.controls;
	requires transitive java.logging;
	requires transitive javafx.graphics;
	
	
	



    opens co.edu.poli.ooodle.vista to javafx.fxml;
    exports co.edu.poli.ooodle.vista;
    exports co.edu.poli.ooodle.servicios;
    exports co.edu.poli.ooodle.modelo;
    exports co.edu.poli.ooodle.controller;
    
    
}
