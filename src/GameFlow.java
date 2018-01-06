import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import java.io.File;
import java.util.List;



/**
 * GameFlow class.
 */
public class GameFlow {
    //member
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private Counter livesCounter;
    private Counter scoreCounter;
    private Counter blocksCounter;
    private GUI gui;
    private HighScoresTable highScoresTable;

    /**
     * GameFlow function.
     * @param ar - AnimationRunner obj.
     * @param ks - KeyboardSensor obj.
     * @param gui - GUI obj.
     * @param highScoresTable - HighScoresTable obj.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, GUI gui, HighScoresTable highScoresTable) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.blocksCounter = new Counter();
        this.scoreCounter = new Counter();
        this.livesCounter = new Counter();
        this.gui = gui;
        this.highScoresTable = highScoresTable;
    }

    /**
     * runLevels function.
     * @param levels - list of LevelInformation objects.
     */
    public void runLevels(List<LevelInformation> levels) {
        this.livesCounter.increase(7);
        for (LevelInformation levelInfo : levels) {

            GameLevel level = new GameLevel(levelInfo, this.keyboardSensor, this.animationRunner,
                    this.gui, this.livesCounter, this.scoreCounter, this.blocksCounter);
            level.initialize();
            // while level has more blocks and player has more lives
            while (blocksCounter.getValue() > 0 && livesCounter.getValue() > 0) {
                level.playOneTurn();

                if (this.blocksCounter.getValue() == 0) {
                    this.scoreCounter.increase(100);
                }
            }
            if (livesCounter.getValue() <= 0) {
                //lose screen
                this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY,
                        new EndScreen(this.keyboardSensor,
                        "Game Over. Your score is: ", this.scoreCounter)));
                presentHighScoreScreen();
                return;
            }

        }
        //winning screen
        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY,
                new EndScreen(this.keyboardSensor, "You Win! Your score is: ", this.scoreCounter)));
        presentHighScoreScreen();

    }

    /**
     * presentHighScoreScreen function.
     */
    private void presentHighScoreScreen() {
        int rank = this.highScoresTable.getRank(this.scoreCounter.getValue());
        if (rank <= this.highScoresTable.size()) {
            DialogManager dialog = gui.getDialogManager();
            String name = dialog.showQuestionDialog("Name", "What is your name?", "");
            ScoreInfo scoreInfo = new ScoreInfo(name, this.scoreCounter.getValue());
            this.highScoresTable.add(scoreInfo);
        }
        try {
            this.highScoresTable.save((new File("highscores")));
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY,
                new HighScoresAnimation(this.highScoresTable, this.gui)));

    }
}
