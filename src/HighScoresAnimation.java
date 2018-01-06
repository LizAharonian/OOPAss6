import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import java.util.List;
import java.awt.Color;

/**
 * HighScoresAnimation class.
 */
public class HighScoresAnimation  implements Animation {
    private HighScoresTable highScoresTable;
    private String endKey;
    private GUI gui;
    private KeyboardSensor keyboardSensor;
    private boolean stop;

    /**
     * constructor method.
     * @param scores - HighScoresTable obj.
     * @param endKey - key of animation end.
     * @param gui - GUI obj.
     */
    public HighScoresAnimation(HighScoresTable scores, String endKey, GUI gui) {
        this.highScoresTable = scores;
        this.endKey = endKey;
        this.gui = gui;
        this.keyboardSensor = gui.getKeyboardSensor();
        this.stop = false;
    }

    /**
     * constructor method.
     * @param scores -  HighScoresTable obj.
     * @param gui - GUI obj.
     */
    public HighScoresAnimation(HighScoresTable scores, GUI gui) {
        this.highScoresTable = scores;
        this.gui = gui;
        this.keyboardSensor = gui.getKeyboardSensor();
        this.stop = false;
    }
    /**
     * doOneFrame function.
     * @param d - DrawSurfaceObj for paint.
     *//**
     * doOneFrame function.
     * @param d - DrawSurfaceObj for paint.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.yellow.darker());
        d.drawText(10, 40, "High Scores:", 32);
        d.setColor(Color.green.darker());
        d.drawText(20, 70, "Player Name:", 30);
        d.drawText(500, 60, "Score:", 30);
        List<ScoreInfo> scoreInfoList = this.highScoresTable.getHighScores();
        int y = 100;
        for (ScoreInfo item: scoreInfoList) {
            d.drawText(20, y, item.getName(), 25);
            d.drawText(500, y, String.valueOf(item.getScore()), 25);
            y += 35;
        }
        d.setColor(Color.red);
        d.drawText(250, 500, "Press space to continue!", 30);
    }

    /**
     * shouldStop function.
     * @return true if should stop, false otherwise.
     */
    public boolean shouldStop() { return this.stop; }
}



