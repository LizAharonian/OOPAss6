import biuoop.DrawSurface;

/**
 * Sprite interface.
 */
public interface Sprite {
    /**
     * drawOn function.
     * draw the sprite to the screen.
     * @param d - DrawSurface obj.
     */
    void drawOn(DrawSurface d);

    /**
     * timePassed function.
     * notify the sprite that time has passed.
     * @param dt - the amount of seconds passed since the last call.
     */
    void timePassed(double dt);
}
