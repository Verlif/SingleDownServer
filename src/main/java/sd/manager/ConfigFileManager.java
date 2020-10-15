package sd.manager;

import org.reflections.Reflections;
import sd.tools.FileTool;

import java.io.File;
import java.lang.annotation.*;
import java.util.HashMap;
import java.util.Set;

public class ConfigFileManager {

    private static final String CONFIG_PATH = "config/";
    private static ConfigFileManager instance;

    private final FileTool fileTool;

    private final HashMap<String, Config> configHashMap;

    private ConfigFileManager() {
        fileTool = new FileTool();
        configHashMap = new HashMap<>();

        // 初始化配置
        Reflections reflections = new Reflections("sd");
        Set<Class<? extends Config>> configSet = reflections.getSubTypesOf(Config.class);
        for (Class<? extends Config> c : configSet) {
            try {
                Config config = c.newInstance();
                loadConfig(config);
                configHashMap.put(c.getSimpleName(), config);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // 创建配置文件夹
        new File(CONFIG_PATH).mkdir();
        save();
    }

    public static ConfigFileManager getInstance() {
        if (instance == null) {
            synchronized (ConfigFileManager.class) {
                if (instance == null) {
                    instance = new ConfigFileManager();
                }
            }
        }
        return instance;
    }

    /**
     * 从配置文件中获取配置对象数据
     *
     * @param config 配置文件对象
     */
    private void loadConfig(Config config) {
        File file = new File(getConfigFileName(config));
        if (file.exists()) {
            String s = fileTool.getStringFromFile(file);
            if (s == null || s.length() == 0) {
                config.init();
            } else {
                config.restore(s);
            }
        } else {
            config.init();
        }
    }

    private String getConfigFileName(Config config) {
        return CONFIG_PATH + config.getClass().getSimpleName() + ".config";
    }

    public <T extends Config> T getConfig(Class<? extends Config> cl) {
        return (T) configHashMap.get(cl.getSimpleName());
    }

    public void save() {
        for (Config config : configHashMap.values()) {
            File file = new File(getConfigFileName(config));
            fileTool.saveTextToFile(config.toSave(), file);
        }
    }

    public interface Config {

        /**
         * 从文本行转成配置文件对象
         *
         * @param sLine 可转换的配置文件文本
         */
        void restore(String sLine);

        /**
         * 初始化配置
         * 仅在第一次创建配置文件时调用
         */
        void init();

        /**
         * 从对象转成配置文件文本
         *
         * @return 转换结果文本
         */
        String toSave();
    }
}
