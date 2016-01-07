/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package additional;

import CompositeIterator.FileTreeIterator;
import FileIterator.InitialStructure.FileRepository;
import FileStructureComposite.AppFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author lovro
 */
public class FileInfo {
    
    public static List<String> fileTypes = new ArrayList<>();
    public static List<AppFile> extensionFiles = new ArrayList<>();

    public static long totalFileSize;
    public static int elementCount;
    
    /**
     * Method that gets files extensions and then prints elements 
     * with corresponding extensions
     */
    public void printFileInfo() {
        
        FileTreeIterator ft = new FileTreeIterator();
        
        /**
         * Getting all extensions and then sorting them
         */
        ft.getFileExtensions(FileRepository.directoryTree.get(0));
        Collections.sort(fileTypes,String.CASE_INSENSITIVE_ORDER);

        
        for(String extension:fileTypes){
            totalFileSize = 0;
            elementCount  = 0;
            extensionFiles.clear();
            
            ft.compareExtensions(FileRepository.directoryTree.get(0),extension);
            
            System.out.println(
                    "\n\n"+
                    "------------------------------------\n"+
                    "Extension: " + extension +  "\n" + 
                    "Total files: " + elementCount + "\n" +
                    "Total size: " + utils.FileHelper.formatSize(totalFileSize) + "\n" +
                    "\n");
         
            for(AppFile file:extensionFiles){
                file.print();
            }
        }
    }  
}