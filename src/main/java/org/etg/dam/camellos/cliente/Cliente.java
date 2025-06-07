package org.etg.dam.camellos.cliente;

import javafx.application.Application;
import javafx.stage.Stage;
import org.etg.dam.camellos.cliente.hilo.HiloCliente;
import org.etg.dam.camellos.cliente.util.Constantes;
import org.etg.dam.camellos.cliente.vista.VentanaInicioFX;
import org.etg.dam.camellos.cliente.vista.VistaJuegoFX;
import org.etg.dam.camellos.modelo.Jugador;

import java.io.IOException;
import java.net.Socket;

public class Cliente extends Application {

    private static Jugador jugador;
    private static HiloCliente hilo;
    private static VistaJuegoFX vista;

    @Override
    public void start(Stage primaryStage) {
        new VentanaInicioFX().mostrarVentana(primaryStage);
    }

    public static void iniciarCarrera(String nombreJugador) {
        try {
            Socket socket = new Socket("localhost", Constantes.NUMERO_PUERTO);
            jugador = new Jugador(nombreJugador, socket);

            // Enviar el nombre al servidor
            jugador.getSocket().getOutputStream().write((nombreJugador + "\n").getBytes());

            vista = new VistaJuegoFX(nombreJugador);
            vista.start(new Stage());

            hilo = new HiloCliente(jugador, vista);
            new Thread(hilo).start();

        } catch (IOException e) {
            System.err.println(" Error al conectar con el servidor: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
