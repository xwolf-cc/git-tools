package cc.xwolf.url;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.io.Files;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GitMain1 {

    public static void main(String[] args) throws IOException {
        String basePath = "D:\\base-workspace\\workspaces";
        Files.createParentDirs(new File(basePath));
        //读取分组数据,最多处理50页
        for (int i = 1; i < 50; i++) {
            JSONArray itemList = getGroup(i);
            List<JSONObject> items = itemList.toJavaList(JSONObject.class);
            items.forEach(item -> {
                System.out.println(item.get("full_name") + "==" + item.get("id") + "==" + item.get("full_path"));
//                读取分组内所有项目信息,读取9页.
                for (int j = 1; j < 10; j++) {
//                    System.out.println("抓取"+item.get("full_name")+"的项目: 保存到目录"+item.get("full_path"));
                    List<JSONObject> projects = getProjects((Integer) item.get("id"), j).toJavaList(JSONObject.class);
                    projects.forEach(it -> {
                        System.out.println(it);
                        System.out.println("开始clone " + it.get("http_url_to_repo") + "到" + basePath + "\\" + it.get("path_with_namespace"));
                        try {
                            GitUtil.gitClone((String) it.get("http_url_to_repo"), basePath + "\\" + it.get("path_with_namespace"));

                        } catch (Exception e) {
                            System.err.println(" clone 失败" + it.get("http_url_to_repo") + "到" + basePath + "\\" + it.get("path_with_namespace"));
                        }
                    });
                    if (projects == null || projects.size() < 1) {
                        System.out.println("continue----");
                        continue;
                    }
                }
            });
        }


    }

    /**
     * 获取所有的项目
     *
     * @param groupId
     * @param pageNumm
     * @return
     */
    public static JSONArray getProjects(int groupId, int pageNumm) {
        String url = "http://code.bestranran.ren/api/v3/groups/" + groupId + "/projects?page=" + pageNumm + "&private_token=oaD3Uz2sTmSTd4wU7MUM";
        return httpUtil(url);
    }


    /**
     * 获取所有的群组
     *
     * @param pageNumm
     * @return
     * @throws IOException
     */
    public static JSONArray getGroup(int pageNumm) throws IOException {
        String url = "http://code.bestranran.ren/api/v3/groups?page=" + pageNumm + "&private_token=oaD3Uz2sTmSTd4wU7MUM";
        return httpUtil(url);
    }

    public static JSONArray httpUtil(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).ignoreContentType(true).get();
            String restring = doc.text();
            JSONArray jsonObjectList = JSONObject.parseArray(restring);
            return jsonObjectList;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new JSONArray();
    }
}