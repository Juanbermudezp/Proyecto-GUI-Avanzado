module org.example.chessgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.swing;
    requires javafx.media;

    opens org.example.chessgame to javafx.fxml;
    exports org.example.chessgame;
}
