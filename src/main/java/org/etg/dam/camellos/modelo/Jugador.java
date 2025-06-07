package org.etg.dam.camellos.modelo;

import java.net.Socket;

public class Jugador {

    private final String nombre;
    private int posicion;
    private final Socket socket;

    public Jugador(String nombre, Socket socket) {
        this.nombre = nombre;
        this.socket = socket;
        this.posicion = 0;
    }

    public void avanzar(int pasos) {
    this.posicion += pasos;
}


    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getNombre() {
        return nombre;
    }

    public Socket getSocket() {
        return socket;
    }

    public void reiniciar() {
        this.posicion = 0;
    }
}
