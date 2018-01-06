/**
 * HitListener interface.
 */
public interface HitListener {

    /**
     * hitEvent function.
     * This method is called whenever the beingHit object is hit.
     * @param beingHit - the being hit obj.
     * @param hitter - the Ball that's doing the hitting.
     */
    void hitEvent(Block beingHit, Ball hitter);
}
