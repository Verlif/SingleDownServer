package sd.controller.picture;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sd.config.ext.FilePathConfig;
import sd.controller.file.FileServer;
import sd.filter.RequestFilter;
import sd.manager.ConfigFileManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("picture")
public class PictureController {

    /**
     * 缩略图目标的判定大小
     */
    private static final int MIN_SIZE = 102400;

    private final FilePathConfig filePathConfig;

    @Resource
    private FileServer fileServer;

    public PictureController() {
        filePathConfig = ConfigFileManager.getInstance().getConfig(FilePathConfig.class);
    }

    /**
     * 获取图片缩略图
     *
     * @param path     目标文件路径
     * @param fileName 目标文件名
     * @param response 响应数据
     */
    @RequestMapping("/thumbnail")
    public void getThumbnail(
            @RequestAttribute(RequestFilter.PATH) String path,
            @RequestParam String fileName,
            HttpServletResponse response
    ) {
        // 获取原文件
        File file = new File(filePathConfig.getRoot() + path, fileName.replaceAll("\\.\\.", ""));
        // 判定原文件
        if (file.exists() && file.length() > MIN_SIZE) {
            // 对原文件名切片
            int index = fileName.lastIndexOf(".");
            // 生成缩略图文件对象
            String thumbnailName = filePathConfig.getRoot() + path + "thumbnail_" + fileName;
            File tf = new File(thumbnailName);
            try {
                if (index > 0) {
                    // 创建缩略图
                    Thumbnails.of(file)
                            .size(200, 200)
                            .outputFormat(fileName.substring(index + 1))
                            .toFile(tf);
                    if (tf.exists()) {
                        fileServer.downloadFile(response, tf);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                tf.delete();
            }
        } else {
            fileServer.downloadFile(response, file);
        }
    }
}
