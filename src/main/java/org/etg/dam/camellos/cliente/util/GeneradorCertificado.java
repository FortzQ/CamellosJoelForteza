package org.etg.dam.camellos.cliente.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class GeneradorCertificado {
    public static void generar(String nombreJugador) {
        String fecha = LocalDate.now().toString();
        String contenido = Constantes.CONTENIDO_CERTIFICADO.formatted(nombreJugador, fecha);
        File carpeta = new File(Constantes.RUTA_CERTIFICADO);

        if (!carpeta.exists()) carpeta.mkdirs();
        File archivo = new File(carpeta, "ganador_" + nombreJugador + ".md");

        try (FileWriter fw = new FileWriter(archivo)) {
            fw.write(contenido);
        } catch (IOException e) {
            System.err.println("❌ Error generando certificado: " + e.getMessage());
        }
    }

    public static void generarPDFconBat(String nombreJugador) {
    try {
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "convertir.bat", nombreJugador);
        pb.inheritIO(); // 🔧 Muestra en terminal
        pb.start();     // 🔧 ¡Sin waitFor(), así no se bloquea!
        System.out.println(" Generando PDF en segundo plano");
    } catch (Exception e) {
        System.err.println(" Error ejecutando convertir.bat: " + e.getMessage());
    }
}
}

