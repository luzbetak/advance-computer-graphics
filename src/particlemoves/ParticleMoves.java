/*------------------------------------------------------------------------------
 * Advance Computer Graphics
 * Two particle moving across frame with different velocity 
 * and different sizes.
 * Written by: Kevin Luzbetak
 -----------------------------------------------------------------------------*/
package particlemoves;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;

/*----------------------------------------------------------------------------*/
public class ParticleMoves {

    public static Graphics image5;
    public static int TotalFrames = 400;

    /*------------------------------------------------------------------------*/
    public static void main(String[] args) throws IOException {

        Particle part0 = new Particle(
                10, 10, // position x, y
                3, 3,   // velocity x, y
                50,     // size
                750,    // lifespan
                Color.ORANGE);
        
         Particle part1 = new Particle(
                10, 20, // position x, y
                5, 6,   // velocity x, y
                30,     // size
                750,    // lifespan
                Color.RED);       

        ArrayList<Image> images = new ArrayList<Image>(100);
        Image image;

        /*---------------------------------------------------*/
        int i = 0;
        boolean move=true;
        
        for(i=0; i<TotalFrames; i++) {
            //img.bresenhamCircle(particle,255,255,255);
            image = new Image(600, 600, 0, 0, 0);
            
            if(part0.next) { 
                image.filledCircle(part0); 
                part0.move();
            } 
            if(part1.next) { 
                image.filledCircle(part1); 
                part1.move();
            } 
            
            images.add(image);
            i++;
        } 
        
        /*---------------------------------------------------*/
        i = 0;
        for (Image img : images) {

            String filename = String.format("/Users/ktd/Desktop/img/particle%03d.png", (int) i);
            img.write(filename);
            i++;
        }
    }
    /*------------------------------------------------------------------------*/
}
