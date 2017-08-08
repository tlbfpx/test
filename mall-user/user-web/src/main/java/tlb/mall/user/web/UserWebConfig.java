package tlb.mall.user.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by tianlinbo on 2017/8/8.
 */
@ImportResource({"classpath:spring-disconf.xml"})
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan("tlb.mall")
public class UserWebConfig {

    public static void main(String[] args){
        SpringApplication.run(UserWebConfig.class,args);
    }
}
