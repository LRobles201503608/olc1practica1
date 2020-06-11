/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analisis;

import java.util.ArrayList;

/**
 *
 * @author LRobles
 */
public class Nivel {
    int nivel,filas,columnas;
    String id;
    ArrayList<Character> tablero=new ArrayList<Character>();
    char [][] tab;
    public Nivel(int nivel, int filas, int columnas,String id,ArrayList<Character> tablero) {
        this.nivel = nivel;
        this.filas = filas;
        this.columnas = columnas;
        this.id = id;
        this.tablero=tablero;
    }
    public Nivel(int nivel, int filas, int columnas,String id,char [][] tablero,ArrayList<Character> tablero2) {
        this.nivel = nivel;
        this.filas = filas;
        this.columnas = columnas;
        this.id = id;
        this.tab=tablero;
        this.tablero=tablero2;
    }

    public char[][] getTab() {
        return tab;
    }

    public void setTab(char[][] tab) {
        this.tab = tab;
    }
    
    public ArrayList<Character> getTablero() {
        return tablero;
    }

    public void setTablero(ArrayList<Character> tablero) {
        this.tablero = tablero;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }
    
}
