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
        Pieza piezaDesde = tablero.getPiezaEnPosicion(desde);
        Pieza piezaHasta = tablero.getPiezaEnPosicion(hasta);

        if (piezaDesde != null && piezaDesde.getColor().equals(jugadorActual.getColor()) &&
                piezaDesde.esMovimientoValido(desde, hasta, tablero.getTablero())) {

            Pieza[][] tableroCopia = copiarTablero(tablero.getTablero());
            tableroCopia[Character.getNumericValue(hasta.charAt(1)) - 1][hasta.charAt(0) - 'a'] = piezaDesde;
            tableroCopia[Character.getNumericValue(desde.charAt(1)) - 1][desde.charAt(0) - 'a'] = null;

            if (!estaEnJaque(jugadorActual.getColor(), tableroCopia, buscarRey(jugadorActual.getColor(), tableroCopia))) {
                tablero.setPiezaEnPosicion(hasta, piezaDesde);
                tablero.setPiezaEnPosicion(desde, null);

                cambiarTurno();
                return true;
            }
        }
        return false;
    }

    private void cambiarTurno() {
        jugadorActual = (jugadorActual == jugadorBlanco) ? jugadorNegro : jugadorBlanco;
    }

    public boolean estaEnJaque(String color) {
        String posicionRey = buscarRey(color);
        return estaEnJaque(color, tablero.getTablero(), posicionRey);
    }

    private boolean estaEnJaque(String color, Pieza[][] tableroCopia, String posicionRey) {
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                Pieza pieza = tableroCopia[fila][col];
                if (pieza != null && !pieza.getColor().equals(color)) {
                    if (pieza.esMovimientoValido("" + (char) ('a' + col) + (fila + 1), posicionRey, tableroCopia)) {
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
                            if (pieza.esMovimientoValido(desde, hasta, tablero.getTablero())) {
                                Pieza[][] tableroCopia = copiarTablero(tablero.getTablero());
                                tableroCopia[hastaFila][hastaCol] = pieza;
                                tableroCopia[fila][col] = null;
                                if (!estaEnJaque(color, tableroCopia, buscarRey(color, tableroCopia))) {
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
        return buscarRey(color, tablero.getTablero());
    }

    private String buscarRey(String color, Pieza[][] tableroCopia) {
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                Pieza pieza = tableroCopia[fila][col];
                if (pieza instanceof Rey && pieza.getColor().equals(color)) {
                    return "" + (char) ('a' + col) + (fila + 1);
                }
            }
        }
        return null;
    }

    private Pieza[][] copiarTablero(Pieza[][] tableroOriginal) {
        Pieza[][] tableroCopia = new Pieza[8][8];
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                tableroCopia[fila][col] = tableroOriginal[fila][col];
            }
        }
        return tableroCopia;
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

    public boolean enrocar(String lado) {
        if (!estaEnJaque(jugadorActual.getColor())) {
            if (jugadorActual.getColor().equals("blanco")) {
                if (lado.equals("corto") && tablero.getPiezaEnPosicion("e1") instanceof Rey && tablero.getPiezaEnPosicion("h1") instanceof Torre) {
                    if (tablero.getPiezaEnPosicion("f1") == null && tablero.getPiezaEnPosicion("g1") == null) {
                        if (!((Rey) tablero.getPiezaEnPosicion("e1")).haMovido() && !((Torre) tablero.getPiezaEnPosicion("h1")).haMovido()) {
                            tablero.moverPieza("e1", "g1");
                            tablero.moverPieza("h1", "f1");
                            return true;
                        }
                    }
                } else if (lado.equals("largo") && tablero.getPiezaEnPosicion("e1") instanceof Rey && tablero.getPiezaEnPosicion("a1") instanceof Torre) {
                    if (tablero.getPiezaEnPosicion("b1") == null && tablero.getPiezaEnPosicion("c1") == null && tablero.getPiezaEnPosicion("d1") == null) {
                        if (!((Rey) tablero.getPiezaEnPosicion("e1")).haMovido() && !((Torre) tablero.getPiezaEnPosicion("a1")).haMovido()) {
                            tablero.moverPieza("e1", "c1");
                            tablero.moverPieza("a1", "d1");
                            return true;
                        }
                    }
                }
            } else if (jugadorActual.getColor().equals("negro")) {
                if (lado.equals("corto") && tablero.getPiezaEnPosicion("e8") instanceof Rey && tablero.getPiezaEnPosicion("h8") instanceof Torre) {
                    if (tablero.getPiezaEnPosicion("f8") == null && tablero.getPiezaEnPosicion("g8") == null) {
                        if (!((Rey) tablero.getPiezaEnPosicion("e8")).haMovido() && !((Torre) tablero.getPiezaEnPosicion("h8")).haMovido()) {
                            tablero.moverPieza("e8", "g8");
                            tablero.moverPieza("h8", "f8");
                            return true;
                        }
                    }
                } else if (lado.equals("largo") && tablero.getPiezaEnPosicion("e8") instanceof Rey && tablero.getPiezaEnPosicion("a8") instanceof Torre) {
                    if (tablero.getPiezaEnPosicion("b8") == null && tablero.getPiezaEnPosicion("c8") == null && tablero.getPiezaEnPosicion("d8") == null) {
                        if (!((Rey) tablero.getPiezaEnPosicion("e8")).haMovido() && !((Torre) tablero.getPiezaEnPosicion("a8")).haMovido()) {
                            tablero.moverPieza("e8", "c8");
                            tablero.moverPieza("a8", "d8");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
