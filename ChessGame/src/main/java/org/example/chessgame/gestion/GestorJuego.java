package org.example.chessgame.gestion;

import org.example.chessgame.piezas.*;

public class GestorJuego {
    private Tablero tablero;
    private Jugador jugadorBlanco;
    private Jugador jugadorNegro;
    private Jugador jugadorActual;

    public GestorJuego() {
        iniciarJuego();
    }

    public void iniciarJuego() {
        tablero = new Tablero();
        jugadorBlanco = new Jugador("blanco");
        jugadorNegro = new Jugador("negro");
        jugadorActual = jugadorBlanco;
    }

    public boolean moverPieza(String desde, String hasta) {
        boolean movimientoValido = tablero.moverPieza(desde, hasta);
        if (movimientoValido) {
            cambiarTurno();
        }
        return movimientoValido;
    }

    private void cambiarTurno() {
        if (jugadorActual == jugadorBlanco) {
            jugadorActual = jugadorNegro;
        } else {
            jugadorActual = jugadorBlanco;
        }
    }

    public boolean estaEnJaque(String color) {
        String posicionRey = buscarRey(color);
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                Pieza pieza = tablero.getPiezaEnPosicion(fila, col);
                if (pieza != null && !pieza.getColor().equals(color)) {
                    if (pieza.esMovimientoValido("" + (char) ('a' + col) + (fila + 1), posicionRey, tablero.getTablero())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean estaEnJaqueMate(String color) {
        if (!estaEnJaque(color)) {
            return false;
        }

        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                Pieza pieza = tablero.getPiezaEnPosicion(fila, col);
                if (pieza != null && pieza.getColor().equals(color)) {
                    String desde = "" + (char) ('a' + col) + (fila + 1);
                    for (int hastaFila = 0; hastaFila < 8; hastaFila++) {
                        for (int hastaCol = 0; hastaCol < 8; hastaCol++) {
                            String hasta = "" + (char) ('a' + hastaCol) + (hastaFila + 1);
                            if (tablero.moverPieza(desde, hasta)) {
                                boolean enJaque = estaEnJaque(color);
                                tablero.moverPieza(hasta, desde); // Revertir movimiento
                                if (!enJaque) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private String buscarRey(String color) {
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                Pieza pieza = tablero.getPiezaEnPosicion(fila, col);
                if (pieza instanceof Rey && pieza.getColor().equals(color)) {
                    return "" + (char) ('a' + col) + (fila + 1);
                }
            }
        }
        return null;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public Jugador getJugadorBlanco() {
        return jugadorBlanco;
    }

    public Jugador getJugadorNegro() {
        return jugadorNegro;
    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }
}
