package tlb.mall.user.rpc.api.basic;


import tlb.mall.entity.basic.BasicConfig;
import tlb.mall.user.rpc.api.BaseService;

public interface BasicConfigService extends BaseService<BasicConfig> {
    
    /**
     * 根据配置名称获取值
     * @description 
     * 
     * @author zhoubang 
     * @date 2017年3月20日 下午3:00:20 
     * 
     * @param configName
     * @return
     */
    public BasicConfig getConfigByName(String configName);
}
