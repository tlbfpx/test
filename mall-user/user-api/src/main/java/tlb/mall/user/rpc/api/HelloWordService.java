package tlb.mall.user.rpc.api;

/**
 * 接口服务，交由dubbo暴露服务接口
 */
public interface HelloWordService {

    /**
     * 
     * @description 最简单的helloword（调试使用）
     * 
     * @author zhoubang
     * @date 2017年3月3日 下午2:40:26
     * 
     * @param msg
     * @return
     * @throws Exception
     */
    public String sayHello(String msg) throws Exception;
}
