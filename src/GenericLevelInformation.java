import java.util.List;

/**
 * Created by lizah on 08/06/2017.
 */
public class GenericLevelInformation implements LevelInformation {
private  int numberOfBalls;
private int paddleSpeed;
private List<Velocity> initialBallVelocities;
private int paddleWidth;
private String levelName;
private Sprite background;
private List<Block> blocks;
private int numberOfBlocksToRemove;

    /**
     * constructor method.
     * @param initialBallVelocities - ball velocities.
     * @param paddleSpeed - int paddle's speed.
     * @param paddleWidth - int paddle's width.
     * @param levelName - string level name.
     * @param background - sprite obj.
     * @param blocks - list of clocks.
     */
    public GenericLevelInformation(List<Velocity> initialBallVelocities, int paddleSpeed,
                               int paddleWidth, String levelName, Sprite background, List<Block> blocks) {
    this.initialBallVelocities = initialBallVelocities;
    this.paddleSpeed = paddleSpeed;
    this.paddleWidth = paddleWidth;
    this.levelName = levelName;
    this.background = background;
    this.blocks = blocks;

}

    /**
     * numberOfBalls function.
     * @return number of balls.
     */
    public int numberOfBalls() {
        return this.initialBallVelocities.size();
    }
    /**
     * initialBallVelocities function.
     * @return the initial velocity of each ball.
     */
    public List<Velocity> initialBallVelocities() {
       return this.initialBallVelocities;
    }

    /**
     * paddleSpeed function.
     * @return paddle speed.
     */
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * paddleWidth function.
     * @return paddle width.
     */
    public int paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * levelName function.
     * @return the level name will be displayed at the top of the screen.
     */
    public String levelName() {
        return this.levelName;
    }

    /**
     * getBackground function.
     * @return a sprite with the background of the level.
     */
    public Sprite getBackground() {

        return this.background;
    }

    /**
     * blocks function.
     * @return the Blocks that make up this level, each block contains
    its size, color and location.
     */
    public List<Block> blocks() {
       return this.blocks;
    }

    /**
     * numberOfBlocksToRemove function.
     * @return the number of blocks that should be removed
    before the level is considered to be "cleared".
     */
    public int numberOfBlocksToRemove() {
        return this.blocks.size();
    }

}
