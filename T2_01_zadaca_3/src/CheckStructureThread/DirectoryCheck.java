/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CheckStructureThread;

/**
 *
 * @author tonovosel
 */
public class DirectoryCheck extends Thread {

    private int secondsNum;
    private volatile boolean running;
    private volatile boolean active;

    public DirectoryCheck(int secondsNum) {
        this.secondsNum = secondsNum;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public synchronized void interrupt() {
        super.interrupt();
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void run() {

        while (running) {
            active = true;
            System.out.println("Checking for directory changes...");
            //TODO check for directory changes

            active = false;
            try {
                Thread.sleep(secondsNum * 1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                System.out.println("Dretva zaustavljena.");
            }
        }//while

    }

}
