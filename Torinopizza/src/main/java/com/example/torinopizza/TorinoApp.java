package com.example.torinopizza;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TorinoApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Sætter stage til torino-view med controller MainController
        FXMLLoader fxmlLoader = new FXMLLoader(TorinoApp.class.getResource("torino-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Torino Pizzaria - Ordre System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}