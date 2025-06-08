package org.etg.dam.camellos.cliente.vista;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.etg.dam.camellos.cliente.Cliente;
import org.etg.dam.camellos.cliente.util.Constantes;

public class VentanaInicioFX {

    public void mostrarVentana(Stage stagePrincipal) {
        Label label = new Label(Constantes.TXT_INTRODUCIR_NOMBRE_CARRERA);
        TextField nombreInput = new TextField();
        nombreInput.setPromptText(Constantes.TXT_EJEMPLO_NOMBRE_VENTANA_INICIO);

        Button btnUnirse = new Button(Constantes.TXT_BOTON_UNIRSE_CARRERA);
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
        stagePrincipal.setTitle(Constantes.TXT_TITULO_VENTANA_INICIO);
        stagePrincipal.setResizable(false);
        stagePrincipal.show();
    }
}
