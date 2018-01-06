import biuoop.DrawSurface;
import biuoop.GUI;


/**
 * AnimationRunner class.
 * The AnimationRunner takes an Animation object and runs it.
 */
public class AnimationRunner {
    //members
    private GUI gui;
    private int framesPerSecond;
    private biuoop.Sleeper sleeper;

    /**
     * AnimationRunner constructor.
     * @param gui - Gui object of the game.
     * @param framesPerSecond - requested frames per second to be presented.
     */
    AnimationRunner(GUI gui, int framesPerSecond) {
        this.gui = gui;
        this.framesPerSecond = framesPerSecond;
        sleeper = new biuoop.Sleeper();

    }

    /**
     * run function.
     * this function runs the animation loop.
     * @param animation - Animation obj for run.
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            animation.doOneFrame(d, 1.0 / this.framesPerSecond);
            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            //check timing
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}