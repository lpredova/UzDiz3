/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import java.util.ArrayList;
import java.util.Scanner;
import static mvc.View.ANSI_ESC;

/**
 * Class for receiving user inputs and returning response
 * @author Josip
 */
public class Controller {
    private View view;
    private Model model;

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
            System.out.print(ANSI_ESC + "s");
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
                    view.updateFirstScreen("I usually don't do this. Heya ho. Happy new year everyone!");
                    break;

                case "6":
                    System.out.print(ANSI_ESC + "u");
                    System.out.print(ANSI_ESC + "K");
                    System.out.print("Odaberi n: ");
                    in.nextLine();
                    
                    break;

                case "7":
                    System.out.print(ANSI_ESC + "u");
                    System.out.print(ANSI_ESC + "K");
                    System.out.print("Odaberi m: ");
                    in.nextLine();
                    break;

                case "8":
                    break;

                case "9":
                    break;
            }
            System.out.print(ANSI_ESC + "u");
            System.out.print(ANSI_ESC + "K");
        } while (!choice.equalsIgnoreCase("Q"));

        System.out.print(ANSI_ESC + "K");
    }
}
