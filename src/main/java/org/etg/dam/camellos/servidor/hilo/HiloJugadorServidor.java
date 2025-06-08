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
            // establece la comunicación con el cliente (entrada y salida)
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // recibe el nombre del jugador
            String nombre = in.readLine();
            jugador = new Jugador(nombre, socket);
            carrera.agregarJugador(jugador);


            out.println(Constantes.MSG_ESPERANDO_JUGADOR);

            // actualiza el servidor y comprueba si la carrera está llena
            while (!carrera.carreraLlena()) {
                Thread.sleep(Constantes.TIEMPO_ESPERA_JUGADORES_CONECTAODOS);
            }

            out.println(Constantes.MSG_INICIO_CARRERA);

            // lógica de carrera
            boolean haGanado = false;
            while (carrera.getGanador() == null) {
                Thread.sleep(Constantes.TIEMPO_AVANCE_CAMELLOS);

                if (!haGanado) {
                    haGanado = carrera.avanzar(jugador);
                    enviarPosiciones();
                }
            }

            // envia mensajes de los resultados finales 
            if (carrera.esGanador(jugador)) {
                out.println(Constantes.MSG_GANADOR);
            } else {
                out.println(Constantes.MSG_PERDEDOR);
            }

        } catch (Exception e) {
            System.err.println(Constantes.MSG_ERROR_CLASE_HILO_JUGADOR_SERVIDOR + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                System.err.println(Constantes.MSG_ERROR_CIERRE_SOCKET + e.getMessage());
            }
        }
    }

    //envia  a los clientes en tiempo real las posiciones de los jugadores
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
