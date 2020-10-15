package sd.command.path;

import sd.annotation.Command;
import sd.annotation.CommandT;
import sd.config.ext.FilePathConfig;
import sd.log.Logger;
import sd.manager.ConfigFileManager;

import java.io.File;

@Command("root")
public class ModifyRootCommand implements CommandT {

    @Override
    public String desc() {
        return "修改主存储路径";
    }

    @Override
    public void run(String[] params) {
        if (params.length == 1) {
            String root = params[0] + File.separator;
            File rootFile = new File(root);
            StringBuilder sb = new StringBuilder();
            if (!rootFile.exists()) {
                if (rootFile.mkdir()) {
                    sb.append("-->").append("成功创建文件夹: [ ").append(root).append(" ] ");
                } else {
                    sb.append("未能创建文件夹: [ ").append(root).append(" ] , 修改失败!!!");
                }
            }
            if (rootFile.exists()) {
                FilePathConfig filePathConfig = ConfigFileManager.getInstance().getConfig(FilePathConfig.class);
                filePathConfig.setRoot(root);
                sb.append("--> 修改成功, 当前主存储路径为: ").append(root);
            }
            new Logger().log(Logger.TAG_SERVER, sb.toString());
        } else {
            new Logger().log(Logger.TAG_SERVER, "修改路径格式错误, 格式为: root {newRoot}, 路径中请勿使用空格");
        }
    }
}
