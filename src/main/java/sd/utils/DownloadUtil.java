package sd.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;

public class DownloadUtil {

    /**
     * 下载文件---返回下载后的文件存储路径
     *
     * @param url 文件地址
     * @param dir 存储目录
     * @param fileName 存储文件名
     * @return
     */
    public static void downloadFromUrl(String url, String dir, String fileName) {
        try {
            URL httpUrl = new URL(url);
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileUtils.copyURLToFile(httpUrl, new File(dir+fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
