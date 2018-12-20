package particlemoves;

import java.awt.Color;

/*----------------------------------------------------------------------------*/
public class Particle {

    public int x;
    public int y;
    private int velocity_x;
    private int velocity_y;
    public int size;
    private int life;
    public Color color;
    public boolean next;


    /*------------------------------------------------------------------------*/
    public Particle(int x, int y, int velocity_x, int velocity_y, int size, int life, Color c) {
        this.x = x;
        this.y = y;

        this.velocity_x = velocity_x;
        this.velocity_y = velocity_y;

        this.size = size;

        this.life = life;
        this.color = c;

        this.next = true;
    }

    /*------------------------------------------------------------------------*/
    public void move() {
        x += velocity_x;
        y += velocity_y;
        life--;

        if (life <= 0) {
            this.next = false;
        }
        this.next = true;
    }
    /*------------------------------------------------------------------------*/
}