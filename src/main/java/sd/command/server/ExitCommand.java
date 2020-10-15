package sd.command.server;

import sd.annotation.Command;
import sd.annotation.CommandT;

@Command({"exit", "e", "close"})
public class ExitCommand implements CommandT {

    @Override
    public String desc() {
        return "退出程序";
    }

    @Override
    public void run(String[] params) {
        System.exit(0);
    }
}
