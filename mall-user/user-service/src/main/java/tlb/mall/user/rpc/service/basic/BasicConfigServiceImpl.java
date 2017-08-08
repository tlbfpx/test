package tlb.mall.user.rpc.service.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tlb.mall.entity.basic.BasicConfig;
import tlb.mall.user.dao.basic.BasicConfigMapper;
import tlb.mall.user.rpc.api.basic.BasicConfigService;
import tlb.mall.user.rpc.service.BaseServiceImpl;

import java.util.List;

@Service("basicConfigServiceImpl")
public class BasicConfigServiceImpl extends BaseServiceImpl<BasicConfig> implements BasicConfigService {

    @Autowired
    private BasicConfigMapper basicConfigMapper;

    @Override
    public BasicConfig getConfigByName(String configName) {
        Example example = new Example(BasicConfig.class);
        example.createCriteria().andEqualTo("configName", configName);
        List<BasicConfig>  list = basicConfigMapper.selectByExample(example);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }
}
