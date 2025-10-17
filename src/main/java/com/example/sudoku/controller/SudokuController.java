package com.example.sudoku.controller;

import com.example.sudoku.model.Tablero;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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

        // Limitar a solo 1 número del 1 al 6
        tf.setTextFormatter(new TextFormatter<>(c -> {
            if (c.getControlNewText().isEmpty()) {
                return c; // permitir borrar
            }
            // permitir solo un dígito entre 1 y 6
            if (c.getControlNewText().matches("[1-6]")) {
                return c;
            }
            return null; // rechazartodo lo demas
        }));

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

    private TextField crearCampoTexto() {
        TextField tf = new TextField();
        tf.setPrefSize(60, 60);
        tf.setAlignment(Pos.CENTER);

        tf.setOnKeyTyped(event ->{
            String character = event.getCharacter();

            if (character.equals("\b")) return;

            if (!character.matches("[1-6]")){
                event.consume();
                return;
            }
            if (tf.getText().length()>=1 ){
                event.consume();
            }
        });
        return tf;
    }


    //metodo q genera el tablero de 6x6
    private void generarTablero(){

        Tablero tablero=new Tablero();
        tablero.generarNuevoTablero();

        sudokuContainer.getChildren().clear();//limpia el gridpane antes de generar

        for (int fila = 0; fila < 3; fila ++) { //generar 3 filas de bloques
            //generar 2 columnas de bloques
            for (int columna=0; columna <2; columna++){

                GridPane gp = bloques();
                sudokuContainer.add(gp, columna, fila);


                for (int row = 0; row<2; row++){

                    for (int column=0; column<3; column++){

                        TextField tf = campoTexto();

                        int filaReal=fila* 2 + row ;
                        int columnaReal = columna*3+column;

                        int valor = tablero.getNumero(filaReal, columnaReal);

                        if (valor!=0){
                            tf.setText(String.valueOf(valor));
                            tf.setDisable(true);
                        }


                        gp.add(tf, column, row);
                    }
                }

            }
        }

    }
}
