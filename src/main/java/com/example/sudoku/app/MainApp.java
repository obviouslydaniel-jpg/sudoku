package com.example.sudoku.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader =new FXMLLoader(MainApp.class.getResource("/fxml/sudoku-view.fxml"));

        Scene scene =new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Sudoku 6x6");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
