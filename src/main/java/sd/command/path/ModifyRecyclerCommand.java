package sd.command.path;

import sd.annotation.Command;
import sd.annotation.CommandT;
import sd.config.ext.FilePathConfig;
import sd.log.Logger;
import sd.manager.ConfigFileManager;

import java.io.File;

@Command({"recycler", "rc"})
public class ModifyRecyclerCommand implements CommandT {

    @Override
    public String desc() {
        return "修改回收站路径";
    }

    @Override
    public void run(String[] params) {
        if (params.length == 1) {
            String recycler = params[0] + File.separator;
            File recyclerFile = new File(recycler);
            StringBuilder sb = new StringBuilder();
            if (!recyclerFile.exists()) {
                if (recyclerFile.mkdir()) {
                    sb.append("-->").append("成功创建文件夹: [ ").append(recycler).append(" ] ");
                } else {
                    sb.append("未能创建文件夹: [ ").append(recycler).append(" ] , 修改失败!!!");
                }
            }
            if (recyclerFile.exists()){
                FilePathConfig filePathConfig = ConfigFileManager.getInstance().getConfig(FilePathConfig.class);
                filePathConfig.setRecycleBinPath(recycler);
                sb.append("--> 修改成功, 当前回收站路径为: ").append(recycler);
            }
            new Logger().log(Logger.TAG_SERVER, sb.toString());
        } else {
            new Logger().log(Logger.TAG_SERVER, "修改路径格式错误, 格式为: recycler {newRecycler}, 路径中请勿使用空格");
        }
    }
}
