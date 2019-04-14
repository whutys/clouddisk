package cn.clouddisk.utils;

public class MyUtils {
    public static String getFileType(String fileName){
        return fileName.substring(fileName.lastIndexOf('.'));
    }
}
