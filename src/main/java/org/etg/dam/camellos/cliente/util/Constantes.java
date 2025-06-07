package org.etg.dam.camellos.cliente.util;

public class Constantes {

    // ──────────────────────────── MENSAJES DE FLUJO ─────────────────────────────
    public static final String MSG_ESPERANDO_JUGADORES = "Servidor iniciado. Esperando jugadores...";
    public static final String MSG_JUGADOR_REGISTRADO = "Jugador registrado: ";
    public static final String MSG_INICIO_CARRERA = "Comienza la carrera";
    public static final String MSG_ESPERANDO_JUGADOR = "Esperando al segundo jugador...";
    public static final String MSG_GANADOR = "¡HAS GANADO!";
    public static final String MSG_PERDEDOR = "Has perdido.";
    public static final String MSG_ERROR_SERVIDOR = "Error en el servidor: ";
    public static final String MSG_OK = "OK";

    // ────────────────────────────── PROTOCOLO ──────────────────────────────────
    public static final String POSICIONES = "POSICIONES:";
    public static final String UNIRSE_CARRERA = "Unirse a la carrera";
    public static final String CARRERA_CAMELLOS = "Carrera de Camellos";

    // ──────────────────────────────── CONFIG ────────────────────────────────────
    public static final String LOCALHOST = "localhost";
    public static final int NUMERO_PUERTO = 8888;
    public static final int META = 100;
    public static final int MAX_PASOS_AVANZAR = 10;
    public static final int MAX_JUGADORES = 2;
    public static final int POSICION_INICIAL_JUGADOR = 0;
    public static final int TIEMPO_AVANCE_CAMELLOS = 1000;

    // ──────────────────────────────── CERTIFICADOS ──────────────────────────────
    public static final String CERTIFICADO_MD = "certificado/ganador.md";
    public static final String CERTIFICADO_PDF = "certificado/ganador.pdf";
    public static final String RUTA_CERTIFICADO = "certificados";
    public static final String RAIZ_ARCHIVO_CERTIFICADO = "certificado_";
    public static final String FORMATO_CERTIFICADO = ".txt";
    public static final String ERROR_GENERAR_CERTIFICADO = "Error al generar el certificado: ";

    public static final String CONTENIDO_CERTIFICADO = """
            CERTIFICADO DE GANADOR
            ----------------------
            Jugador: %s
            Fecha: %s
            ¡Has ganado la carrera de camellos!
            """;

    // ──────────────────────────────── HISTORIAL ─────────────────────────────────
    public static final String RUTA_HISTORIAL_PARTIDAS = "data/historial.txt";
    public static final String TXT_PARTIDA = "Partida ";
    public static final String ESPACIO_PARTIDA = " | ";
    public static final String ESPACIO_GANADOR = " | Ganador: ";
    public static final String VS = " vs ";
    public static final String ERROR_GUARDAR_HISTORIAL = "ERROR AL GUARDAR EL HISTORIAL: ";
}
