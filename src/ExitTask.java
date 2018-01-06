/**
 * Created by lizah on 06/06/2017.
 */
public class ExitTask implements Task<Void> {
    //members
    private AnimationRunner runner;

    /**
     * constructor method.
     * @param runner - AnimationRunner obj.
     */
    public ExitTask(AnimationRunner runner) {
        this.runner = runner;

    }

    /**
     * run function.
     * @return null.
     */
    public Void run() {
        System.exit(1);
        return null;
    }
}
