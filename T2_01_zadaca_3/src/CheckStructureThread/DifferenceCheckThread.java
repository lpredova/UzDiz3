/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CheckStructureThread;

import FileIterator.InitialStructure.FileRepository;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.Model;
import mvc.View;
import t2_01_zadaca_3.T2_01_zadaca_3;

/**
 *
 * @author tonovosel
 */
public class DifferenceCheckThread extends Thread {

    private int secondsNum;
    private View view;
    private Model model;
    private volatile boolean running;
    private volatile boolean active;

    private DifferenceChecker differenceChecker;
    
    public DifferenceCheckThread(int secondsNum, View view, Model model) {
        this.secondsNum = secondsNum;
        this.view = view;
        this.model = model;
        
        differenceChecker = new DifferenceChecker(view, model, 2);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public synchronized void interrupt() {
        active = false;
        super.interrupt();
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public synchronized void run() {

        long duration;
        long startTime;
        
        while (running) {
            
            duration = 0;
            startTime = System.currentTimeMillis();

            try {

                differenceChecker.CheckDifference(new File(T2_01_zadaca_3.rootDirectory), FileRepository.directoryTree.get(0));                           
                
                duration = System.currentTimeMillis() - startTime;

                try {
                    Thread.sleep(secondsNum * 1000 - duration);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    active = false;
                }

            } catch (IOException ex) {
                Logger.getLogger(DifferenceCheckThread.class.getName()).log(Level.SEVERE, null, ex);
            }

        }//while

    }  
}
