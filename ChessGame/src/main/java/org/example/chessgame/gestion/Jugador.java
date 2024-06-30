package org.example.chessgame.gestion;

public class Jugador {
    private String color;
    private String nombre;

    public Jugador(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
