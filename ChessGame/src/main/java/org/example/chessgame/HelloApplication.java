package org.example.chessgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showStartScreen();
    }

    public void showStartScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("startscreen.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Inicio del Juego de Ajedrez");

            StartScreenController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startGame(String jugadorBlanco, String jugadorNegro) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chessboard.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Juego de Ajedrez");

            ChessController controller = loader.getController();
            controller.setJugadorBlanco(jugadorBlanco);
            controller.setJugadorNegro(jugadorNegro);

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
