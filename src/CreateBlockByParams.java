import java.awt.Image;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * CreateBlockByParams class.
 */
public class CreateBlockByParams implements BlockCreator {
    private double width;
    private double height;
    private int hitPoints;
    private Color stroke;
    private Image img;
    private Map<Integer, Color> colorMap = new HashMap<Integer, Color>();
    private Map<Integer, Image> imageMap = new HashMap<Integer, Image>();
    /**
     * constructor method.
     * @param width - block's width.
     * @param height - block's height.
     * @param hitPoints - block's hit points.
     * @param colorMap - block's colors.
     * @param imageMap - block's images.
     * @param stroke - block's border color.
     */
    public CreateBlockByParams(double width, double height, int hitPoints, Map<Integer, Color> colorMap,
                           Map<Integer, Image> imageMap, Color stroke) {
    this.width = width;
    this.height = height;
    this.hitPoints = hitPoints;
    this.stroke = stroke;
    this.colorMap = colorMap;
    this.imageMap = imageMap;
}
    /**
     * create function.
     * Create a block at the specified location.
     * @param xpos - x upperLeftPoint of rectangle.
     * @param ypos - x upperLeftPoint of rectangle.
     * @return new block.
     */
    public Block create(int xpos, int ypos) {

        Block block = new Block(new Point(xpos, ypos), this.width, this.height,
                this.stroke, this.colorMap, this.imageMap, this.hitPoints);
        return block;

    }
}
