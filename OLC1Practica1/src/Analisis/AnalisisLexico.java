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
    ArrayList<Character> tablero;
    ArrayList<Figuras> figuras=new ArrayList<Figuras>();
    ArrayList<Tokens> tokens=new ArrayList<Tokens>();
    ArrayList<String> Errores=new ArrayList<String>();
    char [][] tab;
    public AnalisisLexico(ArrayList<Nivel>niveles, ArrayList<Figuras>figuras,ArrayList<String> Errores,ArrayList<Tokens> tokens) {
        this.niveles=niveles;
        this.figuras=figuras;
        this.tokens=tokens;
        this.Errores=Errores;
    }
    
    public void AnalisisTableroNivelesV2(String cadena){
        tablero=new ArrayList<Character>();
        int linea = 1;
        int columna = 0;
        String cadconcat="";
        int estado=0;
        int caracter =0;
        char caracteractual;
        String simbolo="";
        String posicion="";
        int nivel =0;
        int N=0;
        int P=0;
        String id="";
        ArrayList<Character> tableroaux =new ArrayList<Character>();
        for(int i=0;i<cadena.length();i++){
            caracteractual=cadena.charAt(i);
            caracter=(int)caracteractual;
            columna++;
            switch(estado){
                case 0:
                    if(caracter == 60){
                        cadconcat+=String.valueOf(caracteractual);
                        estado = 1;
                    }else if(caracter>47&&caracter<58){
                        cadconcat+=String.valueOf(caracteractual);
                        if(cadena.charAt(i+1)=='\n'||cadena.charAt(i+1)=='\r'){
                            if(Integer.parseInt(cadconcat)<=10&&Integer.parseInt(cadconcat)>=3){
                                nivel=Integer.parseInt(cadconcat);
                                tokens.add(new Tokens(linea,columna,"Numero",String.valueOf(nivel)));
                                cadconcat="";
                            }else{
                                System.out.println("ERROR EL NIVEL NO ES PERMITIDO");
                            }
                    }
                        if(this.tablero.size()!=0){
                            tableroaux=tablero;
                            tab=new char[N][P];
                            Llenado(tableroaux,N,P);
                            niveles.add(new Nivel(nivel,N,P,id,tab,tableroaux));
                            id="";
                            N=0;
                            P=0;
                            tableroaux=new ArrayList<Character>();
                            tablero =new ArrayList<Character>();
                            this.tablero.clear();
                            tableroaux.clear();
                            
                        }
                        estado=7;
                    }else if((caracter>64&&caracter<91)||(caracter>96&&caracter<123)){
                        cadconcat+=String.valueOf(caracteractual);
                        estado=10;
                    }else if(caracter==47){
                        cadconcat+=String.valueOf(caracteractual);
                        estado=11;
                    }else if(caracter==35||caracter==42){
                        estado=0;
                        tokens.add(new Tokens(linea,columna,"Token: "+caracteractual,String.valueOf(caracteractual)));
                        tablero.add(caracteractual);
                        /*if(((int)cadena.charAt(i+2)>47&&(int)cadena.charAt(i+2)<58)||((int)cadena.charAt(i+3)>47&&(int)cadena.charAt(i+3)<58)){
                            tableroaux=tablero;
                            niveles.add(new Nivel(nivel,N,P,id,tableroaux));
                            id="";
                            N=0;
                            P=0;
                        }*/
                    }else if(caracter==32||caracter==10||caracter==13||caracter==9){
                        if(caracter==10||caracter==13){
                            linea++;
                            columna=0;
                        }
                    }else{
                        Errores.add("Caracter erroneo: "+caracteractual+" Linea: "+linea+" Columna: "+columna);
                    }
                    break;
                case 1:
                    if(caracter==33){
                        cadconcat+=String.valueOf(caracteractual);
                        estado=2;
                    }else{
                        Errores.add("Caracter erroneo: "+caracteractual+" Linea: "+linea+" Columna: "+columna);
                        estado=2;
                    }
                    break;
                case 2:
                    if(caracter==10||caracter==13){
                            linea++;
                            columna=0;
                        }
                    cadconcat+=String.valueOf(caracteractual);
                    estado=3;
                    break;
                case 3:
                    if(caracter==62){
                        cadconcat+=String.valueOf(caracteractual);
                        cadconcat.replace('\r', ' ');
                        cadconcat.replace('!', ' ');
                        estado=0;
                        tokens.add(new Tokens(linea,columna,"Comentario Multilinea",cadconcat.replace('\n', ' ')));
                        cadconcat="";
                    }else{
                        if(caracter==10||caracter==13){
                            linea++;
                            columna=0;
                        }
                        cadconcat+=String.valueOf(caracteractual);
                        estado=3;
                    }
                    break;
                case 7:
                    if(caracter>47&&caracter<58){
                        cadconcat+=String.valueOf(caracteractual);
                        if(cadena.charAt(i+1)=='\n'||cadena.charAt(i+1)=='\r'){
                            if(Integer.parseInt(cadconcat)<=10&&Integer.parseInt(cadconcat)>=3){
                                nivel=Integer.parseInt(cadconcat);
                                tokens.add(new Tokens(linea,columna,"Numero",String.valueOf(nivel)));
                                cadconcat="";
                                linea++;
                                columna=0;
                            }else{
                                System.out.println("ERROR EL NIVEL NO ES PERMITIDO");
                            }
                    }
                        estado=7;
                    }else if(caracter==45){
                        N=Integer.parseInt(cadconcat);
                        tokens.add(new Tokens(linea,columna,"Numero",String.valueOf(N)));
                        cadconcat="";
                        tokens.add(new Tokens(linea,columna,"Token: "+caracteractual,String.valueOf(caracteractual)));
                        estado = 8;
                    }else if(caracter==32||caracter==9||caracter==10||caracter==13){
                        if(caracter==10||caracter==13){
                            linea++;
                            columna=0;
                        }
                    }else{
                        Errores.add("Caracter erroneo: "+caracteractual+" Linea: "+linea+" Columna: "+columna);
                    }
                    break;
                case 8:
                    if(caracter>47&&caracter<58){
                        cadconcat+=String.valueOf(caracteractual);
                        estado=9;
                        if(((int)cadena.charAt(i+1)>64&&(int)cadena.charAt(i+1)<91)||((int)cadena.charAt(i+1)>96&&(int)cadena.charAt(i+1)<123)){
                                P=Integer.parseInt(cadconcat);
                                tokens.add(new Tokens(linea,columna,"Numero",String.valueOf(P)));
                                cadconcat="";
                                estado=0;
                        }
                    }else if(caracter==32||caracter==9){
                    
                    }else{
                        Errores.add("Caracter erroneo: "+caracteractual+" Linea: "+linea+" Columna: "+columna);
                    }
                    break;
                case 9:
                    if(caracter>47&&caracter<58){
                        cadconcat+=String.valueOf(caracteractual);
                        estado=9;
                        if(((int)cadena.charAt(i+1)>64&&(int)cadena.charAt(i+1)<91)||((int)cadena.charAt(i+1)>96&&(int)cadena.charAt(i+1)<123)||(int)cadena.charAt(i+1)==32||(int)cadena.charAt(i+1)==9){
                                P=Integer.parseInt(cadconcat);
                                tokens.add(new Tokens(linea,columna,"Numero",String.valueOf(P)));
                                cadconcat="";
                                estado=0;
                        }
                    }else if(caracter==32||caracter==9){
                    
                    }else{
                        Errores.add("Caracter erroneo: "+caracteractual+" Linea: "+linea+" Columna: "+columna);
                    }
                    
                    break;
                case 10:
                    if((caracter>64&&caracter<91)||(caracter>96&&caracter<123)){
                        cadconcat+=String.valueOf(caracteractual);
                        estado=10;
                    }else if(caracter==95){
                        cadconcat+=String.valueOf(caracteractual);
                        estado=10;
                    }else if(caracter>47&&caracter<58){
                        cadconcat+=String.valueOf(caracteractual);
                        estado=10;
                    }else if(caracter==10||caracter==13){
                        id=cadconcat;
                        linea++;
                        columna=0;
                        tokens.add(new Tokens(linea,columna,"Token Identificador",id));
                        estado=0;
                        cadconcat="";
                    }else{
                        Errores.add("Caracter erroneo: "+caracteractual+" Linea: "+linea+" Columna: "+columna);
                    }
                    break;
                case 11:
                    if(caracter==47){
                        cadconcat+=String.valueOf(caracteractual);
                        estado=12;
                    }else{
                        Errores.add("Caracter erroneo: "+caracteractual+" Linea: "+linea+" Columna: "+columna);
                    }
                    break;
                case 12:
                    cadconcat+=String.valueOf(caracteractual);
                    estado=13;
                    break;
                case 13:
                    if(caracter==10||caracter==13){
                        tokens.add(new Tokens(linea,columna,"Comentario Unilinea",cadconcat));
                        linea++;
                        columna=0;
                        estado=0;
                        cadconcat="";
                    }else{
                        cadconcat+=String.valueOf(caracteractual);
                        estado=13;
                    }
            }
        }
        tableroaux=tablero;
        tab=new char[N][P];
        Llenado(tableroaux,N,P);
        niveles.add(new Nivel(nivel,N,P,id,tab,tableroaux));
        id="";
        N=0;
        P=0;
        System.out.println("Se ingreso el nivel correctamente");
    }
    public void AnalisisTableroNiveles(String cadena){
        int linea = 1;
        int columna = 0;
        String cadconcat="";
        int estado=0;
        int caracter =0;
        char caracteractual;
        int nivel =0;
        int N=0;
        int P=0;
        String id="";
        ArrayList<Character> tableroaux =new ArrayList<Character>();
        int contadorsalto=0, contadormayor=0, contadorexclamacion=0,contadorbarra=0;
        for (int i = 0; i<cadena.length() ; i++){
            caracteractual=cadena.charAt(i);
            caracter=(int)caracteractual;
            columna++;
            switch(estado){
                case 0:
                    if(caracter == 47 || caracter == 60){
                        estado=1;
                    }else if(caracter>=50 && caracter<=58){
                        cadconcat += String.valueOf(caracteractual);
                        estado=2;
                    }else{
                        Errores.add("Caracter erroneo: "+caracteractual+" Linea: "+linea+" Columna: "+columna);
                        System.out.println("Existe un error en los caracteres, case 0 archivo 1");
                        //estado=90;
                    }
                    break;
//************************************case 1 estado de eliminacion de comentarios*********************************************************
                case 1:
                    if(caracter == 33 || caracter == 47){
                        estado=1;
                        if(caracter==33){
                            contadorexclamacion++;    
                        }else{
                            contadorbarra++;
                        }
                    }else if(caracter == 62){
                        contadormayor++;
                    }else if(caracteractual=='\n'){
                        contadorsalto++;
                        linea++;
                        columna=0;
                    }else if(caracter>=47 && caracter <=58){
                        if((contadorsalto==1&&contadorbarra>=1)|| (contadormayor==1&&contadorexclamacion==2)){
                          if(caracter>=50 && caracter<=58){
                                cadconcat += String.valueOf(caracteractual);
                                estado=2;
                                contadorsalto=0;
                                contadormayor=0;
                                contadorexclamacion=0;
                                tokens.add(new Tokens(linea,columna,"Comentario Multilinea","Comentario Omitido"));
                            }  
                        }else{
                            cadconcat+=String.valueOf(caracteractual);
                            estado = 1;
                        }
                    }
                    else{
                        tokens.add(new Tokens(linea,columna,"Comentario Una Linea","Comentario Omitido"));
                        estado=2;
                    }
                    break;
//**********************************case 2estado de nivel y traslado a dimensiones del tablero*********************************************
                case 2:
                    tablero =new ArrayList<Character>();
                    tableroaux=new ArrayList<Character>();
                    tablero.clear();
                    tableroaux.clear();
                    if(caracter==32||caracter==10||caracter==13){
                        estado = 2;
                    }
                    if(cadconcat.equals("")){
                        if(caracter>=47 && caracter <=58){
                            cadconcat += String.valueOf(caracteractual);
                            estado=2;
                        }
                    }else{
                        if(cadena.charAt(i+1)=='\n'||cadena.charAt(i)=='\n'){
                            if(cadena.charAt(i)!='\r'){
                                //cadconcat += String.valueOf(caracteractual);
                                linea++;
                                columna=0;
                                estado=2;   
                            }
                            if(Integer.parseInt(cadconcat)<=10&&Integer.parseInt(cadconcat)>=3){
                                nivel=Integer.parseInt(cadconcat);
                                tokens.add(new Tokens(linea,columna,"Numero",String.valueOf(nivel)));
                                cadconcat="";
                            } else {
                                cadconcat="";
                                Errores.add("Numero de nivel no permitido Linea: "+linea+" Columna: "+columna);
                                System.out.println("ERROR NUMERO DE NIVEL NO PERMITIDO");
                            }
                        }else if(cadena.charAt(i+1)=='x'){
                            if(caracter>=47 && caracter <=58){
                                cadconcat += String.valueOf(caracteractual);
                                N=Integer.parseInt(cadconcat);
                                cadconcat="";
                                tokens.add(new Tokens(linea,columna,"Numero",String.valueOf(N)));
                                estado=3;
                            }
                        }else if(caracter>=47 && caracter <=58){
                            cadconcat += String.valueOf(caracteractual);
                        }
                    }
                    break;
//*******************************case 3 traspaso de la x de la dimension N a la dimension P***************************            
                case 3:
                    if(caracter==120||caracter==88){
                        tokens.add(new Tokens(linea,columna,"Token X ","x"));
                        estado=4;
                    }else if(caracter==32){
                        estado = 3;
                    }else{
                        Errores.add("Caracter erroneo: "+caracteractual+" Linea: "+linea+" Columna: "+columna);
                        System.out.println("Existe un error en los caracteres, case 3 archivo 1");
                        //estado=90;
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
                    }else if(caracter==32||caracter==10||caracter==13){
                        P=Integer.parseInt(cadconcat);
                        tokens.add(new Tokens(linea,columna,"Numero",String.valueOf(P)));
                        estado=5;
                        cadconcat="";
                    } else {
                        Errores.add("Caracter erroneo: "+caracteractual+" Linea: "+linea+" Columna: "+columna);
                        System.out.println("Existe un error en los caracteres, case 4 archivo 1");
                        //estado=90;
                    }
                    break;
                case 5:
                    if(cadconcat.equals("")){
                        if((caracter>=64&&caracter<=91)||(caracter>=96&&caracter<=123)){
                            cadconcat+=caracteractual;
                        }else{
                            Errores.add("ERROR UN ID SOLO PUEDE EMPEZAR CON UNA LETRA Linea: "+linea+" Columna: "+columna);
                            System.out.println("ERROR UN ID SOLO PUEDE EMPEZAR CON UNA LETRA");
                        }
                    }else{
                        if(caracter==32){
                            
                        }else if(caracteractual=='\n'){
                            id=cadconcat;
                            linea++;
                            columna=0;
                            tokens.add(new Tokens(linea,columna,"Token Identificador",id));
                            estado=6;
                            cadconcat="";
                        }else{
                            cadconcat+=caracteractual;
                        }
                        
                    }
                    break;
                case 6:
                    if(caracter==42||caracter==35){
                        tokens.add(new Tokens(linea,columna,"Token: "+caracteractual,String.valueOf(caracteractual)));
                        tablero.add(caracteractual);
                        estado=6;
                    }else if(caracteractual=='\n'){
                        linea++;
                        columna=0;
                        estado=6;
                    }else if(caracter>=47 && caracter <=58){
                        tableroaux=tablero;
                        niveles.add(new Nivel(nivel,N,P,id,tableroaux));
                        id="";
                        N=0;
                        P=0;
                        cadconcat+=caracteractual;
                        estado=2;
                        System.out.println("Se ingreso el nivel correctamente");
                    }else if(caracter==32||caracter==10||caracter==13){
                        
                    }else{
                        Errores.add("Caracter erroneo: "+caracteractual+" Linea: "+linea+" Columna: "+columna);
                        System.out.println("Existe un error en los caracteres, case 6 archivo 1");
                    }
                    break;
            }
        }
        tableroaux=tablero;
        niveles.add(new Nivel(nivel,N,P,id,tableroaux));
        id="";
        N=0;
        P=0;
        System.out.println("Se ingreso el nivel correctamente");
    }
    public void AnalisisFiguras(String cadena){
        int linea = 1;
        int columna = 0;
        String cadconcat="";
        int estado=0;
        int caracter =0;
        char caracteractual;
        String simbolo="";
        String posicion="";
        for(int i=0;i<cadena.length();i++){
            caracteractual=cadena.charAt(i);
            caracter=(int)caracteractual;
            columna++;
            switch(estado){
                case 0:
                    if(caracter==73||caracter==74||caracter==76||caracter==79||caracter==83||caracter==84||caracter==90){
                        simbolo=String.valueOf(caracteractual);
                        tokens.add(new Tokens(linea,columna,"Pieza Tetris",String.valueOf(simbolo)));
                        estado=1;
                    }else{
                        Errores.add("Caracter erroneo: en archivo 2"+caracteractual+" Linea: "+linea+" Columna: "+columna);
                        System.out.println("Existe un error en los caracteres, case 0 archivo 2");
                        //estado=90;
                    }
                    break;
                case 1:
                    if(caracter==32){
                        estado=1;
                    }else if(caracter==44){
                        tokens.add(new Tokens(linea,columna,"Coma",","));
                        estado=2;
                    }else{
                        Errores.add("Caracter erroneo en archivo 2: "+caracteractual+" Linea: "+linea+" Columna: "+columna);
                        System.out.println("Existe un error en los caracteres, case 1 archivo 2");
                        //estado=90;
                    }
                    break;
                case 2:
                    if(caracter==94||caracter==118||caracter==60||caracter==62){
                        posicion=String.valueOf(caracteractual);
                        tokens.add(new Tokens(linea,columna,"Posicion Pieza",String.valueOf(posicion)));
                        estado=3;
                    }else if(caracter==32){
                        estado=2;
                    }
                    break;
                case 3:
                    if (cadena.charAt(i)=='\r'||cadena.charAt(i+1)=='\n'){
                        figuras.add(new Figuras(simbolo,posicion));
                        estado=4;
                        linea++;
                        columna=0;
                    }else{
                        Errores.add("Caracter erroneo en archivo 2: "+caracteractual+" Linea: "+linea+" Columna: "+columna);
                        System.out.println("Existe un error en los caracteres, case 3 archivo 2");
                        //estado=90;
                    }
                    break;
                case 4:
                    if (caracter==13||caracter==10){
                    
                    }else if(caracter==73||caracter==74||caracter==76||caracter==79||caracter==83||caracter==84||caracter==90){
                        simbolo=String.valueOf(caracteractual);
                        tokens.add(new Tokens(linea,columna,"Pieza Tetris",String.valueOf(simbolo)));
                        estado=1;
                    }else{
                        Errores.add("Caracter erroneo en archivo 2: "+caracteractual+" Linea: "+linea+" Columna: "+columna);
                        System.out.println("Existe un error en los caracteres, case 4 archivo 2");
                        //estado=90;
                    }
                    break;
            }
        }
        figuras.add(new Figuras(simbolo,posicion));
        System.out.println("");
    }

    private void Llenado(ArrayList<Character>tableroaux,int X ,int Y) {
        int posarreglo=0;
        for(int i=0;i<X;i++){
            for(int j =0;j<Y;j++){
                if(posarreglo<tableroaux.size()){
                    tab[i][j]=tableroaux.get(posarreglo);
                    posarreglo++;
                }
                
            }
        }
    }
}
