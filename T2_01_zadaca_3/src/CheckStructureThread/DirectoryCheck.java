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
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import static mvc.Controller.caretaker;
import static mvc.Controller.originator;
import mvc.Model;
import mvc.View;
import t2_01_zadaca_3.T2_01_zadaca_3;

/**
 *
 * @author tonovosel
 */
public class DirectoryCheck extends Thread {

    private int secondsNum;
    private View view;
    private Model model;
    private volatile boolean running;
    private volatile boolean active;
    private boolean deltaExists;
    private FileTreeIterator ft = null;
    private File rootDir = null;
    private AppFile compositeRoot;

    private ArrayList<String> compositeFiles = new ArrayList<String>();
    private ArrayList<String> fileSystemFiles = new ArrayList<String>();

    public DirectoryCheck(int secondsNum, View view, Model model) {
        this.secondsNum = secondsNum;
        this.view = view;
        this.model = model;
        ft = new FileTreeIterator();
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
        rootDir = new File(T2_01_zadaca_3.rootDirectory);
        compositeRoot = FileRepository.directoryTree.get(0);

        long duration;
        long startTime;
        
        while (running) {
            
            duration = 0;
            startTime = System.currentTimeMillis();

            try {

                fileSystemFiles.clear();
                compositeFiles.clear();

                setFileSystemFiles(new File(T2_01_zadaca_3.rootDirectory));
                setCompositeFiles(FileRepository.directoryTree.get(0));

                if (checkForDelta(new File(T2_01_zadaca_3.rootDirectory), FileRepository.directoryTree.get(0)) == false
                        && checkForAddedFiles(new File(T2_01_zadaca_3.rootDirectory)) == false
                        && checkForDeletedFiles(FileRepository.directoryTree.get(0)) == false) {
                    view.updateFirstScreenByString(getCurrentTimeStamp() + ": Ne postoje promjene", "31");
                } else {

                    originator.set(T2_01_zadaca_3.rootComposite.clone());
                    caretaker.addMemento(originator.saveToMemento());

                    T2_01_zadaca_3.filesRepository.directoryTree.clear();
                    T2_01_zadaca_3.filesRepository.getIterator(T2_01_zadaca_3.rootDirectory);
                }
                
                duration = System.currentTimeMillis() - startTime;

                try {
                    Thread.sleep(secondsNum * 1000 - duration);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    active = false;
                }

            } catch (IOException ex) {
                Logger.getLogger(DirectoryCheck.class.getName()).log(Level.SEVERE, null, ex);
            }

        }//while

    }

    private boolean checkForDelta(File parent, AppFile compositeParent) throws IOException {

        boolean deltaExists = false;

        if (parent.exists()) {
            for (Iterator iter = ft.getIterator(); iter.hasNext(compositeParent);) {
                AppFile nextElement = (AppFile) iter.getNextChild(compositeParent);
                File[] files = parent.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (nextElement.getName().equalsIgnoreCase(files[i].getName())) {
                        if (!nextElement.getUpdatedAt().equalsIgnoreCase(formatDate(files[i]))) {

                            view.updateSecondScreenByString(getCurrentTimeStamp() + " File/folder " + files[i].getName() + "je ažuriran, "
                                    + " putanja: " + files[i].getCanonicalPath(), "31", false);
                            deltaExists = true;
                        }
                        if (!nextElement.getType().equalsIgnoreCase("directory") && files[i].isFile()) {
                            if (!nextElement.getFormattedSize().equalsIgnoreCase(formatSize(files[i]))) {

                                view.updateSecondScreenByString(getCurrentTimeStamp() + " File " + files[i].getName() + "ima drugačiju veličinu, "
                                        + " putanja: " + files[i].getCanonicalPath(), "31", false);
                                deltaExists = true;
                            }
                        }
                        if (nextElement.getType().equalsIgnoreCase("directory") && files[i].isDirectory()) {
                            if (!nextElement.getFormattedSize().equalsIgnoreCase(formatFolderSize(files[i]))) {

                                view.updateSecondScreenByString(getCurrentTimeStamp() + " Folder " + files[i].getName() + "ima drugačiju veličinu, "
                                        + " putanja: " + files[i].getCanonicalPath(), "31", false);
                                deltaExists = true;
                            }
                        }
                    }
                    if (files[i].isDirectory() && nextElement.getType().equalsIgnoreCase("directory")) {
                        checkForDelta(files[i], nextElement);
                    }
                }
            }
        } else {
            view.updateSecondScreenByString("Ne postoji root direktorij.", "31", false);
        }
        return deltaExists;

    }

    private boolean checkForAddedFiles(File parent) throws IOException {
        boolean added = false;
        for (File files : parent.listFiles()) {
            if (!compositeFiles.contains(files.getName())
                    && fileSystemFiles.contains(files.getName())) {
                view.updateSecondScreenByString(getCurrentTimeStamp() + " File/folder " + files.getName() + " je dodan, "
                        + " putanja: " + files.getCanonicalPath(), "31", false);
                added = true;
            }
            if (files.isDirectory()) {
                checkForAddedFiles(files);
            }
        }
        return added;
    }

    private boolean checkForDeletedFiles(AppFile parent) throws IOException {
        boolean deleted = false;
        for (Iterator iter = ft.getIterator(); iter.hasNext(parent);) {
            AppFile nextElement = (AppFile) iter.getNextChild(parent);
            if (!fileSystemFiles.contains(nextElement.getName())
                    && compositeFiles.contains(nextElement.getName())) {
                view.updateSecondScreenByString(getCurrentTimeStamp() + " File/folder " + nextElement.getName() + " je obrisan, "
                        + " putanja: " + getFilePath(nextElement.getName()), "31", false);
                deleted = true;
            }
            if (nextElement.getType().equalsIgnoreCase("directory")) {
                checkForDeletedFiles(nextElement);
            }
        }
        return deleted;
    }

    private String getFilePath(String fileName) throws IOException {
        String path = "";
        File filee = new File(fileName);
        path = filee.getCanonicalPath();
        return path;
    }

    private String formatDate(File file) {
        String formattedDate = "";
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        formattedDate = f.format(file.lastModified());
        return formattedDate;
    }

    private String formatSize(File file) {
        String formattedSize = "";
        String pattern = "###,###.###";
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        formattedSize = myFormatter.format(file.length()).replace(',', '.') + " B";
        return formattedSize;
    }

    private void setCompositeFiles(AppFile parent) {

        for (Iterator iter = ft.getIterator(); iter.hasNext(parent);) {
            AppFile nextElement = (AppFile) iter.getNextChild(parent);
            if (!nextElement.getType().equalsIgnoreCase("directory")) {
                compositeFiles.add(nextElement.getName());
            }
            if (nextElement.getType().equals("directory")) {
                compositeFiles.add(nextElement.getName());
                setCompositeFiles(nextElement);
            }
        }
    }

    private void setFileSystemFiles(File parent) {
        for (File file : parent.listFiles()) {
            if (file.isFile()) {
                fileSystemFiles.add(file.getName());
            } else {
                fileSystemFiles.add(file.getName());
                setFileSystemFiles(file);
            }
        }
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date now = new Date();
        String strDate = sdf.format(now);
        return strDate;

    }

    private long getFolderSize(File folder) {
        long size = 0;
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                size += file.length();
            } else {
                size += getFolderSize(file);
            }
        }

        return size;
    }

    private String formatFolderSize(File folder) {
        String formattedSize = "";
        String pattern = "###,###.###";
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        formattedSize = myFormatter.format(getFolderSize(folder)).replace(',', '.') + " B";
        return formattedSize;
    }

}
