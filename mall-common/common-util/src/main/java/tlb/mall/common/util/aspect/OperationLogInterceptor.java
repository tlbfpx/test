package tlb.mall.common.util.aspect;

import com.google.common.collect.Maps;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import tlb.mall.common.util.annotation.SignInterceptor;
import tlb.mall.common.util.enums.SignInterceptStatus;


import java.lang.reflect.Method;
import java.util.Map;

@Aspect
@Component
public class OperationLogInterceptor {

    private Logger logger = LoggerFactory.getLogger(OperationLogInterceptor.class);
    
    @Pointcut("@annotation(tlb.mall.common.util.annotation.SignInterceptor)") // 这个是拦截带有@SignInterceptor注解的方法
    private void myMethods() {
    };

    @Around("myMethods()")
    public Object doMethod(ProceedingJoinPoint point) {
        Object obj = null;
        try {
            obj = point.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
            logger.error("方法执行异常了");
        }
        try {
            Object target = point.getTarget();
            String method = point.getSignature().getName();
            Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();

            Method m = target.getClass().getMethod(method, parameterTypes);

            // 可以对自定义注解进行处理
            SignInterceptor signInterceptor = m.getAnnotation(SignInterceptor.class);

            SignInterceptStatus signInterceptStatus = signInterceptor.signIntercept();
            if (signInterceptStatus == SignInterceptStatus.YES) {
                logger.debug("需要验签");
            } else {
                logger.debug("不需要验签");
            }
        } catch (Exception e) {
        }
        return obj;
    }

    public static void main(String[] args) throws NoSuchMethodException {
        StandardEvaluationContext context = new StandardEvaluationContext();
        Map<String, Object> namedParams = Maps.newHashMap();
        namedParams.put("param2", 0);
        namedParams.put("param1", 1);
        namedParams.put("param0", 10);
        context.setVariables(namedParams);
        ExpressionParser parser = new SpelExpressionParser();
        String randomPhrase = parser.parseExpression("#{#param0}", new TemplateParserContext()).getValue(context,  String.class);
        System.out.print(randomPhrase);
    }
}