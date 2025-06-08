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
            System.out.println(Constantes.MSG_SERVIDOR_INICIADO_PUERTO + Constantes.NUMERO_PUERTO);
            LoggerServidor.logInfo(Constantes.MSG_SERVIDOR_INICIADO_PUERTO + Constantes.NUMERO_PUERTO);
            
            //espera a que los se conecten los dos jugadores
            while (true) {
                Socket jugador1 = servidor.accept();
                Socket jugador2 = servidor.accept();

                new Thread(() -> ejecutarCarrera(jugador1, jugador2)).start(); // se crean los hilos para cada carrera
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

            LoggerServidor.logInfo(Constantes.MSG_JUGADOR_CONECTADO + nombre1);
            LoggerServidor.logInfo(Constantes.MSG_JUGADOR_CONECTADO + nombre2);

            Jugador j1 = new Jugador(nombre1, s1);
            Jugador j2 = new Jugador(nombre2, s2);

            Carrera carrera = new Carrera();
            carrera.agregarJugador(j1);
            carrera.agregarJugador(j2);

            LoggerServidor.logInfo(Constantes.MSG_INICIANDO_CARRERA_ENTRE + nombre1 + " vs " + nombre2);

            out1.println(Constantes.MSG_ESPERANDO_JUGADOR);
            out2.println(Constantes.MSG_ESPERANDO_JUGADOR);

            Thread.sleep(Constantes.TIEMPO_ESPERA_COMIENZO_CARRERA);

            out1.println(Constantes.MSG_INICIO_CARRERA);
            out2.println(Constantes.MSG_INICIO_CARRERA);

            Jugador ganador = null;

            // bucle de la carrera hasta que haya un ganador
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

                // Enviar actualizaciones a los jugadores
                for (Jugador j : carrera.getJugadores()) {
                    PrintWriter out = new PrintWriter(j.getSocket().getOutputStream(), true);
                    out.println(Constantes.POSICIONES);
                    for (Jugador otro : carrera.getJugadores()) {
                        out.println(otro.getNombre() + ": " + otro.getPosicion());
                    }
                    out.println("---"); //delimitador
                }

                if (ganador != null) break;

                Thread.sleep(Constantes.TIEMPO_AVANCE_CAMELLOS);
            }

            LoggerServidor.logInfo(Constantes.MSG_FIN_CARRERA_DETERMINA_GANADOR + ganador.getNombre());

            RegistroPartidas.registrar(carrera.getJugadores(), ganador);

            //informa a los jugadores del resultado de la carrera
            for (Jugador j : carrera.getJugadores()) {
                PrintWriter out = new PrintWriter(j.getSocket().getOutputStream(), true);
                if (j.getNombre().equals(ganador.getNombre())) {
                    out.println(Constantes.MSG_GANADOR);
                    LoggerServidor.logInfo(Constantes.MSG_FIN_CARRERA_DETERMINA_GANADOR + j.getNombre());

                    GeneradorCertificado.generar(j.getNombre());
                    GeneradorCertificado.generarPDFconBat(j.getNombre());
                } else {
                    out.println(Constantes.MSG_PERDEDOR);
                }
                j.getSocket().close();
            }

        } catch (Exception e) {
            String msg = Constantes.MSG_ERROR_CARRERA+ e.getMessage();
            System.err.println(msg);
            LoggerServidor.logError(msg);
        }
    }
}
