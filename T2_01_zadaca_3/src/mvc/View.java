/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import java.util.ArrayList;

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
            show(i, halfCols + 1, 33, "*");
        }
        for (int j = 1; j <= cols + 1; j++) {
            show(rows, j, 33, "*");
        }
        System.out.print(ANSI_ESC + (rows + 1) + ";1f");
        System.out.print("Choose option: ");
    }

    private void ODivision() {
        for (int i = 1; i <= cols; i++) {
            show(halfRows + 1, i, 33, "*");
        }
        if (rows % 2 == 0) {
            for (int j = 1; j <= cols; j++) {
                show(rows, j, 33, "*");
            }
            System.out.print(ANSI_ESC + (rows + 1) + ";1f");
            System.out.print("Choose option: ");
        } else {
            for (int j = 1; j <= cols; j++) {
                show(rows + 1, j, 33, "*");
            }
            System.out.print(ANSI_ESC + (rows + 2) + ";1f");
            System.out.print("Choose option: ");
        }

    }

    public void updateFirstScreen(ArrayList<String> data) {
        this.eraseFirstScreen();
        int counter = 0;
        if (this.division.equalsIgnoreCase("v")) {
            for (int i = 1; i <= rows - 1; i++) {
                System.out.print(ANSI_ESC + i + ";1f");
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
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
            }
            //this.eraseFirstScreen();
        } else if (this.division.equalsIgnoreCase("o")) {
            for (int i = 1; i <= halfRows; i++) {
                System.out.print(ANSI_ESC + i + ";1f");
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

    public void updateFirstScreen(String text) {

        if (this.division.equalsIgnoreCase("v")) {
            for (; currentRow <= rows - 1; currentRow++) {
                System.out.print(ANSI_ESC + currentRow + ";1f");

                if (text.length() > halfCols) {
                    System.out.print(text.substring(0, halfCols));
                    for (int k = halfCols; k < text.length(); k = k + halfCols) {
                        if (halfCols < text.substring(k).length()) {
                            System.out.print("\n" + text.substring(k, k + halfCols));
                        } else {
                            System.out.print("\n" + text.substring(k));
                        }

                        currentRow++;
                    }

                } else {
                    System.out.print(text);
                }

                currentRow = rows;

                if (currentRow == rows - 1) {
                    this.eraseFirstScreen();
                    currentRow = 1;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
            }
            //this.eraseFirstScreen();
        }

    }

    public void updateSecondScreen() {
        if (this.division.equalsIgnoreCase("v")) {
            for (int i = 1; i <= rows - 1; i++) {
                System.out.print(ANSI_ESC + i + ";" + (halfCols + 2) + "f");
                System.out.print("Ispis: " + i);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
            }
            this.eraseSecondScreen();
        } else if (this.division.equalsIgnoreCase("o")) {
            for (int i = halfRows + 2; i <= (halfRows * 2 + 1); i++) {
                System.out.print(ANSI_ESC + i + ";1f");
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
                System.out.print(ANSI_ESC + i + ";" + halfCols + "f");
                System.out.print(ANSI_ESC + "1K");
                try {
                    Thread.sleep(80);
                } catch (InterruptedException ex) {
                }
            }
        } else if (this.division.equalsIgnoreCase("o")) {
            for (int i = 1; i <= halfRows; i++) {
                System.out.print(ANSI_ESC + i + ";" + cols + "f");
                System.out.print(ANSI_ESC + "1K");
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
                System.out.print(ANSI_ESC + i + ";" + (halfCols + 2) + "f");
                System.out.print(ANSI_ESC + "K");
                try {
                    Thread.sleep(80);
                } catch (InterruptedException ex) {
                }
            }
        } else if (this.division.equalsIgnoreCase("o")) {
            for (int i = halfRows + 2; i <= (halfRows * 2 + 1); i++) {
                System.out.print(ANSI_ESC + i + ";" + cols + "f");
                System.out.print(ANSI_ESC + "1K");
                try {
                    Thread.sleep(80);
                } catch (InterruptedException ex) {
                }
            }

        }

    }

    private void set(int x, int y) {
        System.out.print(ANSI_ESC + x + ";" + y + "f");
    }

    private void show(int x, int y, int color, String text) {
        set(x, y);
        System.out.print(ANSI_ESC + color + "m");
        System.out.print(text);
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
        }
    }
}
