/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t2_01_zadaca_3;

import FileIterator.InitialStructure.FileRepository;
import argumentValidation.ArgumentValidator;

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

    }

}
