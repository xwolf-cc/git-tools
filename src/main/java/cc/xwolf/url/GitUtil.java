package cc.xwolf.url;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;

public class GitUtil {
    public static void main(String[] args) {
        gitClone("http://10.144.128.29:90/portal/sinochem-config.git",
                "D:\\base-workspace\\workspaces");
    }

    public static void gitClone(String remoteUrl, String repoDirPath) {
        gitClone(remoteUrl,repoDirPath,"xuping","aaaaaaaa");
    }

    public static void gitClone(String remoteUrl, String repoDirPath,String username,String psw) {
        CloneCommand cc = Git.cloneRepository().setURI(remoteUrl);
        cc.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, psw));
        File file = new File(repoDirPath);
        file.mkdirs();
        try {
            cc.setDirectory(file).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

        System.out.println("下载完成......");
    }
}

