package com.am.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class CalculatorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CalculatorApplication.class.getResource("views/mainScreen-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 730, 430);
        stage.setResizable(false);
        stage.setTitle("Calculator Inpro");
        stage.getIcons().add(new Image(CalculatorApplication.class.getResourceAsStream("images/logo.JPG")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}