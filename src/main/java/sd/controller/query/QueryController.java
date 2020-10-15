package sd.controller.query;

import org.springframework.web.bind.annotation.*;
import sd.config.ext.FilePathConfig;
import sd.filter.RequestFilter;
import sd.manager.ConfigFileManager;
import sd.model.FileInfo;
import sd.model.result.FailResult;
import sd.model.result.Result;
import sd.model.result.SusResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("query")
public class QueryController {

    private final FilePathConfig filePathConfig;

    public QueryController() {
        filePathConfig = ConfigFileManager.getInstance().getConfig(FilePathConfig.class);
    }

    /**
     * 显示文件夹内的文件信息
     * @param filePath  目标路径
     * @return  返回信息
     */
    @RequestMapping("/list")
    public Result listFile(
            @RequestAttribute(RequestFilter.PATH) String filePath
    ) {
        File file = new File(filePathConfig.getRoot() + filePath);
        if (file.exists()) {
            List<FileInfo> fileInfoList = new ArrayList<>();
            String[] names = file.list();
            if (names != null) {
                for (String s : names) {
                    File temp = new File(file.getAbsolutePath() + "/" + s);
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setFileName(s);
                    fileInfo.setPath(filePath);
                    fileInfo.setFile(temp.isFile());
                    fileInfo.setSize(temp.length());
                    fileInfo.setUploadTime(temp.lastModified());
                    fileInfoList.add(fileInfo);
                }
            }
            Result result = new SusResult();
            result.addList("fileInfo", fileInfoList);
            return result;
        } else return new FailResult("文件路径错误:" + filePath);
    }

}
