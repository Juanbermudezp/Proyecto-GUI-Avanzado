package org.example.chessgame.piezas;

public class PiezaFactory {
    public static Pieza crearPieza(String tipo, String color, String imagenURL) {
        switch (tipo.toLowerCase()) {
            case "alfil":
                return new Alfil(color, imagenURL);
            case "caballo":
                return new Caballo(color, imagenURL);
            case "peon":
                return new Peon(color, imagenURL);
            case "reina":
                return new Reina(color, imagenURL);
            case "rey":
                return new Rey(color, imagenURL);
            case "torre":
                return new Torre(color, imagenURL);
            default:
                throw new IllegalArgumentException("Tipo de pieza no válido: " + tipo);
        }
    }
}

