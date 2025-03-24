/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya;

/**
 *
 * @author Miguel Angel
 */

public class Juego {
    public static final char VACIO = ' ';
    public static final char JUGADOR_X = 'X';
    public static final char JUGADOR_O = 'O';
    
    private char[][] tablero;
    private char turno;
    private char jugadorPrincipal;  // Para saber quién es el jugador que inicia el juego
    
    public Juego(String simboloJugadorPrincipal) {
        tablero = new char[3][3];
        reiniciar(simboloJugadorPrincipal);
    }
    
    public void reiniciar(String simboloJugadorPrincipal) {
        // Inicializa el tablero
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = VACIO;
            }
        }
        // Asignamos el símbolo del jugador principal
        if ("X".equals(simboloJugadorPrincipal)) {
            jugadorPrincipal = JUGADOR_X;
            turno = JUGADOR_X;  // El jugador principal comienza con "X"
        } else {
            jugadorPrincipal = JUGADOR_O;
            turno = JUGADOR_X;  // El primer turno lo tiene siempre "X", aunque el jugador principal sea "O"
        }
    }
    
    public boolean hacerMovimiento(int fila, int columna) {
        if (fila < 0 || fila >= 3 || columna < 0 || columna >= 3) {
            return false;
        }
        if (tablero[fila][columna] != VACIO) {
            return false;
        }
        // Realizamos el movimiento
        tablero[fila][columna] = turno;
        turno = (turno == JUGADOR_X ? JUGADOR_O : JUGADOR_X);  // Cambiar el turno
        return true;
    }
    
    public char chequearGanador() {
        // Comprobar si hay un ganador (filas, columnas y diagonales)
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0] != VACIO && tablero[i][0] == tablero[i][1] && tablero[i][1] == tablero[i][2]) {
                return tablero[i][0];
            }
        }
        for (int j = 0; j < 3; j++) {
            if (tablero[0][j] != VACIO && tablero[0][j] == tablero[1][j] && tablero[1][j] == tablero[2][j]) {
                return tablero[0][j];
            }
        }
        if (tablero[0][0] != VACIO && tablero[0][0] == tablero[1][1] && tablero[1][1] == tablero[2][2]) {
            return tablero[0][0];
        }
        if (tablero[0][2] != VACIO && tablero[0][2] == tablero[1][1] && tablero[1][1] == tablero[2][0]) {
            return tablero[0][2];
        }
        return VACIO;
    }
    
    public boolean tableroCompleto() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == VACIO) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public char getTurno() {
        return turno;
    }
    
    public char getValorEn(int fila, int columna) {
        return tablero[fila][columna];
    }
    
    // Método para obtener el jugador principal
    public char getJugadorPrincipal() {
        return jugadorPrincipal;
    }
}


