package tlb.mall.user.rpc.service;

import org.springframework.stereotype.Service;
import tlb.mall.user.rpc.api.HelloWordService;


/**
 * 接口实现（测试调试使用）
 * 
 * @description
 * 
 * @author zhoubang
 * @date 2017年3月3日 下午3:13:44
 *
 */
@Service("helloWordServiceImpl")
public class HelloWordServiceImpl implements HelloWordService {

    @Override
    public String sayHello(String msg) throws Exception {
        return "dubbo服务成功返回：" + msg;
    }

}
