import java.util.ArrayList;

/**
 * Rectangle class.
 * Rectangle has upper left point, width and height.
 */
public class Rectangle {
    //define members
    private Point upperLeft;
    private double width;
    private double height;
    private Line leftLine;
    private Line topLine;
    private Line downLine;
    private Line rightLine;

    /**
     * constructor method.
     * Create a new rectangle with location and width/height.
     * @param upperLeft - upper left point.
     * @param width - width of rectangle.
     * @param height - height of rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
       this.upperLeft = upperLeft;
       this.width = width;
       this.height = height;
       this.leftLine = new Line(upperLeft.getX(), upperLeft.getY(), upperLeft.getX(), upperLeft.getY() + height);
       this.topLine = new Line(upperLeft.getX(), upperLeft.getY(), upperLeft.getX() + width, upperLeft.getY());
       this.downLine = new Line(upperLeft.getX(), upperLeft.getY() + height,
               upperLeft.getX() + width, upperLeft.getY() + height);
       this.rightLine = new Line(upperLeft.getX() + width, upperLeft.getY(),
               upperLeft.getX() + width, upperLeft.getY() + height);
    }

    /**
     * intersectionPoints function.
     * find intersection points with the specified line.
     * @param line - the possibly intersection line.
     * @return Return a (possibly empty) List of intersection points.
     */
    public java.util.List intersectionPoints(Line line) {
        java.util.List<Point> intersectionPoints = new ArrayList<Point>();
        this.addIntersectionPoints(this.leftLine, line, intersectionPoints);
        this.addIntersectionPoints(this.rightLine, line, intersectionPoints);
        this.addIntersectionPoints(this.topLine, line, intersectionPoints);
        this.addIntersectionPoints(this.downLine, line, intersectionPoints);
        return intersectionPoints;
    }

    /**
     * addIntersectionPoints function.
     * adds intersection points to point list.
     * @param l1 - rectangle line.
     * @param l2 triangle line.
     * @param intersectionPoints - list of intersection points.
     */
    private void addIntersectionPoints(Line l1, Line l2, java.util.List intersectionPoints) {
        Point intersectionPoint = l1.intersectionWith(l2);
        if (intersectionPoint != null) {
            intersectionPoints.add(intersectionPoint);
        }
    }
    /**
     * getWidth function.
     * @return the width of the rectangle.
     */
    public double getWidth() {
        return this.width;
    }
    /**
     * getHeight function.
     * @return the height of the rectangle.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * getUpperLeft function.
     * @return the upper-left point of the rectangle
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * getLeftLine function.
     * @return the left line of the rectangle
     */
    public Line getLeftLine() {
        return this.leftLine;
    }
    /**
     * getTopLine function.
     * @return the top line of the rectangle
     */
    public Line getTopLine() {
        return this.topLine;
    }
    /**
     * getDownLine function.
     * @return the down line of the rectangle
     */
    public Line getDownLine() {
        return this.downLine;
    }
    /**
     * getRightLine function.
     * @return the right line of the rectangle
     */
    public Line getRightLine() {
        return this.rightLine;
    }

}
