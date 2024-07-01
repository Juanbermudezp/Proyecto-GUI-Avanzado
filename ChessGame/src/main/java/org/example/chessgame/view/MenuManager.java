package org.example.chessgame.view;

import java.io.IOException;
import java.util.Scanner;

public class MenuManager {
    private static MenuManager instancia;
    private Scanner scanner;

    private MenuManager() {
        scanner = new Scanner(System.in);
    }

    public static MenuManager obtenerInstancia() {
        if (instancia == null) {
            instancia = new MenuManager();
        }
        return instancia;
    }

    public int mostrarMenuPrincipal() {
        limpiarConsola();
        System.out.println("=====CHESS GAME=====");
        System.out.println("Menu:");
        System.out.println("1. Jugar");
        System.out.println("2. Reiniciar");
        System.out.println("3. Salir");
        System.out.print("Elige una opción: ");
        int option = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer después de nextInt()
        return option;
    }

    public String obtenerMovimiento() {
        limpiarConsola();
        System.out.print("Ingresa tu movimiento (ej. e2 e4) o '9' para volver al menú: ");
        return scanner.nextLine().trim();
    }

    public String obtenerEntrada() {
        limpiarConsola();
        return scanner.nextLine().trim();
    }

    public int obtenerOpcion() {
        int option = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer después de nextInt()
        return option;
    }

    public void limpiarConsola() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error al limpiar la consola: " + e.getMessage());
        }
    }
}
