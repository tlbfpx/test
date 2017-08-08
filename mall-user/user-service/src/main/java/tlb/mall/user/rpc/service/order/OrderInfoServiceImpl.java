package tlb.mall.user.rpc.service.order;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tlb.mall.common.util.AjaxResult;
import tlb.mall.common.util.enums.order.OrderSource;
import tlb.mall.entity.order.OrderInfo;
import tlb.mall.user.rpc.api.activemq.QueueMessageService;
import tlb.mall.user.rpc.api.order.OrderInfoService;
import tlb.mall.user.rpc.service.BaseServiceImpl;


//@Service("orderServiceImpl")
public class OrderInfoServiceImpl extends BaseServiceImpl<OrderInfo> implements OrderInfoService {
    
    /**
     * 点对点消息生产者
     */
    //@Autowired
    private QueueMessageService queueMessageService;
    
    //@Autowired
    //private OrderMapper orderMapper;
    
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    public void orderSync(OrderInfo order,AjaxResult<String> result) throws Exception {
        boolean bool = invalideOrder(order, result);
        
        //订单信息无误，发送订单同步消息
        if(bool){
            queueMessageService.sendOrderSyncQueueMessage(order);
        }
    }
    
    
    public static boolean invalideOrder(OrderInfo order,AjaxResult<String> result){
        if(order == null){
            result.setCode(10001);
            result.setMsg("订单信息为空");
            return false;
        }
        if(StringUtils.isBlank(order.getOrderNo())){
            result.setCode(10002);
            result.setMsg("订单号不能为空");
            return false;
        }
        if(StringUtils.isBlank(order.getGoodName())){
            result.setCode(10003);
            result.setMsg("订单商品名称不能为空");
            return false;
        }
        if(order.getTotalFee() <= 0){
            result.setCode(10004);
            result.setMsg("订单金额为空或者金额小于等于0");
            return false;
        }

        if(!OrderSource.exists(order.getOrderSource())){
            result.setCode(10005);
            result.setMsg("订单来源有误");
            return false;
        }
        return true;
    }
}
