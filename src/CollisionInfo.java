/**
 * CollisionInfo calss.
 * holds information about collision.
 */
public class CollisionInfo {
    //members
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * constructor method.
     * @param collisionPoint - intersection point.
     * @param collisionObject - the collidable object involved in the collision.
     */
    CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * collisionPoint function.
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * collisionObject function.
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}
