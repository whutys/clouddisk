package cn.clouddisk.utils.TestFunction;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloadUrl {
    @Test
    void test() {
        String url = "thunder://QUFmdHA6Ly95Z2R5ODp5Z2R5OEB5ZzQ1LmR5ZHl0dC5uZXQ6ODQyNy8lRTklOTglQjMlRTUlODUlODklRTclOTQlQjUlRTUlQkQlQjF3d3cueWdkeTguY29tLiVFOCVCRiU4NyVFNiU5OCVBNSVFNSVBNCVBOS5IRC4xMDgwcC4lRTUlOUIlQkQlRTglQUYlQUQlRTQlQjglQUQlRTglOEIlQjElRTUlOEYlOEMlRTUlQUQlOTcubXA0Wlo=";
        String decoder = Thunder.decoder(url);
        System.out.println(decoder);
//        download(decoder);
//        ftp://ygdy8:ygdy8@yg45.dydytt.net:8427/%E9%98%B3%E5%85%89%E7%94%B5%E5%BD%B1www.ygdy8.com.%E8%BF%87%E6%98%A5%E5%A4%A9.HD.1080p.%E5%9B%BD%E8%AF%AD%E4%B8%AD%E8%8B%B1%E5%8F%8C%E5%AD%97.mp4
        Pattern pattern = Pattern.compile("ftp://([\\w\\d]+):([\\w\\d]+)@([\\w\\d.]+):(\\d+)/(.+)");
//        Pattern pattern = Pattern.compile("ftp://(((?!:).)*):(((?!:).)*)@(((?!:).)*):(\\d+)/(.+)");
        Matcher matcher = pattern.matcher(decoder);
        String address;
        int port;
        String username;
        String password;
        while (matcher.find()) {
            username = matcher.group(1);
            password = matcher.group(2);
            address = matcher.group(3);
            port = Integer.valueOf(matcher.group(4));
            try {
                System.out.println(URLDecoder.decode(matcher.group(5), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//            FTPUtil ftpUtil = new FTPUtil();
//            ftpUtil.login(address, port, username, password);
//            try {
//                FTPFile[] ftpFiles = ftpUtil.getFtpClient().listFiles();
//                System.out.println(ftpFiles[0].getName());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            download(url);
        }
    }

    public static void download(String url) {
//        Connection connect = Jsoup.connect(url);
        try {
//            Runtime.getRuntime().exec("D:\\Thunder\\Program\\Thunder.exe");
            Runtime.getRuntime().exec("vbs");
//            Connection.Response response = connect.execute();
//            String filename = response.header("Content-Disposition").substring(21);
//            System.out.println(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static class Thunder {
        public static String encoder(String resource) {
            return "Thunder://" + getBASE64(("AA" + resource + "ZZ"));
        }

        public static String decoder(String resource) {
            String fromBASE64 = getFromBASE64(resource.substring(10));
            return fromBASE64.substring(2, fromBASE64.length() - 2);
        }
    }

    public static class Flashget {

        public static String encoder(String resource) {
            return "Flashget://" + getBASE64(("[Flashget]" + resource + "[Flashget]")) + "&something";
        }

        public static String decoder(String resource) {
            String fromBASE64 = getFromBASE64(resource.substring(11));
            return fromBASE64.substring(2, fromBASE64.length() - 2);
        }
    }

    public static class QQDownLoad {

        public static String encoder(String resource) {
            return "qqdl://" + getBASE64(resource);
        }

        public static String decoder(String resource) {
            return getFromBASE64(resource.substring(7));
        }
    }

    static String getBASE64(String s) {
        if (s == null)
            return null;
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(s.getBytes());
    }

    //BASE64 解码
    static String getFromBASE64(String s) {
        if (s == null)
            return null;
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            byte[] b = decoder.decode(s);
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }
}
