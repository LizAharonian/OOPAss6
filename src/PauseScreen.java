import biuoop.DrawSurface;
import biuoop.KeyboardSensor;


/**
 * PauseScreen obj.
 */
public class PauseScreen implements Animation {
    //members
    private KeyboardSensor keyboard;
    private boolean stop;
    private KeyPressStoppableAnimation keyPressStoppableAnimation;

    /**
     * PauseScreen constructor.
     * @param k - KeyBoard sensor obj.
     */
    public PauseScreen(KeyboardSensor k) {
        this.keyboard = k;
        this.stop = false;
    }

    /**
     * doOneFrame function.
     * @param d - DrawSurfaceObj for paint.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);
        //if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) { this.stop = true; }
    }
    /**
     * shouldStop function.
     * @return true for stop, false otherwise.
     */
    public boolean shouldStop() { return this.stop; }
}
