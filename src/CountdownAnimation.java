import biuoop.DrawSurface;
import java.awt.Color;


/**
 * CountdownAnimation class.
 * The CountdownAnimation will display the given gameScreen,
   for numOfSeconds seconds, and on top of them it will show
   a countdown from countFrom back to 1, where each number will
   appear on the screen for (numOfSeconds / countFrom) secods, before
   it is replaced with the next one.
 */
public class CountdownAnimation implements Animation {
    //members
    private boolean stop;
    private Counter countFrom;
    private double numOfSeconds;
    private SpriteCollection gameScreen;
    private long startTime = System.currentTimeMillis();

    /**
     * CountdownAnimation constructor.
     * @param numOfSeconds of count down animation.
     * @param countFrom - number to count from.
     * @param gameScreen - presented game screen.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = new Counter(countFrom);
        this.gameScreen = gameScreen;
        this.stop = false;
    }

    /**
     * doOneFrame function.
     * @param d - DrawSurfaceObj for paint.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        long millisecondsPerFrame = (long) ((this.numOfSeconds * 1000) / this.countFrom.getValue());
        this.gameScreen.drawAllOn(d);
        d.setColor(Color.ORANGE.darker().darker());
        d.drawText(d.getWidth() / 2, d.getHeight() / 2, String.valueOf(this.countFrom.getValue()), 32);
        //check timing
        if (System.currentTimeMillis() - this.startTime > millisecondsPerFrame) {
            this.countFrom.decrease(1);
            this.startTime = System.currentTimeMillis();
        }
        if (this.countFrom.getValue() == 0) {
            this.stop = true;
            return;
        }

    }

    /**
     * shouldStop function.
     * @return true for stop, false otherwise.
     */
    public boolean shouldStop() {
        return this.stop;

    }
}
