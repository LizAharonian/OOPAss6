import java.util.List;

/**
 * Line class.
 * A line (actually a line-segment) connects two points - a start point and an end point.
 * Lines have lengths, and may intersect with other lines.
 */
public class Line {
    //members
    private Point startPoint;
    private Point endPoint;
    private double x1Difference;
    private double y1Difference;

    /**
     * constructor method.
     * the func initializes start and end points.
     * @param start point of line.
     * @param end   point of line.
     */
    public Line(Point start, Point end) {
        this.startPoint = start;
        this.endPoint = end;
        this.x1Difference = this.startPoint.getX() - this.endPoint.getX();
        this.y1Difference = this.startPoint.getY() - this.endPoint.getY();
    }

    /**
     * constructor method.
     * the func initializes start and end points.
     * @param x1 value of start point.
     * @param y1 value of start point.
     * @param x2 value of end point.
     * @param y2 value of end point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        startPoint = new Point(x1, y1);
        endPoint = new Point(x2, y2);
        this.x1Difference = this.startPoint.getX() - this.endPoint.getX();
        this.y1Difference = this.startPoint.getY() - this.endPoint.getY();
    }

    /**
     * length method.
     * the func returns line length.
     * @return double length of this line.
     */
    public double length() {
        return startPoint.distance(endPoint);
    }

    /**
     * middle method.
     * the func returns the middle point of the line.
     * @return middle point of the line.
     */
    public Point middle() {
        double xMiddle = (this.startPoint.getX() + this.endPoint.getX()) / 2;
        double yMiddle = (this.startPoint.getY() + this.endPoint.getY()) / 2;
        Point middle = new Point(xMiddle, yMiddle);
        return middle;
    }

    /**
     * start method.
     * @return the start point of the line.
     */
    public Point start() {
        return this.startPoint;
    }

    /**
     * end method.
     * @return the end point of the line.
     */
    public Point end() {
        return this.endPoint;
    }

    /**
     * isIntersecting method.
     * @param other - another line.
     * @return true if the lines intersect, false otherwise.
     */

    public boolean isIntersecting(Line other) {

        //check if m is infinit
        if (this.x1Difference == 0 && other.x1Difference != 0 || this.x1Difference != 0 && other.x1Difference == 0) {
            return (intersectingHanddle(other) != null);
            // check if slant is infinit
        } else if (this.x1Difference == 0 && other.x1Difference == 0) {
            return false;
        } else {
            double m1 = this.y1Difference / this.x1Difference;
            double m2 = other.y1Difference / other.x1Difference;
            if (m1 == m2) {
                return false;
            } else {
                //check if intersection point is in range of the lines
                return  (intersectingHanddle(other) != null);
            }
        }
    }

    /**
     * findSlant method.
     * @return double slant of line.
     */
    private double findSlant() {
        return this.y1Difference / this.x1Difference;

    }

    /**
     * intersectingHanddle method.
     * @param other - another line.
     * @return intersection point if the lines intersect, null otherwise.
     */
    private Point intersectingHanddle(Line other) {
        double x, y, m1, m2;
        //func of form x=n
        if (this.x1Difference == 0 && other.x1Difference != 0) {
            x = this.startPoint.getX();
            m2 = other.findSlant();
            y = (m2 * x) + (m2 * (-other.startPoint.getX())) + other.startPoint.getY();
        } else if (this.x1Difference != 0 && other.x1Difference == 0) {
            x = other.startPoint.getX();
            m1 = this.findSlant();
            y = (m1 * x) + (m1 * (-this.startPoint.getX())) + this.startPoint.getY();
        } else {
            m1 = this.findSlant();
            m2 = other.findSlant();
            //this line
            double temp = ((-m1) * this.startPoint.getX()) + this.startPoint.getY();
            //other line
            m2 = other.findSlant();
            double temp2 = ((-m2) * other.startPoint.getX()) + other.startPoint.getY();
            //calculate x intersection
            x = (temp2 - temp) / (m1 - m2);
            //calculate y intersection
            y = (m2 * x + temp2);
            x = precisionDouble(x, 3);
            y = precisionDouble(y, 3);
        }
        Point intersectionPoint = new Point(x, y);
        //check if intersection point is range of the lines
        if (isIntersectInRange(intersectionPoint, other)) {
            return intersectionPoint;
        } else {
            return null;
        }
    }

    /**
     * precisionDouble function.
     * @param num - double num for precision.
     * @param accuracy - level of precision.
     * @return precision number.
     */
    private double precisionDouble(double num, int accuracy) {
        double per10 = Math.pow(10, accuracy);
        return Math.round(num * per10) / per10;
    }

    /**
     * isIntersectInRange method.
     * @param intersectionPoint - optional point pf lines intersection
     * @param other - another line.
     * @return true if the lines intersect, false otherwise.
     */
    private boolean isIntersectInRange(Point intersectionPoint, Line other) {
        //this min/max vals
        double minX1 = Math.min(this.startPoint.getX(), this.endPoint.getX());
        double minY1 = Math.min(this.startPoint.getY(), this.endPoint.getY());
        double maxX1 = Math.max(this.startPoint.getX(), this.endPoint.getX());
        double maxY1 = Math.max(this.startPoint.getY(), this.endPoint.getY());
        // other min/max vals
        double minX2 = Math.min(other.startPoint.getX(), other.endPoint.getX());
        double minY2 = Math.min(other.startPoint.getY(), other.endPoint.getY());
        double maxX2 = Math.max(other.startPoint.getX(), other.endPoint.getX());
        double maxY2 = Math.max(other.startPoint.getY(), other.endPoint.getY());
        //check if intersection point is range of the lines
        return  (minX1 <= intersectionPoint.getX() && maxX1 >= intersectionPoint.getX()
                && minY1 <= intersectionPoint.getY() && maxY1 >= intersectionPoint.getY()
                && minX2 <= intersectionPoint.getX() && maxX2 >= intersectionPoint.getX()
                && minY2 <= intersectionPoint.getY() && maxY2 >= intersectionPoint.getY());
    }

    /**
     * intersectionWith method.
     * @param other - another line.
     * @return the intersection point if the lines intersect,
     * and null otherwise.
     */
    public Point intersectionWith(Line other) {
        if (isIntersecting(other)) {
            Point intersectionPoint = intersectingHanddle(other);
            return intersectionPoint;
        } else {
            return null;
        }
    }

    /**
     * equals method.
     * @param other - another line.
     * @return true if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        return (this.startPoint.equals(other.startPoint) && this.endPoint.equals(other.endPoint));

    }

    /**
     * closestIntersectionToStartOfLine function.
     * finds the closest point to start point of line.
     * @param rect - intersection rectangle.
     * @return - If this line does not intersect with the rectangle, return null.
       Otherwise, return the closest intersection point to the
       start of the line.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intersectionPoints = rect.intersectionPoints(this);
        if (intersectionPoints.size() == 0) {
            return null;
        } else {
            //calculate distance of first intersection point and line start point
            double distance = intersectionPoints.get(0).distance(this.startPoint);
            Point closestPoint = intersectionPoints.get(0);
            for (Point intersectionPoint : intersectionPoints) {
                double temp = intersectionPoint.distance(this.startPoint);
                if (temp < distance) {
                    distance = temp;
                    closestPoint = intersectionPoint;
                }
            }
            return closestPoint;
        }
    }
}