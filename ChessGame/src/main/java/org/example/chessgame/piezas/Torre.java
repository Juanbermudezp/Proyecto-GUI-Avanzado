package org.example.chessgame.piezas;

public class Torre extends Pieza {

    public Torre(String color, String imagenURL) {
        super(color, imagenURL);
    }

    @Override
    public boolean esMovimientoValido(String desde, String hasta, Pieza[][] tablero) {
        int desdeFila = Character.getNumericValue(desde.charAt(1)) - 1;
        int desdeCol = desde.charAt(0) - 'a';
        int hastaFila = Character.getNumericValue(hasta.charAt(1)) - 1;
        int hastaCol = hasta.charAt(0) - 'a';

        if (desdeFila != hastaFila && desdeCol != hastaCol) {
            return false;
        }

        int filaIncremento = (hastaFila > desdeFila) ? 1 : (hastaFila < desdeFila) ? -1 : 0;
        int colIncremento = (hastaCol > desdeCol) ? 1 : (hastaCol < desdeCol) ? -1 : 0;
        int filaActual = desdeFila + filaIncremento;
        int colActual = desdeCol + colIncremento;

        while (filaActual != hastaFila || colActual != hastaCol) {
            if (filaActual < 0 || filaActual >= 8 || colActual < 0 || colActual >= 8) {
                return false;  // Evitar índices fuera de límites
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
