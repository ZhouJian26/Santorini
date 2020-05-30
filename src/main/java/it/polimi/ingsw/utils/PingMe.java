package it.polimi.ingsw.utils;

import java.util.Date;

public class PingMe extends Observable<String> implements Observer<String>, Runnable {
    private Date lastPing;

    public PingMe() {
        lastPing = new Date();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
                if (new Date().getTime() - lastPing.getTime() < 10000)
                    notify("ping");
                else
                    notify("close");
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void update(String message) {
        if (message.equals("ping"))
            lastPing = new Date();
    }
}