package sd.controller.file;

import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sd.config.ext.FilePathConfig;
import sd.filter.RequestFilter;
import sd.log.Logger;
import sd.manager.ConfigFileManager;
import sd.manager.RecycleBinManager;
import sd.model.SDFile;
import sd.model.result.FailResult;
import sd.model.result.Result;
import sd.model.result.SusResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("file")
public class FileController {

    @Resource
    private Logger logger;

    private final FilePathConfig filePathConfig;

    @Resource
    private FileServer fileServer;
    @Resource
    private RecycleBinManager recycleBinManager;

    public FileController() {
        filePathConfig = ConfigFileManager.getInstance().getConfig(FilePathConfig.class);
    }

    @PostMapping("/upload")
    @ResponseBody
    public Result upload(
            @RequestAttribute(RequestFilter.PATH) String filePath,
            @RequestParam("file") MultipartFile file,
            @RequestAttribute(RequestFilter.USER_NAME) String userName
    ) {
        logger.log(userName, "正在上传文件: " + file.getOriginalFilename());
        if (file.isEmpty()) {
            return new FailResult("不能上传空文件");
        }
        String fileName = file.getOriginalFilename();
        SDFile dest = new SDFile(filePathConfig.getRoot() + filePath + fileName);
        try {
            OutputStream os = new FileOutputStream(dest);
            InputStream is = file.getInputStream();
            StreamUtils.copy(is, os);
            is.close();
            os.close();
            return new SusResult();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new FailResult("未知错误");
    }

    @RequestMapping("/download")
    public void download(
            @RequestParam(value = "fileName") String fileName,
            @RequestAttribute(RequestFilter.PATH) String filePath,
            HttpServletResponse response,
            @RequestAttribute(RequestFilter.USER_NAME) String userName
    ) {
        SDFile file = new SDFile(filePathConfig.getRoot() + filePath + fileName);
        logger.log(userName, "正在下载文件: " + file.getAbsolutePath());
        fileServer.downloadFile(response, file);
    }

    @RequestMapping("/newFolder")
    public Result newFolder(
            @RequestParam("folderName") String folderName,
            @RequestAttribute(value = RequestFilter.PATH) String path,
            @RequestAttribute(value = RequestFilter.USER_NAME) String userName
    ) {
        String folderPath = filePathConfig.getRoot() + path + folderName;
        SDFile file = new SDFile(folderPath);
        logger.log(userName, "正在创建文件夹: " + file.getAbsolutePath());
        if (!file.exists()) {
            if (new File(folderPath + folderName).mkdirs()) {
                return new SusResult();
            } else {
                return new FailResult("创建失败");
            }
        } else return new FailResult("路径错误");
    }

    @RequestMapping("/del")
    public Result delFiles(
            @RequestParam("fileName") String fileName,
            @RequestAttribute(RequestFilter.PATH) String path,
            @RequestAttribute(RequestFilter.USER_NAME) String userName
    ) {
        SDFile file = new SDFile(filePathConfig.getRoot() + path + fileName);
        logger.log(userName, "正在删除文件: " + file.getAbsolutePath() + "(" + file.exists() + ")");
        if (recycleBinManager.recycleFile(file)) {
            return new SusResult();
        } else return new FailResult("删除失败");
    }

    @RequestMapping("/rename")
    public Result rename(
            @RequestParam("fileName") String fileName,
            @RequestParam("nowName") String nowName,
            @RequestAttribute(RequestFilter.PATH) String path,
            @RequestAttribute(RequestFilter.USER_NAME) String userName
    ) {
        logger.log(userName, "正在重命名文件: " + filePathConfig.getRoot() + path + fileName + " -> " + nowName);
        String oldName = filePathConfig.getRoot() + path + fileName;
        String thisName = filePathConfig.getRoot() + path + nowName;
        SDFile file = new SDFile(oldName);
        if (file.exists()) {
            if (file.renameTo(new SDFile(thisName))) {
                return new SusResult();
            } else return new FailResult("重命名失败");
        } else return new FailResult("未找到该文件");
    }
}
