package cn.clouddisk.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 爬虫工具
 */
public class CrawlUtils {
    private static String cssQueryS = "{\"qq\":\".result_episode_list\",\"iqiyi\":\".result_album\"}";

    private static JSONObject json = JSON.parseObject(cssQueryS);

    public static String getFileType(String fileName) {
        int beginIndex = fileName.lastIndexOf('.');
        if (beginIndex < 0) return "";
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
//        url = "https://so.iqiyi.com/so/q_时间";
//        String text = "【安全防护】 天地伟业技术有限公司";
//        System.out.println(Integer.parseInt("2345"));
        url="()";
        StringBuilder sb = new StringBuilder(url);sb.insert(2,"()");
        System.out.println(sb.toString());
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

    @Test
    void a() throws Exception {
        String urlink = "http://scc.whut.edu.cn/infoView.shtml?iid=6193";
//        getInfo(urlink);
        byte b = 1;
        b += 1;
        String s = "";

        switch (s) {
            case "a":
                b = (byte) (b + 1);
                break;
            case "":
                break;
        }
//        URL url = new URL(urlink);
//        URLConnection urlConnection = url.openConnection();
//        InputStream inputStream = urlConnection.getInputStream();
//        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//        StringBuilder sb = new StringBuilder();
//        String buff = null;
//        while ((buff = br.readLine()) != null) sb.append(buff);
//        System.out.println(sb.toString());
    }

    @Test
    public void WorkInfo() {
        boolean flag = true;
        int i = 0;
        while (flag) {
//            String url = "http://scc.whut.edu.cn/meetList.shtml?date=&searchForm=&pageNow="+(++i);
            String url = "http://scc.whut.edu.cn/infoList.shtml?tid=1001&searchForm=&pageNow=" + (++i);
//        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);
//        webClient.getOptions().setJavaScriptEnabled(true);
//        webClient.getOptions().setCssEnabled(false);
//        webClient.getOptions().setThrowExceptionOnScriptError(false);
//        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
//        webClient.getOptions().setTimeout(5000);
//        webClient.getOptions().setActiveXNative(true);
//        webClient.waitForBackgroundJavaScript(3000);
//        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//        webClient.getOptions().setDoNotTrackEnabled(true);
//        webClient.openWindow(new URL(url),"aa");
            Connection connect = Jsoup.connect(url);

//        System.out.println("a" + connect.response().statusCode());
            try {
//            HtmlPage page = webClient.getPage(url);
//            Map<String, String> map = new HashMap<>();
//            map.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0");
//            connect.headers(map);
//            connect.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//            connect.header("Accept-Encoding", "gzip, deflate");
//            connect.header("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
//            connect.header("Connection", "keep-alive");
//            connect.header("Host", url);
//            connect.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0");
//            connect.maxBodySize(10000);
//            Document document = connect.get();
                Document document = connect.timeout(5000).get();
//            System.out.println(document.html());
                Elements col_con = document.getElementsByClass("col_con_list");
                Elements li = col_con.select("li");
                if (li.size() == 0 || i > 30) {
                    flag = false;
                    break;
                }
                for (Element e : li) {
                    Elements a = e.select("a");
//                    String text = e.select("a").text().trim();
////                System.out.println(text);
////                boolean bol1=text.matches("^(【】)\\s\\S+");
////                boolean b = text.startsWith("【");
//                    if (text.startsWith("【信息产业】") || text.matches("【互联网】")) {
//                        System.out.println(i);
                    getInfo("http://scc.whut.edu.cn/" + a.attr("href"));
//                    }
                }
            } catch (IOException e) {
                flag = false;
                e.printStackTrace();
            }
        }
    }

    @Test
    public void getInfo(String url) {
        Connection connect = Jsoup.connect(url);
        try {
            Document document = connect.get();
            Elements main = document.body().getElementsByClass("col_con");
            String text = main.text();
            if ((text.contains("JAVA") || text.contains("Java")) && text.contains("武汉"))
                System.out.println(document.body().getElementsByClass("nr-tit").text() + " " + url);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
