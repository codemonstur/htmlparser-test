package gauntlet.util;

public final class Counter {
    private int value = 0;

    public void increment() {
        value++;
    }
    public int get() {
        return value;
    }
}
