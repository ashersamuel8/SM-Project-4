package com.project4.project_4_group_81;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class that runs the application
 * @author Bhavya Patel
 * @author Samuel Asher Kappala
 */
public class RunPizzeria extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("RU PIZZERIA");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method that launches the application
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}