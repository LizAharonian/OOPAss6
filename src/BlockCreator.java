/**
 * BlockCreator interface.
 */
public interface BlockCreator {
    /**
     * create function.
     * Create a block at the specified location.
     * @param xpos - x upperLeftPoint of rectangle.
     * @param ypos - x upperLeftPoint of rectangle.
     * @return new block.
     */
    Block create(int xpos, int ypos);
}
