import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

/**
 * EndScreen class.
 */
public class EndScreen implements Animation {
    //members
    private KeyboardSensor keyboard;
    private boolean stop;
    private String text;
    private Counter scoreCounter;

    /**
     * EndScreen function.
     * @param k - keyboard sensor.
     * @param text - text for draw.
     * @param scoreCounter - score counter.
     */
    public EndScreen(KeyboardSensor k, String text, Counter scoreCounter) {
        this.keyboard = k;
        this.stop = false;
        this.text = text;
        this.scoreCounter = scoreCounter;
    }

    /**
     * doOneFrame function.
     * @param d - DrawSurfaceObj for paint.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.drawText(10, d.getHeight() / 2, text + this.scoreCounter.getValue(), 32);
       // if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) { this.stop = true; }
        if (this.text.contains("Win")) {
            d.setColor(Color.yellow);
            d.fillRectangle(300, 400, 220, 50);
            int x = 300;
            int y = 350;
            int width = 20;
            int height = 50;
            for (int i = 0; i < 6; i++, x += 40) {
                d.fillRectangle(x, y, width, height);

            }
        } else {
            d.setColor(Color.RED);
            d.fillCircle(400, 450, 100);
            d.setColor(Color.WHITE);
            d.fillRectangle(370, 425, 70, 50);
        }
    }

    /**
     * shouldStop function.
     * @return true if should stop, false otherwise.
     */
    public boolean shouldStop() { return this.stop; }
}

