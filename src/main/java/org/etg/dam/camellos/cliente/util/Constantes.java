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
    public static final String MSG_SERVIDOR_INICIADO_PUERTO="Servidor iniciado en el puerto";
    public static final String MSG_JUGADOR_CONECTADO="Jugador conectado: ";
    public static final String MSG_INICIANDO_CARRERA_ENTRE="Iniciando carrera entre ";
    public static final String MSG_FIN_CARRERA_DETERMINA_GANADOR="Fin de la carrera. Ganador/a/e:  ";
    public static final String MSG_ERROR_CARRERA="Error en la carrera: ";
    public static final String MSG_ENHORABUENA_CARRERA="¡¡OLE!! ¡HAS GANADO!";
    public static final String MSG_DERROTA_JUGADOR="Ouch... ¡Has perdido! :()";
    public static final String MSG_ERROR_EJECUCION_BAT="Error ejecutando convertir.bat:";
    public static final String MSG_GENERANDO_PDF_SEGUNDO_PLANO="Generando PDF en segundo plano...";
    public static final String NOMBRE_BAT_FICHERO_CONVERTIR="convertir.bat";
    public static final String CAMELLOS_LOGGER="CamellosLogger";
    public static final String ERROR_LOGGER="Error al iniciar el logger: ";
    public static final String RUTA_LOGS="logs";
    public static final String ARCHIVO_LOG="logs/servidor.log";
    public static final String TXT_LOCAL_HOST="localhost";
    public static final String MSG_ERROR_CONECTAR_SERVIDOR="Error al conectar con el servidor: ";
    public static final String MSG_ERROR_CIERRE_SOCKET="Error al cerrar el socket: ";
    public static final String MSG_ERROR_CLASE_HILO_JUGADOR_SERVIDOR="Error en HiloJugadorServidor: ";
    public static final String TXT_BOTON_UNIRSE_CARRERA="Unirse";
    public static final String TXT_INTRODUCIR_NOMBRE_CARRERA="Introduce tu nombre: ";
    public static final String TXT_TITULO_VENTANA_INICIO="🐪 Unirse a la carrera";
    public static final String TXT_EJEMPLO_NOMBRE_VENTANA_INICIO="Ej: Pako";
    public static final String ERROR_LECTURA_SOCKET="Error al iniciar lectura del socket: ";
    public static final String ERROR_CLASE_HILO_CLIENTE="Error en HiloCliente: ";
    public static final String ERROR_LEER_POSICIONES="Error al leer posiciones: ";


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
    public static final int TIEMPO_ESPERA_COMIENZO_CARRERA = 1000;
    public static final int TIEMPO_ESPERA_JUGADORES_CONECTAODOS=500;

    // ──────────────────────────────── CERTIFICADOS ──────────────────────────────
    public static final String CERTIFICADO_MD = "certificado/ganador.md";
    public static final String CERTIFICADO_PDF = "certificado/ganador.pdf";
    public static final String RUTA_CERTIFICADO = "certificados";
    public static final String RAIZ_ARCHIVO_CERTIFICADO = "certificado_";
    public static final String FORMATO_CERTIFICADO = ".txt";
    public static final String ERROR_GENERAR_CERTIFICADO = "Error al generar el certificado: ";
    public static final String MSG_ABRIR_CERTIFICADO="📄 Abrir Certificado PDF";
    public static final String ERROR_NO_SE_ENCUENTRA_PDF = " NO se encontró el PDF";
    public static final String RAIZ_NOMBRE_CERTIFICADO_PDF="certificados/ganador_";
    public static final String RAIZ_GANADOR_CERTIFICADO_MD="ganador_";

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
