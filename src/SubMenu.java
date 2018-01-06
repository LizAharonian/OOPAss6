import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * SubMenu class.
 * @param <T> - task to operate.
 */
public class SubMenu<T> implements Menu<T> {
//members
    private Map<String, String> lineToPrintMap = new HashMap<String, String>();
    private Map<String, T> taskToOperateMap = new HashMap<String, T>();
    private T status = null;
    private GUI gui;
    private KeyboardSensor keyboardSensor;
    private boolean shouldStop;
    private Map<String, Menu<T>> subMenus = new HashMap<String, Menu<T>>();

    /**
     * constructor method.
     * @param gui - GUI obj.
     */
    public SubMenu(GUI gui) {
        this.gui = gui;
        this.keyboardSensor = gui.getKeyboardSensor();
        this.shouldStop = false;
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
       for (Map.Entry<String, String> entry: this.lineToPrintMap.entrySet()) {
            String key = entry.getKey();
           if (this.keyboardSensor.isPressed(key)) {
               this.status = this.taskToOperateMap.get(key);
               this.shouldStop = true;
           }
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
