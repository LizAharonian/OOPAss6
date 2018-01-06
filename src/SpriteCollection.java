import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * SpriteCollection class.
 * holds a collection of sprites.
 */
public class SpriteCollection {
    //member
    private List<Sprite> sprites;

    /**
     * constructor method.
     */
    SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();
    }

    /**
     * addSprite function.
     * @param s - Sprite obj.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * removeSprite function.
     * @param s - Sprite obj.
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /**
     * notifyAllTimePassed function.
     * call timePassed() on all sprites.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    public void notifyAllTimePassed(Double dt) {
        List<Sprite> tempSprites = new ArrayList<Sprite>(this.sprites);
        for (Sprite sprite : tempSprites) {
            sprite.timePassed(dt);
        }
    }

    /**
     * drawAllOn function.
     * call drawOn(d) on all sprites.
     * @param d - DrawSurface obj.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite : this.sprites) {
            sprite.drawOn(d);
        }
    }
}
