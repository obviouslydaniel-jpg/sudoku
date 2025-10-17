package com.example.sudoku.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Tablero {

    private int[][] celdas = new int [6][6];
    private Random random=new Random();


    //guardar un numero en el tablero
    public void setNumero (int fila, int columna, int valor){
        celdas[fila][columna]=valor;
    }

    //obtener un numero del tablero
    public int getNumero(int fila, int columna){
        return celdas [fila][columna];
    }

    //validar si se puede poner valor en esa celda
    public boolean esValido(int fila,int columna, int valor){
        return validarFila (fila, valor) &&
                validarColumna (columna, valor) &&
                validarBloque(fila,columna, valor);
    }

    private boolean validarFila(int fila, int valor){
        for (int c=0; c<6; c++){
            if (celdas[fila][c]==valor)return false;
        }
        return true;
    }
    private boolean validarColumna(int columna,int valor){
        for (int f=0; f<6; f++){
            if (celdas [f][columna]==valor) return false;
        }
        return true;
    }
    private boolean validarBloque(int fila, int columna, int valor){
        int inicioFila=(fila/2)*2;
        int inicioCol=(columna/3)*3;
        for (int f =inicioFila; f<inicioFila+2; f++){
            for (int c=inicioCol; c < inicioCol + 3; c++){
                if (celdas[f][c]==valor) return false;
            }
        }
        return true ;
    }

    private boolean resolver(int fila, int col){
        if (fila==6) return true;
        if (col==6) return resolver(fila + 1, 0);
        if (celdas[fila][col]!=0) return resolver(fila, col + 1);

        List<Integer> numeros = new ArrayList<>();
        for (int i = 1; i<= 6; i++) numeros.add(i);
        Collections.shuffle(numeros);

        for (int num : numeros) {
            if (esValido(fila, col, num)) {
                celdas[fila][col] = num;
                if (resolver(fila, col + 1)) {
                    return true;
                }
                celdas[fila][col] = 0;
            }
        }
        return false;

    }

    private void mezclarFilas() {
        for (int i = 0; i < 3; i++) { // hay 3 bloques de 2 filas
            int base = i * 2;
            if (random.nextBoolean()) {
                int[] temp = celdas[base];
                celdas[base] = celdas[base + 1];
                celdas[base + 1] = temp;
            }
        }
    }
    private void quitarNumeros() {
        // Generar posiciones 0..35
        List<int[]> posiciones = new ArrayList<>();
        for (int f = 0; f < 6; f++) {
            for (int c = 0; c < 6; c++) {
                posiciones.add(new int[]{f, c});
            }
        }
        // Barajar
        Collections.shuffle(posiciones);

        int aDejar = 7;
        int eliminados = 0;

        // quitar 36 - aDejar = 29 celdas
        for (int[] pos : posiciones) {
            if (eliminados >= 29) break;
            celdas[pos[0]][pos[1]] = 0;
            eliminados++;
        }
    }
    public void generarNuevoTablero() {
        // Limpio primero
        for (int f = 0; f < 6; f++) {
            for (int c = 0; c < 6; c++) {
                celdas[f][c] = 0;
            }
        }

        int numerosColocados = 0;
        while (numerosColocados < 7) {
            int fila = random.nextInt(6);
            int columna = random.nextInt(6);

            if (celdas[fila][columna] == 0) {
                int valor = random.nextInt(6) + 1;
                if (esValido(fila, columna, valor)) {
                    celdas[fila][columna] = valor;
                    numerosColocados++;
                }
            }
        }
    }

}
