package com.example.sudoku.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class SudokuController {

    @FXML
    private GridPane sudokuContainer;

    @FXML
    private Button btnStart;

    private GridPane bloques(){
        GridPane gp= new GridPane();
        gp.setPrefSize(60, 60);
        gp.setMaxSize(60, 60);

        gp.getStyleClass().add("bloque");
        gp.setHgap(0);
        gp.setVgap(0);
        return gp;
    }

    private TextField campoTexto(){ //tamaño de los bloques de adentro
        TextField tf = new TextField();
        tf.setPrefSize(60, 60);
        tf.setMinSize(60, 60);
        tf.setMaxSize(60, 60);
        tf.setAlignment(Pos.CENTER);
        return tf;
    }

    @FXML
    public void initialize() {
        System.out.println("hola");

        sudokuContainer.setVgap(0);
        sudokuContainer.setHgap(0);
        sudokuContainer.setAlignment(Pos.CENTER);

        btnStart.setOnAction(event -> mostrarAlertInicio());//evento del botón de inicio:
    }

    private void mostrarAlertInicio(){
        Alert alert=new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Comenzar Juego");
        alert.setHeaderText("Iniciar juego de sudoku :)");
        alert.setContentText("al aceptar se generará la cuadrícula de 6x6");

        alert.showAndWait().ifPresent(response->{
            if (response == ButtonType.OK){
                generarTablero();
            }
        });
    }
    //metodo q genera el tablero de 6x6
    private void generarTablero(){
        sudokuContainer.getChildren().clear();//limpia el gridpane antes de generar
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
