package sd.command.server;

import sd.annotation.Command;
import sd.annotation.CommandT;
import sd.filter.RequestFilter;

@Command({"continue", "c"})
public class ContinueCommand implements CommandT {

    @Override
    public String desc() {
        return "继续接收客户端请求";
    }

    @Override
    public void run(String[] params) {
        RequestFilter.able = true;
        System.out.println("服务器已恢复接收客户端请求");
    }
}
