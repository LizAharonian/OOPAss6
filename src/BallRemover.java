/**
 * BallRemover function.
 * a BallRemover is in charge of removing balls from the game, as well as keeping count
  of the number of balls that remain.
 */
public class BallRemover implements HitListener {
// members
    private GameLevel game;
    private Counter remainingBalls;

    /**
     * BallRemover function.
     * @param game - Game obj.
     * @param removedBalls - number of remaining balls of game.
     */
    public BallRemover(GameLevel game, Counter removedBalls) {
        this.game = game;
        this.remainingBalls = removedBalls;
    }

    /**
     * hitEvent function.
     * this function removes the hitter ball from game.
     * @param beingHit - the being hit block.
     * @param hitter - the hitter ball.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        remainingBalls.decrease(1);

    }
}
