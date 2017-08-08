package tlb.mall.user.rpc.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tlb.mall.entity.sys.SysUser;
import tlb.mall.user.rpc.api.user.UserService;

/**
 * Created by tianlinbo on 2017/8/4.
 */

@SpringBootApplication
@ComponentScan("tlb.mall")
//@RestController
public class UserServiceConfiguration {

//    @Autowired
//    UserService userService;
//    @RequestMapping("/getUser")
//    public SysUser getUser(){
//        SysUser user = userService.getUserByName("admin");
//        return user;
//    }
    public static void main(String[] args){
        SpringApplication.run(UserServiceConfiguration.class,args);
    }
}
