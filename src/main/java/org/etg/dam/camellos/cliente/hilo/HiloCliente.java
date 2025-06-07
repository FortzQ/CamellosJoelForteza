package org.etg.dam.camellos.cliente.hilo;

import javafx.application.Platform;
import org.etg.dam.camellos.cliente.util.Constantes;
import org.etg.dam.camellos.cliente.vista.VistaJuegoFX;
import org.etg.dam.camellos.modelo.Jugador;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HiloCliente implements Runnable {

    private final Jugador jugador;
    private final VistaJuegoFX vista;
    private BufferedReader in;

    public HiloCliente(Jugador jugador, VistaJuegoFX vista) {
        this.jugador = jugador;
        this.vista = vista;
        try {
            this.in = new BufferedReader(new InputStreamReader(jugador.getSocket().getInputStream()));
        } catch (Exception e) {
            System.err.println("❌ Error al iniciar lectura del socket: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            String linea;
            while ((linea = in.readLine()) != null) {
                procesarMensaje(linea.trim());
            }
        } catch (Exception e) {
            System.err.println("❌ Error en hilo cliente: " + e.getMessage());
        }
    }

    private void procesarMensaje(String msg) {
        switch (msg) {
            case Constantes.MSG_GANADOR -> Platform.runLater(() -> vista.mostrarGanador(true));
            case Constantes.MSG_PERDEDOR -> Platform.runLater(() -> vista.mostrarGanador(false));
            case Constantes.POSICIONES -> recibirPosiciones();
            
        }
    }

    private void recibirPosiciones() {
        try {
            String linea;
            while ((linea = in.readLine()) != null && !linea.equals("---")) {
                String[] partes = linea.split(":");
                if (partes.length == 2) {
                    String nombre = partes[0].trim();
                    int posicion = Integer.parseInt(partes[1].trim());

                    Platform.runLater(() -> {
                        if (nombre.equals(jugador.getNombre())) {
                            jugador.setPosicion(posicion);
                            vista.actualizarCamello(posicion);
                        } else {
                            vista.actualizarRival(nombre, posicion);
                        }
                    });
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error al leer posiciones: " + e.getMessage());
        }
    }
}
