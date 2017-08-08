package tlb.mall.user.rpc.api.activemq;


import tlb.mall.common.util.activemq.order.OrderSync;
import tlb.mall.entity.order.OrderInfo;
import tlb.mall.entity.sys.SysUser;

public interface QueueMessageService {

    /**
     * 发送订单同步点对点消息
     * @description 
     * 
     * @author zhoubang 
     * @date 2017年3月15日 下午7:36:36 
     * 
     * @param order
     */
    public void sendOrderSyncQueueMessage(OrderInfo order);
    
    
    /**
     * 订单同步，发送微信模板消息
     * @description 
     * 
     * @author zhoubang 
     * @date 2017年3月15日 下午7:46:34 
     *
     */
    public void sendOrderSyncTemplateMessage(OrderSync orderSync);
    
    
    /**
     * 用户关注微信，发送模板消息通知
     * @description 
     * 
     * @author zhoubang 
     * @date 2017年3月15日 下午7:52:59 
     * 
     * @param user
     */
    public void sendWechatSubscribeQueueMessage(SysUser user);
}
