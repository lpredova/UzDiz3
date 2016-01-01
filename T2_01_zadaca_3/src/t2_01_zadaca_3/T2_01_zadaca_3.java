/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t2_01_zadaca_3;

import FileIterator.InitialStructure.FileRepository;
import FileIterator.InitialStructure.Iterator;
import argumentValidation.ArgumentValidator;
import mvc.Controller;
import mvc.Model;
import mvc.View;

/**
 *
 * @author Steyskal
 */
public class T2_01_zadaca_3 {

    public static FileRepository fileTree;
    public static String rootDirectory;

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
        int colNum = Integer.parseInt(args[1]);
        String screenDivision = args[2];
        rootDirectory = args[3];
        int seconds = Integer.parseInt(args[4]);
        
        View v = new View(rowNum, colNum, screenDivision);
        Model m = new Model();
        Controller c = new Controller(v, m);
        c.showScreen();
        c.processOption();

    }

}
