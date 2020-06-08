/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analisis;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import javax.swing.JFrame;

/**
 *
 * @author LRobles
 */
public class JuegoTetris extends JPanel{
    int contadorrepro=0;    
    private ArrayList<String>pi=new ArrayList();
    int dimensionx=0,dimensiony=0;
    private final Point[][][] mypoint={
        {
            //I
            {new Point(0,1),new Point(1,1),new Point(2,1),new Point(3,1)}, // I,<
            {new Point(1,0),new Point(1,1),new Point(1,2),new Point(1,3)}, // I,^
            {new Point(0,1),new Point(1,1),new Point(2,1),new Point(3,1)}, // I,>
            {new Point(1,0),new Point(1,1),new Point(1,2),new Point(1,3)} // I,v
        },
        {
            //L
            {new Point(0,1),new Point(1,1),new Point(2,1),new Point(2,0)}, // L,<
            {new Point(1,0),new Point(1,1),new Point(1,2),new Point(2,2)}, // L,^
            {new Point(0,1),new Point(1,1),new Point(2,1),new Point(0,2)}, // L,>
            {new Point(1,0),new Point(1,1),new Point(1,2),new Point(0,0)} //L,v
        },
        {
            //J
            {new Point(0,1),new Point(1,1),new Point(2,1),new Point(0,0)}, // J,>
            {new Point(1,0),new Point(1,1),new Point(1,2),new Point(0,2)}, // J,^
            {new Point(0,0),new Point(1,0),new Point(2,0),new Point(2,1)}, // J,<
            {new Point(1,0),new Point(1,1),new Point(1,2),new Point(2,0)} // J,v
        },
        {
            //O
            {new Point(0,0),new Point(0,1),new Point(01,0),new Point(1,1)}, // O,>
            {new Point(0,0),new Point(0,1),new Point(01,0),new Point(1,1)}, // O,^
            {new Point(0,0),new Point(0,1),new Point(01,0),new Point(1,1)}, // O,<
            {new Point(0,0),new Point(0,1),new Point(01,0),new Point(1,1)} // O,v
        },
        {
            //S
            {new Point(0,1),new Point(1,1),new Point(1,0),new Point(2,0)},// S,^
            {new Point(0,0),new Point(0,1),new Point(1,1),new Point(1,2)},// S,<
            {new Point(0,1),new Point(1,1),new Point(1,0),new Point(2,0)},//S,v
            {new Point(0,0),new Point(0,1),new Point(1,1),new Point(1,2)}//S,>
        },
        {
            //Z
            {new Point(0,0),new Point(1,0),new Point(1,1),new Point(2,1)},//z,^
            {new Point(0,1),new Point(0,2),new Point(1,0),new Point(1,1)},//z,<
            {new Point(0,0),new Point(1,0),new Point(1,1),new Point(2,1)},//z,v
            {new Point(0,1),new Point(0,2),new Point(1,0),new Point(1,1)}//z,>
        },
        {
            //T
            {new Point(0,0),new Point(1,0),new Point(2,0),new Point(1,1)},//t,^
            {new Point(0,0),new Point(0,1),new Point(0,2),new Point(1,1)},//t,<
            {new Point(0,1),new Point(1,1),new Point(2,1),new Point(1,0)},//t,v
            {new Point(0,1),new Point(1,0),new Point(1,1),new Point(1,2)}//t,>
        }
    };
    
    private final Color [] color={Color.CYAN,Color.BLUE,Color.MAGENTA,Color.GREEN,Color.RED,Color.WHITE,Color.PINK,Color.BLACK,Color.ORANGE};

    private Point pt;
    private int currentPiece,rotation;
    private ArrayList<Integer> siguientePieza=new ArrayList<>();
    private long score;
    private Color[][] well; // es el fondo
    ArrayList<Nivel>niveles;
    
    public JuegoTetris(ArrayList<Nivel>niveles) {
        this.niveles=niveles;
    }
    public void iniciar(){
        JFrame f= new JFrame();
        f.setTitle(niveles.get(0).getId());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(12*26+10, 26*23+25); // variar el 12 y 26 de las dimenciones x y con los valores que me den en el archivo de entrada
        f.setVisible(true);
        f.setResizable(false);

        init();
        f.add(this);
        
        f.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
         
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()){
                    case KeyEvent.VK_UP:
                        rotate(-1);
                        break;
                    case KeyEvent.VK_SPACE:
                        rotate(+1);
                        break;
                    case KeyEvent.VK_RIGHT:
                        move(1);
                        break;
                    case KeyEvent.VK_LEFT:
                        move(-1);
                        break;
                    case KeyEvent.VK_DOWN:
                        drop();
                        break;                        
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }
        });
        
        new Thread(){
            public void run(){
                while(true){
                    try {
                        Thread.sleep(1000); // con esto se maneja la velocidad del nivel
                        
                    } catch (InterruptedException ex) {
                        
                    }
                    drop();
                }
            }
        }.start();
    }
    private void init(){
    well= new Color[12][24]; // el 12 y 24 son las dimensiones del juego -2
    for(int i=0;i<12;i++){
        for(int j=0;j<23;j++){
            if(i==0||i==11||j==22){
                well[i][j]=Color.PINK;
            }else{
                well[i][j]=Color.BLACK;
            }
        }
    }
    pi.add("J");
    pi.add("L");
    NuevaPieza();
}

private void NuevaPieza(){
    pt=new Point(5,1);
    rotation=0;
    if(siguientePieza.isEmpty()){
        Collections.addAll(siguientePieza, 0,1,2,3,4,5);
        Collections.shuffle(siguientePieza);
    }else{
        if(currentPiece>siguientePieza.size()){
            currentPiece=0;
        }else{
            
        }
    }
}
private void NuevaPiezav2(){
    pt=new Point(5,-1);
    rotation=0;
    if(siguientePieza.isEmpty()){
        Collections.addAll(siguientePieza, 0,1,2,3);
        Collections.shuffle(siguientePieza);
    }
}

private boolean Choque(int x, int y,int rotation){
    for(Point p: mypoint[currentPiece][rotation]){
        if(well[p.x+x][p.y+y+1]!=Color.BLACK){
            return true;
        }
    }
    return false;
}

private void rotate(int i){
    int nuevaRotacion=(rotation+i)%4;
    if(nuevaRotacion<0){
        nuevaRotacion=3;
    }
    if(!Choque(pt.x, pt.y,nuevaRotacion)){
        rotation=nuevaRotacion;
        
    }
    repaint();
}
public void move(int i){ //mover a la derecha o izquierda
    if(!Choque(pt.x, pt.y, rotation)){
        pt.x+=i;
    }
    repaint();
}
public void drop(){ //movimiento hacia abajo
    if(!Choque(pt.x, pt.y, rotation)){
        pt.y+=1;
    }else{
        fixToWell();
    }
    repaint();
}

public void fixToWell(){
    for(Point p:mypoint[currentPiece][rotation]){
        well[pt.x+p.x][pt.y+p.y]=color[currentPiece];
    }
    clearRow();
    currentPiece++;
    NuevaPieza();
}


public void deleteRow(int row){
    for(int j =row-1;j>0;j--){
        for(int i=1;i<12;i++){
            well[i][j+1]=well[i][j];
        }
    }
}

public void clearRow(){
    boolean gap;
    int numClear=0;
    for(int j=21;j>0;j--){
        gap=false;
        for(int i=1;i<11;i++){
            if(well[i][j]==Color.BLACK){
                gap=true;
                break;
            }
        }
    
        if(!gap){
            deleteRow(numClear);
            j+=1;
            numClear+=1;
        }
    }
    System.out.println(numClear);
    switch(numClear){ //son las condiciones para poner el punteo, el numClear es la cantidad de filas que limpio
        case 22:
             score+=100;
             break;
        case 20:
            score+=200;
            break;
        case 23:
            score+=300;
            break;
        case 21:
            score+=400;
            break;
    }
    
}

public void dibujarPieza(Graphics g){
    if(contadorrepro==0){
        contadorrepro++;
        Fx(0);
    }
    g.setColor(color[currentPiece]);
    for(Point p:mypoint[currentPiece][rotation]){
        g.fillRect((p.x+pt.x)*26,(p.y+pt.y)*26, 25, 25);
    }
}

public void paintComponent(Graphics g){
    g.fillRect(0, 0, 26*12, 26*23); //se multiplica 26*tamaño en x-2 26* tamaño en y-3
    for(int i=0;i<12;i++){
        for(int j=0;j<24;j++){
            g.setColor(well[i][j]);
            g.fillRect(26*i, 26*j, 25, 25);
        }
    }
    g.setColor(Color.WHITE);
    g.drawString("Puntuacion: "+score, 19*12, 25);
    dibujarPieza(g);
}

 public void Fx(int indice){
        try{
            File file = new File("").getAbsoluteFile();
            String rutt = file+"\\src\\Analisis\\Tetris.wav";
            file = new File(rutt);
            Clip sonido = AudioSystem.getClip();
            sonido.open(AudioSystem.getAudioInputStream(file));
            sonido.start();
        }catch(Exception ex){
            
        }
    }

}
