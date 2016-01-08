/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package additional.layers;

import mvc.View;

/**
 *
 * @author lovro
 */
public class LayerStructure {

    String info;
    LayerInterface layer;
    private View view;
    
     public LayerStructure(View view) {
         layer = new ReportLayer();
         this.view = view;
    }
     
     
    public void doActions(){
    
        //Getting report
        this.layer.action();
        info = this.layer.pull();
        view.updateFirstScreenByString(info, "37");
        
        //Creating file
        this.layer = new FileLayer(view);
        this.layer.push(info);
        this.layer.action();
        info = this.layer.pull();
        view.updateFirstScreenByString(info, "37");

        //Zipping file
        this.layer = new ZipLayer();
        this.layer.push(info);
        this.layer.action();
        info = this.layer.pull();
        
    }  
}
