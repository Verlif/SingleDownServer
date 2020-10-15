package sd.controller.login;

import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sd.config.ext.UserConfig;
import sd.filter.RequestFilter;
import sd.manager.ConfigFileManager;
import sd.model.User;
import sd.model.result.FailResult;
import sd.model.result.Result;
import sd.model.result.SusResult;
import sd.utils.JwtUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;

@RestController
@RequestMapping("login")
public class LoginController {

    private UserConfig userConfig;

    public LoginController() {
        userConfig = ConfigFileManager.getInstance().getConfig(UserConfig.class);
    }

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param password 密码
     * @return 登录是否成功
     */
    @RequestMapping("")
    public Result login(
            String userName,
            String password
    ) {
        if (checkUser(userName, password)) {
            Result result = new SusResult();
            result.addObject("token", getToken(userName));
            return result;
        } else return new FailResult("登录失败");
    }

    /**
     * 刷新用户token
     *
     * @param token 当前token
     * @return token是否过期或新token
     */
    @RequestMapping("/refresh")
    public Result refresh(
            @RequestParam("token") String token
    ) {
        Claims claims = JwtUtils.parseJWT(token);
        if (claims != null) {
            Result result = new SusResult();
            result.addObject("token", getToken(claims.get(RequestFilter.USER_NAME) + ""));
            return result;
        } else {
            return new FailResult("登录失效");
        }
    }

    private String getToken(String userName) {
        HashMap<String, Serializable> map = new HashMap<>();
        map.put(RequestFilter.USER_NAME, userName);
        return JwtUtils.createJWT(map);
    }

    private boolean checkUser(String userName, String password) {
        Hashtable<String, User> userHashMap = userConfig.getUserHashtable();
        User user = userHashMap.get(userName);
        return user != null && user.getPassword().equals(password);
    }
}
