import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tlb.mall.entity.sys.SysUser;
import tlb.mall.user.dao.UserDaoConfiguration;
import tlb.mall.user.rpc.api.user.UserService;
import tlb.mall.user.rpc.service.UserServiceConfiguration;

import java.util.List;

/**
 * Created by tianlinbo on 2017/8/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {UserServiceConfiguration.class, UserDaoConfiguration.class})
public class ServiceTest {
    @Autowired
    UserService userService;

    @Test
    public void getUserByName() {
        SysUser user = userService.getUserByName("admin");
        System.out.print(user);
    }
}
