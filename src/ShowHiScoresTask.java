import biuoop.GUI;
import biuoop.KeyboardSensor;

/**
 * ShowHiScoresTask class.
 */
public class ShowHiScoresTask implements Task<Void> {
    //members
    private AnimationRunner runner;
    private Animation highScoresAnimation;
    private GUI gui;
    private KeyboardSensor keyboardSensor;

    /**
     * constructor.
     * @param runner - AnimationRunner obj.
     * @param highScoresAnimation - Animation obj.
     * @param gui - GUI obj.
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation, GUI gui) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
        this.gui = gui;
        this.keyboardSensor = gui.getKeyboardSensor();
    }

    /**
     * run method.
     * @return null.
     */
    public Void run() {
        this.runner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY,
                this.highScoresAnimation));
        return null;
    }
}

