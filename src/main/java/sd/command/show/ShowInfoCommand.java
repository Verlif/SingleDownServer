package sd.command.show;

import sd.annotation.Command;
import sd.annotation.CommandT;
import sd.log.Logger;

@Command("show")
public class ShowInfoCommand implements CommandT {

    @Override
    public String desc() {
        return "显示一些系统数据";
    }

    @Override
    public void run(String[] params) {
        new Logger().log(Logger.TAG_SERVER, "我还没有做, 哈哈, 没想到吧");
    }
}
