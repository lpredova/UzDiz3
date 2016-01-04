/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package additional.visitor;

import FileStructureComposite.AppFile;

/**
 *
 * @author lovro
 */
public class HashVisitor implements TreeElementVisitor{

    @Override
    public void visit(AppFile file) {
        String fileHash = file.getName()+file.getType()+file.getCreatedAt()+file.getUpdatedAt()+file.getFormattedSize();
        String hash = helpers.FileHelper.MD5(fileHash);
        
        System.out.println(hash);
    }
    
}
