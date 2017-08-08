package tlb.mall.user.web.controller.redis;

import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.session.Session;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.crazycake.shiro.SerializeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


import tlb.mall.common.util.AjaxResult;
import tlb.mall.common.util.enums.UserStatus;
import tlb.mall.common.util.util.JsonUtil;
import tlb.mall.entity.sys.SysUser;
import tlb.mall.user.web.util.redis.RedisClientTemplate;

//@Controller
//@RequestMapping("/redis")
public class RedisController {

	@Autowired
	private RedisSessionDAO redisSessionDAO;
	
	@Autowired
    private RedisClientTemplate redisClientTemplate;
	
	/**
	 * 测试redis缓存session的。没有实际作用
	 * @param map
	 * @return
	 */
	@RequestMapping("shiroSession")
    public String shiroSession(ModelMap map) {
		Session s = redisSessionDAO.readSession("26968521-58ca-4d72-8cfd-2a794353b929");
		System.out.println(s.toString());
		
		RedisManager redisManager = redisSessionDAO.getRedisManager();
		Set<byte[]> keys = redisManager.keys("shiro_redis_session*");
		if(keys != null && keys.size()>0){
			for(byte[] key:keys){
				Session ss = (Session)SerializeUtils.deserialize(redisManager.get(key));
				System.out.println(ss.getId());
			}
		}
		
		//下面作用是让session过期。测试前端是否需要登录，验证缓存功能的有效性
		//redisSessionDAO.delete(s);
		//System.out.println("移除成功");
		
		s.setAttribute("cao", "什么情况");
		redisSessionDAO.create(s);
		
        return "/redis/redis";
    }
	
	
   /**
    * 基本缓存测试
    * @return
    */
    @RequestMapping("redis")
    public void redis() {
        boolean b = redisClientTemplate.exists("sex");
        System.out.println(b);
        
        String s = redisClientTemplate.set("sex", "男");
        System.out.println(s);
        
        boolean bb = redisClientTemplate.exists("sex");
        System.out.println(bb);
        
        String ss = redisClientTemplate.get("sex");
        System.out.println(ss);
        
        String sss = redisClientTemplate.get("sex2");
        System.out.println(sss);
        
        //Long l =redisClientTemplate.expireAt("sex", 0);
        //System.out.println(l);
        
        String r = redisClientTemplate.set("test", JsonUtil.toJson(new AjaxResult<String>()));
        System.out.println(r);
        String rr = redisClientTemplate.get("test");
        System.out.println(rr);
        
        
        //测试存储对象
        SysUser u = new SysUser();
        u.setCreateTime(new Date());
        u.setPassword("123456");
        u.setUserName("zb周棒@*/-、|‘，“~_-【】[]<>》《？");
        u.setStatus(UserStatus.ENABLE);
        
        String rrr = redisClientTemplate.set("user", JsonUtil.toJson(u));
        System.out.println(rrr);
        
        String userStr = redisClientTemplate.get("user");
        System.out.println(userStr);
        SysUser uu = JsonUtil.fromJson(userStr, SysUser.class);
        System.out.println(uu);
        
    }
	
    //这里其实还是从redis缓存中读取的。HttpSession其实是shiro自己的session管理器.
    @RequestMapping("/tomcatSession")
    public void tomcatSession(HttpServletRequest request,HttpServletResponse response){
        HttpSession s = request.getSession();
        System.out.println(s.getClass().getName());
        s.setAttribute("name", "tomcat的session测试");
        System.out.println("session存储成功");
    }
    
    @RequestMapping("/getTomcatSession")
    public void getTomcatSession(HttpServletRequest request,HttpServletResponse response){
        HttpSession s = request.getSession();
        System.out.println(s.getClass().getSimpleName());
        
        Object obj = s.getAttribute("name");
        System.out.println(JsonUtil.toJson(obj));
    }
}
