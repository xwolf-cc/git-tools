package cc.xwolf.url;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestJsoup {
    public static void main(String[] args)   {
        String url ="https://gitlab.sinochem.cloud/api/v3/groups?page=1&private_token=m5gx6F6YHuUsSnmXMMWW";
        Document doc = null;
        try {
            Map header= new HashMap();
            header.put("Accept","*/*");
            header.put("Accept-Encoding","gzip, deflate, br");
            header.put("Accept-Language","zh-CN");
            header.put("Cache-Control","no-cache");
            header.put("DNT","1");
            header.put("Host","gitlab.sinochem.cloud");
            header.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134");

            doc = Jsoup.connect(url).ignoreContentType(true).timeout(3000).headers(header).get();
            System.out.println(doc.html());
        } catch (IOException e) {
             System.out.println(e.getMessage());
        }

    }

}
