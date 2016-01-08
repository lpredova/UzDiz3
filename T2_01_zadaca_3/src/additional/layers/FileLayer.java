/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package additional.layers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.View;

/**
 *
 * @author lovro
 */
public class FileLayer implements LayerInterface {

    String info;
    private View view;
    public FileLayer(View view) {
        this.view = view;
    }

    @Override
    public void action() {

        try {
            PrintWriter out;
            String filename = (System.currentTimeMillis() / 1000L) + ".txt";

            out = new PrintWriter(filename);
            out.println(info);
            out.close();
            view.updateFirstScreenByString("File saved", "37");
            this.info = filename;
            view.updateFirstScreenByString("File created", "37");

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
