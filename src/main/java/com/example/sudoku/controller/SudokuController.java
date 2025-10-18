package com.example.sudoku.controller;

import com.example.sudoku.model.Tablero;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * Controlador principal del Sudoku.
 * Maneja la interfaz, los eventos de botones y la validación del juego.
 */
public class SudokuController {

    @FXML
    private GridPane sudokuContainer;

    @FXML
    private Button btnStart;

    @FXML
    private Button btnValidar;

    @FXML
    private Button btnAyuda;

    /** Matriz de campos de texto que representan las celdas del Sudoku */
    private TextField[][] campos = new TextField[6][6];

    /** Modelo del tablero (lógica del Sudoku) */
    private Tablero tablero;

    private GridPane bloques() {
        GridPane gp = new GridPane();
        gp.setPrefSize(60, 60);
        gp.setMaxSize(60, 60);
        gp.getStyleClass().add("bloque");
        gp.setHgap(0);
        gp.setVgap(0);
        return gp;
    }

    private TextField campoTexto() {
        TextField tf = new TextField();
        tf.setPrefSize(60, 60);
        tf.setMinSize(60, 60);
        tf.setMaxSize(60, 60);
        tf.setAlignment(Pos.CENTER);

        // Limitar a solo 1 número del 1 al 6
        tf.setTextFormatter(new TextFormatter<>(c -> {
            if (c.getControlNewText().isEmpty()) return c;
            if (c.getControlNewText().matches("[1-6]")) return c;
            return null;
        }));

        return tf;
    }

    /**
     * Inicializa la interfaz y asigna los eventos a los botones.
     */
    @FXML
    public void initialize() {
        sudokuContainer.setVgap(0);
        sudokuContainer.setHgap(0);
        sudokuContainer.setAlignment(Pos.CENTER);

        btnValidar.setOnAction(event -> validarTablero());
        btnStart.setOnAction(event -> mostrarAlertInicio());
        btnAyuda.setOnAction(event -> mostrarPista());
    }

    /**
     * Muestra un cuadro de confirmación antes de generar un nuevo tablero.
     */
    private void mostrarAlertInicio() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Comenzar Juego");
        alert.setHeaderText("Iniciar juego de sudoku :)");
        alert.setContentText("Al aceptar se generará la cuadrícula de 6x6");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                generarTablero();
            }
        });
    }

    /**
     * Muestra una pista automática si hay una celda con una única opción válida.
     */
    private void mostrarPista() {
        // Recorrer todas las celdas
        for (int fila =0;fila<6; fila++) {
            for (int col= 0;col<6; col++) {

                // Solo en celdas vacías y que no estén deshabilitadas
                if (campos[fila][col].getText().isEmpty() && !campos[fila][col].isDisable()) {

                    // Probar valores del 1 al 6
                    int posibles =0;
                    int ultimoValido =0;

                    for (int num = 1;num<= 6;num++) {
                        if (tablero.esValido(fila, col, num)) {
                            posibles++;
                            ultimoValido =num;
                        }
                        if (posibles>1) break;
                    }

                    // Si solo hay UNA opción válida, la colocamos
                    if (posibles ==1) {
                        campos[fila][col].setText(String.valueOf(ultimoValido));
                        tablero.setNumero(fila, col, ultimoValido);
                        return;
                    }
                }
            }
        }

        // Si llegamos aquí significa que no hay pistas simples disponibles
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ayuda");
        alert.setHeaderText(null);
        alert.setContentText("No hay pistas disponibles por ahora.");
        alert.showAndWait();
    }

    /**
     * Resalta errores y detecta si el usuario ha ganado.
     */
    @FXML
    private void validarTablero() {
        boolean hayError = false;

        // Quitar estilo de error
        for (int fila =0;fila<6; fila++) {
            for (int col =0; col<6; col++) {
                campos[fila][col].setStyle("");
            }
        }

        //colocar los valores del jugador en el tablero
        for (int fila =0;fila<6; fila++) {
            for (int col =0;col< 6; col++) {
                String texto =campos[fila][col].getText();
                if (texto.isEmpty()) return; // hay casilla vacía, no validamos

                int valor =Integer.parseInt(texto);
                tablero.setNumero(fila, col, valor);
            }
        }

        //validar cada celda individualmente
        for (int fila=0;fila<6;fila++) {
            for (int col =0;col< 6;col++) {
                int valor =tablero.getNumero(fila, col);
                tablero.setNumero(fila, col, 0); // quitar para validar correctamente

                if (!tablero.esValido(fila, col, valor)) {
                    //Marcar celda incorrecta
                    campos[fila][col].setStyle("-fx-border-color: red; -fx-border-width: 2px");
                    hayError = true;
                }

                tablero.setNumero(fila, col, valor); // restaurar
            }
        }

        // si hubo errores no se muestra ganaste
        if (hayError) return;

        // si no hubo errores, ganó
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ganaste!");
        alert.setHeaderText(null);
        alert.setContentText("Felicidades! Completaste el Sudoku :)");
        alert.showAndWait();
    }


    /**
     * Genera el tablero visual y coloca las pistas del modelo.
     */
    private void generarTablero() {
        tablero = new Tablero();
        tablero.generarNuevoTablero();

        sudokuContainer.getChildren().clear();

        for (int fila = 0; fila < 3; fila++) { // 3 filas de bloques
            for (int columna = 0; columna < 2; columna++) { // 2 columnas de bloques
                GridPane gp = bloques();
                sudokuContainer.add(gp, columna, fila);

                for (int row = 0; row < 2; row++) {
                    for (int column = 0; column < 3; column++) {
                        TextField tf = campoTexto();

                        int filaReal = fila * 2 + row;
                        int columnaReal = columna * 3 + column;

                        int valor = tablero.getNumero(filaReal, columnaReal);
                        if (valor != 0) {
                            tf.setText(String.valueOf(valor));
                            tf.setDisable(true);
                        }

                        // Listener automático para validar al escribir
                        tf.textProperty().addListener((obs, oldText, newText) -> validarTablero());

                        campos[filaReal][columnaReal] = tf;
                        gp.add(tf, column, row);
                    }
                }
            }
        }
    }
}
