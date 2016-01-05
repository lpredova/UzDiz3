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
    private boolean deltaExists;
    private FileTreeIterator ft = null;
    private File rootDir = null;
    private AppFile compositeRoot = FileRepository.directoryTree.get(0);
    private FileRepository fr = null;

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
        long duration = 0;
        long startTime = System.currentTimeMillis();

        while (running) {

            try {

                fileSystemFiles.clear();
                compositeFiles.clear();

                countFileSystemFIles(rootDir);
                countCompositeFiles(compositeRoot);

                if (!checkForDelta(rootDir, compositeRoot) && newFilesExist() == false) {
                    view.updateFirstScreenByString(getCurrentTimeStamp() + ": Ne postoje promjene", "31");
                }
                System.out.println(fileSystemFiles.size());
                System.out.println(compositeFiles.size());

                try {
                    Thread.sleep((secondsNum * 1000) - duration);
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
                            T2_01_zadaca_3.filesRepository.directoryTree.clear();
                            fr = new FileRepository();
                            fr.getIterator(T2_01_zadaca_3.rootDirectory);
                            view.updateSecondScreenByString(currentTime + " File " + files[i].getName() + "je ažuriran, "
                                    + " putanja: " + files[i].getCanonicalPath(), "31", false);
                            deltaExists = true;
                        }
                        if (!nextElement.getType().equalsIgnoreCase("directory") && files[i].isFile()) {
                            if (!nextElement.getFormattedSize().equalsIgnoreCase(formatSize(files[i]))) {

                                //TODO spremiti stari composite u memento
                                T2_01_zadaca_3.filesRepository.directoryTree.clear();
                                fr = new FileRepository();
                                fr.getIterator(T2_01_zadaca_3.rootDirectory);
                                view.updateSecondScreenByString(currentTime + " File " + files[i].getName() + "ima drugačiju veličinu, "
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
            view.updateSecondScreenByString("Can't find root directory.", "31", false);
        }
        return deltaExists;

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

    private void countCompositeFiles(AppFile parent) {

        for (Iterator iter = ft.getIterator(); iter.hasNext(parent);) {
            AppFile nextElement = (AppFile) iter.getNextChild(parent);
            compositeFiles.add(nextElement.getName());
            if (nextElement.getType().equals("directory") && !nextElement.getChildren().isEmpty()) {
                countCompositeFiles(nextElement);
            }
        }
    }

    private void countFileSystemFIles(File parent) {
        for (File file : parent.listFiles()) {
            if (file.isFile()) {
                fileSystemFiles.add(file.getName());
            } else {
                fileSystemFiles.add(file.getName());
                countFileSystemFIles(file);
            }
        }
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        return strDate;

    }

    private String getRootPath(String file) throws IOException {
        String path = "";
        File filee = new File(file);
        path = filee.getCanonicalPath();
        return path;
    }

    private boolean newFilesExist() throws IOException {
        boolean exist = false;
        if (fileSystemFiles.size() != compositeFiles.size()) {
            T2_01_zadaca_3.filesRepository.directoryTree.clear();
            fr = new FileRepository();
            fr.getIterator(T2_01_zadaca_3.rootDirectory);

            //TODO spremiti stari composite u memento
            view.updateSecondScreenByString(getCurrentTimeStamp()
                    + " Postoje novi fileovi/folderi na disku na putanji "
                    + getRootPath(T2_01_zadaca_3.rootDirectory), "31", false);
            exist = true;
        }
        return exist;
    }

}
