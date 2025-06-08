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
        File archivo = new File(carpeta, Constantes.RAIZ_GANADOR_CERTIFICADO_MD + nombreJugador + ".md");

        try (FileWriter fw = new FileWriter(archivo)) {
            fw.write(contenido);
        } catch (IOException e) {
            System.err.println(Constantes.ERROR_GENERAR_CERTIFICADO + e.getMessage());
        }
    }

    public static void generarPDFconBat(String nombreJugador) {
    try {
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", Constantes.NOMBRE_BAT_FICHERO_CONVERTIR, nombreJugador);
        pb.inheritIO(); // 🔧 Muestra en terminal
        pb.start();     // 🔧 ¡Sin waitFor(), así no se bloquea!
        System.out.println(Constantes.MSG_GENERANDO_PDF_SEGUNDO_PLANO);
    } catch (Exception e) {
        System.err.println(Constantes.MSG_ERROR_EJECUCION_BAT + e.getMessage());
    }
}
}

