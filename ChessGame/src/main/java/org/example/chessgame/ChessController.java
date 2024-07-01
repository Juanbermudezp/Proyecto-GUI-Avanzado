package org.example.chessgame;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.example.chessgame.gestion.GestorJuego;
import org.example.chessgame.gestion.Jugador;
import org.example.chessgame.gestion.Tablero;
import org.example.chessgame.piezas.Pieza;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ChessController {

    private MediaPlayer jaqueMateGanador;
    private MediaPlayer errorMovimiento;
    private MediaPlayer movimiento;
    private MediaPlayer jaqueSonido;

    @FXML
    private VBox chessBoard;

    @FXML
    private Label turnLabel;

    private final Map<String, Button> buttons = new HashMap<>();
    private GestorJuego gestorJuego;
    private Tablero tablero;
    private Jugador jugadorBlanco;
    private Jugador jugadorNegro;

    private String nombreJugadorBlanco;
    private String nombreJugadorNegro;

    @FXML
    private Button a1, b1, c1, d1, e1, f1, g1, h1;
    @FXML
    private Button a2, b2, c2, d2, e2, f2, g2, h2;
    @FXML
    private Button a3, b3, c3, d3, e3, f3, g3, h3;
    @FXML
    private Button a4, b4, c4, d4, e4, f4, g4, h4;
    @FXML
    private Button a5, b5, c5, d5, e5, f5, g5, h5;
    @FXML
    private Button a6, b6, c6, d6, e6, f6, g6, h6;
    @FXML
    private Button a7, b7, c7, d7, e7, f7, g7, h7;
    @FXML
    private Button a8, b8, c8, d8, e8, f8, g8, h8;

    private Button selectedPiece = null;
    private String selectedPosition = null;
    private boolean gameFinished = false;

    private final String lightColor = "#F0D9B5";
    private final String darkColor = "#B58863";

    // Duraciones de los sonidos en segundos
    private final double duracionMovimiento = 2.0;
    private final double duracionErrorMovimiento = 2.0;
    private final double duracionJaqueSonido = 4.0;
    private final double duracionSonidoGanador = 6.0;

    public void setJugadorBlanco(String nombreJugadorBlanco) {
        this.nombreJugadorBlanco = nombreJugadorBlanco;
    }

    public void setJugadorNegro(String nombreJugadorNegro) {
        this.nombreJugadorNegro = nombreJugadorNegro;
    }

    @FXML
    public void initialize() {
        setupBoard();
        updateTurnLabel();
    }

    private void setupBoard() {
        buttons.put("a1", a1);
        buttons.put("b1", b1);
        buttons.put("c1", c1);
        buttons.put("d1", d1);
        buttons.put("e1", e1);
        buttons.put("f1", f1);
        buttons.put("g1", g1);
        buttons.put("h1", h1);

        buttons.put("a2", a2);
        buttons.put("b2", b2);
        buttons.put("c2", c2);
        buttons.put("d2", d2);
        buttons.put("e2", e2);
        buttons.put("f2", f2);
        buttons.put("g2", g2);
        buttons.put("h2", h2);

        buttons.put("a3", a3);
        buttons.put("b3", b3);
        buttons.put("c3", c3);
        buttons.put("d3", d3);
        buttons.put("e3", e3);
        buttons.put("f3", f3);
        buttons.put("g3", g3);
        buttons.put("h3", h3);

        buttons.put("a4", a4);
        buttons.put("b4", b4);
        buttons.put("c4", c4);
        buttons.put("d4", d4);
        buttons.put("e4", e4);
        buttons.put("f4", f4);
        buttons.put("g4", g4);
        buttons.put("h4", h4);

        buttons.put("a5", a5);
        buttons.put("b5", b5);
        buttons.put("c5", c5);
        buttons.put("d5", d5);
        buttons.put("e5", e5);
        buttons.put("f5", f5);
        buttons.put("g5", g5);
        buttons.put("h5", h5);

        buttons.put("a6", a6);
        buttons.put("b6", b6);
        buttons.put("c6", c6);
        buttons.put("d6", d6);
        buttons.put("e6", e6);
        buttons.put("f6", f6);
        buttons.put("g6", g6);
        buttons.put("h6", h6);

        buttons.put("a7", a7);
        buttons.put("b7", b7);
        buttons.put("c7", c7);
        buttons.put("d7", d7);
        buttons.put("e7", e7);
        buttons.put("f7", f7);
        buttons.put("g7", g7);
        buttons.put("h7", h7);

        buttons.put("a8", a8);
        buttons.put("b8", b8);
        buttons.put("c8", c8);
        buttons.put("d8", d8);
        buttons.put("e8", e8);
        buttons.put("f8", f8);
        buttons.put("g8", g8);
        buttons.put("h8", h8);

        gestorJuego = new GestorJuego();
        tablero = gestorJuego.getTablero();
        jugadorBlanco = gestorJuego.getJugadorBlanco();
        jugadorNegro = gestorJuego.getJugadorNegro();

        actualizarTablero();
    }

    private void actualizarTablero() {
        for (String pos : buttons.keySet()) {
            Button btn = buttons.get(pos);
            Pieza pieza = tablero.getPiezaEnPosicion(pos);
            if (pieza != null) {
                btn.setStyle("-fx-background-image: url('/org/example/chessgame/images/" + pieza.getImagenURL() + "'); -fx-background-size: cover;");
            } else {
                btn.setStyle("");
            }

            int row = Character.getNumericValue(pos.charAt(1)) - 1;
            int col = pos.charAt(0) - 'a';
            if ((row + col) % 2 == 0) {
                btn.setStyle(btn.getStyle() + "-fx-background-color: " + lightColor + ";");
            } else {
                btn.setStyle(btn.getStyle() + "-fx-background-color: " + darkColor + ";");
            }
        }
    }

    @FXML
    private void handleMove(javafx.event.ActionEvent event) {
        if (gameFinished) {
            return;
        }

        try {
            Button clickedButton = (Button) event.getSource();
            String position = getButtonPosition(clickedButton);

            if (selectedPiece == null) {
                Pieza pieza = tablero.getPiezaEnPosicion(position);
                if (pieza != null && pieza.getColor().equals(gestorJuego.getJugadorActual().getColor())) {
                    selectedPiece = clickedButton;
                    selectedPosition = position;
                    clickedButton.setStyle(clickedButton.getStyle() + "-fx-border-color: red;");
                }
            } else {
                if (position != null && gestorJuego.moverPieza(selectedPosition, position)) {
                    actualizarTablero();
                    selectedPiece.setStyle(selectedPiece.getStyle().replace("-fx-border-color: red;", ""));
                    selectedPiece = null;
                    selectedPosition = null;

                    URL mov = getClass().getResource("/org/example/chessgame/Sonido/Movimiento.mp3");
                    if (mov != null) {
                        Media movi = new Media(mov.toExternalForm());
                        movimiento = new MediaPlayer(movi);
                        movimiento.setStopTime(Duration.seconds(duracionMovimiento));
                        movimiento.play();
                    } else {
                        System.err.println("No se encontró el archivo de sonido de movimiento");
                    }

                    if (gestorJuego.estaEnJaqueMate(gestorJuego.getJugadorActual().getColor())) {
                        URL ganador = getClass().getResource("/org/example/chessgame/Sonido/SonidoGanador.mp3");
                        if (ganador != null) {
                            Media jaqueMate = new Media(ganador.toExternalForm());
                            jaqueMateGanador = new MediaPlayer(jaqueMate);
                            jaqueMateGanador.setStopTime(Duration.seconds(duracionSonidoGanador));
                            jaqueMateGanador.play();
                        } else {
                            System.err.println("No se encontró el archivo de sonido de jaque mate");
                        }
                        mostrarMensaje("¡Jaque mate!", "Ha ganado " + (gestorJuego.getJugadorActual().getColor().equals("blanco") ? nombreJugadorNegro : nombreJugadorBlanco), Alert.AlertType.INFORMATION);
                        gameFinished = true;
                    } else if (gestorJuego.estaEnJaque(gestorJuego.getJugadorActual().getColor())) {
                        URL jaque = getClass().getResource("/org/example/chessgame/Sonido/jaquesonido.mp3");
                        if (jaque != null) {
                            Media jaqueMedia = new Media(jaque.toExternalForm());
                            jaqueSonido = new MediaPlayer(jaqueMedia);
                            jaqueSonido.setStopTime(Duration.seconds(duracionJaqueSonido));
                            jaqueSonido.play();
                        } else {
                            System.err.println("No se encontró el archivo de sonido de jaque");
                        }
                        mostrarMensaje("¡Jaque!", "Las piezas " + gestorJuego.getJugadorActual().getColor() + " están en jaque.", Alert.AlertType.WARNING);
                    } else {
                        updateTurnLabel();
                    }
                } else {
                    URL error = getClass().getResource("/org/example/chessgame/Sonido/ErrorDeMovimiento.mp3");
                    if (error != null) {
                        Media errorMov = new Media(error.toExternalForm());
                        errorMovimiento = new MediaPlayer(errorMov);
                        errorMovimiento.setStopTime(Duration.seconds(duracionErrorMovimiento));
                        errorMovimiento.play();
                    } else {
                        System.err.println("No se encontró el archivo de sonido de error de movimiento");
                    }
                    selectedPiece.setStyle(selectedPiece.getStyle().replace("-fx-border-color: red;", ""));
                    selectedPiece = null;
                    selectedPosition = null;
                    mostrarMensaje("Movimiento inválido", "El movimiento no es válido.", Alert.AlertType.ERROR);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("Error", "Ha ocurrido un error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private String getButtonPosition(Button button) {
        for (Map.Entry<String, Button> entry : buttons.entrySet()) {
            if (entry.getValue() == button) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void mostrarMensaje(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText("Mensaje");
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    @FXML
    private void handleReset() {
        try {
            gestorJuego.iniciarJuego();
            tablero = gestorJuego.getTablero();
            actualizarTablero();
            selectedPiece = null;
            selectedPosition = null;
            updateTurnLabel();
            gameFinished = false;
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("Error", "Ha ocurrido un error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    private void updateTurnLabel() {
        if (gestorJuego.getJugadorActual().getColor().equals("blanco")) {
            turnLabel.setText("Turno de " + nombreJugadorBlanco);
        } else {
            turnLabel.setText("Turno de " + nombreJugadorNegro);
        }
        turnLabel.setVisible(true);
    }
}
