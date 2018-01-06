import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * KeyPressStoppableAnimation class.
 */
public class KeyPressStoppableAnimation  implements Animation {
    //members
    private boolean isAlreadyPressed;
    private KeyboardSensor keyboardSensor;
    private Animation animation;
    private String key;
    private boolean stop;

    /**
     * constructor method.
     * @param sensor - KeyboardSensor obj.
     * @param key - end of animation key.
     * @param animation - Animation obj.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.isAlreadyPressed = true;
        this.keyboardSensor = sensor;
         this.animation = animation;
         this.key = key;
    }
    /**
     * doOneFrame function.
     * @param d - DrawSurfaceObj for paint.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        animation.doOneFrame(d, dt);
        if (this.keyboardSensor.isPressed(key) && this.isAlreadyPressed) {
            String a = "d";
        } else if (!this.keyboardSensor.isPressed(key)) {
            this.isAlreadyPressed = false;
        }
    }
    /**
     * shouldStop function.
     * @return true for stop, false otherwise.
     */
    public boolean shouldStop() {
        if (this.keyboardSensor.isPressed(key) && !this.isAlreadyPressed) {
            this.stop = true;
        }
        return this.stop;
    }
}
