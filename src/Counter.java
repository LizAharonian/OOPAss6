/**
 * Counter class.
 */
public class Counter {
    //member
    private int counter;

    /**
     * Counter constructor.
     */
    public Counter() {
        counter = 0;
    }

    /**
     * Counter constructor.
     * @param intializeNum - number to initialize the counter.
     */
    public Counter(int intializeNum) {
        this();
        this.increase(intializeNum);
    }

    /**
     * increase function.
     * @param number - number for add to current count.
     */
    public void increase(int number) {
        counter += number;
    }

    /**
     * decrease function.
     * @param number number for subtract from current count.
     */
    public void decrease(int number) {
        counter -= number;
    }

    /**
     * getValue function.
     * @return val of the counter.
     */
    public int getValue() {
        return this.counter;
    }
}
