import biuoop.DrawSurface;

import java.awt.Color;

/**
 * LivesIndicator class.
 */
public class LivesIndicator implements Sprite {

    //members
    private Rectangle scoreRectangle;
    private Block block;
    private Color fontColor;
    private Color rectangleColor;
    private java.util.List<HitListener> hitListeners;
    private Counter livesCounter;

    /**
     * LivesIndicator constructor.
     * @param block - score's block.
     * @param fontColor - font color.
     * @param livesCounter - Count - lives counter.
     */
    LivesIndicator(Block block, Color fontColor, Counter livesCounter) {
        this.scoreRectangle = block.getCollisionRectangle();
        this.fontColor = fontColor;
        this.rectangleColor = rectangleColor;
        this.block = block;
        this.livesCounter = livesCounter;
    }

    /**
     * drawOn function.
     * @param surface - DrowSurface ibj.
     */
    public void drawOn(DrawSurface surface) {
        drawText(surface, "Lives: " + this.livesCounter.getValue());
    }

    /**
     * drawText function.
     * draws hit points number on block's body.
     * @param surface - DrawSurface object.
     * @param val     - string value for print.
     */
    private void drawText(DrawSurface surface, String val) {
        surface.drawText((int) (this.scoreRectangle.getUpperLeft().getX() + (this.scoreRectangle.getWidth() / 10)),
                (int) (this.scoreRectangle.getUpperLeft().getY() + (this.scoreRectangle.getHeight() / 2 + 5)), val, 15);
    }

    /**
     * timePassed function.
     * notify the sprite that time has passed.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {

    }
}


