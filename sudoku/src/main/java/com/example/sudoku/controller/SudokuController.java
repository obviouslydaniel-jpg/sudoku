package com.example.sudoku.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;

public class SudokuController {

    @FXML
    private GridPane gridSudoku;

    @FXML
    private Button btnStart;

    @FXML
    public void initialize() {
        int size =6 ; //tamaño del tablero

        for (int fila = 0; fila < size; fila ++) {

            for (int columna=0; columna <size; columna++){ //dos bucles anidados que recorren las filas y las columnas generando las 36 celdas

                TextField casilla = new TextField();
                casilla.setPrefWidth(40);
                casilla.setPrefHeight(40);
                gridSudoku.add(casilla, columna, fila);


            }
        }

    }
}
