package tlb.mall.user.web.dubbo;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tlb.mall.common.dubbo.DubboBase;
import tlb.mall.user.rpc.api.user.UserService;

/**
 * Created by tianlinbo on 2017/8/8.
 */

@Configuration
public class UserServiceConsumer extends DubboBase {

    @Bean
    public ReferenceBean<UserService> person() {
        ReferenceBean<UserService> ref = new ReferenceBean<UserService>();
        ref.setVersion("myversion");
        ref.setInterface(UserService.class);
        ref.setTimeout(5000);
        ref.setRetries(3);
        ref.setCheck(false);
        return ref;
    }
}
