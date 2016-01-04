/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CheckStructureThread;

import mvc.View;

/**
 *
 * @author tonovosel
 */
public class DirectoryCheck extends Thread {

    private int secondsNum;
    private View view;
    private volatile boolean running;
    private volatile boolean active;

    public DirectoryCheck(int secondsNum, View view) {
        this.secondsNum = secondsNum;
        this.view = view;
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
            //TODO check for directory changes

            active = false;
            try {
                Thread.sleep(secondsNum * 1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }//while

    }

}
