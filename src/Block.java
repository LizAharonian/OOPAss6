import biuoop.DrawSurface;
import java.awt.Image;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Block class.
 * block has color, rectangle and hit points.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    //define members
    private Rectangle blockRectangle;
    private int hitPoints;
    private List<HitListener> hitListeners;
    private Color stroke = null;
    private Map<Integer, Color> colorMap = new HashMap<Integer, Color>();
    private Map<Integer, Image> imageMap = new HashMap<Integer, Image>();


    /**
     * constructor method.
     * @param rectangle - block's rectangle.
     * @param color     - block's color.
     * @param hitPoints - number of hit points.
     */
    public Block(Rectangle rectangle, java.awt.Color color, int hitPoints) {
        this.blockRectangle = rectangle;
        this.colorMap.put(0, color);
        this.hitPoints = hitPoints;
        //memory allocation
        hitListeners = new ArrayList<HitListener>();

    }

    /**
     * constructor method.
     * this constructor support creating changes blocks.
     * @param point - blocks' upper left point
     * @param width - block's width.
     * @param height - block's height.
     * @param stroke - block's border color.
     * @param colorMap - block's colors.
     * @param imageMap - block's images.
     * @param hitPoints - block's hit points.
     */
    public Block(Point point, double width, double height, Color stroke, Map<Integer, Color> colorMap,
                 Map<Integer, Image> imageMap, int hitPoints) {
        this.blockRectangle = new Rectangle(point, width, height);
        this.stroke = stroke;
        this.hitPoints = hitPoints;
        this.colorMap = colorMap;
        this.imageMap = imageMap;
        //memory allocation
        hitListeners = new ArrayList<HitListener>();

    }

    /**
     * getColor function.
     * @return Color object
     */
    public Color getColor() {
        return this.colorMap.get(0);
    }

    /**
     * addToGame function.
     * @param g - Game object.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
    // Collidable implemntation:

    /**
     * getCollisionRectangle function.
     * @return block's rectangle.
     */
    public Rectangle getCollisionRectangle() {
        return this.blockRectangle;
    }

    /**
     * hit function.
     * Notify the object that we collided with it at collisionPoint with
     a given velocity.
     * @param hitter - hitter ball.
     * @param collisionPoint - the triangle collision point with rectangle.
     * @param currentVelocity - current ball velocity.
     * @return the new velocity expected after the hit (based on
       the force the object inflicted on us).
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        this.hitPoints -= 1;
        Velocity newVelocity = currentVelocity;
        //calculate new velocity
        if (isOnLeft(collisionPoint) || isOnRight(collisionPoint)) {
            newVelocity = new Velocity(-(currentVelocity.getDx()), currentVelocity.getDy());
        }
        if ((isOnTop(collisionPoint) || isOnDown(collisionPoint))) {
            newVelocity = new Velocity(newVelocity.getDx(), (-currentVelocity.getDy()));
        }
        //notify listener
        this.notifyHit(hitter);
        return newVelocity;
    }


    /**
     * isOnLeft function.
     * @param collisionPoint - optional intersection point with left line of rectangle.
     * @return true if intersect, false otherwise.
     */

    private boolean isOnLeft(Point collisionPoint) {
        return (collisionPoint.getX() == this.blockRectangle.getLeftLine().start().getX());

    }
    /**
     * isOnTop function.
     * @param collisionPoint - optional intersection point with top line of rectangle.
     * @return true if intersect, false otherwise.
     */

    private boolean isOnTop(Point collisionPoint) {
        return (collisionPoint.getY() == this.blockRectangle.getUpperLeft().getY());
    }
    /**
     * isOnDown function.
     * @param collisionPoint - optional intersection point with down line of rectangle.
     * @return true if intersect, false otherwise.
     */
    private boolean isOnDown(Point collisionPoint) {
        return (collisionPoint.getY() == this.blockRectangle.getDownLine().start().getY());
    }
    /**
     * isOnRight function.
     * @param collisionPoint - optional intersection point with right line of rectangle.
     * @return true if intersect, false otherwise.
     */
    private boolean isOnRight(Point collisionPoint) {
        return (collisionPoint.getX() == this.blockRectangle.getRightLine().start().getX());
    }
    //Sprite implementation:

    /**
     * drawOn function.
     * draws block's rectangle.
     * @param surface - DrawSurface object.
     */
    public void drawOn(DrawSurface surface) {
        if (this.colorMap.size() == 1 && this.imageMap.isEmpty()) {
            surface.setColor(this.getColor());
        } else if (this.colorMap.containsKey(this.hitPoints - 1)) {
            surface.setColor(this.colorMap.get(this.hitPoints - 1));
        } else if (this.imageMap.containsKey(this.hitPoints - 1)) {
            Image currentImage = this.imageMap.get(this.hitPoints - 1);
            surface.drawImage((int) this.blockRectangle.getUpperLeft().getX(),
                    (int) this.blockRectangle.getUpperLeft().getY(), currentImage);
            return;
        }
            //draw rectangle
            surface.fillRectangle((int) blockRectangle.getUpperLeft().getX(),
                    (int) blockRectangle.getUpperLeft().getY(),
                    (int) blockRectangle.getWidth(), (int) blockRectangle.getHeight());
        if (this.stroke != null) {
            surface.setColor(this.stroke);
            surface.drawRectangle((int) blockRectangle.getUpperLeft().getX(),
                    (int) blockRectangle.getUpperLeft().getY(),
                    (int) blockRectangle.getWidth(), (int) blockRectangle.getHeight());
        }
    }

    /**
     * timePassed function.
     * currently does nothing
     * @param dt - the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {

    }

    /**
     * removeFromGame function.
     * this function removes block frome game's sprites and collidables.
     * @param game - GameLevel obj.
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * addHitListener function.
     * this function add hl as a listener to hit events.
     * @param hl - HitListener obj.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }
    /**
     * removeHitListener function.
     * this function remove hl from the list of listeners to hit events.
     * @param hl - listener to be removed.
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);

    }

    /**
     * notifyHit function.
     * this function notify all listeners about a hit event
     * @param hitter - ball obj.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * getHitPoints function.
     * @return this.hitPoints.
     */
    public int getHitPoints() {
        return this.hitPoints;
    }


}
