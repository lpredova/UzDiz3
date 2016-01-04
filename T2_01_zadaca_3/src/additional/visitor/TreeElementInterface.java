/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package additional.visitor;

/**
 *
 * @author lovro
 */
public interface TreeElementInterface {
       public void accept(TreeElementVisitor computerPartVisitor);

}
