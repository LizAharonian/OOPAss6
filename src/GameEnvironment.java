import java.util.ArrayList;
import java.util.List;

/**
 * GameEnvironment class.
 * manges game objects.
 */
public class GameEnvironment {
    private List<Collidable> collidables;

    /**
     * constructor method.
     */
    GameEnvironment() {
        collidables = new ArrayList<Collidable>();
    }

    /**
     * addCollidable function.
     * add the given collidable to the environment.
     * @param c - given collidable.
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * removeCollidable function.
     * remove a given collidable from the environment.
     * @param c - given collidable.
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * getClosestCollision function.
     * @param trajectory - ball's way.
     * @return the information about the closest collision that is going to occur or null.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        List<Point> collisionPoints = new ArrayList<Point>();
        List<Collidable> collidables1 = new ArrayList<Collidable>();
        for (Collidable collidable : this.collidables) {
            Point p = trajectory.closestIntersectionToStartOfLine(collidable.getCollisionRectangle());
            if (p != null) {
                collisionPoints.add(p);
                collidables1.add(collidable);
            }
        }
        return createCollisionInfo(collisionPoints, trajectory, collidables1);
    }

    /**
     * createCollisionInfo function.
     * @param collisionPoints - list of intersection points.
     * @param trajectory - ball's way.
     * @param collidables1 - - list of collidables.
     * @return the information about the closest collision that is going to occur or null.
     */
    private CollisionInfo createCollisionInfo(List<Point> collisionPoints, Line trajectory,
                                              List<Collidable> collidables1) {
        //check if there are intersection points
        if (collisionPoints.size() == 0) {
            return null;
        } else {
            //calculate distance of trajectory and first collision point
            double distance = trajectory.start().distance(collisionPoints.get(0));
            Point closestPoint = collisionPoints.get(0);
            //find minimum distance (closest intersection point)
            if (collidables1.size() != 0) {
                Collidable collidable = collidables1.get(0);
                int i = 0;
                for (Point intersectionPoint : collisionPoints) {
                    double temp = trajectory.start().distance(intersectionPoint);
                    if (temp < distance) {
                        distance = temp;
                        closestPoint = intersectionPoint;
                        collidable = collidables1.get(i);
                    }
                    i++;
                }
                return new CollisionInfo(closestPoint, collidable);
            } else {
                return null;
            }
        }
    }
}

