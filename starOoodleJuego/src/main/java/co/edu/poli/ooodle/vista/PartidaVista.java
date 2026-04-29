package co.edu.poli.ooodle.vista;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import co.edu.poli.ooodle.controller.PartidaController;

public class PartidaVista {

    private final Principal main;
    private final PartidaController controller;
    private final String modo;

    private final List<TextField> campos = new ArrayList<>();
    private final Label estado = new Label();
    private final Label contadorIntentos = new Label();

    private final Button btnIntentar = new Button("Intentar");
    private final Button btnReintentar = new Button("Intentar de nuevo");
    private final Button btnMenu = new Button("Volver al menú");

    public PartidaVista(Principal main, PartidaController controller, String modo) {
        this.main = main;
        this.controller = controller;
        this.modo = modo;
    }

    public void mostrar(Stage stage) {

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);

        Label ecuacion = new Label(controller.getEcuacion());

        contadorIntentos.setText(
                "Intento 0 de " + controller.getMaxIntentos()
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

        btnReintentar.setOnAction(e -> main.mostrarPartida(modo));
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

        boolean todosVerdes = true;

        for (int i = 0; i < 4; i++) {

            try {
                int valor = Integer.parseInt(campos.get(i).getText());

                if (controller.numeroBloqueado(valor)) {
                    estado.setText("El número " + valor + " ya no puede usarse");
                    return;
                }

                String resultado = controller.validarNumero(i, valor);

                switch (resultado) {

                    case "VERDE":
                        campos.get(i).setStyle("-fx-background-color: lightgreen;");
                        campos.get(i).setEditable(false);
                        break;

                    case "AMARILLO":
                        campos.get(i).setStyle("-fx-background-color: khaki;");
                        todosVerdes = false;
                        break;

                    default:
                        campos.get(i).setStyle("-fx-background-color: lightgray;");
                        todosVerdes = false;
                }

            } catch (NumberFormatException e) {
                estado.setText("Ingresa solo números");
                return;
            }
        }

        controller.sumarIntento();

        contadorIntentos.setText(
                "Intento " + controller.getIntentos() +
                " de " + controller.getMaxIntentos()
        );

        if (controller.gano(todosVerdes)) {

            estado.setText("GANASTE");

            controller.guardarSiEsDiaria("GANADA");

            btnIntentar.setDisable(true);
            btnReintentar.setVisible(true);
            return;
        }

        if (controller.perdio()) {

            controller.guardarSiEsDiaria("PERDIDA");

            estado.setText(
                    "PERDISTE - Solución: " +
                    controller.getSolucion()
            );

            btnIntentar.setDisable(true);
            btnReintentar.setVisible(true);
        }
    }
}