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

import FileStructureMemento.Caretaker;
import FileStructureMemento.Originator;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
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

    public void setForEntry() {
        view.enterInput();
    }

    public void processOption() {
        FileTreeIterator ft = new FileTreeIterator();
        Caretaker caretaker = new Caretaker();
        Originator originator = new Originator();

        // Saving initial state to memento
        originator.set(T2_01_zadaca_3.root.clone());
        caretaker.addMemento(originator.saveToMemento());

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
                    System.out.print(Constants.CURSOS_RESTORE);
                    System.out.print(Constants.ERASE_END_OF_LINE);

                    HashMap<Object, String> savedStates = caretaker.getSavedStates();
                    ArrayList<String> stringOutputOption5 = new ArrayList<>();
                    
                    stringOutputOption5.add("Information about all states:");
                    stringOutputOption5.add("");
                    
                    for (int i = 0; i < savedStates.size(); i++) {
                        stringOutputOption5.add("State: " + i + " - Saved: " + savedStates.values().toArray()[i]);
                    }
                    
                    model.setData(stringOutputOption5);
                    view.updateFirstScreen(model.getData());
                    
                    break;

                case "6":
                    System.out.print(Constants.CURSOS_RESTORE);
                    System.out.print(Constants.ERASE_END_OF_LINE);

                    //SAVING STATE EXAMPLE
//                    originator.set(T2_01_zadaca_3.root.clone());
//                    caretaker.addMemento(originator.saveToMemento());
//                    
                    int numberOfPossibleStates = caretaker.getNumberOfPossibleStates() - 1;
                    
                    int chosenState = -1;
                    do{
                        System.out.print("Odaberi n(0 - " + numberOfPossibleStates + "): ");
                        chosenState = Integer.parseInt(in.nextLine());
                    }while(chosenState < 0 || chosenState > numberOfPossibleStates);
                    
                    originator.restoreFromMemento(caretaker.getMemento(chosenState).getKey());
                    T2_01_zadaca_3.root = originator.getState();

                    ArrayList<String> stringOutputOption6 = new ArrayList<>();
                    stringOutputOption6.add("Restored state(" + chosenState + "): " + caretaker.getMemento(chosenState).getKey() + "\nFrom: " + caretaker.getMemento(chosenState).getValue());
                    
                    model.setData(stringOutputOption6);
                    view.updateFirstScreen(model.getData());
                    
                    break;

                case "7":
                    System.out.print(Constants.CURSOS_RESTORE);
                    System.out.print(Constants.ERASE_END_OF_LINE);
                    System.out.print("Odaberi m: ");
                    in.nextLine();
                    // TODO
                    break;

                case "8":

                    //Clearing all previous states and building dir tree again
                    caretaker.clearAllStates();

                    T2_01_zadaca_3.filesRepository.directoryTree.clear();
                    T2_01_zadaca_3.filesRepository.getIterator(T2_01_zadaca_3.rootDirectory);

                    //Setting the new root element and saving it to memento
                    T2_01_zadaca_3.root = T2_01_zadaca_3.filesRepository.directoryTree.get(0);

                    originator.set(T2_01_zadaca_3.root.clone());
                    caretaker.addMemento(originator.saveToMemento());

                    break;

                case "9":

                    FileInfo fi = new FileInfo();
                    fi.printFileInfo();

                    // TODO OWN FUNCIONALITY
                    break;
            }
            this.setForEntry();
        } while (!choice.equalsIgnoreCase("Q"));

        System.out.print(Constants.ERASE_END_OF_LINE);
    }

    public void showDir(int indent, File file) throws IOException {
        String text = "";
        for (int i = 0; i < indent; i++) {
            text += "-";
        }
        DecimalFormat df = new DecimalFormat("###,###.###");
        if (file.isDirectory()) {
            text += file.getName();
        } else {
            text += file.getName();
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

}
