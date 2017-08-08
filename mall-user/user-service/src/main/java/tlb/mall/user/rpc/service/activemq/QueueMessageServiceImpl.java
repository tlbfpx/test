package tlb.mall.user.rpc.service.activemq;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.stereotype.Service;
//import tlb.mall.common.util.activemq.order.OrderSync;
//import tlb.mall.common.util.util.JsonUtil;
//import tlb.mall.entity.order.OrderInfo;
//import tlb.mall.entity.sys.SysUser;
//import tlb.mall.user.rpc.api.activemq.QueueMessageService;


//import javax.annotation.Resource;
//import javax.jms.Queue;

//@Service("queueMessageServiceImpl")
public class QueueMessageServiceImpl{

//implements QueueMessageService {

//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    /**
//     * spring消息发送模版
//     */
//    @Resource(name = "baseMessageConverterQueueJmsTemplate")
//    private JmsTemplate jmsTemplate;
//
//    /**
//     * 订单同步消息目的地
//     */
//    @Resource(name = "orderSyncQueueDestination")
//    private Queue orderSyncQueueDestination;
//
//    /**
//     * 微信关注事件，消息目的地
//     */
//    @Resource(name = "wechatSubscribeQueueDestination")
//    private Queue wechatSubscribeQueueDestination;
//
//    /**
//     * 订单同步，发送微信模板消息通知， 消息目的地
//     */
//    @Resource(name = "orderSyncWechatTemplateMessageQueueDestination")
//    private Queue orderSyncWechatTemplateMessageQueueDestination;
//
//    @Override
//    public void sendOrderSyncQueueMessage(OrderInfo order) {
//        // getJmsTemplate().convertAndSend(order);//如果配置文件中指定了目的地，可以使用这句话发送消息。
//
//        logger.debug("OrderSyncQueueMessageProducer【订单同步】， 消息生产者开始发送消息：", JsonUtil.toJson(order));
//
//        // 目的地、模版，都是通过注入方式引入，并不是通过配置bean的方式引入.
//        jmsTemplate.convertAndSend(this.orderSyncQueueDestination, order);
//    }
//
//    @Override
//    public void sendOrderSyncTemplateMessage(OrderSync orderSync) {
//        logger.debug("OrderSyncQueueMessageProducer【订单同步，发送微信模板消息】， 消息生产者开始发送消息：", JsonUtil.toJson(orderSync));
//
//        // 目的地、模版，都是通过注入方式引入，并不是通过配置bean的方式引入.
//        jmsTemplate.convertAndSend(this.orderSyncWechatTemplateMessageQueueDestination, orderSync);
//
//        // 发送之后，这里变成消息消费者，等待那边给我发送确认消息
//        /*
//         * Connection connection =
//         * getJmsTemplate().getConnectionFactory().createConnection(); Session
//         * session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
//         * Destination recall_destination = session.createQueue("recall_queue");
//         * MessageConsumer replyConsumer
//         * =session.createConsumer(recall_destination); // 这里我们用个消息监听
//         * replyConsumer.setMessageListener(new MessageListener() {
//         *
//         * @Override public void onMessage(Message message) { TextMessage
//         * textMessage = (TextMessage) message; try {
//         * System.out.println(textMessage.getText()); } catch (JMSException e) {
//         * e.printStackTrace(); } } });
//         */
//    }
//
//    @Override
//    public void sendWechatSubscribeQueueMessage(SysUser user) {
//        // getJmsTemplate().convertAndSend(user);//如果配置文件中指定了目的地，可以使用这句话发送消息。
//
//        logger.debug("WechatSubscribeQueueDestination【微信关注发送openid消息】， 消息生产者开始发送消息：", JsonUtil.toJson(user));
//
//        // 目的地、模版，都是通过注入方式引入，并不是通过配置bean的方式引入.
//        jmsTemplate.convertAndSend(this.wechatSubscribeQueueDestination, user);
//    }

}
