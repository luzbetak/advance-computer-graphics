package particlemoves;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;

public class Image {

    int height;
    public int[][][] image;
    int width;

    /*------------------------------------------------------------------------*/
    public Image(int width_p, int height_p, int r, int g, int b) {

        this.width = width_p;
        this.height = height_p;
        image = new int[3][height][width];

        for (int i = 0; i < height; ++i) {

            for (int j = 0; j < width; ++j) {
                image[0][i][j] = (byte) r;
                image[1][i][j] = (byte) g;
                image[2][i][j] = (byte) b;
            }
        }
    }

    /*-------------------------------------------------------------------------
     *  Move image into BufferedImage object then write Image into the File   */
    public void write(String filename) {
        System.out.println("Image: " + filename);
        try {
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < height; ++i) {
                for (int j = 0; j < width; ++j) {
                    int pixel = (image[0][i][j] << 16) | (image[1][i][j] << 8) | (image[2][i][j]);
                    bi.setRGB(j, i, pixel);
                }
            }
            File outputfile = new File(filename);
            ImageIO.write(bi, "png", outputfile);
        } catch (IOException e) {
            System.out.println("Image write error");
        }
    }

    /*------------------------------------------------------------------------*/
    protected void setPixel(int x, int y, Color color) {
        try {
            //System.out.println("y=" + x + " x=" + y);
            if (y > -1 && x > -1 && y < height && x < width) {
                image[0][y][x] = (byte) color.getRed();
                image[1][y][x] = (byte) color.getGreen();
                image[2][y][x] = (byte) color.getBlue();
            }
        } catch (Exception e) {
            System.err.println("Exception: y=" + y + " x=" + x);
        }
    }
    /*------------------------------------------------------------------------*/

    public void bresenhamCircle(Particle part) {
        int i = 0;
        int j = part.size;
        while (i <= j) {
            setPixel(part.x + i, part.y - j, part.color);
            setPixel(part.x + j, part.y - i, part.color);
            setPixel(part.x + i, part.y + j, part.color);
            setPixel(part.x + j, part.y + i, part.color);
            setPixel(part.x - i, part.y - j, part.color);
            setPixel(part.x - j, part.y - i, part.color);
            setPixel(part.x - i, part.y + j, part.color);
            setPixel(part.x - j, part.y + i, part.color);
            i++;
            j = (int) (Math.sqrt(part.size * part.size - i * i) + 0.5);
        }
    }
    /*------------------------------------------------------------------------*/

    void filledCircle(Particle part) {
        int diameter = part.size;
        int posX = part.x;
        int posY = part.y;

        int FULL = (1 << 2);
        int HALF = (FULL >> 1);

        int size = (diameter << 2);
        int radius = (size >> 1);
        int dY2;
        int ray2 = radius * radius;
        int posMin, posMax;
        int Y, X;
        int x = ((diameter & 1) == 1) ? radius : radius - HALF;
        int y = HALF;
        
        posX -= (diameter >> 1);
        posY -= (diameter >> 1);

        for (;; y += FULL) {
            dY2 = (radius - y) * (radius - y);

            for (;; x -= FULL) {
                if (dY2 + (radius - x) * (radius - x) <= ray2) {
                    continue;
                }

                if (x < y) {
                    Y = (y >> 2);
                    posMin = Y;
                    posMax = diameter - Y;

                    // draw inside square
                    while (Y < posMax) {
                        for (X = posMin; X < posMax; X++) {
                            setPixel(posX + X, posY + Y, part.color);
                        }
                        Y++;
                    }

                    return;
                }

                // Draw the 4 borders
                X = (x >> 2) + 1;
                Y = y >> 2;
                posMax = diameter - X;
                int mirrorY = diameter - Y - 1;

                while (X < posMax) {
                    setPixel(posX + X, posY + Y, part.color);
                    setPixel(posX + X, posY + mirrorY, part.color);
                    setPixel(posX + Y, posY + X, part.color);
                    setPixel(posX + mirrorY, posY + X, part.color);
                    X++;
                }

                break;
            }
        }
    }

    /*------------------------------------------------------------------------*/
    public void draw(Particle part) {

        for (int y = 0; y < part.size; y++) {
            for (int x = 0; x < part.size; x++) {
                setPixel(part.x + x, part.y + y, part.color);
            }
        }

    }
    /*------------------------------------------------------------------------*/
}
