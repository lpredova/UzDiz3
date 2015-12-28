/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t2_01_zadaca_3;

import argumentValidation.ArgumentValidator;
import java.util.Scanner;

/**
 *
 * @author Steyskal
 */
public class T2_01_zadaca_3 {

    public static final String ANSI_ESC = "\033[";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        ArgumentValidator av = new ArgumentValidator(args);
        if (!av.Validate()) {
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
        String choice = "1337";
        do {
            System.out.println("-------------------------------------------------");
            System.out.println("MAIN MENU");
            System.err.println("-------------------------------------------------");
            System.out.println("1 - ispis ukupnog broja direktorija i datoteka u strukturi (prikaz u 1. prozoru)");
            System.out.println("2 - ispis sadržaja strukture direktorija i datoteka uz prikaz naziva, vremena (formatiranog u HR obliku), veličina (u formatu 999.999.999 B) (prikaz u 1. prozoru)");
            System.out.println("3 - izvršavanje dretve (prikaz u 1. prozoru)");
            System.out.println("4 - prekid izvršavanja dretve (prikaz u 1. prozoru)");
            System.out.println("5 - ispis informacija o svim spremljenim stanjima (redni broj i vrijeme spremljenja) (prikaz u 1. prozoru)");
            System.out.println("6 n - postavljanje stanja strukture na promjenu s rednim brojem n čime ono postaje novo trenutno stanje strukture (prikaz u 1. prozoru)");
            System.out.println("7 m - uspoređivanje trenutnog stanja strukture i promjene s rednim brojem m (prikaz u 1. prozoru)");
            System.out.println("8 - ponovno učitavanje strukture uz poništavanje svih spremljenih stanja strukture (prikaz kao i kod inicijalnog učitavanja strukture)");
            System.out.println("9 - dodana vlastita funkcionalnost (prikaz u 1. prozoru)");
            System.out.println("Q - prekid rada programa");

            Scanner in = new Scanner(System.in);

            System.out.print("Odaberi opciju: ");
            choice = in.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("ispis ukupnog broja direktorija i datoteka u strukturi (prikaz u 1.                             prozoru)");
                    break;

                case "2":
                    System.out.println("ispis sadržaja strukture direktorija i datoteka uz prikaz naziva,                               vremena (formatiranog u HR obliku), veličina (u formatu 999.999.999 B) (prikaz u 1.                             prozoru)");
                    break;

                case "3":
                    System.out.println("izvršavanje dretve (prikaz u 1. prozoru)");
                    break;

                case "4":
                    System.out.println("prekid izvršavanja dretve (prikaz u 1. prozoru)");
                    break;

                case "5":
                    System.out.println("ispis informacija o svim spremljenim stanjima (redni broj i vrijeme                             spremljenja) (prikaz u 1. prozoru)");
                    break;

                case "6":
                    System.out.println("postavljanje stanja strukture na promjenu s rednim brojem n čime ono                            postaje novo trenutno stanje strukture (prikaz u 1. prozoru)");
                    break;

                case "7":
                    System.out.println("uspoređivanje trenutnog stanja strukture i promjene s rednim brojem m (                         prikaz u 1. prozoru)");
                    break;

                case "8":
                    System.out.println("ponovno učitavanje strukture uz poništavanje svih spremljenih stanja                            strukture (prikaz kao i kod inicijalnog učitavanja strukture)");
                    break;

                case "9":
                    System.out.println("dodana vlastita funkcionalnost (prikaz u 1. prozoru)");
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
        System.out.print(ANSI_ESC + "41;1f");
        System.out.print(ANSI_ESC + "31m" + "Crvena " + ANSI_ESC + "33m" + "Zelena " + ANSI_ESC + "32m" + "Plava"
                + ANSI_ESC + "0m");
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
