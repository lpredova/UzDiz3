/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package additional.layers;

import CompositeIterator.FileTreeIterator;
import FileIterator.InitialStructure.FileRepository;
import FileStructureComposite.AppFile;
import additional.visitor.HashVisitor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author lovro
 */
public class ReportLayer implements LayerInterface {

    String info;
    public static List<String> fileTypes = new ArrayList<>();
    public static List<AppFile> extensionFiles = new ArrayList<>();

    public static long totalFileSize;
    public static int elementCount;

    /**
     * Method that gets files extensions and then prints elements with
     * corresponding extensions
     */
    @Override
    public void action() {

        FileTreeIterator ft = new FileTreeIterator();
        String fileContents = "";
        String fileRow;

        /**
         * Calculating hash values of files with visitor
         *
         */
        HashVisitor hv = new HashVisitor();
        ft.calculateHash(FileRepository.directoryTree.get(0), hv);

        /**
         * Getting all extensions and then sorting them
         */
        ft.getFileExtensions(FileRepository.directoryTree.get(0));
        Collections.sort(fileTypes, String.CASE_INSENSITIVE_ORDER);
        
        System.out.println(fileTypes);
        

        for (String extension : fileTypes) {
            totalFileSize = 0;
            elementCount = 0;
            extensionFiles.clear();
            fileRow = "";

            ft.compareExtensions(FileRepository.directoryTree.get(0), extension);

            fileRow
                    += "\n\n"
                    + "------------------------------------\n"
                    + "Extension: " + extension + "\n"
                    + "Total files: " + elementCount + "\n"
                    + "Total size: " + Helpers.FileHelper.formatSize(totalFileSize) + "\n"
                    + "\n";

            for (AppFile file : extensionFiles) {
                fileRow += file.elementData();
            }

            fileContents += fileRow;
        }
        this.info = fileContents;
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
