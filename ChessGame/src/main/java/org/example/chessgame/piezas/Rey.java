package org.example.chessgame.piezas;

public class Rey extends Pieza {

    public Rey(String color, String imagenURL) {
        super(color, imagenURL);
    }

    @Override
    public boolean esMovimientoValido(String desde, String hasta, Pieza[][] tablero) {
        int desdeFila = Character.getNumericValue(desde.charAt(1)) - 1;
        int desdeCol = desde.charAt(0) - 'a';
        int hastaFila = Character.getNumericValue(hasta.charAt(1)) - 1;
        int hastaCol = hasta.charAt(0) - 'a';

        int filaDiff = Math.abs(desdeFila - hastaFila);
        int colDiff = Math.abs(desdeCol - hastaCol);

        if (filaDiff > 1 || colDiff > 1) {
            return false;
        }

        Pieza piezaDestino = tablero[hastaFila][hastaCol];
        return piezaDestino == null || !piezaDestino.getColor().equals(this.getColor());
    }
}
