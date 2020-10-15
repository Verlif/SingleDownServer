package sd.command;

import org.reflections.Reflections;
import sd.annotation.Command;
import sd.annotation.CommandT;

import java.util.Arrays;
import java.util.Set;

@Command({"help", "h"})
public class HelpCommand implements CommandT {

    @Override
    public String desc() {
        return "显示所有指令";
    }

    @Override
    public void run(String[] params) {
        Reflections reflections = new Reflections("sd");
        Set<Class<? extends CommandT>> commandSet = reflections.getSubTypesOf(CommandT.class);
        try {
            for (Class<? extends CommandT> commandT : commandSet) {
                if (commandT.isAnnotationPresent(Command.class)) {
                    Command command = commandT.getAnnotation(Command.class);
                    String[] commandName = command.value();
                    StringBuilder sb = new StringBuilder();
                    sb.append(Arrays.toString(commandName));
                    int length = sb.length();
                    for (int i = length; i < 30; i++) {
                        sb.append(" ");
                    }
                    sb.append(" - ").append(commandT.newInstance().desc());
                    System.out.println(sb.toString());
                }
            }
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
