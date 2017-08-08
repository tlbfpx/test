package tlb.mall.common.disconf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tianlinbo on 2017/8/4.
 */
//@ComponentScan(basePackages = {"tlb.mall.common.disconf"})
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
//@ImportResource({"classpath:spring-disconf.xml"})
//@SpringBootApplication
//@RestController
public class DisconfConfiguration {
    public static void main(String[] args){
        SpringApplication.run(DisconfConfiguration.class,args);

    }

}
