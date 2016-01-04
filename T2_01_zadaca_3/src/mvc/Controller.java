/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import FileStructureMemento.Caretaker;
import FileStructureMemento.Originator;
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
    public static int numDir = 0;
    public static int numFile = 0;
    public static int overallSize = 0;

    Caretaker caretaker = new Caretaker();
    Originator originator = new Originator();

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
    }

    public void showScreen() {
        view.printScreen();
    }

    public void processOption() {
        String choice = "";
        do {
            System.out.print(Constants.CURSOR_SAVE);
            Scanner in = new Scanner(System.in);
            choice = in.nextLine();
            switch (choice) {
                case "1":
                    ArrayList<String> lista1 = new ArrayList<>();
                    String stringara5 = "Ukupan broj direktorija: 5";
                    String stringara6 = "Ukupan broj datoteka: 16";
                    lista1.add(stringara5);
                    lista1.add(stringara6);
                    model.setData(lista1);
                    view.updateFirstScreen(lista1);
                    break;

                case "2":
                    ArrayList<String> lista = new ArrayList<>();
                    String stringara = "some string for testing border crosses and fix that! Glupi kermek daje grupnu zadacu dok smo svi na praznicima. Kakav krele!!!";
                    String stringara2 = "something else on my mind hehe";
                    lista.add(stringara);
                    lista.add(stringara2);
                    lista.add("a");
                    lista.add("b");
                    lista.add("c");
                    lista.add("d");
                    lista.add("e");
                    lista.add("f");
                    lista.add("g");
                    lista.add("h");
                    lista.add("j");
                    lista.add("k");
                    lista.add("l");
                    lista.add("m");
                    lista.add("n");
                    lista.add("o");
                    lista.add("p");
                    lista.add("p");
                    lista.add("p");
                    lista.add("p");
                    model.setData(lista);
                    view.updateFirstScreen(model.getData());
                    break;

                case "3":
                    ArrayList<String> list = new ArrayList<>();
                    String stringara3 = "some string for testing border crosses and fix that! Glupi kermek daje grupnu zadacu dok smo svi na praznicima. Kakav krele!!!";
                    String stringara4 = "something else on my mind hehe";
                    list.add(stringara3);
                    list.add(stringara4);
                    model.setData(list);
                    view.updateFirstScreen(model.getData());
                    break;

                case "4":
                    view.updateSecondScreen();
                    break;

                case "5":
                    view.updateFirstScreenByString("I usually don't do this.", "32");
                    view.updateFirstScreenByString("I hate Java", "32");
                    break;

                case "6":
                    System.out.print(Constants.CURSOS_RESTORE);
                    System.out.print(Constants.ERASE_END_OF_LINE);
                    
                    //SAVING STATE EXAMPLE
//                    originator.set(T2_01_zadaca_3.root.clone());
//                    caretaker.addMemento(originator.saveToMemento());
//                    
//                    T2_01_zadaca_3.root.setName("NOVO");
//                    originator.set(T2_01_zadaca_3.root.clone());
//                    caretaker.addMemento(originator.saveToMemento());
                    //#
                    
                    int numberOfPossibleStates = caretaker.getNumberOfPossibleStates() - 1;
                    
                    if(numberOfPossibleStates < 0){
                        System.out.println("There are no saved states!");
                        break;
                    }

                    System.out.println("Odaberi n(0 - " + numberOfPossibleStates + "):");
         
                    int chosenState = Integer.parseInt(in.nextLine());

                    originator.restoreFromMemento(caretaker.getMemento(chosenState));
                    T2_01_zadaca_3.root = originator.getState();

                    break;

                case "7":
                    System.out.print(Constants.CURSOS_RESTORE);
                    System.out.print(Constants.ERASE_END_OF_LINE);
                    System.out.print("Odaberi m: ");
                    in.nextLine();
                    break;

                case "8": 
                    //Do we need this???
                    /*try {
                        showDir(0, new File(T2_01_zadaca_3.rootDirectory));
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }*/
                    
                    //Clearing all previous states and building dir tree again
                    caretaker.clearAllStates();
                    
                    T2_01_zadaca_3.filesRepository.directoryTree.clear();
                    T2_01_zadaca_3.filesRepository.getIterator(T2_01_zadaca_3.rootDirectory);
                    break;

                case "9":
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
