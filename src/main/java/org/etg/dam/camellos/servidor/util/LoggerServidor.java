package org.etg.dam.camellos.servidor.util;

import java.io.File;
import java.util.logging.*;

import org.etg.dam.camellos.cliente.util.Constantes;

public class LoggerServidor {

    private static Logger logger;

    //se ejecuta al cargar la clase
    static {
        try {
            new File(Constantes.RUTA_LOGS).mkdirs();

            logger = Logger.getLogger(Constantes.CAMELLOS_LOGGER);
            FileHandler fh = new FileHandler(Constantes.ARCHIVO_LOG, true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setUseParentHandlers(false);
        } catch (Exception e) {
            System.err.println(Constantes.ERROR_LOGGER + e.getMessage());
        }
    }

    // Métodos específicos que faltaban:
    public static void logInfo(String msg) {
        logger.log(Level.INFO, msg);
    }


    //registra errores y/o excepciones
    public static void logError(String msg) {
        logger.log(Level.SEVERE, msg);
    }
    
    public static void logWarning(String msg) {
        logger.log(Level.WARNING, msg);
    }
}
