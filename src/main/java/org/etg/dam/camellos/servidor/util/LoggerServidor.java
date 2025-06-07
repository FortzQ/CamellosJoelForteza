package org.etg.dam.camellos.servidor.util;

import java.io.File;
import java.util.logging.*;

public class LoggerServidor {

    private static final String RUTA_LOGS = "logs";
    private static final String ARCHIVO_LOG = "logs/servidor.log";
    private static Logger logger;

    static {
        try {
            new File(RUTA_LOGS).mkdirs();

            logger = Logger.getLogger("CamellosLogger");
            FileHandler fh = new FileHandler(ARCHIVO_LOG, true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setUseParentHandlers(false);
        } catch (Exception e) {
            System.err.println(" Error al iniciar logger: " + e.getMessage());
        }
    }

    // ✅ Métodos específicos que faltaban:
    public static void logInfo(String msg) {
        logger.log(Level.INFO, msg);
    }

    public static void logError(String msg) {
        logger.log(Level.SEVERE, msg);
    }

    public static void logWarning(String msg) {
        logger.log(Level.WARNING, msg);
    }
}
