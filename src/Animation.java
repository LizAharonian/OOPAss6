import biuoop.DrawSurface;

/**
 * Animation interface.
 */
public interface Animation {
    /**
     * doOneFrame function.
     * @param d - DrawSurfaceObj for paint.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * shouldStop function.
     * @return true for stop, false otherwise.
     */
    boolean shouldStop();
}
