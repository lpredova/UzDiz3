/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import FileIterator.InitialStructure.FileRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lovro
 */
public class FileHelper {

    /**
     * Method that checks if file exists
     *
     * @param path
     * @return
     */
    public static boolean fileExists(String path) {
        File f = new File(path);
        return f.exists();

    }

    /**
     * Method that checks if tree element is directory
     *
     * @param path
     * @return
     */
    public static boolean isDirectory(String path) {
        System.out.println("PATH CHECK: " + path);
        
        File f = new File(path);
        return f.isDirectory();
    }

    /**
     * Method for getting file name from path
     *
     * @param path
     * @return
     */
    public static String getFileNameFromPath(String path) {
        File f = new File(path);
        return f.getName();
    }

    /**
     * Method that returns element size
     *
     * @param path
     * @return
     */
    public  static long getFileSizeFromPath(String path) {
        File f = new File(path);
        return f.length();
    }

    /**
     * Method for getting file type, returns directory if file is directory or
     * extension otherwise
     *
     * @param path
     * @return
     */
    public static String getFileTypeFromPath(String path) {
        if (Helpers.FileHelper.isDirectory(path)) {
            return "directory";
        }
        File f = new File(path);
        String name = f.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1) + " file";
        } catch (Exception e) {
            return "file";
        }
    }

    /**
     * Method for getting time file is created at
     *
     * @param path
     * @return
     */
    public static String getFileCreatedAtTimeFromPath(String path) {

        File file = new File(path);
        Path filePath = file.toPath();

        return FileHelper.getCreatedTime(filePath);
    }

    /**
     * Method for getting last updated time of the file
     *
     * @param path
     * @return
     */
    public static String getFileUpdatedAtTimeFromPath(String path) {

        File file = new File(path);
        Path filePath = file.toPath();
        
        return FileHelper.getUpdatedTime(filePath);
    }
    

    /**
     * Method for getting file name from file
     *
     * @param file
     * @return
     */
    public static String getFileName(File file) {
        return file.getName();
    }

    /**
     * Method that returns element size
     *
     * @param file
     * @return
     */
    public  static long getFileSize(File file) {
        return file.length();
    }

    /**
     * Method for getting file type, returns directory if file is directory or
     * extension otherwise
     *
     * @param file
     * @return
     */
    public static String getFileType(File file) {
 
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1) + " file";
        } catch (Exception e) {
            return "file";
        }
    }

    /**
     * Method for getting time file is created at
     *
     * @param file
     * @return
     */
    public static String getFileCreatedAtTime(File file) {

        Path filePath = file.toPath();
        return FileHelper.getCreatedTime(filePath);
       
    }

    /**
     * Method for getting last updated time of the file
     * @param file
     * @return
     */
    public static String getFileUpdatedAtTime(File file) {

        Path filePath = file.toPath();
        return FileHelper.getUpdatedTime(filePath);
    }
    
    private static String getCreatedTime(Path filePath){
         try {
            BasicFileAttributes fileAttributes = Files.readAttributes(filePath, BasicFileAttributes.class);

            long milliseconds = fileAttributes.creationTime().to(TimeUnit.MILLISECONDS);
            if ((milliseconds > Long.MIN_VALUE) && (milliseconds < Long.MAX_VALUE)) {
                Date creationDate = new Date(fileAttributes.creationTime().to(TimeUnit.MILLISECONDS));
                return creationDate.getDate() + "." + (creationDate.getMonth() + 1) + "." + (creationDate.getYear() + 1900);
            }

        } catch (IOException ex) {
            Logger.getLogger(FileRepository.class.getName()).log(Level.SEVERE, null, ex);
            return "Unable to fetch time";
        }
        return "1.1.2016";
    }
    
    
    private static String getUpdatedTime(Path filePath){
         try {
            BasicFileAttributes fileAttributes = Files.readAttributes(filePath, BasicFileAttributes.class);

            long milliseconds = fileAttributes.creationTime().to(TimeUnit.MILLISECONDS);
            if ((milliseconds > Long.MIN_VALUE) && (milliseconds < Long.MAX_VALUE)) {
                Date updatedDate = new Date(fileAttributes.lastModifiedTime().to(TimeUnit.MILLISECONDS));
                return updatedDate.getDate() + "." + (updatedDate.getMonth() + 1) + "." + (updatedDate.getYear() + 1900);
            }
        } catch (IOException ex) {
            Logger.getLogger(FileRepository.class.getName()).log(Level.SEVERE, null, ex);
            return "Unable to fetch time";
        }
        return "1.1.2016";
    }   
}
