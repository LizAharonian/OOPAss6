/**
 * BlockRemover class.
 * a BlockRemover is in charge of removing blocks from the game, as well as keeping count
   of the number of blocks that remain.
 */

public class BlockRemover implements HitListener {
    //members
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     *
     * @param game - GameLevel obj.
     * @param removedBlocks - number of remining blocks of the game.
     */
    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
    }

    /**
     * hitEvent function.
     * this function removes the beingHit block from game.
     * @param beingHit - the being hit block.
     * @param hitter - the hitter ball.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(this.game);
            remainingBlocks.decrease(1);
        }
    }
}
