package org.etg.dam.camellos.servidor.hilo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.etg.dam.camellos.cliente.util.Constantes;
import org.etg.dam.camellos.modelo.Jugador;
import org.etg.dam.camellos.servidor.modelo.Carrera;

public class HiloJugadorServidor implements Runnable {

    private final Socket socket;
    private final Carrera carrera;
    private PrintWriter out;
    private BufferedReader in;
    private Jugador jugador;

    public HiloJugadorServidor(Socket socket, Carrera carrera) {
        this.socket = socket;
        this.carrera = carrera;
    }

    @Override
    public void run() {
        try {
            // Establecer comunicación
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Recibir nombre del jugador
            String nombre = in.readLine();
            jugador = new Jugador(nombre, socket);
            carrera.agregarJugador(jugador);

            out.println(Constantes.MSG_ESPERANDO_JUGADOR);

            // Esperar a que se unan los dos jugadores
            while (!carrera.carreraLlena()) {
                Thread.sleep(500);
            }

            out.println(Constantes.MSG_INICIO_CARRERA);

            // Lógica de carrera
            boolean haGanado = false;
            while (carrera.getGanador() == null) {
                Thread.sleep(Constantes.TIEMPO_AVANCE_CAMELLOS);

                if (!haGanado) {
                    haGanado = carrera.avanzar(jugador);
                    enviarPosiciones();
                }
            }

            // Resultado final
            if (carrera.esGanador(jugador)) {
                out.println(Constantes.MSG_GANADOR);
            } else {
                out.println(Constantes.MSG_PERDEDOR);
            }

        } catch (Exception e) {
            System.err.println("Error en HiloJugadorServidor: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                System.err.println("Error cerrando socket: " + e.getMessage());
            }
        }
    }

    private void enviarPosiciones() {
        try {
            out.println(Constantes.POSICIONES);
            for (Jugador j : carrera.getJugadores()) {
                if (j != null) {
                    out.println(j.getNombre() + ": " + j.getPosicion());
                }
            }
            out.println("---");
            out.flush(); // Asegura el envío inmediato
        } catch (Exception ignored) {}
    }
}
