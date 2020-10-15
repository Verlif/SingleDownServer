package sd.listener;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;
import sd.manager.ConfigFileManager;

@Component
public class MyDisposableBean implements DisposableBean {

    /**
     * 服务结束时调用
     */
    @Override
    public void destroy() {
        ConfigFileManager.getInstance().save();
    }
}

