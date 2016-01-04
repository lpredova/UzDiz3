/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package additional.layers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author lovro
 */
public class ZipLayer implements LayerInterface {

    String info;

    /**
     * Method that zips text file contents
     * https://stackoverflow.com/questions/1091788/how-to-create-a-zip-file-in-java
     */
    @Override
    public void action() {

        ZipOutputStream out = null;

        try {
                        System.out.println("INFO : " + info);

            
            String fileContents = Helpers.FileHelper.readFile(info);
            
            
            
            System.out.println(fileContents);
            
            final File f = new File(info.split("\\.")[0] + ".zip");
            
            out = new ZipOutputStream(new FileOutputStream(f));
            ZipEntry e = new ZipEntry(info);
            out.putNextEntry(e);
            byte[] data =fileContents.getBytes();
            
            
            out.write(data, 0, data.length);
            out.closeEntry();
            out.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ZipLayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ZipLayer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(ZipLayer.class.getName()).log(Level.SEVERE, null, ex);
            }
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
