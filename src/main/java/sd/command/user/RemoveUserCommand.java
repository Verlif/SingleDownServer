package sd.command.user;

import sd.annotation.Command;
import sd.annotation.CommandT;
import sd.config.ext.UserConfig;
import sd.log.Logger;
import sd.manager.ConfigFileManager;
import sd.model.User;

import java.util.HashMap;
import java.util.Hashtable;

@Command({"removeUser", "ru"})
public class RemoveUserCommand implements CommandT {

    @Override
    public String desc() {
        return "删除可用用户";
    }

    @Override
    public void run(String[] params) {
        if (params.length == 1) {
            UserConfig userConfig = ConfigFileManager.getInstance().getConfig(UserConfig.class);
            Hashtable<String, User> userHashMap = userConfig.getUserHashtable();
            User user = userHashMap.remove(params[0]);
            if (user != null) {
                new Logger().log(Logger.TAG_SERVER, "已删除用户: " + user.getUserName());
            } else {
                new Logger().log(Logger.TAG_SERVER, "未找到该用户: " + params[0]);
            }
        } else {
            new Logger().log(Logger.TAG_SERVER, "删除用户格式错误, 格式为: removeUser {userName}");
        }
    }
}
