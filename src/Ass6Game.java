import biuoop.GUI;

import java.io.Reader;
import java.io.InputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;


/**
 * Ass6Game class.
 */
public class Ass6Game {
    /**
     * main function.
     * this function runs the game.
     *
     * @param args - string arr of requested levels.
     */
    public static void main(String[] args) {

        //create the game gui
        GUI gui = new GUI("Game", 800, 600);
        AnimationRunner animationRunner = new AnimationRunner(gui, 60);
        //load or create highScoreTable
        HighScoresTable highScoresTable = HighScoresTable.loadFromFile((new File("highscores")));
        if (highScoresTable.getHighScores().isEmpty()) {
            //create default score table
            highScoresTable = new HighScoresTable(4);
        }
        HighScoresAnimation highScoresAnimation = new HighScoresAnimation(highScoresTable, gui);
        //sub menu
        Menu<Task<Void>> subMenu = new SubMenu<Task<Void>>(gui);
        String sourceLevelSet;
        if (args.length != 0) {
            sourceLevelSet = args[0];
        } else {
            sourceLevelSet = "default_level_sets.txt";
        }
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(sourceLevelSet);
            Reader r = new InputStreamReader(is);
            LineNumberReader lineNumberReader = new LineNumberReader(r);
            String line;
            String url;
            String[] arr = null;
            while ((line = lineNumberReader.readLine()) != null) {
                line = line.trim();
                if (line == "") {
                    continue;
                }
                int lineNumber = lineNumberReader.getLineNumber();
                if (lineNumber % 2 != 0) {
                    arr = line.split(":");
                } else {
                    url = line;
                    StartGameTask startGameTask = new StartGameTask(animationRunner, gui, highScoresTable, url);
                    subMenu.addSelection(arr[0], arr[1], startGameTask);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
            System.exit(1);

        }
        //create menue
        Menu<Task<Void>> menuAnimation = new MenuAnimation<Task<Void>>(gui, animationRunner);
        menuAnimation.addSelection("h", "High Scores",
                new ShowHiScoresTask(animationRunner, highScoresAnimation, gui));
        menuAnimation.addSelection("q", "Exit",
                new ExitTask(animationRunner));
        menuAnimation.addSelection("s", "Start Game",
                new StartGameTask(animationRunner, gui, highScoresTable, sourceLevelSet));
        menuAnimation.addSelection("s", "Start Game", new SubMenuTask(subMenu, animationRunner));
        //add subMenu
         menuAnimation.addSubMenu("s", "sub Menu", subMenu);

        while (true) {
            animationRunner.run(menuAnimation);
            Task<Void> task = menuAnimation.getStatus();
            if (task != null) {
                task.run();
            }
            menuAnimation.setStop(false);

        }

    }
}
