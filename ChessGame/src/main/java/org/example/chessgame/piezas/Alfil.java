package org.example.chessgame.piezas;

public class Alfil extends Pieza {

    public Alfil(String color, String imagenURL) {
        super(color, imagenURL);
    }

    @Override
    public boolean esMovimientoValido(String desde, String hasta, Pieza[][] tablero) {
        int desdeFila = Character.getNumericValue(desde.charAt(1)) - 1;
        int desdeCol = desde.charAt(0) - 'a';
        int hastaFila = Character.getNumericValue(hasta.charAt(1)) - 1;
        int hastaCol = hasta.charAt(0) - 'a';

        if (Math.abs(desdeFila - hastaFila) != Math.abs(desdeCol - hastaCol)) {
            return false;
        }

        int filaIncremento = (hastaFila > desdeFila) ? 1 : -1;
        int colIncremento = (hastaCol > desdeCol) ? 1 : -1;
        int filaActual = desdeFila + filaIncremento;
        int colActual = desdeCol + colIncremento;

        while (filaActual != hastaFila || colActual != hastaCol) {
            if (filaActual < 0 || filaActual >= 8 || colActual < 0 || colActual >= 8) {
                return false;
            }
            if (tablero[filaActual][colActual] != null) {
                return false;
            }
            filaActual += filaIncremento;
            colActual += colIncremento;
        }

        Pieza piezaDestino = tablero[hastaFila][hastaCol];
        return piezaDestino == null || !piezaDestino.getColor().equals(this.getColor());
    }
}
