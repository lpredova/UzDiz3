/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package additional.layers;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lovro
 */
public class FileLayer implements LayerInterface{

    String info;

    @Override
    public void action() {
                
        try {
            PrintWriter out;
            
            String filename = (System.currentTimeMillis() / 1000L) + ".txt";
            
                    System.out.println(filename);

            out = new PrintWriter(filename);
            out.println(info);
            out.close();
            System.out.println("File saved");
            this.info = filename;
            System.out.println("File created");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String pull() {
        return this.info;
    }

    @Override
    public void push(String info) {
        this.info = info; 
    }

   
}
