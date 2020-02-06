package view;

public class Timer {

    private long startingTime;

    public Timer() {
        this.startingTime = System.currentTimeMillis();
    }

    public long listen(int timer) {
        long cur = System.currentTimeMillis();
        return (timer * 1000 - cur + startingTime) / 1000;
    }
}
