package sd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import sd.annotation.CommandT;
import sd.manager.CommandManager;
import sd.manager.ConfigFileManager;
import sd.utils.PrintUtil;

import java.util.Arrays;
import java.util.Scanner;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static CommandManager commandManager;
    public static ConfigFileManager configFileManager;
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(sd.Application.class, args);
        commandManager = CommandManager.getInstance();
        configFileManager = ConfigFileManager.getInstance();
        // 接受命令并执行
        PrintUtil.log("命令初始化完成: " + commandManager.getAllComment().size() + ", 可以输入[help]指令来获取指令信息");
        String command;
        while (true) {
            command = new Scanner(System.in).nextLine();
            command(command.trim().replaceAll(" +", " ").split(" "));
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }

    private static void command(String[] command) {
        if (command != null && command.length > 0) {
            CommandT commandT = commandManager.getCommand(command[0]);
            if (commandT != null) {
                if (command.length > 1) {
                    commandT.run(Arrays.copyOfRange(command, 1, command.length));
                } else commandT.run(new String[]{});
            } else {
                System.out.println("[" + command[0] + "] 是什么?");
            }
        }
    }

    public static void close() {
        context.close();
    }
}
