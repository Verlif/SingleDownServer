package sd.command.user;

import sd.annotation.Command;
import sd.annotation.CommandT;
import sd.config.ext.UserConfig;
import sd.log.Logger;
import sd.manager.ConfigFileManager;
import sd.model.User;

import java.util.Hashtable;

@Command({"addUser", "au"})
public class AddUserCommand implements CommandT {

    @Override
    public String desc() {
        return "添加一个新用户";
    }

    @Override
    public void run(String[] params) {
        if (params.length == 2) {
            UserConfig userConfig = ConfigFileManager.getInstance().getConfig(UserConfig.class);
            Hashtable<String, User> userHashMap = userConfig.getUserHashtable();
            User user = new User();
            user.setUserName(params[0]);
            user.setPassword(params[1]);
            userHashMap.put(user.getUserName(), user);
            new Logger().log(Logger.TAG_SERVER, "已添加新用户: " + user.getUserName());
        } else {
            new Logger().log(Logger.TAG_SERVER, "添加用户格式错误, 格式为: addUser {userName} {password}");
        }
    }
}
