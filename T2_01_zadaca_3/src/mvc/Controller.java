/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import CheckStructureThread.DirectoryCheck;
import CompositeIterator.FileTreeIterator;
import FileIterator.InitialStructure.FileRepository;
import additional.FileInfo;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import t2_01_zadaca_3.T2_01_zadaca_3;
import utils.Constants;

/**
 * Class for receiving user inputs and returning response
 *
 * @author Josip
 */
public class Controller {

    private View view;
    private Model model;
    private int seconds;
    public static int numDir = 0;
    public static int numFile = 0;
    public static int overallSize = 0;

    DirectoryCheck thread = null;

    public Controller(View view, Model model, int seconds) {
        this.view = view;
        this.model = model;
        this.seconds = seconds;
    }

    public void showScreen() {
        view.printScreen();
    }

    public void processOption() {
        FileTreeIterator ft = new FileTreeIterator();
        String choice = "";
        do {
            System.out.print(Constants.CURSOR_SAVE);
            Scanner in = new Scanner(System.in);
            choice = in.nextLine();
            switch (choice) {
                case "1":
                    ft.clearData();
                    ft.calculateNumberOfDirsAndFiles(FileRepository.directoryTree.get(0));
                    model.setData(ft.getNumberDirsAndFiles());
                    view.updateFirstScreen(model.getData());
                    break;

                case "2":
                    ft.clearData();
                    model.setData(ft.getElementData(FileRepository.directoryTree.get(0)));
                    view.updateFirstScreen(model.getData());
                    break;

                case "3":
                    thread = new DirectoryCheck(seconds, view);
                    thread.setRunning(true);
                    thread.start();
                    view.updateFirstScreenByString("Thread is running.\n", "32");
                    break;

                case "4":
                    thread.setRunning(false);
                    thread.interrupt();
                    view.updateFirstScreenByString("Thread is stopped.", "33");
                    break;

                case "5":
                    view.updateFirstScreenByString("I usually don't do this.", "32");
                    view.updateFirstScreenByString("I hate Java", "32");
                    break;

                case "6":
                    System.out.print(Constants.CURSOS_RESTORE);
                    System.out.print(Constants.ERASE_END_OF_LINE);
                    System.out.print("Odaberi n: ");
                    in.nextLine();

                    break;

                case "7":
                    System.out.print(Constants.CURSOS_RESTORE);
                    System.out.print(Constants.ERASE_END_OF_LINE);
                    System.out.print("Odaberi m: ");
                    in.nextLine();
                    break;

                case "8": {
                    try {
                        showDir(0, new File(T2_01_zadaca_3.rootDirectory));
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

                case "9":
                    FileInfo fi = new FileInfo();
                    fi.printFileInfo();
                    break;
            }
            System.out.print(Constants.CURSOS_RESTORE);
            System.out.print(Constants.ERASE_END_OF_LINE);
            System.out.print(Constants.ANSI_ESC + "33m");
        } while (!choice.equalsIgnoreCase("Q"));

        System.out.print(Constants.ERASE_END_OF_LINE);
    }

    private void showDir(int indent, File file) throws IOException {
        String text = "";
        for (int i = 0; i < indent; i++) {
            text += "-";
        }
        SimpleDateFormat formatedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dateModified = formatedDate.format(file.lastModified());
        DecimalFormat df = new DecimalFormat("###,###.###");
        if (file.isDirectory()) {
            text += file.getName() + " " + dateModified + " " + df.format(getFolderSize(file)).replace(",", ".") + "B";
        } else {
            text += file.getName() + " " + dateModified + " " + df.format(file.length()).replace(",", ".") + "B";
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        overallSize += file.length();
        if (file.isDirectory()) {
            numDir++;
            view.updateFirstScreenByString(text, "31");

        } else {
            numFile++;
            view.updateFirstScreenByString(text, "36");

        }
        view.updateSecondScreenByString("Ukupan broj direktorija: " + numDir, "33", true);
        view.updateSecondScreenByString("Ukupan broj datoteka: " + numFile, "33", false);
        view.updateSecondScreenByString("Ukupna veličina: " + df.format(overallSize).replace(",", ".") + "B", "33", false);

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {

                showDir(indent + 2, files[i]);

            }
        }
    }

    private long getFolderSize(File directory) {
        long size = 0;

        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                size += file.length();
            } else {
                size += getFolderSize(file);
            }
        }

        return size;
    }
}
