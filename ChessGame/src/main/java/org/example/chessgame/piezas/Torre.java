package org.example.chessgame.piezas;

public class Torre extends Pieza {
    private boolean haMovido;

    public Torre(String color, String imagenURL) {
        super(color, imagenURL);
        this.haMovido = false;
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
                return false;
            }
            if (tablero[filaActual][colActual] != null) {
                return false;
            }
            filaActual += filaIncremento;
            colActual += colIncremento;
        }

        Pieza piezaDestino = tablero[hastaFila][hastaCol];
        if (piezaDestino == null || !piezaDestino.getColor().equals(this.getColor())) {
            haMovido = true;
            return true;
        }
        return false;
    }

    public boolean haMovido() {
        return haMovido;
    }
}
