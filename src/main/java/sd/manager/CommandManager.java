package sd.manager;

import org.reflections.Reflections;
import sd.annotation.Command;
import sd.annotation.CommandT;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CommandManager {

    private static CommandManager instance;

    private Set<Class<? extends CommandT>> commandTSet;
    private HashMap<String, CommandT> commandTHashMap;
    private Set<String> commandFilter;

    public static CommandManager getInstance() {
        if (instance == null) {
            synchronized (CommandManager.class) {
                if (instance == null) {
                    instance = new CommandManager();
                }
            }
        }
        return instance;
    }

    private CommandManager() {
        // 初始化命令集
        Reflections reflections = new Reflections("sd");
        commandTSet = reflections.getSubTypesOf(CommandT.class);
        commandTHashMap = new HashMap<>();
        commandFilter = new HashSet<>();
        for (Class<? extends CommandT> commandT : commandTSet) {
            addCommand(commandT);
        }
    }

    public CommandT getCommand(String key) {
        return commandTHashMap.get(key);
    }

    public Set<Class<? extends CommandT>> getAllComment() {
        return commandTSet;
    }

    public void addFilter(String key) {
        commandFilter.add(key);
    }

    public void removeFilter(String key) {
        commandFilter.remove(key);
    }

    public void deleteCommand(Class<? extends CommandT> cl) {
        try {
            CommandT commandT = cl.newInstance();
            if (cl.isAnnotationPresent(Command.class)) {
                Command command = cl.getAnnotation(Command.class);
                // 获取命令属性
                String[] commandName = command.value();
                // 注入命令
                for (String s : commandName) {
                    commandTHashMap.remove(s);
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void addCommand(Class<? extends CommandT> cl) {
        // 获取注释标记
        if (cl.isAnnotationPresent(Command.class)) {
            Command command = cl.getAnnotation(Command.class);
            // 获取命令属性
            String[] commandName = command.value();
            try {
                CommandT co = cl.newInstance();
                // 注入命令
                for (String s : commandName) {
                    commandTHashMap.put(s, co);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
