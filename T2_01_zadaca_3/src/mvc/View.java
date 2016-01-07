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

    public int currentRowScreenOne;
    public int currentRowScreenTwo;

    private int rows, cols, halfRows, halfCols, endOfScreenOne, endOfScreenTwo,
            length, beginOfScreenOne, beginOfScreenTwo, startColumnScreenOne,
            startColumnScreenTwo;
    private String division;

    public View(int rows, int cols, String division) {
        this.rows = rows;
        this.cols = cols;
        this.division = division;
    }

    /**
     * Method which calculates necessary variables for manipulating the screen
     */
    private void calculateScreen() {
        this.halfRows = (rows - 1) / 2;
        this.halfCols = (cols / 2);
        currentRowScreenOne = 1;
        this.beginOfScreenOne = 1;
        this.endOfScreenTwo = rows - 1;
        this.startColumnScreenOne = 1;
        if (division.equalsIgnoreCase("v")) {
            currentRowScreenTwo = 1;
            this.length = halfCols;
            this.endOfScreenOne = rows - 1;
            this.startColumnScreenTwo = halfCols + 2;
            this.beginOfScreenTwo = 1;
        } else if (division.equalsIgnoreCase("o")) {
            currentRowScreenTwo = halfRows + 2;
            this.length = cols;
            this.endOfScreenOne = halfRows;
            this.beginOfScreenTwo = halfRows + 2;
            this.startColumnScreenTwo = 1;
            if (rows % 2 != 0) {
                this.endOfScreenTwo++;
            }
        }
    }

    /**
     * Method which first calculates other variables then draws screen on
     * terminal according to type of screen division
     */
    public void printScreen() {
        calculateScreen();
        System.out.print(Constants.ERASE_SCREEN);
        if (this.division.equalsIgnoreCase("v")) {
            this.VDivision();
        } else if (this.division.equalsIgnoreCase("o")) {
            this.ODivision();
        }
        this.printInputScreen();
        //printMenu();
    }

    /**
     * Method which draws screen vertically on two equal parts
     */
    private void VDivision() {
        for (int i = 1; i <= rows - 1; i++) {
            show(i, halfCols + 1, 37, "*");
        }
        for (int j = 1; j <= cols + 1; j++) {
            show(rows, j, 37, "*");
        }
        this.setCursor(rows + 1, 1);
    }

    /**
     * Method which draws screen horizontally on two equal parts
     */
    private void ODivision() {
        for (int i = 1; i <= cols; i++) {
            show(halfRows + 1, i, 37, "*");
        }
        if (rows % 2 == 0) {
            for (int j = 1; j <= cols; j++) {
                show(rows, j, 37, "*");
            }
            this.setCursor(rows + 1, 1);
        } else {
            for (int j = 1; j <= cols; j++) {
                show(rows + 1, j, 37, "*");
            }
            this.setCursor(rows + 2, 1);
        }
    }

    /**
     * Method which draws screen for user inputs which always has size of one
     * row
     */
    private void printInputScreen() {
        System.out.print("Choose option: ");
        System.out.print(Constants.CURSOR_SAVE);
    }

    /**
     * Method which updates first screen by given list of strings
     *
     * @param data
     */
    public void updateFirstScreen(ArrayList<String> data) {
        //this.eraseFirstScreen();
        System.out.print(Constants.ANSI_ESC + "37m");
        for (int counter = 0; counter < data.size(); counter++) {
            System.out.print(Constants.ANSI_ESC + currentRowScreenOne + ";" + startColumnScreenOne + "f");
            if (data.get(counter).length() > length) {
                this.wrapString(data.get(counter), 1, currentRowScreenOne, startColumnScreenOne, endOfScreenOne, beginOfScreenOne);
            } else {
                System.out.print(data.get(counter));
            }
            this.checkIfEndOfScreen();
            this.setSleep(500);
        }
    }

    /**
     * Method which updates first screen by given string
     *
     * @param text
     * @param color
     */
    public void updateFirstScreenByString(String text, String color) {

        System.out.print(Constants.ANSI_ESC + currentRowScreenOne + ";" + startColumnScreenOne + "f");
        System.out.print(Constants.ANSI_ESC + color + "m");
        if (text.length() > length) {
            this.wrapString(text, 1, currentRowScreenOne, startColumnScreenOne, endOfScreenOne, beginOfScreenOne);
        } else {
            System.out.print(text);
        }
        this.checkIfEndOfScreen();
    }

    private void checkIfEndOfScreen() {
        if (currentRowScreenOne == endOfScreenOne) {
            this.eraseFirstScreen();
            currentRowScreenOne = beginOfScreenOne;
        } else {
            currentRowScreenOne++;
        }
    }

    /**
     * Method which wraps string into multi lines
     *
     * @param data
     */
    private void wrapString(String data, int screen, int currentRow, int startColumn, int endOfScreen, int beginOfScreen) {
        System.out.print(data.substring(0, length));
        for (int k = length; k < data.length(); k = k + length) {

            System.out.print(Constants.ANSI_ESC + "1B");
            System.out.print(Constants.ANSI_ESC + (length) + "D");

            if (currentRow == endOfScreen) {
                if (screen == 1) {
                    this.eraseFirstScreen();
                    currentRowScreenOne = beginOfScreen;
                    System.out.print(Constants.ANSI_ESC + currentRowScreenOne + ";" + startColumn + "f");
                } else {
                    this.eraseSecondScreen();
                    currentRowScreenTwo = beginOfScreen;
                    System.out.print(Constants.ANSI_ESC + currentRowScreenTwo + ";" + startColumn + "f");
                }

            }
            if (length < data.substring(k).length()) {
                System.out.print(data.substring(k, k + length));
            } else {
                System.out.print(data.substring(k));
            }
            currentRow++;
            if (screen == 1) {
                currentRowScreenOne++;
            } else {
                currentRowScreenTwo++;
            }

            this.setSleep(500);
        }
    }

    public void updateSecondScreenByString(String text, String color, boolean erase) {
        if (erase) {
            currentRowScreenTwo = beginOfScreenTwo;
        }
        System.out.print(Constants.ANSI_ESC + currentRowScreenTwo + ";" + startColumnScreenTwo + "f");
        System.out.print(Constants.ANSI_ESC + color + "m");

        if (text.length() > length) {
            this.wrapString(text, 2, currentRowScreenTwo, startColumnScreenTwo, endOfScreenTwo, beginOfScreenTwo);
        } else {
            System.out.print(text);
        }

        if (currentRowScreenTwo == endOfScreenTwo) {
            this.eraseSecondScreen();
            currentRowScreenTwo = beginOfScreenTwo;
        } else {
            currentRowScreenTwo++;
        }
        this.setSleep(500);

    }

    public void updateSecondScreen(ArrayList<String> data) {
        System.out.print(Constants.ANSI_ESC + "37m");
        for (int counter = 0; counter < data.size(); counter++) {
            System.out.print(Constants.ANSI_ESC + currentRowScreenTwo + ";" + startColumnScreenTwo + "f");
            if (data.get(counter).length() > length) {
                this.wrapString(data.get(counter), 2, currentRowScreenTwo, startColumnScreenTwo, endOfScreenTwo, beginOfScreenTwo);
            } else {
                System.out.print(data.get(counter));
            }
            if (currentRowScreenTwo == endOfScreenTwo) {
                this.eraseSecondScreen();
                currentRowScreenTwo = beginOfScreenTwo;
            } else {
                currentRowScreenTwo++;
            }
            this.setSleep(500);
        }
    }

    /**
     * Method for clearing first screen regardless of V or O division
     */
    private void eraseFirstScreen() {
        for (int i = beginOfScreenOne; i <= endOfScreenOne; i++) {
            this.setCursor(i, length);
            System.out.print(Constants.ERASE_START_OF_LINE);
            this.setSleep(20);
        }
    }

    /**
     * Method for clearing second screen regardless of V or O division
     */
    private void eraseSecondScreen() {

        for (int i = beginOfScreenTwo; i <= endOfScreenTwo; i++) {
            this.setCursor(i, startColumnScreenTwo);
            System.out.print(Constants.ERASE_END_OF_LINE);
            this.setSleep(20);
        }

    }

    public void restoreInput() {
        System.out.print(Constants.CURSOS_RESTORE);
        System.out.print(Constants.ERASE_END_OF_LINE);
        System.out.print(Constants.ANSI_ESC + "37m");
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

    /**
     * Method for setting cursor position
     */
    private void setCursor(int x, int y) {
        System.out.print(Constants.ANSI_ESC + x + ";" + y + "f");
    }

    /**
     * Method for showing text with certain color from certain cursor position
     */
    private void show(int x, int y, int color, String text) {
        setCursor(x, y);
        System.out.print(Constants.ANSI_ESC + color + "m");
        System.out.print(text);
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
        }
    }

    private void setSleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
        }
    }
}
