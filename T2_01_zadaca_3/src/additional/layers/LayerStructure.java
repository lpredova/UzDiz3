/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package additional.layers;

/**
 *
 * @author lovro
 */
public class LayerStructure {

    String info;
    LayerInterface layer;
    
     public LayerStructure() {
         layer = new ReportLayer();
    }
     
     
    public void doActions(){
    
        //Getting report
        this.layer.action();
        info = this.layer.pull();
        
        
        //Creating file
        this.layer = new FileLayer();
        this.layer.push(info);
        this.layer.action();
        info = this.layer.pull();

        //Zipping file
        this.layer = new ZipLayer();
        this.layer.push(info);
        this.layer.action();
        info = this.layer.pull();
        
        //uploading file to dropbox
        
    }
    
    
    
}
