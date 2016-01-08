/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CheckStructureThread;

import CompositeIterator.FileTreeIterator;
import CompositeIterator.Iterator;
import FileIterator.InitialStructure.FileRepository;
import FileStructureComposite.AppFile;
import java.io.File;
import mvc.View;
import t2_01_zadaca_3.T2_01_zadaca_3;

/**
 *
 * @author tonovosel
 */
public class DirectoryCheck extends Thread {
    

    private int secondsNum;
    private View view;
    private volatile boolean running;
    private volatile boolean active;
    FileTreeIterator ft = null;
    File rootDir = null;

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
    public synchronized void run() {

        rootDir = new File(T2_01_zadaca_3.rootDirectory);

        while (running) {
            active = true;

            active = false;
            try {
                Thread.sleep(secondsNum * 1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }//while

    }

    public void checkForDelta(File root) {
        if (root.exists()) {
            if (root.isDirectory()) {
                ft = new FileTreeIterator();
                for (Iterator iter = ft.getIterator(); iter.hasNext(FileRepository.directoryTree.get(0));) {
                    AppFile nextElement = (AppFile) iter.getNextChild(FileRepository.directoryTree.get(0));
                    File[] files = root.listFiles();
                    for (int i = 0; i < files.length; i++) {
                    }
                }
            } else {
                view.updateFirstScreenByString("Root is not directory.", "31");
            }
        } else {
            view.updateFirstScreenByString("Can't find root directory.", "31");
        }
    }

}
