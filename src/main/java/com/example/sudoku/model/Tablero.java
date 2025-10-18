package com.example.sudoku.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Clase que representa el tablero del Sudoku 6x6.
 * Contiene la lógica de generación, validación y resolución del juego
 */

public class Tablero implements validadorSudoku {

    /** Matriz que guarda los valores del Sudoku */
    private int[][] celdas = new int[6][6];
    private Random random = new Random();


    /**
     * Asigna un número a una celda del tablero.
     * @param fila Fila del tablero (0-5)
     * @param columna Columna del tablero (0-5)
     * @param valor Valor a colocar (1-6)
     */
    public void setNumero(int fila, int columna, int valor) {
        celdas[fila][columna] = valor;
    }

    /**
     * Obtiene el número de una celda del tablero.
     * @param fila Fila del tablero (0-5)
     * @param columna Columna del tablero (0-5)
     * @return Número almacenado en la celda
     */
    public int getNumero(int fila, int columna) {
        return celdas[fila][columna];
    }

    /**
     * Verifica si un valor puede colocarse en la posición indicada.
     * @param fila Fila del tablero
     * @param columna Columna del tablero
     * @param valor Valor a validar
     * @return true si el valor es válido, false si hay conflicto
     */
    @Override
    public boolean esValido(int fila, int columna, int valor) {
        return validarFila(fila, valor) &&
                validarColumna(columna, valor) &&
                validarBloque(fila, columna, valor);
    }

    /** Valida que el número no se repita en la fila */
    private boolean validarFila(int fila, int valor) {
        for (int c = 0; c < 6; c++) {
            if (celdas[fila][c] == valor) return false;
        }
        return true;
    }

    /** Valida que el número no se repita en la columna */
    private boolean validarColumna(int columna, int valor) {
        for (int f = 0; f < 6; f++) {
            if (celdas[f][columna] == valor) return false;
        }
        return true;
    }

    /** Valida que el número no se repita en el bloque 2x3 */
    private boolean validarBloque(int fila, int columna, int valor) {
        int inicioFila = (fila / 2) * 2;
        int inicioCol = (columna / 3) * 3;
        for (int f = inicioFila; f < inicioFila + 2; f++) {
            for (int c = inicioCol; c < inicioCol + 3; c++) {
                if (celdas[f][c] == valor) return false;
            }
        }
        return true;
    }

    /**
     * Resuelve el tablero por completo usando backtracking.
     * @param fila Fila actual
     * @param col Columna actual
     * @return true si se puede resolver, false si no
     */
    private boolean resolver(int fila, int col) {
        if (fila == 6) return true;
        if (col == 6) return resolver(fila + 1, 0);
        if (celdas[fila][col] != 0) return resolver(fila, col + 1);

        List<Integer> numeros = new ArrayList<>();
        for (int i = 1; i <= 6; i++) numeros.add(i);
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

    /** Mezcla aleatoriamente las filas dentro de cada bloque de 2 */

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

    /** Genera un tablero nuevo, resuelto y luego elimina celdas según dificultad */
    public void generarNuevoTablero() {
        // Limpiar tablero
        for (int f = 0; f < 6; f++)
            for (int c = 0; c < 6; c++)
                celdas[f][c] = 0;

        // Resolver tablero completo
        resolver(0, 0);

        // Lista para marcar todas las celdas como ocultas al principio
        boolean[][] mostrar =new boolean[6][6];

        int pistasDeseadas =18;
        int pistasColocadas =0;

        // Distribuir pistas bloque por bloque
        List<int[]> bloques = new ArrayList<>();
        for (int br = 0; br <3;br++)
            for (int bc=0;bc< 2;bc++)
                bloques.add(new int[]{br, bc});

        Collections.shuffle(bloques, random);

        for (int[] bloque:bloques){
            int br = bloque[0], bc = bloque[1];

            List<int[]> celdasBloque = new ArrayList<>();
            for (int r = br*2;r<br* 2+ 2;r++)
                for (int c= bc* 3; c<bc *3+ 3;c++)
                    celdasBloque.add(new int[]{r, c});

            Collections.shuffle(celdasBloque, random);

            int pistasBloque =1;
            if (pistasColocadas +3<= pistasDeseadas) pistasBloque = 3;

            for (int i =0; i<pistasBloque; i++) {
                int[] pos=celdasBloque.get(i);
                mostrar[pos[0]][pos[1]] = true;
                pistasColocadas++;
            }
            if (pistasColocadas >= pistasDeseadas) break;
        }

        // Poner en 0 las celdas que no se mostrarán
        for (int f =0; f<6;f++)
            for (int c =0; c<6;c++)
                if (!mostrar[f][c])
                    celdas[f][c] = 0;
    }




}



