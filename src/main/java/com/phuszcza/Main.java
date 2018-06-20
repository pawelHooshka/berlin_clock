package com.phuszcza;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clock.fxml"));
        Parent root = loader.load();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Berlin Clock");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        ClockController clockController = loader.getController();
        primaryStage.setOnCloseRequest(event -> {
            clockController.stop();
        });

        clockController.start();
    }
}
