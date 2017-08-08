package tlb.mall.common.util.annotation;

import org.springframework.web.bind.annotation.Mapping;
import tlb.mall.common.util.enums.SignInterceptStatus;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface SignInterceptor {

    /**
     * 标识是否需要进行sign验签
     * 
     */
    SignInterceptStatus signIntercept() default SignInterceptStatus.NO;
}
