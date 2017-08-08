import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tlb.mall.user.dao.UserDaoConfiguration;
import tlb.mall.user.dao.sys.UserMapper;

import java.util.List;

/**
 * Created by tianlinbo on 2017/8/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UserDaoConfiguration.class)
public class DaoTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void getUserByName(){
        List list = userMapper.getUserByName("admin");
        System.out.print(list);
    }
}
