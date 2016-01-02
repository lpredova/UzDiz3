/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import java.util.ArrayList;
import utils.Constants;

/**
 *
 * @author Josip
 */
public class View {

    public static int currentRow = 1;
    public static int currentRow1 = 1;
    public int rows;
    public int cols;
    public String division;
    public int halfRows;
    public int halfCols;

    public View(int rows, int cols, String division) {
        this.rows = rows;
        this.cols = cols;
        this.halfRows = (rows - 1) / 2;
        this.halfCols = (cols / 2);
        this.division = division;
    }

    public void printScreen() {
        System.out.print(Constants.ERASE_SCREEN);
        if (this.division.equalsIgnoreCase("v")) {
            this.VDivision();
        } else if (this.division.equalsIgnoreCase("o")) {
            this.ODivision();
        }
//        printMenu();
        System.out.print(Constants.CURSOS_RESTORE);
    }

    private void VDivision() {
        for (int i = 1; i <= rows - 1; i++) {
            show(i, halfCols + 1, 33, "*");
        }
        for (int j = 1; j <= cols + 1; j++) {
            show(rows, j, 33, "*");
        }
        System.out.print(Constants.ANSI_ESC + (rows + 1) + ";1f");
        System.out.print("Choose option: ");
        System.out.print(Constants.CURSOR_SAVE);
    }

    private void ODivision() {
        for (int i = 1; i <= cols; i++) {
            show(halfRows + 1, i, 33, "*");
        }
        if (rows % 2 == 0) {
            for (int j = 1; j <= cols; j++) {
                show(rows, j, 33, "*");
            }
            System.out.print(Constants.ANSI_ESC + (rows + 1) + ";1f");
            System.out.print("Choose option: ");
        } else {
            for (int j = 1; j <= cols; j++) {
                show(rows + 1, j, 33, "*");
            }
            System.out.print(Constants.ANSI_ESC + (rows + 2) + ";1f");
            System.out.print("Choose option: ");
        }

    }

    public void updateFirstScreen(ArrayList<String> data) {
        this.eraseFirstScreen();
        int counter = 0;
        if (this.division.equalsIgnoreCase("v")) {
            for (int i = 1; i <= rows - 1; i++) {
                System.out.print(Constants.ANSI_ESC + i + ";1f");
                if (counter < data.size()) {
                    if (data.get(counter).length() > halfCols) {
                        System.out.print(data.get(counter).substring(0, halfCols));
                        for (int k = halfCols; k < data.get(counter).length(); k = k + halfCols) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                            }
                            if (halfCols < data.get(counter).substring(k).length()) {
                                System.out.print("\n" + data.get(counter).substring(k, k + halfCols));
                            } else {
                                System.out.print("\n" + data.get(counter).substring(k));
                            }

                            i++;
                        }

                    } else {
                        System.out.print(data.get(counter));
                    }

                    counter++;
                    if (i == rows - 1) {
                        this.eraseFirstScreen();
                        i = 0;
                    }
                } else {
                    i = rows;
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                }
            }
            //this.eraseFirstScreen();
        } else if (this.division.equalsIgnoreCase("o")) {
            for (int i = 1; i <= halfRows; i++) {
                System.out.print(Constants.ANSI_ESC + i + ";1f");
                if (counter < data.size()) {
                    if (data.get(counter).length() > cols) {
                        System.out.print(data.get(counter).substring(0, cols));
                        for (int k = cols; k < data.get(counter).length(); k = k + cols) {
                            if (cols < data.get(counter).substring(k).length()) {
                                System.out.print("\n" + data.get(counter).substring(k, k + cols));
                            } else {
                                System.out.print("\n" + data.get(counter).substring(k));
                            }

                            i++;
                        }

                    } else {
                        System.out.print(data.get(counter));
                    }

                    counter++;
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
            }
            //this.eraseFirstScreen();
        }

    }

    public void updateFirstScreenByString(String text, String color) {

        if (this.division.equalsIgnoreCase("v")) {
            System.out.print(Constants.ANSI_ESC + currentRow + ";1f");
            System.out.print(Constants.ANSI_ESC + color + "m");
            if (text.length() > halfCols) {
                System.out.print(text.substring(0, halfCols));
                for (int k = halfCols; k < text.length(); k = k + halfCols) {
                    if (cols < text.substring(k).length()) {
                        System.out.print("\n" + text.substring(k, k + halfCols));
                    } else {
                        System.out.print("\n" + text.substring(k));
                    }
                    currentRow++;
                }

            } else {
                System.out.print(text);
            }

            if (currentRow == rows - 1) {
                this.eraseFirstScreen();
                currentRow = 1;
            } else {
                currentRow++;
            }

        }

    }

    public void updateSecondScreenByString(String text, String color, boolean erase) {
        if(erase) {
            this.eraseSecondScreen();
            currentRow1 = 1;
        }
        if (this.division.equalsIgnoreCase("v")) {
            System.out.print(Constants.ANSI_ESC + currentRow1 + ";" + (halfCols + 2) + "f");
            System.out.print(Constants.ANSI_ESC + color + "m");

            if (text.length() > (cols - halfCols)) {
                System.out.print(text.substring(0, cols - halfCols));

                for (int k = cols - halfCols; k < text.length(); k = k + cols - halfCols) {
                    System.out.print(Constants.ANSI_ESC + "1B");
                    System.out.print(Constants.ANSI_ESC + (halfCols) + "D");
                    if (currentRow1 == rows - 1) {
                        this.eraseSecondScreen();
                        currentRow1 = 1;
                        System.out.print(Constants.ANSI_ESC + currentRow1 + ";" + (halfCols + 2) + "f");
                    }
                    
                    if (cols < text.substring(k).length()) {
                        System.out.print(text.substring(k, k + cols - halfCols));
                    } else {
                        System.out.print(text.substring(k));
                    }
                    currentRow1++;
                }

            } else {
                System.out.print(text);
            }

            if (currentRow1 == rows - 1) {
                this.eraseSecondScreen();
                currentRow1 = 1;
            } else {
                currentRow1++;
            }

        }

    }

    public void updateSecondScreen() {
        this.eraseSecondScreen();
        if (this.division.equalsIgnoreCase("v")) {
            for (int i = 1; i <= rows - 1; i++) {
                System.out.print(Constants.ANSI_ESC + i + ";" + (halfCols + 2) + "f");
                System.out.print("Ispis: " + i);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
            }
            this.eraseSecondScreen();
        } else if (this.division.equalsIgnoreCase("o")) {
            for (int i = halfRows + 2; i <= (halfRows * 2 + 1); i++) {
                System.out.print(Constants.ANSI_ESC + i + ";1f");
                System.out.print("Ispis: " + i);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
            }
            this.eraseSecondScreen();
        }

    }

    private void eraseFirstScreen() {
        if (this.division.equalsIgnoreCase("v")) {
            for (int i = 1; i <= rows - 1; i++) {
                System.out.print(Constants.ANSI_ESC + i + ";" + halfCols + "f");
                System.out.print(Constants.ERASE_START_OF_LINE);
                try {
                    Thread.sleep(80);
                } catch (InterruptedException ex) {
                }
            }
        } else if (this.division.equalsIgnoreCase("o")) {
            for (int i = 1; i <= halfRows; i++) {
                System.out.print(Constants.ANSI_ESC + i + ";" + cols + "f");
                System.out.print(Constants.ERASE_START_OF_LINE);
                try {
                    Thread.sleep(80);
                } catch (InterruptedException ex) {
                }
            }

        }
    }

    private void eraseSecondScreen() {
        if (this.division.equalsIgnoreCase("v")) {
            for (int i = 1; i <= rows - 1; i++) {
                System.out.print(Constants.ANSI_ESC + i + ";" + (halfCols + 2) + "f");
                System.out.print(Constants.ERASE_END_OF_LINE);
                try {
                    Thread.sleep(80);
                } catch (InterruptedException ex) {
                }
            }
        } else if (this.division.equalsIgnoreCase("o")) {
            for (int i = halfRows + 2; i <= (halfRows * 2 + 1); i++) {
                System.out.print(Constants.ANSI_ESC + i + ";" + cols + "f");
                System.out.print(Constants.ERASE_END_OF_LINE);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                }
            }

        }

    }

    private void printMenu() {
        System.out.println("");
        System.out.println("-------------------------------------------------");
        System.out.println("\t\tMAIN MENU");
        System.out.println("-------------------------------------------------");
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
        System.out.println("-------------------------------------------------");
    }

    private void set(int x, int y) {
        System.out.print(Constants.ANSI_ESC + x + ";" + y + "f");
    }

    private void show(int x, int y, int color, String text) {
        set(x, y);
        System.out.print(Constants.ANSI_ESC + color + "m");
        System.out.print(text);
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
        }
    }
}