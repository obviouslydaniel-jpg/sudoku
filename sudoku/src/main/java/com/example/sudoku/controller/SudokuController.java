package com.example.sudoku.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;

public class SudokuController {

    @FXML
    private GridPane grid;

    @FXML
    private Button btnStart;

    @FXML
    public void initialize() {
        System.out.println("Controlador listo.");
    }
}
