import biuoop.DrawSurface;

import java.awt.Color;

/**
 * ScoreIndicator class.
 */
public class ScoreIndicator implements Sprite {
    //members
    private Rectangle scoreRectangle;
    private Block block;
    private Color fontColor;
    private Color rectangleColor;
    private java.util.List<HitListener> hitListeners;
    private Counter scoreCounter;
    private String levalName;

    /**
     * ScoreIndicator constructor.
     * @param block - score's block.
     * @param fontColor - text for print color.
     * @param rectangleColor - rect color.
     * @param scoreCounter - number of score obj.
     * @param levalName - string level name.
     */
    ScoreIndicator(Block block, Color fontColor, Color rectangleColor, Counter scoreCounter, String levalName) {
        this.scoreRectangle = block.getCollisionRectangle();
        this.fontColor = fontColor;
        this.rectangleColor = rectangleColor;
        this.block = block;
        this.scoreCounter = scoreCounter;
        this.levalName = levalName;
    }

    /**
     * drawOn function.
     * draw the sprite to the screen.
     * @param surface - DrawSurface obj.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.rectangleColor);
        //draw rectangle
        surface.fillRectangle((int) scoreRectangle.getUpperLeft().getX(), (int) scoreRectangle.getUpperLeft().getY(),
                (int) scoreRectangle.getWidth(), (int) scoreRectangle.getHeight());
        surface.setColor(this.rectangleColor);
        surface.drawRectangle((int) scoreRectangle.getUpperLeft().getX(), (int) scoreRectangle.getUpperLeft().getY(),
                (int) scoreRectangle.getWidth(), (int) scoreRectangle.getHeight());
        surface.setColor(Color.black);
        drawTextOfScore(surface, "Score: " + this.scoreCounter.getValue());
        drawTextOfLevel(surface, "Level Name: " + this.levalName);
    }

    /**
     * drawText function.
     * draws hit points number on block's body.
     * @param surface - DrawSurface object.
     * @param val     - string value for print.
     */
    private void drawTextOfScore(DrawSurface surface, String val) {
        surface.drawText((int) (this.scoreRectangle.getUpperLeft().getX() + (this.scoreRectangle.getWidth() / 2)),
                (int) (this.scoreRectangle.getUpperLeft().getY() + (this.scoreRectangle.getHeight() / 2 + 5)), val, 15);
    }
    /**
     * drawTextOfLevel function.
     * draws level's name on block's body.
     * @param surface - DrawSurface object.
     * @param val     - string value for print.
     */
    private void drawTextOfLevel(DrawSurface surface, String val) {
        surface.drawText((int) ((this.scoreRectangle.getUpperLeft().getX() + (this.scoreRectangle.getWidth() / 9)) * 6),
                (int) (this.scoreRectangle.getUpperLeft().getY() + (this.scoreRectangle.getHeight() / 2 + 5)), val, 15);
    }

    /**
     * timePassed function.
     * notify the sprite that time has passed.
     * @param dt - the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {

    }
}
