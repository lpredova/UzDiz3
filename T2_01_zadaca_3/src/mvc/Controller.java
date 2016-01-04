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

    public void setForEntry() {
        view.enterInput();
    }

    public void processOption() {
        String choice = "";
        do {
            System.out.print(Constants.CURSOR_SAVE);
            Scanner in = new Scanner(System.in);
            choice = in.nextLine();
            switch (choice) {
                case "1":
                    // TODO
                    break;

                case "2":
                    // TODO
                    break;

                case "3":
                    // TODO
                    break;

                case "4":
                    // TODO
                    break;

                case "5":
                    // TODO
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
                    // TODO
                    break;
                    
                case "8": 
            
                    //Clearing all previous states and building dir tree again
                    caretaker.clearAllStates();
                    
                    T2_01_zadaca_3.filesRepository.directoryTree.clear();
                    T2_01_zadaca_3.filesRepository.getIterator(T2_01_zadaca_3.rootDirectory);


                case "9":
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
