package it.polimi.ingsw.utils;

/**
 * Pinger Class, used to notify a observer a ping every 10s
 */
public class Pinger extends Observable<String> implements Runnable {
    /**
     * Status of the pinger
     */
    private boolean isActive = true;

    /**
     * Pinger Constructor
     * 
     * @param c observer to ping
     */
    public Pinger(Observer<String> c) {
        this.addObservers(c);
    }

    /**
     * Get Pinger Status
     * 
     * @return pinger status
     */
    public boolean getStatus() {
        return isActive;
    }

    /**
     * Run Pinger, ping the target observer each 10s
     */
    @Override
    public void run() {
        try {
            while (isActive) {
                Thread.sleep(10000);
                notify("");
            }
        } catch (Exception e) {
            // Fail sleep or notify
        } isActive = false;
    }

    /**
     * Stop the ping
     */
    public void stop() {
        isActive = false;
    }
}
