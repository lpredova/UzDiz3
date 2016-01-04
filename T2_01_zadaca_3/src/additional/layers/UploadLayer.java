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
public class UploadLayer implements LayerInterface{

    String info;
    
    @Override
    public void action() {
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
