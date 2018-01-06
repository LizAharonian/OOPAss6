import biuoop.DrawSurface;
import java.awt.Color;

/**
 * Paddle class.
 * The Paddle is the player in the game.
 * It is a rectangle that is controlled by the arrow keys, and moves according to the player key presses
 */
public class Paddle implements Sprite, Collidable {
    //members
    private biuoop.KeyboardSensor keyboard;
    private Rectangle paddleRectangle;
    private Color color;
    private double dx;

    /**
     * constructor method.
     * @param rectangle - paddle's Rectangle obj.
     * @param color     - paddle's color.
     * @param keyboard  - KeyboardSensor obj.
     * @param dx        - the change in position on the `x`.
     */
    public Paddle(Rectangle rectangle, java.awt.Color color, biuoop.KeyboardSensor keyboard, double dx) {
        this.paddleRectangle = rectangle;
        this.color = color;
        this.keyboard = keyboard;
        this.dx = dx;
    }

    /**
     * moveLeft function.
     * moves paddle position.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    public void moveLeft(double dt) {
        double x = this.paddleRectangle.getUpperLeft().getX() - this.dx * dt;
        if (x >= 20) {
            setNewUpperLeftOfPaddle(x);
        }
    }

    /**
     * moveRight function.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    public void moveRight(double dt) {
       // this.dx = this.dx * dt;
        double x = this.paddleRectangle.getUpperLeft().getX() + this.dx * dt;
        if (x + getCollisionRectangle().getWidth() <= 780) {
            setNewUpperLeftOfPaddle(x);
        }

    }

    /**
     * setNewUpperLeftOfPaddle function.
     * sets new upper left point of paddle in order to make the paddle move.
     * @param x - new x value of upper left point of rectangle.
     */
    private void setNewUpperLeftOfPaddle(double x) {
        //don't change y position
        double y = this.paddleRectangle.getUpperLeft().getY();
        Point newUpperLeft = new Point(x, y);
        this.paddleRectangle = new Rectangle(newUpperLeft, this.paddleRectangle.getWidth(),
                this.paddleRectangle.getHeight());
    }

    /**
     * getColor function.
     * @return paddle's color.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * addToGame function.
     * Add this paddle to the game.
     * @param g -Game object
     */

    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);

    }

    // Sprite implementation

    /**
     * timePassed function.
     * responsible of paddle's movment.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
        if (keyboard.isPressed(this.keyboard.LEFT_KEY)) {

            this.moveLeft(dt);
        } else if (keyboard.isPressed(this.keyboard.RIGHT_KEY)) {

            this.moveRight(dt);
        }
    }

    /**
     * drawOn function.
     * @param surface - DrawSurface obj.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.getColor());
        surface.fillRectangle((int) this.paddleRectangle.getUpperLeft().getX(),
                (int) this.paddleRectangle.getUpperLeft().getY(),
                (int) this.paddleRectangle.getWidth(), (int) this.paddleRectangle.getHeight());
    }

    // Collidable implementation

    /**
     * getCollisionRectangle function.
     * @return Rectangle obj.
     */
    public Rectangle getCollisionRectangle() {
        return this.paddleRectangle;

    }

    /**
     * hit function.
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * @param hitter - hitter bal.
     * @param collisionPoint - the triangle collision point with rectangle.
     * @param currentVelocity - current ball velocity.
     * @return the new velocity expected after the hit (based on
       the force the object inflicted on us).
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {

        Velocity newVelocity = currentVelocity;
        //change ball's angele according to hitted region
        if (isOnTop(collisionPoint)) {
            double width = this.paddleRectangle.getWidth();
            double widthPerPart = width / 5;
            double x = this.paddleRectangle.getUpperLeft().getX();
            double currentSpeed = currentVelocity.getSpeed();
            //region 1
            if (x <= collisionPoint.getX() && collisionPoint.getX() < x + widthPerPart) {
                return Velocity.fromAngleAndSpeed(300, currentSpeed);
            } else if (x + widthPerPart <= collisionPoint.getX() && collisionPoint.getX() < x + (2 * widthPerPart)) {
                //region 2
                return Velocity.fromAngleAndSpeed(330, currentSpeed);
            } else if (x + (2 * widthPerPart) <= collisionPoint.getX()
                    && collisionPoint.getX() < x + (3 * widthPerPart)) {
                //region 3
                return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
            } else if (x + (3 * widthPerPart) <= collisionPoint.getX()
                    && collisionPoint.getX() < x + (4 * widthPerPart)) {
                //region 4
                return Velocity.fromAngleAndSpeed(30, currentSpeed);
            } else {
                //region 5
                return Velocity.fromAngleAndSpeed(60, currentSpeed);
            }

        } else if (isOnLeft(collisionPoint) || isOnRight(collisionPoint)) {
            newVelocity =  new Velocity(-(currentVelocity.getDx()), currentVelocity.getDy());
        }
        if (isOnDown(collisionPoint)) {
            newVelocity =  new Velocity(newVelocity.getDx(), (-currentVelocity.getDy()));

        }
        return newVelocity;
    }
    /**
     * isOnLeft function.
     * @param collisionPoint - optional intersection point with left line of rectangle.
     * @return true if intersect, false otherwise.
     */

    private boolean isOnLeft(Point collisionPoint) {
        return (collisionPoint.getX() == this.paddleRectangle.getLeftLine().start().getX());

    }
    /**
     * isOnTop function.
     * @param collisionPoint - optional intersection point with top line of rectangle.
     * @return true if intersect, false otherwise.
     */

    private boolean isOnTop(Point collisionPoint) {
        return (collisionPoint.getY() == this.paddleRectangle.getUpperLeft().getY());
    }
    /**
     * isOnDown function.
     * @param collisionPoint - optional intersection point with down line of rectangle.
     * @return true if intersect, false otherwise.
     */
    private boolean isOnDown(Point collisionPoint) {
        return (collisionPoint.getY() == this.paddleRectangle.getDownLine().start().getY());
    }
    /**
     * isOnRight function.
     * @param collisionPoint - optional intersection point with right line of rectangle.
     * @return true if intersect, false otherwise.
     */
    private boolean isOnRight(Point collisionPoint) {
        return (collisionPoint.getX() == this.paddleRectangle.getRightLine().start().getX());
    }
}



