package sd.controller.file;

import org.springframework.stereotype.Service;
import sd.tools.FileTool;
import sd.utils.PrintUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class FileServer {

    @Resource
    private FileTool fileTool;

    public void downloadFile(HttpServletResponse response, File file) {
        // 设置相关格式
        response.setContentType("application/force-download");
        // 设置下载后的文件名以及header
        response.addHeader("Content-disposition", "attachment;fileName=" + file.getName());
        // 设置文件大小
        response.addHeader("Content-Length", file.length() + "");
        // 创建输出对象
        try {
            OutputStream os = response.getOutputStream();
            fileTool.downloadFile(file, os);
        } catch (IOException e) {
            PrintUtil.log(e.getMessage());
        }
    }
}
