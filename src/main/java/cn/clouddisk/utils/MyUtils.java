package cn.clouddisk.utils;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtils {
    private static HttpURLConnection conn = null;

    public static void initConn(String url) {
        try {
            URL realUrl = new URL(url);
            conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setReadTimeout(8000);
            conn.setConnectTimeout(8000);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestProperty("User-Agent", "User-Agent,Mozilla/5.0 (Windows NT 6.1; rv,2.0.1) Gecko/20100101 Firefox/4.0.1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getFileType(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    public static String getTitle(String url) {
        if (url.replaceFirst("https?://","").startsWith("m.")) {
            url = url.replaceFirst("m.", "");
        }
        initConn(url);
        try {
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
//                    Pattern pattern = Pattern.compile("<title>.*</title>");
//                    Matcher matcher = pattern.matcher(line);
//                    matcher.find();
//                    matcher.group();
//                    if (line.startsWith("<title>")) {
                    if (Pattern.matches("<title>.+</title>", line)) {
                        return line.substring(7, line.lastIndexOf('<'));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void test() {
        String encode = null;
        try {
            encode = "https://v.qq.com/x/search/?q=" + URLEncoder.encode("闪电侠第四季", "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(MyUtils.crawlTencentVideo(encode));
    }

    public static Map<String, String> crawlTencentVideo(String url) {
        if (url.matches("(https?://)m\\./w*")) {
            url = url.replaceFirst("m.", "");
        }
        Map<String, String> map = new LinkedHashMap<>();
        initConn(url);
        try {
            int code = conn.getResponseCode();
            System.out.println(code);
            if (code == 301 || code == 302) {
                url = conn.getHeaderField("Location");
                return crawlTencentVideo(url);
            }
            if (code == 200) {
                InputStream is = conn.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    stringBuilder.append(line);
                }
//                (<div class="item">[^<>]+<a href="([^<>\s]+)"[^<>]*>(\d*)</a>[^<>]+</div>[^<>]+)+
                Pattern pattern = Pattern.compile("(<div class=\"item\">(?:[^<>]*)<a href=\"([^<>\\s]+)\"[^<>]*>(\\d*)</a>[^<>]*((<[^<>]*>){0}|(<[^<>]*>){3})[^<>]*</div>[^<>]+)+");
                Matcher matcher = pattern.matcher(stringBuilder);
                while (matcher.find()) {
                    String group = matcher.group();
//                    <span class=\"mark_v\"><img src=\"//i.gtimg.cn/qqlive/images/mark/mark_14.png\" alt=\"会员\" srcset=\"//i.gtimg.cn/qqlive/images/mark/mark_14@2x.png 2x\"/></span>
//                    <div class="item">[^<>]*<a href="([^<>\s]+)"[^<>]*>(\d*)</a>[^<>]*</div>[^<>]+
//                    <div class="item">(?:[^<>]*)<a href="([^<>\s]+)"[^<>]*>(\d*)</a>[^<>]*((<[^<>]*>){0}|(<[^<>]*>){3})[^<>]*</div>
//                    <div class="item">(?:[^<>]*)<a href="([^<>\s]+)"\\1>(\d*)</a>[^<>]*((<[^<>]*>){0}|(<[^<>]*>){3})[^<>]*</div>
                    Pattern pattern2 = Pattern.compile("<div class=\"item\">(?:[^<>]*)<a href=\"([^<>\\s]+)\"[^<>]*>(\\d*)</a>[^<>]*((<[^<>]*>){0}|(<[^<>]*>){3})[^<>]*</div>[^<>]+");
                    Matcher matcher2 = pattern2.matcher(group);
                    while (matcher2.find())
                        map.put(matcher2.group(2), matcher2.group(1));
                    return map;
                }
                map.put("未找到","#");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
