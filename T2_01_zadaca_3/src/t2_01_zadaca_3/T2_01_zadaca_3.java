/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t2_01_zadaca_3;

import AppMVC.MenuController;
import AppMVC.MenuModel;
import AppMVC.MenuView;
import CheckStructureThread.DirectoryCheck;
import FileIterator.InitialStructure.FileRepository;
import FileIterator.InitialStructure.Iterator;
import argumentValidation.ArgumentValidator;
import java.util.Scanner;

/**
 *
 * @author Steyskal
 */
public class T2_01_zadaca_3 {

    public static final String ANSI_ESC = "\033[";
    public static String rootDirectory;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        System.out.println(ANSI_ESC + "0m");

        ArgumentValidator av = new ArgumentValidator(args);
        if (!av.Validate()) {
            System.out.println(ANSI_ESC + "31m");
            System.err.println("Some of the arguments are not valid, restart the program!");
            System.exit(0);
        }

        int rowNum = Integer.parseInt(args[0]);
        int columnNum = Integer.parseInt(args[1]);
        String screenSeparation = args[2];
        int secondsNum = Integer.parseInt(args[0]);

        // OOH DO IT BABY FANCY
        //KermekMetoda();
        // TODO screen preparation
        // ...
        // TODO load directory structure
        // ...
        MenuView mv = new MenuView();
        MenuModel mm = new MenuModel();
        MenuController mc = new MenuController(mm, mv);
        mm.saveToList();
        DirectoryCheck thread = new DirectoryCheck(Integer.parseInt(args[4]));

        String choice = "1337";
        
        //Reading initial file structure - creational iterator, has only one iteration and creates dir tree
        FileRepository filesRepository = new FileRepository();
        for (Iterator iterator = filesRepository.getIterator(args[3]); iterator.hasNext(); ) {
            iterator.next();
        }
        
        System.out.println("Directory tree created" + FileRepository.directoryTree + filesRepository);
        

        
        do {

            System.out.println("");
            System.out.println("");
            mc.updateMenuView();

            Scanner in = new Scanner(System.in);

            System.out.print("Odaberi opciju: ");
            choice = in.nextLine();

            switch (choice) {
                case "1":
                    mc.updateOptionview(Integer.parseInt(choice));
                    break;

                case "2":
                    mc.updateOptionview(Integer.parseInt(choice));
                    break;

                case "3":
                    mc.updateOptionview(Integer.parseInt(choice));
                    thread.setRunning(true);
                    if (thread.getState() == Thread.State.NEW) {
                        thread.start();
                        System.out.println("Dretva pokrenuta");
                    } else {
                        System.out.println("Dretva se već izvršava.");
                    }
                    break;

                case "4":
                    mc.updateOptionview(Integer.parseInt(choice));
                    thread.setRunning(false);
                    if (thread.isAlive() && thread.isActive() == false) {
                        thread.interrupt();
                        System.out.println("Zaustavljam dretvu...");
                    } else {
                        System.out.println("Dretva je već zaustavljena ili nije pokrenuta.");
                    }
                    break;

                case "5":
                    mc.updateOptionview(Integer.parseInt(choice));
                    break;

                case "6":
                    mc.updateOptionview(Integer.parseInt(choice));
                    break;

                case "7":
                    mc.updateOptionview(Integer.parseInt(choice));
                    break;

                case "8":
                    mc.updateOptionview(Integer.parseInt(choice));
                    break;

                case "9":
                    mc.updateOptionview(Integer.parseInt(choice));
                    break;
            }
        } while (!choice.equalsIgnoreCase("Q"));
    }

    static void KermekMetoda() {
        System.out.print(ANSI_ESC + "2J"); // Erase Screen

        int i = 1;
        int j = 80;
        for (; i < 38; i++) {
            prikazi(i, 2 * i, 31, "*");
        }
        for (; j > 1; j = j - 2) {
            prikazi(i, j, 32, "-");
        }
        for (; i > 1; i--) {
            prikazi(i, (80 - (2 * i)), 33, "+");
        }
        for (j = 80; j > 1; j = j - 2) {
            prikazi(i, j, 37, "#");
        }
        System.out.print(ANSI_ESC + "41;1f");//Print on bottom of the screen
        System.out.print(ANSI_ESC + "31m" + "Crvena " + ANSI_ESC + "33m" + "Zelena " + ANSI_ESC + "32m" + "Plava " + ANSI_ESC + "35m" + "Magenta"
                + ANSI_ESC + "0m");//Reset colors
        for (int k = 30; k < 38; k++) {
            prikazi(42, k - 29, k, "@");
        }
    }

    static void postavi(int x, int y) {
        System.out.print(ANSI_ESC + x + ";" + y + "f");
    }

    static void prikazi(int x, int y, int boja, String tekst) {
        postavi(x, y);
        System.out.print(ANSI_ESC + boja + "m");
        System.out.print(tekst);
        try {
            Thread.sleep(30);
        } catch (InterruptedException ex) {
        }
    }

}
