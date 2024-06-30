package org.example.chessgame.piezas;

public abstract class Pieza {
    private String color;
    private String imagenURL;
    private String posicion; // Añadido para manejar la posición de la pieza

    public Pieza(String color, String imagenURL) {
        this.color = color;
        this.imagenURL = imagenURL;
    }

    public String getColor() {
        return color;
    }

    public String getImagenURL() {
        return imagenURL;
    }

    public String getPosicion() { // Método añadido
        return posicion;
    }

    public void setPosicion(String posicion) { // Método añadido
        this.posicion = posicion;
    }

    public abstract boolean esMovimientoValido(String desde, String hasta, Pieza[][] tablero);
}
