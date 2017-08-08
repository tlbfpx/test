package tlb.mall.user.dao.autoconfig;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tlb.mall.common.util.annotation.dynamicDataSource.DynamicDataSource;

import java.util.HashMap;

/**
 * Created by tianlinbo on 2017/8/4.
 */
@Configuration
//@Import(JdbcConfig.class)
@ConfigurationProperties(locations = "classpath:jdbc.properties",prefix = "jdbc")
public class DataSourceConfig {

    //@Value("com.mysql.jdbc.Driver")
    private String driverClassName;

    //@Value("jdbc:mysql://127.0.0.1:3306/zb_server?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true")
    private String url;

    //@Value("root")
    private String username;

    //@Value("abcd1234")
    private String password;

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

//    @Bean
//    public DynamicDataSource dataSource(JdbcConfig jdbcConfig) {
//        DynamicDataSource dataSource = new DynamicDataSource();
//        HashMap map = new HashMap();
//        BasicDataSource ds = new BasicDataSource();
//        ds.setDriverClassName(jdbcConfig.getDriver());
//        ds.setUrl(jdbcConfig.getUrl());
//        ds.setUsername(jdbcConfig.getUsername());
//        ds.setPassword(jdbcConfig.getPassword());
//        map.put("MasterDS", ds);
//        //map.put("SlaveDS", slaveDS);
//        dataSource.setTargetDataSources(map);
//        dataSource.setDefaultTargetDataSource(ds);
//        return dataSource;
//    }
}

