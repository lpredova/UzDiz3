/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppMVC;

import java.util.ArrayList;

/**
 *
 * @author tonovosel
 */
public class MenuModel {

    private ArrayList<String> menuOptions;

    private String header = "########################## MENU ############################";
    private String o1 = "1 - ispis ukupnog broja direktorija i datoteka u strukturi";
    private String o2 = "2 - ispis sadržaja strukture direktorija i datoteka uz prikaz naziva, vremena (formatiranog u HR obliku), veličina (u formatu 999.999.999 B)";
    private String o3 = "3 - izvršavanje dretve";
    private String o4 = "4 - prekid izvršavanja dretve";
    private String o5 = "5 - ispis informacija o svim spremljenim stanjima (redni broj i vrijeme spremljenja)";
    private String o6 = "6 n - postavljanje stanja strukture na promjenu s rednim brojem n čime ono postaje novo trenutno stanje strukture";
    private String o7 = "7 m - uspoređivanje trenutnog stanja strukture i promjene s rednim brojem m";
    private String o8 = "8 - ponovno učitavanje strukture uz poništavanje svih spremljenih stanja strukture (prikaz kao i kod inicijalnog učitavanja strukture)";
    private String o9 = "9 - dodana vlastita funkcionalnost";
    private String o10 = "Q - prekid rada programa";

    public MenuModel() {
        menuOptions = new ArrayList<>();
    }

    public void saveToList() {
        menuOptions.add(header);
        menuOptions.add(o1);
        menuOptions.add(o2);
        menuOptions.add(o3);
        menuOptions.add(o4);
        menuOptions.add(o5);
        menuOptions.add(o6);
        menuOptions.add(o7);
        menuOptions.add(o8);
        menuOptions.add(o9);
        menuOptions.add(o10);

    }

    public ArrayList<String> getMenuOptions() {
        return menuOptions;
    }

}
