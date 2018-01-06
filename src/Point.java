/**
 * Point class.
 * A point has an x and a y value, and can measure the distance to other points, and if its is equal to another point.
 */
public class Point {
    private double x;
    private double y;
    /**
     * The constructor method.
     * the func initializes x and y.
     * @param x value of the point center.
     * @param y value of the point center.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    /**
     * distance method.
     * the func return the distance of this point to the other point.
     * @param other used as the second point for distance calculation.
     * @return distance.
     */
    public double distance(Point other) {
        return Math.sqrt((this.x - other.getX()) * (this.x - other.getX())
                + (this.y - other.getY()) * (this.y - other.getY()));
    }
    /**
     * equals method.
     * the func compare two points.
     * @param other used as the second point for compare.
     * @return true if the points are equal, false otherwise.
     */
    public boolean equals(Point other) {
        return (this.x == other.getX() && this.y == other.getY());
    }
    /**
     * getX method.
     * the func return the x value of this point.
     * @return the x value of this point.
     */
    public double getX() {
        return this.x;
    }
    /**
     * getY method.
     * the func return the y value of this point.
     * @return the y value of this point.
     */
    public double getY() {
        return this.y;
    }
}