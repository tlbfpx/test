package tlb.mall.user.rpc.service.activemq;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.stereotype.Service;
//import tlb.mall.common.util.activemq.PersonInfo;
//import tlb.mall.user.rpc.api.activemq.TopicMessageService;

//
//import javax.annotation.Resource;
//import javax.jms.Topic;

//@Service("topicMessageServiceImpl")
public class TopicMessageServiceImpl {//implements TopicMessageService {

//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    /**
//     * 消息发送模版
//     */
//    @Resource(name="topicJmsTemplate")
//    private JmsTemplate jmsTemplate;
//
//
//    /**
//     * 消息目的地
//     */
//    @Resource(name = "topicDestination")
//    private Topic defaultDestination;
//
//    @Override
//    public void sendTopicMessage(PersonInfo personInfo) {
//        // getJmsTemplate().convertAndSend(personInfo);//如果配置文件中指定了目的地，可以使用这句话发送消息。
//
//        logger.debug("发布/订阅 TopicMessageProducer 消息生产者开始发送消息...");
//
//        //目的地、模版，都是通过注入方式引入，并不是通过配置bean的方式引入.
//
//        jmsTemplate.convertAndSend(this.defaultDestination, personInfo);
//    }

}
