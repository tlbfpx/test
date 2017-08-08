package tlb.mall.user.rpc.api.activemq;


import tlb.mall.common.util.activemq.PersonInfo;

public interface TopicMessageService {

    
    /**
     * 发送 【发布/订阅】 消息
     * @description 
     * 
     * @author zhoubang 
     * @date 2017年3月15日 下午8:11:57 
     * 
     * @param personInfo
     */
    public void sendTopicMessage(PersonInfo personInfo);
    
}
