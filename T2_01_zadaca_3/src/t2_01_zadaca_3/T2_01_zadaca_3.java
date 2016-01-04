/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t2_01_zadaca_3;

import FileIterator.InitialStructure.FileRepository;
import additional.FileInfo;
import FileStructureComposite.AppFile;

import argumentValidation.ArgumentValidator;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public static AppFile root;
    public static FileRepository filesRepository = new FileRepository();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

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

        //Reading initial file structure - creational iterator, has only one iteration and creates dir 
        filesRepository.getIterator(args[3]);

   

        root = filesRepository.directoryTree.get(0);
        

        View v = new View(rowNum, colNum, screenDivision);
        Model m = new Model();
        Controller c = new Controller(v, m, seconds);
        c.showScreen();
        try {
            c.showDir(0, new File(T2_01_zadaca_3.rootDirectory));
        } catch (IOException ex) {
            Logger.getLogger(T2_01_zadaca_3.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.setForEntry();
        c.processOption();

    }

}
