package tlb.mall.user.web.interceptor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import tlb.mall.common.util.annotation.SignInterceptor;
import tlb.mall.common.util.enums.SignInterceptStatus;
import tlb.mall.common.util.util.JsonUtil;


/**
 * 对接口请求的参数进行签名验证。 也可以实现HandlerInterceptor接口
 */
public class wsInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(wsInterceptor.class);

    /**
     * 重写 preHandle()方法，在业务处理器处理请求之前对该请求进行拦截处理
     * 
     * 详细介绍、异常处理，请访问 http://chenzhou123520.iteye.com/blog/1702563
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        /**
         * 这部分代码，通过反向代理，获取请求的方法是否有@SignInterceptor注解，来判断该方法是否需要进行签名验证。很方便
         */
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {// isAssignableFrom是用来判断一个类Class1和另一个类Class2是否相同或是另一个类的超类或接口。
            // 方法名称
            String methodName = ((HandlerMethod) handler).getMethod().getName();

            SignInterceptor signInterceptor = ((HandlerMethod) handler).getMethodAnnotation(SignInterceptor.class);

            // 没有@SignInterceptor注解，则默认不需要进行验签
            if (signInterceptor == null) {
                logger.debug("不签名验证");
                return true;
            } else {
                // 有@SignInterceptor注解，则有2种情况。需要验签、不需要验签
                if (signInterceptor.signIntercept().equals(SignInterceptStatus.NO)) {
                    logger.debug(MessageFormat.format("方法{0}不需要进行验签", methodName));
                    return true;
                } else {
                    logger.debug(MessageFormat.format("方法{0}需要进行验签", methodName));
                }
            }
        } else {
            logger.debug("不签名验证");
            return true;
        }

        request.setCharacterEncoding("UTF-8");

        // 对于某些接口不需要进行sign验证
        // String servletPath = request.getServletPath();

        // 不进行sign签名验证
        // if(servletPathList.contains(servletPath)){
        // return true;
        // }

        // 客户端传输过来的参与签名的参数
        String signParma = null;

        // 标识是否有nonce_str参数，默认是没有
        boolean hasNonceStr = false;

        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        Enumeration<String> parmas = request.getParameterNames();

        logger.debug(" ");
        logger.debug("客户端传递的参数如下：");
        /**
         * 如果传递的不是JSON数据，这里才会获取到参数
         */
        while (parmas.hasMoreElements()) {// 遍历参数
            String paraName = parmas.nextElement();
            // paraName,request.getParameter(paraName)
            logger.debug(MessageFormat.format("{0}={1}", paraName, request.getParameter(paraName)));

            // 排除sign，sign不参与签名
            if (!paraName.equals("sign")) {
                // parameters.put(paraName, new
                // String(request.getParameter(paraName).getBytes("ISO-8859-1"),"UTF-8"));
                // 生产环境的时候，不需要进行转码。否则出现中文乱码。在本地调试，需要转码。
                parameters.put(paraName, request.getParameter(paraName));
            } else {
                // 获取客户端传输过来的sign签名
                signParma = request.getParameter(paraName);
            }
            // 判断是否有nonce_str参数
            if (paraName.equals("nonce_str")) {
                hasNonceStr = true;
            }
        }

        /**
         * 说明客户端是传递的JSON格式数据
         */
        if (parameters == null || parameters.isEmpty()) {
            logger.debug("拦截器————客户端传递的是JSON格式数据");
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/json;charset=UTF-8");

            // 读取请求JSON数据
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            String line = "";
            StringBuilder sbf = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sbf.append(line);
            }
            String postData = sbf.toString();
            if (postData == null || "".equals(postData)) {
                postData = "{}";
            }
            logger.debug(MessageFormat.format("拦截器————服务端接收到的客户端请求的JSON数据：{0}", postData));

            if (StringUtils.isNotBlank(postData)) {
                try {
                    Map<String, Object> jsonMap = JsonUtil.fromJson(postData);

                    // 设置签名参数
                    for (String jsonkey : jsonMap.keySet()) {
                        logger.debug(MessageFormat.format("JSON消息体：{0}={1}", jsonkey, jsonMap.get(jsonkey)));

                        // 排除sign，sign不参与签名
                        if (!jsonkey.equals("sign")) {
                            parameters.put(jsonkey, jsonMap.get(jsonkey));
                        } else {
                            // 获取客户端传输过来的sign签名
                            signParma = String.valueOf(jsonMap.get(jsonkey));
                        }
                        // 判断是否有nonce_str参数
                        if (jsonkey.equals("nonce_str")) {
                            hasNonceStr = true;
                        }

                        parameters.put(jsonkey, jsonMap.get(jsonkey));
                    }
                } catch (Exception e) {
                    logger.debug(MessageFormat.format("拦截器————JSON消息体转换异常，消息体是：{0}", postData));
                }
            }
        }
        return true;
    }

}
