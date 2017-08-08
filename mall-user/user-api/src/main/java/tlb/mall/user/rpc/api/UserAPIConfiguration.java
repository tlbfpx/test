package tlb.mall.user.rpc.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by tianlinbo on 2017/8/4.
 */
@EnableAutoConfiguration
@SpringBootApplication
public class UserAPIConfiguration {

    public static void main(String[] args){
        SpringApplication.run(UserAPIConfiguration.class,args);
    }
}
