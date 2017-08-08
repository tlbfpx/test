package tlb.mall.user.web.controller.login;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping(value="")
public class IndexController {

//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    private MenuService menuService;
//
//    @Autowired
//    private BasicSourceDownloadService basicSourceDownloadService;
//
//    @Value("#{configProperties['service.url']}")
//    private String SERVICE_URL;
//
//    @Autowired
//    private VisitRecordService visitRecordService;
//
//    /**
//     * 进入首页
//     *
//     * 创建日期：2016年8月2日 上午11:14:02 操作用户：zhoubang
//     *
//     * @return
//     */
//    @RequestMapping(value="")
//    public String page(HttpServletRequest request,ModelMap map) {
//        Subject currentUser = SecurityUtils.getSubject();
//        // 验证是否成功登录的方法
//        if (currentUser.isAuthenticated()) {
//
//            // 获取所有左侧父菜单
//            List<SysMenu> parentMenuList = menuService.getAllParentList();
//            for (SysMenu parentMenu : parentMenuList) {
//                parentMenu.setHasMenu(true);
//
//                // 获取子菜单
//                List<SysMenu> subMenu = menuService.getSubMenuByParentId(parentMenu.getId());
//                parentMenu.setSubMenu(subMenu);
//            }
//            String sessionId = request.getSession().getId();
//            map.put("menuList", parentMenuList);
//            map.put("serviceUrl", SERVICE_URL);
//
//
//            Subject subject = SecurityUtils.getSubject();
//            if(subject != null){
//                map.put("userName", String.valueOf(subject.getPrincipal()));
//            }
//
//            /**
//             * 1、实现的功能：同一个账户只能同时在一个终端登录，重复登录会剔除之前的用户。
//             * 2、这里的sessionId的作用，是为了在首页创建socket连接的时候，作为用户名参数的后缀，用于区分相同登录账户的不同客户端。
//             *      比如：new WebSocket("ws://域名/websck?loginUserName=帐户名_sessionId的值");
//             */
//            map.put("sessionId", sessionId);
//            return "index";//跳转到index.jsp
//        }
//        return "login";
//    }
//
//    /**
//     * /index 请求 ， 进入首页后的默认页面
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "/main")
//    public String defaultPage(HttpServletRequest request,ModelMap map) throws Exception {
//        String curLoginIp = getIpAddr(request);
//        map.put("serviceUrl", SERVICE_URL);
//        map.put("curLoginIp", curLoginIp);
//
//        Subject subject = SecurityUtils.getSubject();//当前登录用户
//        String userName = String.valueOf(subject.getPrincipal());
//
//        //获取上次登录信息
//        VisitRecord lastLogin = visitRecordService.getLastLogin(userName);
//        if(lastLogin == null){
//            map.put("lastLoginIp", curLoginIp);
//            map.put("lastLoginTime", DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
//        }else{
//            map.put("lastLoginIp", lastLogin.getCliIp());
//            map.put("lastLoginTime", DateUtil.getDateFormat(lastLogin.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
//        }
//
//        //获取今日登录次数
//        String today = DateUtil.getDateFormat(new Date());
//        Integer todayLoginNum = visitRecordService.getCurUserDayLoginNum(userName,today);
//        if(todayLoginNum == null || todayLoginNum == 0){
//            map.put("todayLoginNum", 1);
//        }else{
//            map.put("todayLoginNum", todayLoginNum);
//        }
//
//
//        /**
//         * 记录来访日志
//         */
//        //先判断是否是当前会话的重复请求
//        VisitRecord vr = visitRecordService.selectBySessionId(request.getSession().getId());
//        if(vr == null){
//            VisitRecord visitRecord = getClientRequestInfo(request,userName);
//            visitRecordService.save(visitRecord);
//        }
//        return "main";
//    }
//
//    /**
//     * 获取客户端请求信息
//     * @param request
//     * @return
//     */
//    public static VisitRecord getClientRequestInfo(HttpServletRequest request,String userName){
//        String clientIp = getIpAddr(request);//获取客户端ip
//
//        VisitRecord visitRecord =new VisitRecord();
//        visitRecord.setCliIp(clientIp);
//        visitRecord.setCliReqMethod(request.getMethod());
//        visitRecord.setCliReqUri(request.getRequestURI());
//        visitRecord.setCliReqUrl(request.getRequestURL().toString());
//        visitRecord.setCliSysAgent(request.getHeader("user-agent"));
//        visitRecord.setCliSysArch(System.getProperty("os.arch"));
//        visitRecord.setCliSysName(System.getProperty("os.name"));
//        visitRecord.setCliSysVersion(System.getProperty("os.version"));
//
//        Date date = new Date();
//        visitRecord.setCreateTime(date);
//        visitRecord.setUpdateTime(date);
//
//        visitRecord.setLocalAddr(request.getLocalAddr());
//        visitRecord.setLocalName(request.getLocalName());
//        visitRecord.setReqSessionId(request.getSession().getId());
//        visitRecord.setUserFromSystem(FromSystemEnum.DEFAULT_CURRENT_USER_CENTER);
//        visitRecord.setLoginUserName(userName);
//        return visitRecord;
//    }
//
//    /**
//     * 获取客户端ip
//     * @param request
//     * @return
//     */
//    private static String getIpAddr(HttpServletRequest request) {
//        String ip = request.getHeader("x-forwarded-for");
//        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//
//        //这种情况下，是因为客户端使用了localhost访问项目，将localhos换成127.0.0.1就变成了IPV4
//        if("0:0:0:0:0:0:0:1".equals(ip)){
//            ip = "127.0.0.1";
//        }
//
//        return ip;
//    }
//
//    /**
//     * 进入项目源码下载页面
//     *
//     * @return
//     */
//    @RequestMapping(value = "/sourceDownload")
//    public String sourceDownload(ModelMap map) {
//        List<BasicSourceDownload> sourceList = basicSourceDownloadService.getAllList();
//        map.put("sourceList", sourceList);
//        return "/down/sourceDownload";
//    }
}
