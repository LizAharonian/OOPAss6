/**
 * Velocity class.
 * Velocity specifies the change in position on the `x` and the `y` axes.
 */
public class Velocity {
    //members
    private double dx;
    private double dy;

    /**
     * Velocity method.
     * the func define velocity by angle and speed.
     * @param angle - the angle of the change in position.
     * @param speed - the length of change in position.
     * @return new velocity.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        //check the angle
        while (angle > 360) {
            angle = angle - 360;
        }
        angle = Math.toRadians(angle);
        double dx = Math.sin(angle) * speed;
        double dy = Math.cos(angle) * speed;
        return new Velocity(dx, -dy);
    }
    /**
     * constructor method.
     * the func initializes dx and dy.
     * @param dx - the change in position on the `x`.
     * @param dy - the change in position on the `y`.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * getSpeed function.
     * @return speed of element movment.
     */
    public double getSpeed() {
        //pitagoras
        return Math.sqrt((this.dx * this.dx) + (this.dy * this.dy));
    }

    /**
     * getAngle function.
     * @return angle of element movment.
     */
    public double getAngle() {
        //smaller then 0 add 360
        return Math.toDegrees(Math.atan2(this.dy, this.dx));

    }

    /**
     * getDx method.
     * @return double dx - the change in position on the `x` axe.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * getDy method.
     * @return double dy -  the change in position on the `y` axe.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * applyToPoint method.
     * Take a point with position (x,y) and return a new point
       with position (x+dx, y+dy)
     * @param p - original point.
     * @return new point with position (x+dx, y+dy).
     */

    public Point applyToPoint(Point p) {
        Point newPoint = new Point(dx + p.getX(), dy + p.getY());
        return newPoint;
    }
}