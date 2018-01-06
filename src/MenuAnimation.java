import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import java.awt.Color;
import java.util.Map;
import java.util.HashMap;


/**
 *  MenuAnimation<T> class.
 * @param <T> - task to operate when menu ends.
 */
public class MenuAnimation<T> implements Menu<T> {
    //members
    private Map<String, String> lineToPrintMap = new HashMap<String, String>();
    private Map<String, T> taskToOperateMap = new HashMap<String, T>();
    private Map<String, Menu<T>> subMenus = new HashMap<String, Menu<T>>();
    private T status;
    private GUI gui;
    private KeyboardSensor keyboardSensor;
    private boolean shouldStop;
    private AnimationRunner animationRunner;

    /**
     * constructor method.
     * @param gui - GUI obj.
     * @param animationRunner - AnimationRunner obj.
     */
    public MenuAnimation(GUI gui, AnimationRunner animationRunner) {
        this.gui = gui;
        this.keyboardSensor = gui.getKeyboardSensor();
        this.shouldStop = false;
        this.animationRunner = animationRunner;
    }
    /**
     * addSubMenu function.
     * @param key - selected key to start new task.
     * @param message - string message of task.
     * @param subMenu - which task to operate.
     */
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
       this.subMenus.put(key, subMenu);
    }
    /**
     * addSelection function.
     * @param key - selected key to start new task.
     * @param message - string message of task.
     * @param returnVal - which task to operate.
     */
    public void addSelection(String key, String message, T returnVal) {
        this.lineToPrintMap.put(key, message);
        this.taskToOperateMap.put(key, returnVal);

    }
    /**
     * getStatus function.
     * @return T obj.
     */
    public T getStatus() {
        return this.status;
    }
    /**
     * doOneFrame function.
     * @param d - DrawSurfaceObj for paint.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.shouldStop = false;
        d.setColor(Color.BLACK);
        int y = 50;
        for (Map.Entry<String, String> entry : this.lineToPrintMap.entrySet()) {
            d.drawText(20, y, entry.getKey() + " " + entry.getValue(), 32);
            y += 20;

        }
        //update status task to operate
        if (this.keyboardSensor.isPressed("h")) {
            this.status = this.taskToOperateMap.get("h");
            this.shouldStop = true;
        } else if (this.keyboardSensor.isPressed("s")) {
            this.animationRunner.run(this.subMenus.get("s"));
            this.status = this.subMenus.get("s").getStatus();
            this.subMenus.get("s").setStop(false);
            this.shouldStop = true;
        } else if (this.keyboardSensor.isPressed("q")) {
            this.status = this.taskToOperateMap.get("q");
            this.shouldStop = true;
        }
    }

    /**
     * shouldStop function.
     * @return true for stop, false otherwise.
     */
    public boolean shouldStop() {
        return this.shouldStop;

    }

    /**
     * setStop function.
     * @param val - new val of stop var.
     */
    public void setStop(boolean val) {
        this.shouldStop = val;
    }

}
