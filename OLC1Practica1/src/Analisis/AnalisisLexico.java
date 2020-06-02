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
public class AnalisisLexico {
    String cadena;
    ArrayList<Nivel> niveles=new ArrayList<Nivel>();
    ArrayList<Character> tablero =new ArrayList<Character>();
    ArrayList<Figuras> figuras=new ArrayList<Figuras>();
    public AnalisisLexico(String cadena) {
        this.cadena = cadena;
    }
    public void AnalisisTableroNiveles(String cadena){
        int linea = 1;
        int columna = 1;
        String cadconcat="";
        int estado=0;
        int caracter =0;
        char caracteractual;
        int nivel =0;
        int N=0;
        int P=0;
        String id="";
        int contadorsalto=0, contadormayor=0, contadorexclamacion=0;
        for (int i = 0; i<cadena.length() ; i++){
            caracteractual=cadena.charAt(i);
            caracter=(int)caracteractual;
            switch(estado){
                case 0:
                    if(caracter == 47 || caracter == 60){
                        estado=1;
                    }else if(caracter>=50 && caracter<=58){
                        cadconcat += String.valueOf(caracteractual);
                        estado=2;
                    }else{
                        estado=90;
                    }
                    break;
//************************************case 1 estado de eliminacion de comentarios*********************************************************
                case 1:
                    if(caracter == 33 || caracter == 47){
                        estado=1;
                        contadorexclamacion++;
                    }else if(caracter == 62){
                        contadormayor++;
                    }else if(caracteractual=='\n'){
                        contadorsalto++;
                    }else if(caracter>=47 && caracter <=58){
                        if(contadorsalto==1 || (contadormayor==1&&contadorexclamacion==2)){
                          if(caracter>=50 && caracter<=58){
                                cadconcat += String.valueOf(caracteractual);
                                estado=2;
                                contadorsalto=0;
                                contadormayor=0;
                                contadorexclamacion=0;
                            }  
                        }else{
                            estado = 1;
                        }
                    }
                    else{
                        estado=2;
                    }
                    break;
//**********************************case 2estado de nivel y traslado a dimensiones del tablero*********************************************
                case 2:
                    if(caracter==32){
                        estado = 2;
                    }
                    if(cadconcat.equals("")){
                        if(caracter>=47 && caracter <=58){
                            cadconcat += String.valueOf(caracteractual);
                            estado=2;
                        }
                    }else{
                        if(cadena.charAt(i+1)=='\n'){
                            cadconcat += String.valueOf(caracteractual);
                            estado=2;
                            if(cadconcat.equalsIgnoreCase("10")){
                                nivel=Integer.parseInt(cadconcat);
                                cadconcat="";
                            } else {
                                cadconcat="";
                                System.out.println("ERROR NUMERO DE NIVEL NO PERMITIDO");
                            }
                        }else if(cadena.charAt(i+1)=='x'){
                            if(caracter>=47 && caracter <=58){
                                cadconcat += String.valueOf(caracteractual);
                                N=Integer.parseInt(cadconcat);
                                cadconcat="";
                                estado=3;
                            }
                        }
                    }
                    break;
//*******************************case 3 traspaso de la x de la dimension N a la dimension P***************************            
                case 3:
                    if(caracter==120||caracter==88){
                        estado=4;
                    }else if(caracter==32){
                        estado = 3;
                    }else{
                        estado=90;
                    }
                    break;
//******************************case 4 determinacion de la dimension P*************************************
                case 4:
                    if(caracter==32){
                        estado = 2;
                    }
                    if(caracter>=47 && caracter <=58){
                            cadconcat += String.valueOf(caracteractual);
                            estado=4;
                    }else if(caracter==32){
                        P=Integer.parseInt(cadconcat);
                        estado=5;
                        cadconcat="";
                    } else {
                        estado=90;
                    }
                    break;
                case 5:
                    if(cadconcat.equals("")){
                        if((caracter>=64&&caracter<=91)||(caracter>=96&&caracter<=123)){
                            cadconcat+=caracteractual;
                        }else{
                            System.out.println("ERROR UN ID SOLO PUEDE EMPEZAR CON UNA LETRA");
                        }
                    }else{
                        if(caracter==32){
                            
                        }else if(caracteractual=='\n'){
                            id=cadconcat;
                            estado=6;
                            cadconcat="";
                        }else{
                            cadconcat+=caracteractual;
                        }
                        
                    }
                    break;
                case 6:
                    if(caracter==42||caracter==35){
                        tablero.add(caracteractual);
                        estado=7;
                    }else if(caracteractual=='\n'){
                        estado=7;
                    }else if(caracter>=47 && caracter <=58){
                        niveles.add(new Nivel(nivel,N,P,id,tablero));
                        id="";
                        N=0;
                        P=0;
                        tablero.clear();
                        cadconcat+=caracteractual;
                        estado=2;
                    }
                    break;
            }
        }
        
    }
    public void AnalisisFiguras(String cadena){
        int linea = 1;
        int columna = 1;
        String cadconcat="";
        int estado=0;
        int caracter =0;
        char caracteractual;
        String simbolo="";
        String posicion="";
        for(int i=0;i<cadena.length();i++){
            caracteractual=cadena.charAt(i);
            caracter=(int)caracteractual;
            switch(estado){
                case 0:
                    if(caracter==73||caracter==74||caracter==76||caracter==79||caracter==83||caracter==84||caracter==90){
                        simbolo=String.valueOf(caracteractual);
                        estado=1;
                    }else{
                        estado=90;
                    }
                    break;
                case 1:
                    if(caracter==32){
                        estado=1;
                    }else if(caracter==44){
                        estado=2;
                    }else{
                        estado=90;
                    }
                    break;
                case 2:
                    if(caracter==94||caracter==118||caracter==60||caracter==62){
                        posicion=String.valueOf(caracteractual);
                        estado=3;
                    }else if(caracter==32){
                        estado=2;
                    }
                    break;
                case 3:
                    if (caracteractual=='\n'){
                        figuras.add(new Figuras(simbolo,posicion));
                        estado=4;
                    }else{
                        estado=90;
                    }
                    break;
                case 4:
                    if(caracter==73||caracter==74||caracter==76||caracter==79||caracter==83||caracter==84||caracter==90){
                        simbolo=String.valueOf(caracteractual);
                        estado=1;
                    }else{
                        estado=90;
                    }
                    break;
            }
        }
    }
}
