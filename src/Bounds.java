/**
 * Bounds class.
 */
public class Bounds {
    private int leftBound;
    private int rightBound;
    private int topBound;
    private int downBound;

    /**
     * constructor method.
     * @param leftBound - left limit of the ball movment.
     * @param rightBound - right limit of the ball movment.
     * @param topBound - top limit of the ball movment.
     * @param downBound - down limit of the ball movment.
     */
    public Bounds(int leftBound, int rightBound, int topBound, int downBound) {
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.topBound = topBound;
        this.downBound = downBound;
    }
    /**
     * getLeftBound function.
     * @return double left limit of the ball movment.
     */
    public int getLeftBound() {
        return this.leftBound;
    }
    /**
     * getRightBound function.
     * @return double right limit of the ball movment.
     */

    public int getRightBound() {
        return this.rightBound;
    }
    /**
     * getDownBound function.
     * @return double down limit of the ball movment
     */

    public int getDownBound() {
        return this.downBound;
    }
    /**
     * getTopBound function.
     * @return double top limit of the ball movment.
     */
    public int getTopBound() {
        return this.topBound;
    }
}
