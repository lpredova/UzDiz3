/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import FileIterator.InitialStructure.FileRepository;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
    
    public static String getParentNameFromPath(File file) {
        return file.getAbsoluteFile().getParentFile().getName();
    }

    /**
     * Method that returns element size
     *
     * @param path
     * @return
     */
    public static String getFileSizeFormattedFromPath(String path) {
        File f = new File(path);
        return utils.FileHelper.getFileSizeFormat(f);
    }

    /**
     * Method for getting file type, returns directory if file is directory or
     * extension otherwise
     *
     * @param path
     * @return
     */
    public static String getFileTypeFromPath(String path) {
        if (utils.FileHelper.isDirectory(path)) {
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
     * Method for getting file(directory) size from path
     *
     * @param path
     * @return
     */
    public static long getFileRawSizeFromPath(String path) {
        File file = new File(path);
        return file.length();
    }

    /**
     * Method for getting last updated time of the file
     *
     * @param file
     * @return
     */
    public static String getFileUpdatedAtTimeFromPath(String file) {

        File filee = new File(file);
        return FileHelper.getUpdatedTime(filee);
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
     * Method for getting absolute address from file
     * @param path
     * @return 
     */
    public static String getAbsoluteAddressFromPath(String path) {
        File file = new File(path);
        return file.getAbsolutePath();
    }

    /**
     * Method that returns element size
     *
     * @param file
     * @return
     */
    public static String getFileFormattedSize(File file) {
        return utils.FileHelper.getFileSizeFormat(file);
    }

    /**
     * Method that returns file size in bytes long format
     *
     * @param file
     * @return
     */
    public static long getFileRawSize(File file) {
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
            return "." + name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "unknown file format";
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
     *
     * @param file
     * @return
     */
    public static String getFileUpdatedAtTime(File file) {
        return FileHelper.getUpdatedTime(file);
    }

    private static String getCreatedTime(Path filePath) {
        String fileCreatedAt = "";
        try {
            SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            BasicFileAttributes fileAttributes = Files.readAttributes(filePath, BasicFileAttributes.class);
            long creationTime = fileAttributes.creationTime().toMillis();
            fileCreatedAt = f.format(creationTime);
        } catch (IOException ex) {
            Logger.getLogger(FileRepository.class.getName()).log(Level.SEVERE, null, ex);
            return "Unable to fetch time";
        }
        return fileCreatedAt;
    }

    public static String getUpdatedTime(File file) {
        String fileUpdatedAt = "";
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        fileUpdatedAt = f.format(file.lastModified());
        return fileUpdatedAt;
    }

    /**
     * Method that gets initial file size on creation tree
     *
     * @param file
     * @return
     */
    public static String getFileSizeFormat(File file) {
        return formatSize(file.length());
    }

    /**
     * Method that converts raw size in long and formats it to string
     *
     * @param size
     * @return
     */
    public static String formatSize(long size) {
        String pattern = "###,###.###";
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String formattedSize = myFormatter.format(size).replace(',', '.') + " B";
        return formattedSize;
    }
    
    /**
     * Method for getting file sizes
     * @param folder
     * @return 
     */
    public static long getFolderSize(File folder) {
        long size = 0;
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                size += file.length();
            } else {
                size += getFolderSize(file);
            }
        }

        return size;
    }
    
    /**
      * Method that returns hash value of string
      * https://stackoverflow.com/questions/415953/howcanigenerateanmd5hash/421696#421696
      *
      * @param md5
      * @return
      **/
     public static String MD5(String md5) {
         try {
             java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
             byte[] array = md.digest(md5.getBytes());
             StringBuilder sb = new StringBuilder();
             for (int i = 0; i < array.length; ++i) {
                 sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
             }
             return sb.toString();
         } catch (java.security.NoSuchAlgorithmException e) {
         }
         return null;
     }
 
     /**
      * Method that returns entire text file contents as string
      * @param fileName
      * @return
      * @throws FileNotFoundException
      * @throws IOException 
      */
     public static String readFile(String fileName) throws FileNotFoundException, IOException {
 
         try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
             StringBuilder sb = new StringBuilder();
             String line = br.readLine();
 
             while (line != null) {
                 sb.append(line);
                 sb.append(System.lineSeparator());
                 line = br.readLine();
             }
             return sb.toString();
         }
 
     }
}
