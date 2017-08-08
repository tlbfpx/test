package tlb.mall.entity.basic;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 全局性配置
 * 
 * @author zhoubang
 * @date 2017年3月20日 下午2:41:38
 *
 */
public class BasicConfig implements Serializable {

    private static final long serialVersionUID = -3311932572648905556L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 自增id

    private String configName;// 配置名称
    private String configValue;// 配置值

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

}