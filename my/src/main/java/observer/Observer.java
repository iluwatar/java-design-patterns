package observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class Test {
    public static void main(String[] args) {
        CountDownTimer countDownTimer = new CountDownTimer(10001, 1000);
        countDownTimer.addObserver(millisUntilFinished -> System.out.println("剩余时间1：" + millisUntilFinished));
        countDownTimer.addObserver(millisUntilFinished -> System.out.println("剩余时间2：" + millisUntilFinished));
        countDownTimer.start();
    }
}

public interface Observer {
    void onTick(int millisUntilFinished);
}

class CountDownTimer {
    private int millisInFuture;
    private final int countDownInterval;
    private final List<Observer> observers = new ArrayList<>();

    public CountDownTimer(int millisInFuture, int countDownInterval) {
        this.millisInFuture = millisInFuture;
        this.countDownInterval = countDownInterval;
    }

    public void addObserver(Observer observer) {
        if (!observers.contains(observer))
            observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void clear() {
        observers.clear();
    }

    public void notifyObservers(int millisUntilFinished) {
        observers.forEach(observer -> observer.onTick(millisUntilFinished));
    }

    public void start() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                millisInFuture -= countDownInterval;
                if (millisInFuture > 0) {
                    notifyObservers(millisInFuture);
                } else {
                    timer.cancel();
                }
            }
        }, 0, countDownInterval);
    }
}