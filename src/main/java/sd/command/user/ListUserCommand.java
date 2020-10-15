package sd.command.user;

import sd.annotation.Command;
import sd.annotation.CommandT;
import sd.config.ext.UserConfig;
import sd.log.Logger;
import sd.manager.ConfigFileManager;
import sd.model.User;

import java.util.Hashtable;

@Command({"listUser", "lu"})
public class ListUserCommand implements CommandT {
    @Override
    public String desc() {
        return "查看用户列表";
    }

    @Override
    public void run(String[] params) {
        UserConfig userConfig = ConfigFileManager.getInstance().getConfig(UserConfig.class);
        Hashtable<String, User> userHashMap = userConfig.getUserHashtable();
        Logger logger = new Logger();
        StringBuilder sb = new StringBuilder();
        sb.append("当前可用用户有:").append("\n");
        for (String userName : userHashMap.keySet()) {
            sb.append("[ ").append(userName).append(" ]").append("\n");
        }
        logger.info(sb.deleteCharAt(sb.length() - 1).toString());
    }
}
