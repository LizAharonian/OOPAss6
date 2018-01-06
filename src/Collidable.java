/**
 * Collidable interface.
 * The Collidable interface is used by things that can be collided with.
 */

public interface Collidable {
    /**
     * getCollisionRectangle function (declaration).
     * @return collision rectangle.
     */
    Rectangle getCollisionRectangle();
  /**
     * hit function (declaration).
     * Notify the object that we collided with it at collisionPoint with
       a given velocity.
     * @param hitter - hitter ball.
     * @param collisionPoint - the triangle collision point with rectangle.
     * @param currentVelocity - current ball velocity.
     * @return the new velocity expected after the hit (based on
      the force the object inflicted on us).
     */

   Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
