package sd.command.server;

import sd.annotation.Command;
import sd.annotation.CommandT;
import sd.filter.RequestFilter;

@Command({"pause", "p"})
public class PauseCommand implements CommandT {

    @Override
    public String desc() {
        return "暂停接收客户端请求";
    }

    @Override
    public void run(String[] params) {
        RequestFilter.able = false;
        System.out.println("服务器已暂停接收客户端请求");
    }
}
