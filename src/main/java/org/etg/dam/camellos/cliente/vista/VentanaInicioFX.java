package org.etg.dam.camellos.cliente.vista;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.etg.dam.camellos.cliente.Cliente;

public class VentanaInicioFX {

    public void mostrarVentana(Stage stagePrincipal) {
        Label label = new Label("Introduce tu nombre:");
        TextField nombreInput = new TextField();
        nombreInput.setPromptText("Ej: Joel");

        Button btnUnirse = new Button("Unirse");
        btnUnirse.setOnAction(_ -> {
            String nombre = nombreInput.getText().trim();
            if (!nombre.isEmpty()) {
                stagePrincipal.close();
                Cliente.iniciarCarrera(nombre);
            }
        });

        VBox root = new VBox(10, label, nombreInput, btnUnirse);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 300, 150);
        stagePrincipal.setScene(scene);
        stagePrincipal.setTitle("🐪 Unirse a la carrera");
        stagePrincipal.setResizable(false);
        stagePrincipal.show();
    }
}
