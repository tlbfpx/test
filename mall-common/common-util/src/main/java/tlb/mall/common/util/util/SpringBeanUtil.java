package tlb.mall.common.util.util;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 普通java类中可以获取bean
 * 
 *  使用方法：
 *          1、首先在web.xml中配置该监听器，配置在 spring监听器的后面
 *          2、配置完成后，就可以在java类中，通过 SpringBeanUtil.getApplicationContext().getBean("bean名称"); 的方式获取注入的bean了
 * 
 * 创建日期：2016年6月23日 下午2:57:01 操作用户：zhoubang
 * 
 */
public class SpringBeanUtil implements ServletContextListener {

    private static WebApplicationContext springContext;

    public SpringBeanUtil() {
        super();
    }

    public void contextInitialized(ServletContextEvent event) {
        springContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
    }

    public void contextDestroyed(ServletContextEvent event) {
    }

    public static ApplicationContext getApplicationContext() {
        return springContext;
    }
    public static Object getBean(String beanName) {  
        return springContext.getBean(beanName);  
    }  
      
    public static <T>T getBean(String beanName , Class<T>clazz) {  
        return springContext.getBean(beanName , clazz);  
    }  
}
