import java.io.Serializable;

/**
 * ScoreInfo class.
 */
public class ScoreInfo implements Serializable {
    //members
    private String name;
    private int score;

    /**
     * constructor method.
     * @param name - players name.
     * @param score - score details.
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * getName function.
     * @return string name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * getScore function.
     * @return score.
     */
    public int getScore() {
        return this.score;
    }
}
