package cn.clouddisk.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyUtils {
    public static String getFileType(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    public static String getTitle(String url) {
        HttpURLConnection conn = null;
        if (url.substring(url.indexOf('/') + 2).startsWith("m.")) {
            url = url.replaceFirst("m.", "");
        }
        try {
            URL realUrl = new URL(url);
            conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setReadTimeout(8000);
            conn.setConnectTimeout(8000);
            conn.setInstanceFollowRedirects(false);
//            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");
            conn.setRequestProperty("User-Agent", "User-Agent,Mozilla/5.0 (Windows NT 6.1; rv,2.0.1) Gecko/20100101 Firefox/4.0.1");
//            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
            int code = conn.getResponseCode();
            System.out.println(code);
            if (code == 301 || code == 302) {
                url = conn.getHeaderField("Location");
//                System.out.println(url);
                return getTitle(url);
            }
            if (code == 200) {
                InputStream is = conn.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line = "";
                while ((line = in.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("<title>")) {
                        return line.substring(7, line.lastIndexOf('<'));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
