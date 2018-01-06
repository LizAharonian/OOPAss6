/**
 * SubMenuTask class.
 */
public class SubMenuTask implements Task<Void> {
    //members
    private  Menu<Task<Void>>  subMenu;
    private AnimationRunner runner;

    /**
     * constructor method.
     * @param subMenu - Menu<Task<Void>> obj.
     * @param runner - AnimationRunner obj.
     */
    public SubMenuTask(Menu<Task<Void>> subMenu, AnimationRunner runner) {
        this.subMenu = subMenu;
        this.runner = runner;

    }
    /**
     * run function.
     * @return null.
     */
    public Void run() {
        this.runner.run(subMenu);
        return null;
    }

}
