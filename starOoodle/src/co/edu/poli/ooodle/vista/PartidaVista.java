package co.edu.poli.ooodle.vista;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import co.edu.poli.ooodle.modelo.Partida;

public class PartidaVista {

    private final Principal main;
    private final Partida logica = new Partida();

    private final List<TextField> campos = new ArrayList<>();
    private final Label estado = new Label();

    private final Button btnIntentar = new Button("Intentar");
    private final Button btnReintentar = new Button("Intentar de nuevo");
    private final Button btnMenu = new Button("Volver al menú");

    private final Set<Integer> numerosBloqueados = new HashSet<>();
    private final Label contadorIntentos = new Label("Intento 0 de 6");

    public PartidaVista(Principal main) {
        this.main = main;
    }

    public void mostrar(Stage stage) {

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);

        Label ecuacion = new Label(logica.getEcuacionVisible());

        contadorIntentos.setStyle(
                "-fx-font-size: 14px; -fx-font-weight: bold;"
        );

        HBox filaCampos = new HBox(10);
        filaCampos.setAlignment(Pos.CENTER);

        for (int i = 0; i < 4; i++) {
            TextField campo = new TextField();
            campo.setPrefWidth(60);
            campos.add(campo);
            filaCampos.getChildren().add(campo);
        }

        btnIntentar.setOnAction(e -> validarIntento());

        btnMenu.setOnAction(e -> main.mostrarMenu());

        btnReintentar.setOnAction(e -> main.mostrarPartida());
        btnReintentar.setVisible(false);

        root.getChildren().addAll(
                new Label("Adivina los números de la ecuación"),
                ecuacion,
                filaCampos,
                contadorIntentos,
                btnIntentar,
                btnReintentar,
                btnMenu,
                estado
        );

        stage.setScene(new Scene(root, 500, 300));
        stage.setTitle("Partida");
        stage.show();
    }

    private void validarIntento() {

        if (logica.getIntentos() >= logica.getMaxIntentos()) {
            perderPartida();
            return;
        }

        boolean todosVerdes = true;

        for (int i = 0; i < 4; i++) {
            try {

                int valor = Integer.parseInt(
                        campos.get(i).getText()
                );

                if (numerosBloqueados.contains(valor)) {
                    estado.setText(
                            "El número " + valor +
                            " ya no puede volver a usarse"
                    );
                    return;
                }

                String resultado =
                        logica.validarNumero(i, valor);

                switch (resultado) {

                    case "VERDE":
                        campos.get(i).setStyle(
                                "-fx-background-color: lightgreen;"
                        );
                        campos.get(i).setEditable(false);
                        break;

                    case "AMARILLO":
                        campos.get(i).setStyle(
                                "-fx-background-color: khaki;"
                        );
                        todosVerdes = false;
                        break;

                    default:
                        campos.get(i).setStyle(
                                "-fx-background-color: lightgray;"
                        );
                        numerosBloqueados.add(valor);
                        todosVerdes = false;
                }

            } catch (NumberFormatException e) {
                estado.setText("Ingresa solo números");
                return;
            }
        }

        logica.sumarIntento();

        contadorIntentos.setText(
                "Intento " + logica.getIntentos() +
                " de " + logica.getMaxIntentos()
        );

        if (todosVerdes) {
            estado.setText(
                    "GANASTE en " +
                    logica.getIntentos() +
                    " intento(s)"
            );

            btnIntentar.setDisable(true);
            btnReintentar.setVisible(true);
            return;
        }

        if (logica.getIntentos() >= logica.getMaxIntentos()) {
            perderPartida();
        }
    }

    private void perderPartida() {

        estado.setText(
                "PERDISTE - Solución: " +
                logica.getSolucion()
        );

        btnIntentar.setDisable(true);
        btnReintentar.setVisible(true);

        for (TextField campo : campos) {
            campo.setEditable(false);
        }
    }
}