/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CheckStructureThread;

import CompositeIterator.FileTreeIterator;
import CompositeIterator.Iterator;
import FileStructureComposite.AppFile;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import static mvc.Controller.caretaker;
import static mvc.Controller.originator;
import mvc.Model;
import mvc.View;
import t2_01_zadaca_3.T2_01_zadaca_3;

/**
 *
 * @author Steyskal
 */
public class DifferenceChecker {

    private View view;
    private Model model;
    private int window;

    private final ArrayList<String> compositeFiles = new ArrayList<String>();
    private final ArrayList<String> fileSystemFiles = new ArrayList<String>();

    private FileTreeIterator ft = null;

    private boolean changeDetected = false;

    public DifferenceChecker(View view, Model model, int window) {
        this.view = view;
        this.model = model;
        ft = new FileTreeIterator();
        ft = new FileTreeIterator();
        this.window = window;
    }

    private void UpdateView(String output) {
        if (window == 1) {
            view.updateFirstScreenByString(output, "31");
        } else {
            view.updateSecondScreenByString(output, "31", false);
        }
    }

    public void CheckDifference(File systemFile, AppFile rootFile) throws IOException {
        changeDetected = false;

        fileSystemFiles.clear();
        compositeFiles.clear();

        setFileSystemFiles(systemFile);
        setCompositeFiles(rootFile);

        checkForDelta(systemFile, rootFile);
        checkForAddedFiles(systemFile);
        checkForDeletedFiles(rootFile);

        if (!changeDetected) {
            view.updateFirstScreenByString(getCurrentTimeStamp() + ": Ne postoje promjene", "31");
        } else {

            T2_01_zadaca_3.filesRepository.directoryTree.clear();
            T2_01_zadaca_3.filesRepository.getIterator(T2_01_zadaca_3.rootDirectory);
            T2_01_zadaca_3.rootComposite = T2_01_zadaca_3.filesRepository.directoryTree.get(0);

            originator.set(T2_01_zadaca_3.rootComposite.clone());
            caretaker.addMemento(originator.saveToMemento());
            UpdateView("Saving to Memento.");
        }
    }

    public void CheckDifference(AppFile rootFile, AppFile mementoFile) throws IOException {
        changeDetected = false;

        fileSystemFiles.clear();
        compositeFiles.clear();

        setFileSystemFiles(rootFile);
        setCompositeFiles(mementoFile);

        //TODO Check the difference, just which files are the delta

        if (!changeDetected) {
            view.updateFirstScreenByString(getCurrentTimeStamp() + ": Ne postoje promjene", "31");
        }
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

    private void setFileSystemFiles(AppFile parent) {
        for (Iterator iter = ft.getIterator(); iter.hasNext(parent);) {
            AppFile nextElement = (AppFile) iter.getNextChild(parent);
            if (!nextElement.getType().equalsIgnoreCase("directory")) {
                fileSystemFiles.add(nextElement.getName());
            }
            if (nextElement.getType().equals("directory")) {
                fileSystemFiles.add(nextElement.getName());
                setFileSystemFiles(nextElement);
            }
        }
    }

    private void checkForDelta(File parent, AppFile compositeParent) throws IOException {

        if (parent.exists()) {
            for (Iterator iter = ft.getIterator(); iter.hasNext(compositeParent);) {
                AppFile nextElement = (AppFile) iter.getNextChild(compositeParent);
                File[] files = parent.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (nextElement.getName().equalsIgnoreCase(files[i].getName())) {
                        if (!nextElement.getUpdatedAt().equalsIgnoreCase(formatDate(files[i]))) {

                            UpdateView(getCurrentTimeStamp() + " File/folder " + files[i].getName() + "je ažuriran, "
                                    + " putanja: " + files[i].getCanonicalPath());
                            changeDetected = true;
                        }
                        if (!nextElement.getType().equalsIgnoreCase("directory") && files[i].isFile()) {
                            if (!nextElement.getFormattedSize().equalsIgnoreCase(formatSize(files[i]))) {

                                UpdateView(getCurrentTimeStamp() + " File " + files[i].getName() + "ima drugačiju veličinu, "
                                        + " putanja: " + files[i].getCanonicalPath());
                                changeDetected = true;
                            }
                        }
                        if (nextElement.getType().equalsIgnoreCase("directory") && files[i].isDirectory()) {
                            if (!nextElement.getFormattedSize().equalsIgnoreCase(formatFolderSize(files[i]))) {

                                UpdateView(getCurrentTimeStamp() + " Folder " + files[i].getName() + "ima drugačiju veličinu, "
                                        + " putanja: " + files[i].getCanonicalPath());
                                changeDetected = true;
                            }
                        }
                    }
                    if (files[i].isDirectory() && nextElement.getType().equalsIgnoreCase("directory")) {
                        checkForDelta(files[i], nextElement);
                    }
                }
            }
        } else {
            UpdateView("Ne postoji root direktorij.");
        }
    }

    private void checkForAddedFiles(File parent) throws IOException {

        for (File file : parent.listFiles()) {
            if (!compositeFiles.contains(file.getName())) {
                UpdateView(getCurrentTimeStamp() + " File/folder " + file.getName() + " je dodan, "
                        + " putanja: " + file.getCanonicalPath());
                changeDetected = true;
            }
            if (file.isDirectory()) {
                checkForAddedFiles(file);
            }
        }

    }

    private void checkForDeletedFiles(AppFile parent) throws IOException {

        for (Iterator iter = ft.getIterator(); iter.hasNext(parent);) {
            AppFile nextElement = (AppFile) iter.getNextChild(parent);
            if (!fileSystemFiles.contains(nextElement.getName())) {
                UpdateView("File/folder " + nextElement.getName() + " je obrisan, "
                        + " putanja: " + getFilePath(nextElement.getName()));
                changeDetected = true;
            }
            if (nextElement.getType().equalsIgnoreCase("directory")) {
                checkForDeletedFiles(nextElement);
            }
        }

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
