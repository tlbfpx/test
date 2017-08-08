package tlb.mall.user.server.dubbo;


import com.alibaba.dubbo.config.spring.ServiceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tlb.mall.common.dubbo.DubboBase;
import tlb.mall.entity.sys.SysUser;
import tlb.mall.user.rpc.api.user.UserService;

/**
 * Created by tianlinbo on 2017/8/8.
 */
@Configuration
public class UserServerConfig extends DubboBase {

    @Bean
    public ServiceBean<UserService> personServiceExport(UserService userService) {
        ServiceBean<UserService> serviceBean = new ServiceBean<UserService>();
        serviceBean.setProxy("javassist");
        serviceBean.setVersion("myversion");
        serviceBean.setInterface(UserService.class.getName());
        serviceBean.setRef(userService);
        serviceBean.setTimeout(5000);
        serviceBean.setRetries(3);
        return serviceBean;
    }

}
