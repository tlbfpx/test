package tlb.mall.user.dao.autoconfig;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by tianlinbo on 2017/8/7.
 */


//@Configuration
//@DisconfFile(filename = "jdbc.properties")
//@DisconfUpdateService(classes = JdbcConfig.class)
public class JdbcConfig{

//    private String driver;
//    private String url;
//    private String username;
//    private String password;
////    private int maxActive;
////    private int maxIdel;
////    private long maxWait;
//
//    @DisconfFileItem(name = "jdbc.driverClassName", associateField = "driver")
//    public String getDriver() {
//        return driver;
//    }
//
//    public void setDriver(String driver) {
//        this.driver = driver;
//    }
//
//    @DisconfFileItem(name = "jdbc.url", associateField = "url")
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    @DisconfFileItem(name = "jdbc.username", associateField = "username")
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    @DisconfFileItem(name = "jdbc.password", associateField = "password")
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

//    @DisconfFileItem(name = "jdbc.maxActive", associateField = "maxActive")
//    public int getMaxActive() {
//        return maxActive;
//    }
//
//    public void setMaxActive(int maxActive) {
//        this.maxActive = maxActive;
//    }
//
//    @DisconfFileItem(name = "jdbc.maxIdel", associateField = "maxIdel")
//    public int getMaxIdel() {
//        return maxIdel;
//    }
//
//    public void setMaxIdel(int maxIdel) {
//        this.maxIdel = maxIdel;
//    }
//
//    @DisconfFileItem(name = "jdbc.maxWait", associateField = "maxWait")
//    public long getMaxWait() {
//        return maxWait;
//    }
//
//    public void setMaxWait(long maxWait) {
//        this.maxWait = maxWait;
//    }

//    public String toString(){
//        return "JdbcConfig [ url: " + this.getUrl() + ", driver: " + this.getDriver() + ", username: " + this.getUsername() + ",password: " + this.getPassword() + "]";
//    }


//    @Bean
//    public BasicDataSource dataSource(JdbcConfig jdbcConfig ) {
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName(jdbcConfig.getDriver());
//        dataSource.setUrl(jdbcConfig.getUrl());
//        dataSource.setUsername(jdbcConfig.getUsername());
//        dataSource.setPassword(jdbcConfig.getPassword());
//        return dataSource;
//    }

}
