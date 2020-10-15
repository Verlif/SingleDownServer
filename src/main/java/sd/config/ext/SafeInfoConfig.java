package sd.config.ext;

import lombok.Getter;
import sd.manager.ConfigFileManager;

import java.util.Random;

public class SafeInfoConfig implements ConfigFileManager.Config {

    @Getter
    private String tokenSecret;

    @Override
    public void restore(String sLine) {
        this.tokenSecret = sLine;
    }

    @Override
    public void init() {
        tokenSecret = random();
    }

    private String random() {
        String all = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+=-[]";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < 20; i++){
            int number = random.nextInt(all.length());
            sb.append(all.charAt(number));
        }
        return sb.toString();
    }

    @Override
    public String toSave() {
        return tokenSecret;
    }
}
