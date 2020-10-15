package sd.command.server;

import sd.Application;
import sd.annotation.Command;
import sd.annotation.CommandT;

@Command({"start"})
public class StartCommand implements CommandT {

    @Override
    public String desc() {
        return "开启服务器";
    }

    @Override
    public void run(String[] params) {
        Application.main(new String[]{});
    }
}
