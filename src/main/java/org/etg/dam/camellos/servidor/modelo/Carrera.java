package org.etg.dam.camellos.servidor.modelo;

import org.etg.dam.camellos.cliente.util.Constantes;
import org.etg.dam.camellos.modelo.Jugador;

public class Carrera {

    private final Jugador[] jugadores = new Jugador[Constantes.MAX_JUGADORES];
    private int contadorJugadores = 0;
    private Jugador ganador = null;

    public void agregarJugador(Jugador jugador) {
        if (contadorJugadores < Constantes.MAX_JUGADORES) {
            jugadores[contadorJugadores++] = jugador;
        }
    }

    public Jugador[] getJugadores() {
        return jugadores;
    }

    public boolean carreraLlena() {
        return contadorJugadores == Constantes.MAX_JUGADORES;
    }

    public boolean avanzar(Jugador jugador) {
    if (ganador != null) return false;

    int pasos = (int)(Math.random() * Constantes.MAX_PASOS_AVANZAR + 1);
    int nuevaPos = Math.min(jugador.getPosicion() + pasos, Constantes.META);
    jugador.setPosicion(nuevaPos); // 🔧 Asegúrate de tener setPosicion en Jugador

    if (nuevaPos >= Constantes.META && ganador == null) {
        ganador = jugador;
        return true;
    }

    return false;
}


    public Jugador getGanador() {
        return ganador;
    }

    public boolean esGanador(Jugador jugador) {
        return ganador != null && ganador.getNombre().equals(jugador.getNombre());
    }

    public void reiniciar() {
        for (Jugador j : jugadores) {
            if (j != null) j.reiniciar();
        }
        ganador = null;
        contadorJugadores = 0;
    }
}
