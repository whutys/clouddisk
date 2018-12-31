package cn.clouddisk.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtils {
    private static String cssQueryS = "{\"qq\":\".result_episode_list\",\"iqiyi\":\".result_album\"}";

    private static JSONObject json = JSON.parseObject(cssQueryS);

    public static String getFileType(String fileName) {
        int beginIndex = fileName.lastIndexOf('.');
        if (beginIndex<0)return "";
        return fileName.substring(beginIndex);
    }

    public static String getTitle(String url) {
        if (url.replaceFirst("https?://", "").startsWith("m.")) {
            url = url.replaceFirst("m.", "");
        }
        try {
            Document document = Jsoup.connect(url).get();
            return document.title();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    void test() {
        String url;
//        url = "http://v.qq.com/x/cover/nphyo88qm4m6i6s.html?vid=v0028cwf3bv";
        url = "https://so.iqiyi.com/so/q_时间";
//        try {
//            url = "https://v.qq.com/x/search/?q=闪电侠第四季";
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        System.out.println(MyUtils.getTitle(url));
        System.out.println(MyUtils.crawlVideo(url));
    }

    public static Map<String, String> crawlVideo(String url) {
        Pattern pattern = Pattern.compile("https?://[^\\s/]*\\.([^\\s./]+)\\.com");
        Matcher matcher = pattern.matcher(url);
        String cssQuery = "";
        while (matcher.find()) {
            cssQuery = matcher.group(1);
        }
        if (url.matches("(https?://)m\\./w*")) {
            url = url.replaceFirst("m.", "");
        }
        Map<String, String> map = new LinkedHashMap<>();
        try {
            Connection connection = Jsoup.connect(url);
            Document document = connection.get();
//            String title = document.title();
//            map.put("title",title);
            Element element = document.selectFirst(json.getString(cssQuery));
            if (element != null) {
                Elements a_info = element.select("a");
                for (Element a : a_info) {
                    String href = a.attr("href");
                    String text = a.text();
                    map.put(text, href);
                }
                return map;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("无", "#");
        return map;
    }

}
