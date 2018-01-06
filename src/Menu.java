

/**
 * Menu<T> interface.
 * @param <T> - menu type.
 */
public interface Menu<T> extends Animation {
    /**
     * addSelection function.
     * @param key - selected key to start new task.
     * @param message - string message of task.
     * @param returnVal - which task to operate.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * getStatus function.
     * @return T obj.
     */
    T getStatus();

    /**
     * addSubMenu function.
     * @param key - selected key to start new task.
     * @param message - string message of task.
     * @param subMenu - which task to operate.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);

    /**
     * setStop function.
     * @param val - new val of stop var.
     */
    void setStop(boolean val);

}
