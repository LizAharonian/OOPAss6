/**
 * ScoreTrackingListener class.
 */
public class ScoreTrackingListener implements HitListener {
    //members
    private Counter currentScore;

    /**
     * ScoreTrackingListener constructor.
     * @param scoreCounter - initialize number for score of game.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * hitEvent function.
     * This method is called whenever the beingHit object is hit.
     * @param beingHit - the being hit obj.
     * @param hitter - the Ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        int live = beingHit.getHitPoints();
        if (live == 0) {
            this.currentScore.increase(15);
        } else {
            this.currentScore.increase(5);
        }
    }
}
