package sd.utils;

import java.util.Date;
import java.util.logging.Logger;

public class PrintUtil {

    private static Logger logger = Logger.getLogger("SingleDown");

    public static void println(Object text) {
        if (text == null) {
            text = "null";
        }
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        sb
                .append(new Date())
                .append(" - Thread-")
                .append(Thread.currentThread().getId())
                .append(" - ");
        if (stack.length > 4) {
            String[] temp;
            for (int i = 4; i > 1; i--) {
                temp = stack[i].getClassName().split("\\.");
                sb.append(temp[temp.length - 1]).append("(").append(stack[i].getMethodName()).append(").");
            }
        }
        sb.append(" -\n\t\t").append(text.toString().replaceAll("\n", "\n\t\t"));
        System.out.println(sb.toString());
    }

    public static void log(Object o) {
        logger.info(o.toString());
    }
}
