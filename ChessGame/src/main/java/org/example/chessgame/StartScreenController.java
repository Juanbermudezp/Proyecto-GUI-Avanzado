package org.example.chessgame;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StartScreenController {

    @FXML
    private TextField nombreBlanco;

    @FXML
    private TextField nombreNegro;

    private HelloApplication mainApp;

    public void setMainApp(HelloApplication mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleStart() {
        String jugadorBlanco = nombreBlanco.getText();
        String jugadorNegro = nombreNegro.getText();

        if (jugadorBlanco.isEmpty() || jugadorNegro.isEmpty()) {
            // Mostrar un mensaje de error si los nombres están vacíos
            return;
        }

        mainApp.startGame(jugadorBlanco, jugadorNegro);
    }
}
