/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analisis;

/**
 *
 * @author LRobles
 */
public class Tokens {
    int linea, columna;
    String token,lexema;

    public Tokens(int linea, int columna, String token, String lexema) {
        this.linea = linea;
        this.columna = columna;
        this.token = token;
        this.lexema = lexema;
    }
    
}
