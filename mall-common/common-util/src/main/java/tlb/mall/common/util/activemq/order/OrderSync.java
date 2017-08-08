package tlb.mall.common.util.activemq.order;

import java.io.Serializable;

/**
 * 订单同步,消息队列发送模板消息
 * @author zhoubang
 *
 */
public class OrderSync implements Serializable {

    private static final long serialVersionUID = 789949814642878355L;

    private String openId;
    private String orderInfoJson;//由于订单bean在zb-bean中，所以这里使用json字符串表示对象

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOrderInfoJson() {
        return orderInfoJson;
    }

    public void setOrderInfoJson(String orderInfoJson) {
        this.orderInfoJson = orderInfoJson;
    }

}
