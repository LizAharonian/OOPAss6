import biuoop.GUI;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.Reader;
import java.io.InputStreamReader;

/**
 * StartGameTask class.
 */
public class StartGameTask implements Task<Void> {
    //members
    private AnimationRunner runner;
    private GUI gui;
    private HighScoresTable highScoresTable;
    private String url;

    /**
     * StartGameTask function.
     *
     * @param runner          - AnimationRunner function.
     * @param gui             - GUI obj.
     * @param highScoresTable - HighScoresTable obj.
     * @param url             - url of levels def.
     */
    public StartGameTask(AnimationRunner runner, GUI gui, HighScoresTable highScoresTable, String url) {
        this.runner = runner;
        this.gui = gui;
        this.highScoresTable = highScoresTable;
        this.url = url;

    }

    /**
     * run function.
     * @return null.
     */
    public Void run() {

        List<LevelInformation> levelInformations = new ArrayList<LevelInformation>();
        LevelSpecificationReader j = new LevelSpecificationReader(this.gui);
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(url);
            Reader reader = new InputStreamReader(is);
            levelInformations = j.fromReader(reader);
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("filed load file in start game task");
            System.exit(1);
        }
        GameFlow gameFlow = new GameFlow(runner, gui.getKeyboardSensor(), gui, highScoresTable);
        //run the requested levels
        gameFlow.runLevels(levelInformations);
        return null;
    }
}
