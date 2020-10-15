package sd.config.ext;

import lombok.Getter;
import sd.manager.ConfigFileManager;
import sd.model.User;

import java.util.Hashtable;

public class UserConfig implements ConfigFileManager.Config {

    private static final String TAG_USER_NAME = "userName:";
    private static final String TAG_PASSWORD = "password:";

    @Getter
    private final Hashtable<String, User> userHashtable;

    public UserConfig() {
        userHashtable = new Hashtable<>();
    }

    @Override
    public void restore(String sLine) {
        String[] users = sLine
                .replaceAll("\t", "")
                .split("\n");
        User user = null;
        for (String s : users) {
            if (s.startsWith(TAG_USER_NAME)) {
                user = new User();
                user.setUserName(s.replace(TAG_USER_NAME, ""));
            } else if (s.startsWith(TAG_PASSWORD)) {
                if (user != null) {
                    user.setPassword(s.replace(TAG_PASSWORD, ""));
                    userHashtable.put(user.getUserName(), user);
                }
            }
        }
    }

    public void init() {
        User user = new User();
        user.setUserName("admin");
        user.setPassword("SingleDown...Happy");
        userHashtable.put(user.getUserName(), user);
    }

    @Override
    public String toSave() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append("\n");
        for (User u : userHashtable.values()) {
            sb.append("\t(\n");
            sb.append("\t\t").append(TAG_USER_NAME).append(u.getUserName()).append("\n");
            sb.append("\t\t").append(TAG_PASSWORD).append(u.getPassword()).append("\n");
            sb.append("\t)\n");
        }
        sb.append("]");
        return sb.toString();
    }

}
