package tlb.mall.user.web.controller.login;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tlb.mall.common.util.AjaxResult;
import tlb.mall.common.util.util.CryptoUtils;
import tlb.mall.common.util.util.JsonUtil;
import tlb.mall.entity.sys.qo.LoginQo;


/**
 * 会员登录与验证
 * 
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 进入登录页面
     * 
     * 创建日期：2016年8月3日 下午1:51:15 操作用户：zhoubang
     * 
     * @return
     */
    @RequestMapping("toLogin")
    public String toLogin() {
        return "/login";
    }

    /**
     * 登录 默认登录密码123456 密码MD5加密后 e10adc3949ba59abbe56e057f20f883e
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginFailed(HttpServletRequest req, HttpServletResponse res, ModelMap map, @Valid LoginQo loginQo,
            BindingResult bindingResult) throws IOException {

        res.setCharacterEncoding("UTF8");
        res.setContentType("text/html;charset=utf-8");

        // ajax登录方式使用
        AjaxResult<String> result = new AjaxResult<String>();

        // 错误消息
        String loginErrorMsg = "";

        // 参数错误验证
        if (bindingResult.hasErrors()) {
            if (!loginQo.getOpenAjaxLogin()) {
                loginErrorMsg = "用户名或密码不能为空";
                map.put("loginErrorMsg", loginErrorMsg);
                return "/login";
            } else {
                result.setCode(10001);
                result.setMsg(loginErrorMsg);
                res.getWriter().println(JsonUtil.toJson(result));
                return null;
            }
        }

        // 验证码验证
        if (loginQo.getOpenVerifyCode()) {
            Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();
            Object code = session.getAttribute("verifyCode");

            if (!loginQo.getOpenAjaxLogin()) {
                if (null == code) {
                    loginErrorMsg = "验证码超时失效";
                    map.put("loginErrorMsg", loginErrorMsg);
                    return "/login";
                }
                if (!String.valueOf(code).equalsIgnoreCase(loginQo.getCode())) {
                    loginErrorMsg = "验证码错误";
                    map.put("loginErrorMsg", loginErrorMsg);
                    return "/login";
                }
            } else {
                if (null == code || !String.valueOf(code).equalsIgnoreCase(loginQo.getCode())) {
                    result.setCode(10002);
                    result.setMsg("codeerror");
                    res.getWriter().println(JsonUtil.toJson(result));
                    return null;
                }
            }
        }

        UsernamePasswordToken token = new UsernamePasswordToken(loginQo.getUserName(),
                CryptoUtils.encodeMD5(loginQo.getPassword()));
        Subject subject = null;
        try {
            // 设置是否记住我
            if (null == loginQo.getRememberMe() || "".equals(loginQo.getRememberMe().trim())
                    || "off".equals(loginQo.getRememberMe())) {
                token.setRememberMe(false);
            } else {
                token.setRememberMe(true);
            }
            SecurityUtils.getSubject().login(token);
            subject = SecurityUtils.getSubject();
            logger.debug("用户：{0} 登录成功", subject.getPrincipal());

            SecurityUtils.getSubject().getSession().setTimeout(1000 * 60 * 60);
            req.getSession().setAttribute("nickName", subject.getPrincipal());
        } catch (Throwable e) {
            /**
             * 这些异常都是shiro中的异常，你可以阵对不同的异常来显示不同的消息提示。 异常的捕捉抛出，请查看
             * pers.zb.web.shiro.GlobalAuthorizingRealm 中的
             * doGetAuthenticationInfo方法。
             * AuthenticationException异常是所有异常的父类，所以，应该把这个异常放在最后一个if判断
             */
            // UnknownAccountException 该账号不存在
            // CredentialsException 密码错误
            if (e instanceof ConcurrentAccessException) {
                loginErrorMsg = "您的账户已经在别处登录";

            } else if (e instanceof LockedAccountException) {
                loginErrorMsg = "您的账户已被锁定";

            } else if (e instanceof DisabledAccountException) {
                loginErrorMsg = "您的账户已被禁用";

            } else if (e instanceof AuthenticationException) {
                loginErrorMsg = "账号不存在或密码错误";

            } else {
                logger.error("未知的登陆错误", e);
                loginErrorMsg = "未知的登陆错误，请稍后重试";
            }
        }

        // 登录出现错误
        if (!"".equals(loginErrorMsg) && loginErrorMsg.length() > 0) {
            if (!loginQo.getOpenAjaxLogin()) {
                map.put("loginErrorMsg", loginErrorMsg);
                return "/login";
            } else {
                result.setCode(10003);
                result.setMsg(loginErrorMsg);
                res.getWriter().println(JsonUtil.toJson(result));
                return null;
            }
        }
        if (!loginQo.getOpenAjaxLogin()) {
            return "redirect:/index";
        } else {
            res.getWriter().println(JsonUtil.toJson(result));
            return null;
        }
    }

    /**
     * 注销
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest req, HttpServletResponse res) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            logger.debug("用户 {0} 退出登录", subject.getPrincipal());
            subject.logout();
        }
        return "redirect:/";
    }

}
