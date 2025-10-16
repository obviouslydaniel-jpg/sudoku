package com.example.sudoku.model;

public class Tablero {

    private int[][] celdas = new int [6][6];

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
}
