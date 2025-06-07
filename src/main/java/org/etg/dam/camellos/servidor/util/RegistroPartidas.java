package org.etg.dam.camellos.servidor.util;

import java.io.*;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

import org.etg.dam.camellos.cliente.util.Constantes;
import org.etg.dam.camellos.modelo.Jugador;

public class RegistroPartidas {
    private static final String RUTA = Constantes.RUTA_HISTORIAL_PARTIDAS;
    private static final AtomicInteger contador = new AtomicInteger(cargarUltimoContador());

    private static int cargarUltimoContador() {
        File archivo = new File(RUTA);
        if (!archivo.exists()) return 1;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int ultimo = 0;

            while ((linea = reader.readLine()) != null) {
                if (linea.startsWith(Constantes.TXT_PARTIDA)) {
                    String[] partes = linea.split(" ");
                    ultimo = Integer.parseInt(partes[1]);
                }
            }

            return ultimo + 1;
        } catch (IOException | NumberFormatException e) {
            return 1;
        }
    }

    public static void registrar(Jugador[] jugadores, Jugador ganador) {
        File archivo = new File(RUTA);
        archivo.getParentFile().mkdirs();

        String fecha = LocalDate.now().toString();

        String linea = Constantes.TXT_PARTIDA + contador.getAndIncrement() +
                Constantes.ESPACIO_PARTIDA + jugadores[0].getNombre() +
                Constantes.VS + jugadores[1].getNombre() +
                Constantes.ESPACIO_PARTIDA + fecha +
                Constantes.ESPACIO_GANADOR + ganador.getNombre();

        try (FileWriter fw = new FileWriter(archivo, true)) {
            fw.write(linea + System.lineSeparator());
        } catch (IOException e) {
            System.err.println(Constantes.ERROR_GUARDAR_HISTORIAL + e.getMessage());
        }
    }
}
