package tlb.mall.user.web.activemq.queue.order;



/**
 * 点对点消息生产者。
 * 
 *      微信关注事件，发送一条微信消息。
 *      目前只是测试发送用户的openid。
 * 
 * 作者: zhoubang 
 * 日期：2015年9月28日 上午10:07:56
 */
//@Component
public class WechatSubscribeQueueMessageProducer {
    
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
//    @Resource(name = "wechatSubscribeQueueDestination")
//    private Queue wechatSubscribeQueueDestination;
//
//    /**
//     * 发送消息
//     *
//     * 作者: zhoubang
//     * 日期：2015年9月28日 上午10:18:54
//     * @param personInfo
//     */
//    public void sendQueueMessage(SysUser user) {
//        // getJmsTemplate().convertAndSend(user);//如果配置文件中指定了目的地，可以使用这句话发送消息。
//
//        logger.debug("WechatSubscribeQueueDestination【微信关注发送openid消息】， 消息生产者开始发送消息：", JsonUtil.toJson(user));
//
//        //目的地、模版，都是通过注入方式引入，并不是通过配置bean的方式引入.
//        getJmsTemplate().convertAndSend(this.wechatSubscribeQueueDestination, user);
//    }
//
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
//    public Queue getWechatSubscribeQueueDestination() {
//        return wechatSubscribeQueueDestination;
//    }
//
//    public void setWechatSubscribeQueueDestination(Queue wechatSubscribeQueueDestination) {
//        this.wechatSubscribeQueueDestination = wechatSubscribeQueueDestination;
//    }

}
