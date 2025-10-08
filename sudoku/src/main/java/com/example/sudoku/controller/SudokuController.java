package com.example.sudoku.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;

public class SudokuController {

    @FXML
    private GridPane sudokuContainer;

    @FXML
    private Button btnStart;

    private GridPane bloques(){
        GridPane gp= new GridPane();
        gp.getStyleClass().add("bloque");
        return gp;
    }

    private TextField campoTexto(){
        TextField tf = new TextField();
        return tf;
    }

    @FXML
    public void initialize() {
    System.out.println("hola");
        for (int fila = 0; fila < 3; fila ++) {

            for (int columna=0; columna <2; columna++){ //dos bucles anidados que recorren las filas y las columnas generando las 36 celdas

                GridPane gp = bloques();
                sudokuContainer.add(gp, columna, fila);


                for (int row = 0; row<2; row++){

                    for (int column=0; column<3; column++){

                        TextField tf = campoTexto();
                        gp.add(tf, column, row);
                    }
                }

            }
        }

    }
}
