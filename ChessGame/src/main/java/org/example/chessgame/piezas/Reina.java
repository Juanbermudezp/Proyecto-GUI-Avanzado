package org.example.chessgame.piezas;

public class Reina extends Pieza {

    public Reina(String color, String imagenURL) {
        super(color, imagenURL);
    }

    @Override
    public boolean esMovimientoValido(String desde, String hasta, Pieza[][] tablero) {
        int desdeFila = Character.getNumericValue(desde.charAt(1)) - 1;
        int desdeCol = desde.charAt(0) - 'a';
        int hastaFila = Character.getNumericValue(hasta.charAt(1)) - 1;
        int hastaCol = hasta.charAt(0) - 'a';

        if (desdeFila == hastaFila || desdeCol == hastaCol || Math.abs(desdeFila - hastaFila) == Math.abs(desdeCol - hastaCol)) {
            int filaIncremento = Integer.compare(hastaFila, desdeFila);
            int colIncremento = Integer.compare(hastaCol, desdeCol);
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

        return false;
    }
}
