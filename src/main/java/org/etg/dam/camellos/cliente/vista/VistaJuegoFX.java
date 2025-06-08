package org.etg.dam.camellos.cliente.vista;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.awt.Desktop;
import java.io.File;

import org.etg.dam.camellos.cliente.util.Constantes;


public class VistaJuegoFX extends Application {

    private static String nombreJugador;
    private final ProgressBar barra1 = new ProgressBar(0);
    private final ProgressBar barra2 = new ProgressBar(0);

    private final Label nombreLabel1 = new Label();
    private final Label nombreLabel2 = new Label("Rival");

    private final Label puntos1 = new Label("0 / 100");
    private final Label puntos2 = new Label("0 / 100");

    private final Label resultado = new Label();

    private final Image camelloImg = new Image(getClass().getResource("/img/camello.png").toExternalForm(), 28, 28, true, true);
    private final ImageView icono1 = new ImageView(camelloImg);
    private final ImageView icono2 = new ImageView(camelloImg);

    public VistaJuegoFX() {}

    public VistaJuegoFX(String nombre) {
        nombreJugador = nombre;
    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(25));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #2e2e2e, #1c1c1c);");

        nombreLabel1.setText(nombreJugador);
        nombreLabel1.setFont(Font.font("Segoe UI", 16));
        nombreLabel1.setStyle("-fx-text-fill: #f0f0f0;");
        nombreLabel2.setFont(Font.font("Segoe UI", 16));
        nombreLabel2.setStyle("-fx-text-fill: #f0f0f0;");

        puntos1.setFont(Font.font("Consolas", 13));
        puntos1.setStyle("-fx-text-fill: #cccccc;");
        puntos2.setFont(Font.font("Consolas", 13));
        puntos2.setStyle("-fx-text-fill: #cccccc;");

        resultado.setFont(Font.font("Segoe UI", 17));
        resultado.setStyle("-fx-font-weight: bold; -fx-text-fill: #ffaa00;");

       root.getChildren().addAll(
            crearFila(icono1, nombreLabel1, barra1, puntos1, "#0066cc"),
            crearFila(icono2, nombreLabel2, barra2, puntos2, "#ff6600"),
            resultado
        );


        Scene scene = new Scene(root, 520, 250);
        stage.setTitle("Camello Race — " + nombreJugador);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private HBox crearFila(ImageView icono, Label nombre, ProgressBar barra, Label puntos, String colorHex) {
        HBox fila = new HBox(20);
        fila.setAlignment(Pos.CENTER_LEFT);
        fila.setPadding(new Insets(10));
        fila.setStyle("-fx-background-color: #3a3a3a; -fx-background-radius: 8; -fx-border-color: #555; -fx-border-radius: 8;");

        VBox datos = new VBox(5, nombre, puntos);
        datos.setAlignment(Pos.CENTER_LEFT);

        barra.setPrefWidth(280);
        barra.setStyle("-fx-accent: " + colorHex + "; -fx-control-inner-background: #1e1e1e;");

        fila.getChildren().addAll(icono, datos, barra);
        return fila;
    }

    public void actualizarCamello(int posicion) {
        double progreso = Math.min(posicion / 100.0, 1.0);
        barra1.setProgress(progreso);
        puntos1.setText(posicion + " / 100");
    }

    public void actualizarRival(String nombre, int posicion) {
        nombreLabel2.setText(nombre);
        double progreso = Math.min(posicion / 100.0, 1.0);
        barra2.setProgress(progreso);
        puntos2.setText(posicion + " / 100");
    }

    public void mostrarGanador(boolean esGanador) {
    if (esGanador) {
        resultado.setText(Constantes.MSG_ENHORABUENA_CARRERA);
        resultado.setStyle("-fx-text-fill: #00cc66; -fx-font-weight: bold;");

        // Crear botón para abrir el PDF
        Button abrirCertificado = new Button(Constantes.MSG_ABRIR_CERTIFICADO);
        abrirCertificado.setOnAction(_ -> {
            try {
                File pdf = new File(Constantes.RAIZ_NOMBRE_CERTIFICADO_PDF + nombreJugador + ".pdf");
                if (pdf.exists()) {
                    Desktop.getDesktop().open(pdf);
                } else {
                    System.err.println(Constantes.ERROR_NO_SE_ENCUENTRA_PDF);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Añadir el botón debajo del resultado
        VBox contenedor = (VBox) resultado.getParent();
        contenedor.getChildren().add(abrirCertificado);

    } else {
        resultado.setText(Constantes.MSG_DERROTA_JUGADOR);
        resultado.setStyle("-fx-text-fill: #cc3333; -fx-font-weight: bold;");
    }
}

}
