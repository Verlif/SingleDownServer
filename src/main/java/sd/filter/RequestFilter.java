package sd.filter;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import sd.config.ext.UserConfig;
import sd.log.Logger;
import sd.manager.ConfigFileManager;
import sd.model.User;
import sd.model.result.FailResult;
import sd.utils.JwtUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class RequestFilter implements Filter {

    /**
     * 过滤的参数名
     */
    public static final String PATH = "path";
    /**
     * 是否继续接收信息
     */
    public static boolean able = true;

    private Hashtable<String, User> userList;
    private Logger logger;

    public RequestFilter(Logger logger) {
        this.logger = logger;
        UserConfig userConfig = ConfigFileManager.getInstance().getConfig(UserConfig.class);
        userList = userConfig.getUserHashtable();
    }

    //与前端统一的位于请求头的token参数名
    public static final String TOKEN = "token";
    //在token中UserId的参数名，可用于向Controller中传入用户Id
    public static final String USER_NAME = "userName";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (able) {
            HttpServletRequest req = (HttpServletRequest) request;
            response.setContentType("application/json;charset=UTF-8");
            if (req.getHeaders(TOKEN).hasMoreElements()) {
                //获取request请求头中的token
                String token = req.getHeaders(TOKEN).nextElement();
                Claims claims = JwtUtils.parseJWT(token);
                /*
                 * 若token无效，则拒绝后续请求动作
                 * 从有效的token中查找"USER_ID_IN_TOKEN"参数
                 * 将userId添加到request参数列表中，便于后续获取
                 * */
                if (claims != null) {
                    String userName = (String) claims.get(USER_NAME);
                    // 筛查用户
                    User user = userList.get(userName);
                    if (user != null) {
                        req.setAttribute(USER_NAME, userName);
                        // 过滤路径中的非法字符
                        String path = req.getParameter(PATH);
                        if (path != null) {
                            if (!path.endsWith(File.pathSeparator)) {
                                path = path + File.separator;
                            }
                            req.setAttribute(PATH, path);
                        } else {
                            req.setAttribute(PATH, "");
                        }
                        chain.doFilter(req, response);
                    } else {
                        logger.warn("不存在用户: [ " + userName + " ] 正在访问 [" + req.getRequestURI() + " ], 请注意!!!");
                        returnFailResult(response, "无效的访问");
                    }
                } else {
                    logger.warn(req.getRequestURI() + " token失效: " + token);
                    returnFailResult(response, "登录已过期");
                }
            } else {
                logger.warn(req.getPathInfo() + " 没有token");
                returnFailResult(response, "缺失必要信息");
            }
        } else {
            returnFailResult(response, "服务器维护中");
        }
    }

    private void returnFailResult(ServletResponse response, String failMsg) throws IOException {
        ServletOutputStream out = response.getOutputStream();
        out.write(JSON.toJSONString(new FailResult(failMsg)).getBytes());
        out.flush();
    }
}
