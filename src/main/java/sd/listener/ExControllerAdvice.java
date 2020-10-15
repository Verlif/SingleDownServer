package sd.listener;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import sd.log.Logger;
import sd.model.result.FailResult;
import sd.model.result.Result;

import javax.annotation.Resource;

@ControllerAdvice
public class ExControllerAdvice {

    @Resource
    private Logger logger;

    public ExControllerAdvice() {

    }

    /**
     * 全局异常捕捉处理
     * @param ex    异常
     * @return  错误提示信息
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result errorHandler(Exception ex) {
        logger.exception(ex.getMessage());
        return new FailResult("请求出错");
    }

}
