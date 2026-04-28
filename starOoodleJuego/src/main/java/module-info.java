module starOoodleJuego {

    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;

    requires java.sql;
    requires jbcrypt;

    opens co.edu.poli.ooodle.vista to javafx.fxml;

    exports co.edu.poli.ooodle.vista;
    exports co.edu.poli.ooodle.modelo;
    exports co.edu.poli.ooodle.servicios;
}