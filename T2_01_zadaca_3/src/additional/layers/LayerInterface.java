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
public interface LayerInterface {
    public void action(); 
    public String pull();
    public void push(String info);
}
