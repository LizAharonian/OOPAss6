import biuoop.DrawSurface;
import java.awt.Color;
/**
 * Ball class (a circle).
 * Balls have size (radius), color, and location (a Point).
 * Balls also know how to draw themselves on a DrawSurface.
 */
public class Ball implements Sprite {
    //members
    private Point point;
    private int radius;
    private Color c;
    private Velocity myVelocity;
    private Bounds bounds;
    private GameEnvironment gameEnvironment;

    /**
     * constructor method.
     * @param center - center point of the ball.
     * @param r      - radius of the ball.
     * @param color  - color of the ball.
     * @throws RuntimeException if radius is not valid.
     */
    public Ball(Point center, int r, java.awt.Color color) throws RuntimeException {
        this.point = center;
        if (r <= 0) {
            throw new RuntimeException("Radius value is not valid");
        }
        this.radius = r;
        this.c = color;
        //set default velocity
        this.setVelocity(2, 2);
    }

    /**
     * constructor method.
     * @param x     - x value of center point.
     * @param y     - y value of center point.
     * @param r     - radius of the ball.
     * @param color - color of the ball.
     * @throws RuntimeException if radius is not valid.
     */
    public Ball(int x, int y, int r, java.awt.Color color) throws RuntimeException {
        this.point = new Point(x, y);
        if (r <= 0) {
            throw new RuntimeException("Radius value is not valid");
        }
        this.radius = r;
        this.c = color;
        //set default velocity
        this.setVelocity(2, 2);
    }

    /**
     * constructor method.
     * @param x     - x value of center point.
     * @param y     - y value of center point.
     * @param r     - radius of the ball.
     * @param color - color of the ball.
     * @param gameEnvironment - game area.
     */

    public Ball(int x, int y, int r, java.awt.Color color, GameEnvironment gameEnvironment) {
        this.radius = r;
        this.point = new Point(x, y);
        this.c = color;
        //set default velocity
        this.setVelocity(2, 2);
        this.gameEnvironment = gameEnvironment;
    }

    /**
     * constructor method.
     * @param x      - x value of center point.
     * @param y      - y value of center point.
     * @param r      - radius of the ball.
     * @param color  - color of the ball.
     * @param bounds - - range of motion of the ball.
     * @throws RuntimeException if radius is not valid.
     */
    public Ball(int x, int y, int r, java.awt.Color color, Bounds bounds) throws RuntimeException {
        this.bounds = bounds;
        //check if start point is in range of motion
        if (x + r > this.bounds.getRightBound() || x - r  <  this.bounds.getLeftBound()
                ||  y - r < this.bounds.getTopBound() || y + r > this.bounds.getDownBound()) {
            this.point = new Point(this.bounds.getLeftBound() + r, this.bounds.getTopBound() + r);
        } else {

            this.point = new Point(x, y);
        }
        if (r <= 0 || r * 2 > bounds.getDownBound() - bounds.getTopBound()
                || r * 2 > bounds.getRightBound() - bounds.getLeftBound()) {
            throw new RuntimeException("Radius value is not valid");
        }
        this.radius = r;
        this.point = new Point(x, y);
        this.c = color;
        //set default velocity
        this.setVelocity(2, 2);
    }

    /**
     * getX function.
     * @return x value of center of the point.
     */
    public int getX() {
        return (int) this.point.getX();
    }

    /**
     * getY function.
     * @return y value of center of the point.
     */
    public int getY() {
        return (int) this.point.getY();
    }

    /**
     * getSize function.
     * @return radius.
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * getColor function.
     * @return color of ball.
     */
    public java.awt.Color getColor() {
        return this.c;
    }

    /**
     * addToGame function.
     * adds ball to sprite's list of game.
     * @param g - game object.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * drawOn function.
     * @param surface - draw the ball on the given DrawSurface.
     */
    public void drawOn(DrawSurface surface) {
        int x = this.getX();
        int y = this.getY();
        int r = this.getSize();
        surface.setColor(this.getColor());
        surface.fillCircle(x, y, r);
        surface.setColor(Color.BLACK);
        surface.drawCircle(x, y, r);
    }

    /**
     * timePassed function.
     * call moveOneStep function to make the ball move.
     * @param dt - the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    /**
     * setVelocity function.
     * @param v - velocity of ball.
     */
    public void setVelocity(Velocity v) {
        this.myVelocity = v;
    }

    /**
     * setVelocity function.
     * @param dx - the change in position on the `x`.
     * @param dy - the change in position on the `y`.
     */
    public void setVelocity(double dx, double dy) {
        Velocity v = new Velocity(dx, dy);
        this.myVelocity = v;
    }

    /**
     * getVelocity function.
     * @return velocity.
     */
    public Velocity getVelocity() {
        return this.myVelocity;
    }

    /**
     * moveOneStep function.
     * the func changes the ball's center.
     * @param dt - the amount of seconds passed since the last call.
     */
    public void moveOneStep(double dt) {

        double newDx = this.getVelocity().getDx() * dt;
        double newDy = this.getVelocity().getDy() * dt;
        Velocity newVelocity1 = new Velocity(newDx, newDy);
        //calculate trajectory (how the ball will move without any obstacles)
        Point nextMovePoint = newVelocity1.applyToPoint(this.point);
       // Point nextMovePoint = this.getVelocity().applyToPoint(this.point);
        Line trajectory = new Line(this.point, nextMovePoint);
        //call getClosestCollision() to get closest intersection point to trajectory
        CollisionInfo collisionInfo = this.gameEnvironment.getClosestCollision(trajectory);
        //check if collisionInfo is null
        if (collisionInfo == null) {
            this.point = trajectory.end();
        } else {
            //check if center of ball is inside collision rectangle
            if (isInsideRectangle(collisionInfo) && (collisionInfo.collisionObject() instanceof Paddle)) {
                this.point = new Point(this.point.getX(), this.point.getY() - 20);
            } else {
                Point almostCollide = this.point;
                double x = this.point.getX();
                if (this.getVelocity().getDx() > 0) {
                    x = collisionInfo.collisionPoint().getX() - 0.1 * this.getSize();
                    almostCollide = new Point(x, collisionInfo.collisionPoint().getY());
                } else if (this.getVelocity().getDx() < 0) {
                    x = collisionInfo.collisionPoint().getX() + 0.1 * this.getSize();
                    almostCollide = new Point(x, collisionInfo.collisionPoint().getY());
                }
                if (this.getVelocity().getDy() < 0) {

                    almostCollide = new Point(x, collisionInfo.collisionPoint().getY() + 0.1 * this.getSize());
                } else if (this.getVelocity().getDy() > 0) {
                    almostCollide = new Point(x, collisionInfo.collisionPoint().getY() - 0.1 * this.getSize());
                }

                this.point = almostCollide;
            }
            Velocity newVelocity = collisionInfo.collisionObject().hit(this, collisionInfo.collisionPoint(),
                    this.myVelocity);
            this.setVelocity(newVelocity);
        }
    }

    /**
     * isInsideRectangle function.
     * @param collisionInfo - CollisionInfo obj.
     * @return true if center is inside collision rectangle and false otherwise
     */
    private boolean isInsideRectangle(CollisionInfo collisionInfo) {
        Rectangle collisionRect = collisionInfo.collisionObject().getCollisionRectangle();
        return (collisionRect.getLeftLine().start().getX() < this.point.getX()
                && this.point.getX() < collisionRect.getRightLine().start().getX()
                && collisionRect.getTopLine().start().getY() < this.point.getY()
                && this.point.getY() < collisionRect.getDownLine().start().getY());

    }

    /**
     * removeFromGame function.
     * this function removes ball from game's sprits.
     * @param g - GameLevel obj.
     */

    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);

    }
}


