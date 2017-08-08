package tlb.mall.user.web.activemq;



/**
 * 消息转换器
 * 
 * 作者: zhoubang 
 * 日期：2015年9月28日 下午12:17:11
 */
//@Component("activeMQMessageConverter")
public class ActiveMQMessageConverter {
        //implements MessageConverter {
    
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    /**
//     * 将发送的实体bean对象封装为Message消息
//     *  对已经实现MessageListener接口的消费者产生作用
//     *
//     * 作者: zhoubang
//     * 日期：2015年9月28日 上午10:46:31
//     * @param obj
//     * @param session
//     * @return
//     * @throws JMSException
//     * (non-Javadoc)
//     * @see org.springframework.jms.support.converter.MessageConverter#toMessage(Object, javax.jms.Session)
//     */
//    public Message toMessage(Object obj, Session session) throws JMSException {
//        logger.debug(MessageFormat.format("将实体bean对象转换为Message消息:{0}", obj));
//
//        //发送的消息对象类型是PersonInfo
//        if (obj instanceof PersonInfo) {
//            ActiveMQObjectMessage msg = (ActiveMQObjectMessage) session.createObjectMessage();
//            msg.setObject((PersonInfo) obj);
//            return msg;
//        }else if (obj instanceof OrderInfo) {
//            ActiveMQObjectMessage msg = (ActiveMQObjectMessage) session.createObjectMessage();
//            msg.setObject((OrderInfo) obj);
//            return msg;
//        }else if(obj instanceof OrderSync){
//            ActiveMQObjectMessage msg = (ActiveMQObjectMessage) session.createObjectMessage();
//            msg.setObject((OrderSync) obj);
//            return msg;
//        } else {//这里可以指定其他的消息类型
//            throw new JMSException("Object:[" + obj + "] is not a instance.");
//        }
//    }
//
//    /**
//     * 将消息对象转换为对应的实体bean并返回
//     *  只对未实现MessageListener接口的消息消费者产生作用，其他的已经实现MessageListener接口的，不会执行该方法
//     *
//     * 作者: zhoubang
//     * 日期：2015年9月28日 上午10:46:59
//     * @param message
//     * @return
//     * @throws JMSException
//     * (non-Javadoc)
//     * @see org.springframework.jms.support.converter.MessageConverter#fromMessage(javax.jms.Message)
//     */
//    public Object fromMessage(Message message) throws JMSException {
//        logger.debug(MessageFormat.format("执行ActiveMQMessageConverter消息转换器 ,message信息如下:{0}", message.toString()));
//        if (message instanceof ObjectMessage) {
//            ObjectMessage oMsg = (ObjectMessage) message;
//            if (oMsg instanceof ActiveMQObjectMessage) {
//                ActiveMQObjectMessage aMsg = (ActiveMQObjectMessage) oMsg;
//                try {
//                    Object obj = aMsg.getObject();
//                    if (obj instanceof PersonInfo) {
//                        PersonInfo personInfo = (PersonInfo) aMsg.getObject();
//                        personInfo.setUserName(personInfo.getUserName() + "，这是二次加工后的用户名.");
//                        //这里可以对实体bean的属性进行其他处理
//                        logger.debug(MessageFormat.format("对实体bean进行二次加工后的结果:{0}", personInfo.toString()));
//
//                        //对消息二次加工之后，返回最终的消息
//                        return personInfo;
//                    }else if (obj instanceof OrderInfo) {
//                        OrderInfo order = (OrderInfo) aMsg.getObject();
//                        order.setGoodName(order.getGoodName() + ",[来自消息队列]");
//                        //这里可以对实体bean的属性进行其他处理
//                        logger.debug(MessageFormat.format("对实体bean进行二次加工后的结果:{0}", order.toString()));
//
//                        //对消息二次加工之后，返回最终的消息
//                        return order;
//                    } else {//这里可以指定其他的消息类型
//                        logger.debug(MessageFormat.format("Object:[{0}] is not a instance.", obj));
//                        throw new JMSException("Object:[" + obj + "] is not a instance.");
//                    }
//                } catch (Exception e) {
//                    logger.debug(MessageFormat.format("Message:[{0}] is not a instance.", message));
//                    throw new JMSException("Message:[" + message + "] is not a instance.");
//                }
//            } else {
//                logger.debug(MessageFormat.format("Message:[{0}] is not a instance of ActiveMQObjectMessage.", message));
//                throw new JMSException("Message:[" + message + "] is not " + "a instance of ActiveMQObjectMessage.");
//            }
//        } else {
//            logger.debug(MessageFormat.format("Message:[{0}] is not a instance of ObjectMessage.", message));
//            throw new JMSException("Message:[" + message + "] is not a instance of ObjectMessage.");
//        }
//    }

    
}
