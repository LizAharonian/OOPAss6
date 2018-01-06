/**
 * HitNotifier interface.
 */
public interface HitNotifier {

    /**
     * addHitListener function.
     * @param hl - add hl as a listener to hit events.
     */
    void addHitListener(HitListener hl);

    /**
     * removeHitListener function.
     * @param hl - remove hl from the list of listeners to hit events.
     */
    void removeHitListener(HitListener hl);

}
