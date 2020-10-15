package sd.command.server;

import sd.Application;
import sd.annotation.Command;
import sd.annotation.CommandT;

@Command({"stop"})
public class StopCommand implements CommandT {

    @Override
    public String desc() {
        return "关闭服务器";
    }

    @Override
    public void run(String[] params) {
        Application.close();
    }
}
