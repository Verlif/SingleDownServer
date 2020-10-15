package sd.manager;

import org.springframework.context.annotation.Configuration;
import sd.config.ext.FilePathConfig;

import java.io.File;

@Configuration
public class RecycleBinManager {

    private final FilePathConfig filePathConfig;

    public RecycleBinManager() {
        filePathConfig = ConfigFileManager.getInstance().getConfig(FilePathConfig.class);
    }

    public boolean recycleFile(File file) {
        if (file.exists()) {
            File recycler = new File(filePathConfig.getRecycleBinPath());
            File dest = new File(recycler, file.getName());
            if (dest.exists()) {
                dest.delete();
            }
            return file.renameTo(dest);
        } else return false;
    }
}
