package tlb.mall.user.server;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by tianlinbo on 2017/8/4.
 */
@ImportResource({"classpath:spring-disconf.xml"})
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan("tlb.mall")
public class UserServerConfiguration {

    public static void main(String[] args){
        SpringApplication.run(UserServerConfiguration.class,args);
    }
}
