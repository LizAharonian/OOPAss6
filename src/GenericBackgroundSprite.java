import java.awt.Image;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import biuoop.DrawSurface;

/**
 * GenericBackgroundSprite class.
 */
public class GenericBackgroundSprite implements Sprite {
    //members
    private Color color;
    private Image img = null;

    /**
     * constructor method.
     * @param color - Color obj.
     */
    public GenericBackgroundSprite(Color color) {
        if (color != null) {
            this.color = color;
        } else {
            System.out.println("background color not valid");
            System.exit(1);
        }
    }

    /**
     * constructor method.
     * @param imageName - string image name.
     */
    public GenericBackgroundSprite(String imageName) {
        try {

            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(imageName);
            this.img = ImageIO.read(is);
        } catch (IOException ex) {
            System.out.println("reading image failed");
            System.exit(1);
        }
    }

    /**
     * drawOn function.
     * draw the sprite to the screen.
     * @param d - DrawSurface obj.
     */
    public void drawOn(DrawSurface d) {
        // Draw the image on a DrawSurface
        if (img != null) {
            d.drawImage(0, 0, img); // draw the image at location 10, 20.
        } else if (color != null) {
            d.setColor(this.color);
            //draw rectangle
            d.fillRectangle(0, 0, 800, 600);
        }

    }

    /**
     * timePassed function.
     * notify the sprite that time has passed.
     * @param dt - the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {

    }

}
