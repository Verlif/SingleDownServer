package sd.log;

import org.springframework.context.annotation.Configuration;
import sd.utils.PrintUtil;

@Configuration
public class Logger {

    public static final String TAG_INFO = "info";
    public static final String TAG_WARNING = "warning";
    public static final String TAG_EXCEPTION = "exception";

    public static final String TAG_SERVER = "server";

    public void log(String tag, Object o) {
        PrintUtil.println("[" + tag + "] - " + o);
    }

    public void info(Object o) {
        PrintUtil.println("[" + TAG_INFO + "] - " + o);
    }

    public void warn(Object o) {
        PrintUtil.println("[" + TAG_WARNING + "] - " + o);
    }

    public void exception(Object o) {
        PrintUtil.println("[" + TAG_EXCEPTION + "] - " + o);
    }
}
