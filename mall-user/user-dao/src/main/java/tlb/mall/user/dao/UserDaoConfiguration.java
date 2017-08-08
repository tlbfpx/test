package tlb.mall.user.dao;

import org.junit.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;
import tlb.mall.entity.sys.SysUser;
import tlb.mall.user.dao.autoconfig.JdbcConfig;
import tlb.mall.user.dao.autoconfig.MyBatisConfig;
import tlb.mall.user.dao.sys.UserMapper;

import java.util.List;

/**
 * Created by tianlinbo on 2017/8/4.
 */
@ComponentScan("tlb.mall")
@SpringBootApplication
public class UserDaoConfiguration {

    public static void main(String[] args){
        SpringApplication.run(UserDaoConfiguration.class,args);
    }
}
