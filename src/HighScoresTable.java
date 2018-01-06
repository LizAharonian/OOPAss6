import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

/**
 * HighScoresTable class.
 */
public class HighScoresTable implements Serializable {
    //members
    private int size;
    private List<ScoreInfo> scoreInfoList = new ArrayList<ScoreInfo>();

    /**
     * HighScoresTable function.
     * @param size - the size means that the table holds up to size top scores.
     */
    public HighScoresTable(int size) {
        this.size = size;
    }

    /**
     * add function.
     * add a high-score.
     * @param score - ScoreInfo obj.
     */
    public void add(ScoreInfo score) {
        this.scoreInfoList.add(score);
        //baubles sort
        for (int i = 0; i < (this.scoreInfoList.size() - 1); i++) {
            for (int j = 0; j < this.scoreInfoList.size() - i - 1; j++) {
                if (this.scoreInfoList.get(j).getScore() < this.scoreInfoList.get(j + 1).getScore()) {
                    ScoreInfo temp = this.scoreInfoList.get(j);
                    this.scoreInfoList.set(j, this.scoreInfoList.get(j + 1));
                    this.scoreInfoList.set(j + 1, temp);
                }
            }
        }
        int listCurrentSize = this.scoreInfoList.size();
        if (listCurrentSize > this.size()) {
            this.scoreInfoList.remove(listCurrentSize - 1);
        }
    }

    /**
     * size function.
     * @return table size.
     */
    public int size() {
        return this.size;
    }

    /**
     * getHighScores function.
     * @return the current high scores.
     */
    public List<ScoreInfo> getHighScores() {

        return this.scoreInfoList;
    }

    /**
     * getRank function.
     * Rank 1 means the score will be highest on the list.
       Rank `size` means the score will be lowest.
       Rank > `size` means the score is too low and will not
       be added to the list.
     * @param score - new score.
     * @return the rank of the current score.
     */
    public int getRank(int score) {
        ScoreInfo newScore = new ScoreInfo("temp", score);
        int position = 0;
        int rank;
        int i;
        for (i = 0; i < (this.scoreInfoList.size()); i++) {
            if (this.scoreInfoList.get(i).getScore() >= newScore.getScore()) {
                position = i + 1;

            }
        }
        rank = position + 1;
        return rank;
    }

    /**
     * clear function.
     * Clears the table.
     */
    public void clear() {
        this.scoreInfoList.clear();
    }

    /**
     * load function.
     * @param filename - File obj.
     * @throws IOException - in case something went wrong.
     */
    public void load(File filename) throws IOException {
        ObjectInputStream objectInputStream = null;
        try {
            //create objectInputStream for reading
            objectInputStream = new ObjectInputStream(
                    new FileInputStream(filename));

            //read the object and convert it
            HighScoresTable highScoresTable = (HighScoresTable) objectInputStream.readObject();
            this.size = highScoresTable.size();
            this.scoreInfoList = highScoresTable.getHighScores();

        } catch (FileNotFoundException e) { // Can't find file to open
            System.err.println("Unable to find file: " + filename);

        } catch (ClassNotFoundException e) { // The class in the stream is unknown to the JVM
            System.err.println("Unable to find class for object in file: " + filename);

        } catch (IOException e) { // Some other problem
            System.err.println("Failed reading object");
            e.printStackTrace(System.err);

        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }

    }

    /**
     * save function.
     * Save table data to the specified file.
     * @param filename - name of high score file.
     * @throws IOException -in case something went wrong.
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(
                    new FileOutputStream(filename.getName()));
            HighScoresTable temp = this;
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            System.err.println("Failed saving object");
            e.printStackTrace(System.err);
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
    }

    /**
     * loadFromFile function.
     * Read a table from file and return it.
       If the file does not exist, or there is a problem with
       reading it, an empty table is returned.
     * @param filename - high score file name.
     * @return HighScoresTable obj.
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable highScoresTable = new HighScoresTable(4);
        try {
            highScoresTable.load(filename);
        } catch (Exception ex) {
            System.out.println("failed in function load from file");
        }
        return highScoresTable;

    }
}
