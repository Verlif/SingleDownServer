package sd.command.server;

import sd.Application;
import sd.annotation.Command;
import sd.annotation.CommandT;

@Command({"restart"})
public class RestartCommand implements CommandT {

    @Override
    public String desc() {
        return "重启服务器";
    }

    @Override
    public void run(String[] params) {
        Application.close();
        Application.main(new String[]{});
    }
}
