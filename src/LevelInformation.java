

import java.util.List;

/**
 * LevelInformation interface.
 */
public interface LevelInformation {
    /**
     * numberOfBalls function.
     * @return number of balls.
     */
    int numberOfBalls();
    /**
     * initialBallVelocities function.
     * @return the initial velocity of each ball.
     */
    List<Velocity> initialBallVelocities();
    /**
     * paddleSpeed function.
     * @return paddle speed.
     */
    int paddleSpeed();
    /**
     * paddleWidth function.
     * @return paddle width.
     */
    int paddleWidth();
    /**
     * levelName function.
     * @return the level name will be displayed at the top of the screen.
     */
    String levelName();
    /**
     * getBackground function.
     * @return a sprite with the background of the level.
     */
    Sprite getBackground();
    /**
     * blocks function.
     * @return the Blocks that make up this level, each block contains
       its size, color and location.
     */
    List<Block> blocks();
    /**
     * numberOfBlocksToRemove function.
     * @return the number of blocks that should be removed
       before the level is considered to be "cleared".
     */
    int numberOfBlocksToRemove();

}