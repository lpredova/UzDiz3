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

    LayerInterface layer;
    
     public LayerStructure() {
         layer = new ReportLayer();
    }
     
     
    public void doAction(){
    
    
        this.layer.action();
        
        
    }
    
    
    
}
