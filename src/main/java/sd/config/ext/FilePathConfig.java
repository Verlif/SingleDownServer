package sd.config.ext;

import lombok.Data;
import sd.manager.ConfigFileManager;

@Data
public class FilePathConfig implements ConfigFileManager.Config {

    private static final String TAG_ROOT = "root:";
    private static final String TAG_RECYCLE = "recycleBin:";

    /**
     * 文件系统根路径
     */
    private String root;

    /**
     * 回收站路径
     */
    private String recycleBinPath;

    @Override
    public void restore(String sLine) {
        String[] info = sLine.split("\n");
        for (String s : info) {
            if (s.startsWith(TAG_ROOT)) {
                root = s.replace(TAG_ROOT, "");
            } else if (s.startsWith(TAG_RECYCLE)) {
                recycleBinPath = s.replace(TAG_RECYCLE, "");
            }
        }
    }

    @Override
    public void init() {
        root = "upload/";
        recycleBinPath = "recycler/";
    }

    @Override
    public String toSave() {
        StringBuilder sb = new StringBuilder();
        sb.append(TAG_ROOT).append(root).append("\n");
        sb.append(TAG_RECYCLE).append(recycleBinPath);
        return sb.toString();
    }
}
