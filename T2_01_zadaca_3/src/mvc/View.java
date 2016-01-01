/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

/**
 *
 * @author Josip
 */
public class View {

    public static final String ANSI_ESC = "\033[";
    public static int currentRow = 1;
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
        System.out.print(ANSI_ESC + "2J");
        if (this.division.equalsIgnoreCase("v")) {
            this.VDivision();
        } else if (this.division.equalsIgnoreCase("o")) {
            this.ODivision();
        }
        //printMenu();
    }
    
    private void VDivision() {
        for (int i = 1; i <= rows - 1; i++) {
            prikazi(i, halfCols + 1, 33, "*");
        }
        for (int j = 1; j <= cols + 1; j++) {
            prikazi(rows, j, 33, "*");
        }
        System.out.print(ANSI_ESC + (rows + 1) + ";1f");
        System.out.print("Choose option: ");
    }

    private void ODivision() {
        for (int i = 1; i <= cols; i++) {
            prikazi(halfRows + 1, i, 33, "*");
        }
        if (rows % 2 == 0) {
            for (int j = 1; j <= cols; j++) {
                prikazi(rows, j, 33, "*");
            }
            System.out.print(ANSI_ESC + (rows + 1) + ";1f");
            System.out.print("Choose option: ");
        } else {
            for (int j = 1; j <= cols; j++) {
                prikazi(rows + 1, j, 33, "*");
            }
            System.out.print(ANSI_ESC + (rows + 2) + ";1f");
            System.out.print("Choose option: ");
        }

    }
    
    private void postavi(int x, int y) {
        System.out.print(ANSI_ESC + x + ";" + y + "f");
    }

    private void prikazi(int x, int y, int color, String text) {
        postavi(x, y);
        System.out.print(ANSI_ESC + color + "m");
        System.out.print(text);
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
        }
    }
}
