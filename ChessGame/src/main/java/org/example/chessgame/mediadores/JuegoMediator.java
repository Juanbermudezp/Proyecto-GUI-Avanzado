package org.example.chessgame.mediadores;

import org.example.chessgame.gestion.GestorJuego;
import org.example.chessgame.gestion.Tablero;
import org.example.chessgame.gestion.Jugador;
import org.example.chessgame.piezas.Pieza;
import org.example.chessgame.view.MenuManager;

public class JuegoMediator implements Mediator {
    private GestorJuego gestor;
    private Tablero tablero;
    private Jugador jugadorBlanco;
    private Jugador jugadorNegro;

    private MenuManager menuManager;

    public JuegoMediator(GestorJuego gestor, Tablero tablero, Jugador jugadorBlanco, Jugador jugadorNegro) {
        this.gestor = gestor;
        this.tablero = tablero;
        this.jugadorBlanco = jugadorBlanco;
        this.jugadorNegro = jugadorNegro;
    }

    @Override
    public void notificar(Object remitente, String evento) {
        if (evento.equals("cambioTurno")) {
            gestor.cambiarTurno();
            boolean esBlanco = gestor.esTurnoBlanco();
            if (tablero.estaEnJaque(esBlanco)) {
                if (tablero.estaEnJaqueMate(esBlanco)) {
                    System.out.println("Jaque mate! " + (esBlanco ? "Negras" : "Blancas") + " ganan.");
                    if (mostrarMenuJaqueMate() == 9) {
                        return;
                    }
                } else {
                    System.out.println("Jaque!");
                }
            }
        } else if (evento.equals("movimientoInvalido")) {
            System.out.println("Movimiento no válido. Inténtalo de nuevo.");
        } else if (evento.equals("finJuego")) {
            System.out.println("El juego ha terminado.");
        }
    }

    public void jugar() {
        while (true) {
            gestor.guardarEstado();
            Tablero.imprimirTablero(tablero.obtenerTablero());
            System.out.println("Es el turno de las " + (gestor.esTurnoBlanco() ? "blancas" : "negras"));
            String move = MenuManager.obtenerInstancia().obtenerMovimiento();

            if (move.equals("9")) {
                break;
            }

            String[] positions = move.split(" ");
            if (positions.length != 2) {
                notificar(this, "movimientoInvalido");
                continue;
            }

            int[] from = parsePosition(positions[0]);
            int[] to = parsePosition(positions[1]);

            if (from == null || to == null) {
                notificar(this, "movimientoInvalido");
                continue;
            }

            Jugador jugadorActual = gestor.esTurnoBlanco() ? jugadorBlanco : jugadorNegro;
            jugadorActual.hacerMovimiento(tablero, from, to);
        }
    }

    private int[] parsePosition(String pos) {
        if (pos.length() != 2) return null;
        char col = pos.charAt(0);
        char row = pos.charAt(1);

        if (col < 'a' || col > 'h' || row < '1' || row > '8') return null;

        return new int[]{row - '1', col - 'a'};
    }

    private int mostrarMenuJaqueMate() {
        while (true) {
            MenuManager.obtenerInstancia().limpiarConsola();
            System.out.println("1. Reiniciar partida");
            System.out.println("9. Volver al menú principal");
            System.out.print("Elige una opción: ");
            int opcion = MenuManager.obtenerInstancia().obtenerOpcion();

            if (opcion == 1) {
                GestorJuego.obtenerInstancia().reiniciarTablero();
                tablero = GestorJuego.obtenerInstancia().obtenerTablero();
                System.out.println("El tablero se ha reiniciado.");
                break;
            } else if (opcion == 9) {
                return 9;
            } else {
                System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
        return 1;
    }

    public void setJugadores(Jugador jugadorBlanco, Jugador jugadorNegro) {
        this.jugadorBlanco = jugadorBlanco;
        this.jugadorNegro = jugadorNegro;
    }
}
