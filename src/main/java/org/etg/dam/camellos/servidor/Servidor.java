package org.etg.dam.camellos.servidor;

import org.etg.dam.camellos.cliente.util.Constantes;
import org.etg.dam.camellos.cliente.util.GeneradorCertificado;
import org.etg.dam.camellos.servidor.modelo.Carrera;
import org.etg.dam.camellos.modelo.Jugador;
import org.etg.dam.camellos.servidor.util.RegistroPartidas;
import org.etg.dam.camellos.servidor.util.LoggerServidor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(Constantes.NUMERO_PUERTO)) {
            System.out.println("Servidor iniciado en el puerto " + Constantes.NUMERO_PUERTO);
            LoggerServidor.logInfo(" Servidor iniciado en el puerto " + Constantes.NUMERO_PUERTO);

            while (true) {
                Socket jugador1 = servidor.accept();
                Socket jugador2 = servidor.accept();

                new Thread(() -> ejecutarCarrera(jugador1, jugador2)).start();
            }
        } catch (IOException e) {
            String msg = Constantes.MSG_ERROR_SERVIDOR + e.getMessage();
            System.err.println(msg);
            LoggerServidor.logError(msg);
        }
    }

    private static void ejecutarCarrera(Socket s1, Socket s2) {
        try {
            BufferedReader in1 = new BufferedReader(new InputStreamReader(s1.getInputStream()));
            BufferedReader in2 = new BufferedReader(new InputStreamReader(s2.getInputStream()));

            PrintWriter out1 = new PrintWriter(s1.getOutputStream(), true);
            PrintWriter out2 = new PrintWriter(s2.getOutputStream(), true);

            String nombre1 = in1.readLine();
            String nombre2 = in2.readLine();

            LoggerServidor.logInfo(" Jugador conectado: " + nombre1);
            LoggerServidor.logInfo(" Jugador conectado: " + nombre2);

            Jugador j1 = new Jugador(nombre1, s1);
            Jugador j2 = new Jugador(nombre2, s2);

            Carrera carrera = new Carrera();
            carrera.agregarJugador(j1);
            carrera.agregarJugador(j2);

            LoggerServidor.logInfo(" Iniciando carrera entre " + nombre1 + " vs " + nombre2);

            out1.println(Constantes.MSG_ESPERANDO_JUGADOR);
            out2.println(Constantes.MSG_ESPERANDO_JUGADOR);

            Thread.sleep(1000);

            out1.println(Constantes.MSG_INICIO_CARRERA);
            out2.println(Constantes.MSG_INICIO_CARRERA);

            Jugador ganador = null;

            while (true) {
                for (Jugador j : carrera.getJugadores()) {
                    if (ganador == null) {
                        boolean haGanado = carrera.avanzar(j);
                        if (haGanado) {
                            ganador = j;
                            break;
                        }
                    }
                }

                // Enviar actualizaciones
                for (Jugador j : carrera.getJugadores()) {
                    PrintWriter out = new PrintWriter(j.getSocket().getOutputStream(), true);
                    out.println(Constantes.POSICIONES);
                    for (Jugador otro : carrera.getJugadores()) {
                        out.println(otro.getNombre() + ": " + otro.getPosicion());
                    }
                    out.println("---");
                }

                if (ganador != null) break;

                Thread.sleep(Constantes.TIEMPO_AVANCE_CAMELLOS);
            }

            LoggerServidor.logInfo(" Carrera finalizada. Ganador: " + ganador.getNombre());

            RegistroPartidas.registrar(carrera.getJugadores(), ganador);

            for (Jugador j : carrera.getJugadores()) {
                PrintWriter out = new PrintWriter(j.getSocket().getOutputStream(), true);
                if (j.getNombre().equals(ganador.getNombre())) {
                    out.println(Constantes.MSG_GANADOR);
                    LoggerServidor.logInfo(" Certificado generado para " + j.getNombre());

                    GeneradorCertificado.generar(j.getNombre());
                    GeneradorCertificado.generarPDFconBat(j.getNombre());
                } else {
                    out.println(Constantes.MSG_PERDEDOR);
                }
                j.getSocket().close();
            }

        } catch (Exception e) {
            String msg = "❌ Error en carrera: " + e.getMessage();
            System.err.println(msg);
            LoggerServidor.logError(msg);
        }
    }
}
