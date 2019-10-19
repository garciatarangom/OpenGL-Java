package oglib;

import java.io.IOException;
import java.lang.Math;

import oglib.components.CompileException;
import oglib.components.CreateException;
import oglib.components.Program;
import oglib.game.GameState;
import oglib.gui.Simple2DBuffer;
import oglib.gui.WindowGL;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.io.IOException;
import java.nio.*;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;


public class App {
    public static void main(String[] args) {
        var gameState = GameState.getGameState();
        var width = 300;//ancho de la ventana
        var height = 300;//alto de la ventana
        var w = new WindowGL(width, height, "Drawing Program", gameState);

        try {
            var program = new Program("screen.vert", "screen.frag");
            var screen = new Simple2DBuffer(width, height);
            /*for (int i = 0; i < width; i++) {
                pixel(screen, i, i);
            }*/ // end for

            drawLine(screen, 10, 100, 11, 200);
           

            while (!w.windowShouldClose()) {
                glClearColor(0f, 0f, 0f, 1.0f);//Borro lo negro
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                program.use();
                screen.draw();
                w.swapBuffers();
                w.pollEvents();
            } //end while
            w.destroy();
        }// end try  
        catch (IOException | CreateException | CompileException e) {
            e.printStackTrace();
        }// end catch

    } // end main
    /* La función drawLine:
    1.- Recibe como parámetros screen (dibuja el pixel), y 3 enteros (x1,y1,x2,y2) que son
    las coordenadas de los puntos de la recta.
    2.- Las variables dx y dy son la distancia entre cada punto(puntos intermedios). 
    3.- "Steps" es una variable que tiene un operador condicional ternario, es decir, si el valor absoluto
    de dx es mayor que el valor abosulto de dy, entonces dx es asigando a steps, en caso contrario 
    o falso (dx sea menor o igual a dy) entonces dy se asigna a steps.
    4.- Las variables Xinc y Yinc son iguales a dividir la distancia entre steps
    5.- Los valores de x y y son asignados a las varaibles x1 y y y1. Es decir las vuelve flotantes.
    6.- Se realiza un for que sirve para dibujar cada pixel que se encuentra en la recta. 
    Con screen.set dibuja cada pixel en el lugar correcto, Math.round es una función que ayuda a redondear 
    los valores de x y y (flotantes), si no se hiciera lo anterior la línea no se dibujaría graficamente 
    correcta. Y see le asigna el color a la línea.
    7.- Finalmente, también se redondean los valores de incremento de x y y.
        */ 
    public static void drawLine(Simple2DBuffer screen, int x1, int y1, int x2, int y2) {

        int dx = x2 - x1;
        int dy = y2 - y1;
        
        int steps = Math.abs(dx) > Math.abs(dy) ? Math.abs(dx) : Math.abs(dy);

        float Xinc = dx / (float) steps;
        float Yinc = dy / (float) steps; 
        
        float x = x1;
        float y = y1; 
       
        for (int i = 0; i <= steps ; i++) { 
            screen.set(Math.round(x), Math.round(y), 200, 100, 200); 
           
            Math.round(x+= Xinc);
            Math.round(y+= Yinc);
        
        } //end for
    } // end drawLine
    
    public static void drawCircle(Simple2DBuffer screen, int x, int y, int r) {
    
    }
} // end App
