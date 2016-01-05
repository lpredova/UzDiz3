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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    FileTreeIterator ft = null;
    File rootDir = null;
    AppFile compositeRoot = FileRepository.directoryTree.get(0);

    ArrayList<String> compositeFiles = new ArrayList<String>();
    ArrayList<String> fileSystemFiles = new ArrayList<String>();

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
        long duration = 0;
        long startTime = System.currentTimeMillis();

        while (running) {

            try {
<<<<<<< HEAD
                checkForDelta(rootDir, compositeRoot);
                if (fileSystemFiles.size() != compositeFiles.size()) {
                    deltaExists = true;
                    view.updateFirstScreenByString("Postoje novi fileovi", "31");
                }
                if (deltaExists == false) {
                    view.updateFirstScreenByString("Ne postoje promjene", "31");
=======
                
                if (!checkForDelta(rootDir, compositeRoot)) {
                    view.updateFirstScreenByString(getCurrentTimeStamp() + ": Ne postoje promjene", "31");
>>>>>>> origin/check-structure-thread
                }
            } catch (IOException ex) {
                Logger.getLogger(DirectoryCheck.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                Thread.sleep((secondsNum * 1000) - duration);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                active = false;
            }
        }//while

    }

    public boolean checkForDelta(File parent, AppFile compositeParent) throws IOException {

<<<<<<< HEAD
        deltaExists = false;

        countFileSystemFIles(parent);
        countCompositeFiles(compositeParent);

=======
        boolean deltaExists = false;
>>>>>>> origin/check-structure-thread
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(cal.getTime());

        if (parent.exists()) {
            for (Iterator iter = ft.getIterator(); iter.hasNext(compositeParent);) {
                AppFile nextElement = (AppFile) iter.getNextChild(compositeParent);
                File[] files = parent.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (nextElement.getName().equalsIgnoreCase(files[i].getName())) {
                        if (!nextElement.getUpdatedAt().equalsIgnoreCase(formatDate(files[i]))) {
                            //TODO spremiti stari composite u memento
                            //TODO ponovno kreirati stablo u compositu sa novim stanjem
                            view.updateSecondScreenByString("File je ažuriran", "31", false);
                            deltaExists = true;
                        }
                        if (!nextElement.getType().equalsIgnoreCase("directory") && files[i].isFile()) {
                            if (!nextElement.getFormattedSize().equalsIgnoreCase(formatSize(files[i]))) {
                                //TODO spremiti stari composite u memento
                                //TODO ponovno kreirati stablo u compositu sa novim stanjem
                                view.updateSecondScreenByString("File ima drugačiju veličinu.", "31", false);
                                deltaExists = true;
                            }
                        }
<<<<<<< HEAD
=======
                        if (!nextElement.getCreatedAt().equalsIgnoreCase(formatCreatedAt(files[i]))) {
                            //TODO spremiti stari composite u memento
                            //TODO ponovno kreirati stablo u compositu sa novim stanjem
                            view.updateSecondScreenByString("Kreiran je novi file sa istim imenom", "31", false);
                            deltaExists = true;
                        }
                    }
                    fileSystemFiles.add(files[i].getName());
                    compositeFiles.add(nextElement.getName());
                    if (fileSystemFiles.size() != compositeFiles.size()) {
                        view.updateSecondScreenByString("Postoje nove fileovi", "31", false);
>>>>>>> origin/check-structure-thread
                    }
                    if (files[i].isDirectory() && nextElement.getType().equalsIgnoreCase("directory")) {
                        checkForDelta(files[i], nextElement);
                    }
                }
            }
        } else {
            view.updateSecondScreenByString("Can't find root directory.", "31", false);
        }
        return deltaExists;
        
    }

    public String formatDate(File file) {
        String formattedDate = "";
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        formattedDate = f.format(file.lastModified());
        return formattedDate;
    }

    public String formatCreatedAt(File file) throws IOException {
        Path path = file.toPath();
        String createdAt = "";
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        BasicFileAttributes fileAttributes = Files.readAttributes(path, BasicFileAttributes.class);
        long creationTime = fileAttributes.creationTime().toMillis();
        createdAt = f.format(creationTime);
        return createdAt;
    }

    public String formatSize(File file) {
        String formattedSize = "";
        String pattern = "###,###.###";
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        formattedSize = myFormatter.format(file.length()).replace(',', '.') + " B";
        return formattedSize;
    }

    public String getFilePath(File file) {
        String filePath = "";
        filePath = file.getAbsolutePath();
        return filePath;
    }

<<<<<<< HEAD
    public void countCompositeFiles(AppFile parent) {

        for (Iterator iter = ft.getIterator(); iter.hasNext(parent);) {
            AppFile nextElement = (AppFile) iter.getNextChild(parent);
            compositeFiles.add(nextElement.getName());
            if (nextElement.getType().equals("directory") && !nextElement.getChildren().isEmpty()) {
                countCompositeFiles(nextElement);
            }
        }
    }

    public void countFileSystemFIles(File parent) {
        for (File file : parent.listFiles()) {
            if (file.isFile()) {
                fileSystemFiles.add(file.getName());
            } else {
                fileSystemFiles.add(file.getName());
                countFileSystemFIles(file);
            }
        }
=======
    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        return strDate;
>>>>>>> origin/check-structure-thread
    }

}
