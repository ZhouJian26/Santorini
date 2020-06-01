package it.polimi.ingsw.utils;

public class Pinger extends Observable<String> implements Runnable {
    private boolean isActive = true;

    public Pinger(Observer<String> c) {
        this.addObservers(c);
    }

    @Override
    public void run() {
        try {
            while (isActive) {
                Thread.sleep(10000);
                notify("");
            }
        } catch (Exception e) {
            // Fait to sleep
        }
    }

    public void stop() {
        isActive = false;
    }
}
