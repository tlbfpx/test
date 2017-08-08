package tlb.mall.user.web.activemq.queue.order;




/**
 * 订单同步：点对点消息生产者
 * 
 * 作者: zhoubang 
 * 日期：2015年9月28日 上午10:07:56
 */
//@Component
public class OrderSyncQueueMessageProducer {
    
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    /**
//     * spring消息发送模版
//     */
//    @Resource(name="baseMessageConverterQueueJmsTemplate")
//    private JmsTemplate jmsTemplate;
//
//    /**
//     * 消息目的地
//     */
//    @Resource(name = "orderSyncQueueDestination")
//    private Queue orderSyncQueueDestination;
//
//    /**
//     * 消息目的地---微信模板消息
//     */
//    @Resource(name = "orderSyncWechatTemplateMessageQueueDestination")
//    private Queue orderSyncWechatTemplateMessageQueueDestination;
//
//    /**
//     * 发送消息
//     *
//     * 作者: zhoubang
//     * 日期：2015年9月28日 上午10:18:54
//     * @param personInfo
//     */
//    public void sendQueueMessage(OrderInfo order) {
//        // getJmsTemplate().convertAndSend(order);//如果配置文件中指定了目的地，可以使用这句话发送消息。
//
//        logger.debug("OrderSyncQueueMessageProducer【订单同步】， 消息生产者开始发送消息：", JsonUtil.toJson(order));
//
//        //目的地、模版，都是通过注入方式引入，并不是通过配置bean的方式引入.
//        getJmsTemplate().convertAndSend(this.orderSyncQueueDestination, order);
//    }
//
//    public void sendOrderSyncTemplateMessage(OrderSync orderSync) throws JMSException {
//        logger.debug("OrderSyncQueueMessageProducer【订单同步，发送微信模板消息】， 消息生产者开始发送消息：", JsonUtil.toJson(orderSync));
//
//        //目的地、模版，都是通过注入方式引入，并不是通过配置bean的方式引入.
//        getJmsTemplate().convertAndSend(this.orderSyncWechatTemplateMessageQueueDestination, orderSync);
//
//        // 发送之后，这里变成消息消费者，等待那边给我发送确认消息
//        /*Connection connection = getJmsTemplate().getConnectionFactory().createConnection();
//        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
//        Destination recall_destination = session.createQueue("recall_queue");
//        MessageConsumer replyConsumer =session.createConsumer(recall_destination);
//         // 这里我们用个消息监听
//         replyConsumer.setMessageListener(new MessageListener() {
//             @Override
//             public void onMessage(Message message) {
//                 TextMessage textMessage = (TextMessage) message;
//                 try {
//                     System.out.println(textMessage.getText());
//                 } catch (JMSException e) {
//                     e.printStackTrace();
//                 }
//             }
//         });*/
//    }
//
//
//    public JmsTemplate getJmsTemplate() {
//        return jmsTemplate;
//    }
//
//    public void setJmsTemplate(JmsTemplate jmsTemplate) {
//        this.jmsTemplate = jmsTemplate;
//    }
//
//    public Queue getDefaultDestination() {
//        return orderSyncQueueDestination;
//    }
//
//    public void setDefaultDestination(Queue defaultDestination) {
//        this.orderSyncQueueDestination = defaultDestination;
//    }


}
