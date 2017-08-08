package tlb.mall.common.dubbo;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.springframework.context.annotation.Configuration;

/**
 * Created by tianlinbo on 2017/8/8.
 */
@Configuration
@DisconfFile(filename = "zookeeper.properties")
public class ZooKeeperConifg {

    private String  url;
    private int  port;
    private String  protecol;
    private String appName;

    @DisconfFileItem(name = "dubbo.appname", associateField = "appName")
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }


    @DisconfFileItem(name = "zookeeper.register.center.url", associateField = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @DisconfFileItem(name = "zookeeper.service.port", associateField = "port")
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @DisconfFileItem(name = "zookeeper.protecol", associateField = "protecol")
    public String getProtecol() {
        return protecol;
    }

    public void setProtecol(String protecol) {
        this.protecol = protecol;
    }
}
