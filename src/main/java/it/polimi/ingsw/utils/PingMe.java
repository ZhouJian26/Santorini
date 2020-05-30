package it.polimi.ingsw.utils;

import java.io.Closeable;
import java.io.IOException;
import java.util.Date;

public class PingMe<T> extends Observable<String> implements Observer<T>, Runnable {
    private Date lastPing;
    private boolean status;
    private final int sleepDelta;
    private final int lastPingDelta;
    private final transient Closeable connection;

    public PingMe(Closeable connection) {
        this.connection = connection;
        this.lastPingDelta = 15000;
        this.sleepDelta = 5000;
    }

    public PingMe(Closeable connection, int sleepDelta, int lastPingDelta) {
        this.connection = connection;
        this.lastPingDelta = lastPingDelta;
        this.sleepDelta = sleepDelta;
    }

    @Override
    public void run() {
        lastPing = new Date();
        status = true;
        try {
            while (status) {
                Thread.sleep(sleepDelta);
                if (new Date().getTime() - lastPing.getTime() > lastPingDelta)
                    break;
                notify("0");
            }
            if (status)
                connection.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(T message) {
        lastPing = new Date();
    }

    public void stop() {
        status = false;
    }
}