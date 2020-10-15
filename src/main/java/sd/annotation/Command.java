package sd.annotation;

import java.lang.annotation.*;

/**
 * 指令注释, 便于添加指令名称
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Command {
    /**
     * 命令标识, 用于标记命令
     * @return  该命令可用的所有命令标识
     */
    String[] value() default "command";
}
