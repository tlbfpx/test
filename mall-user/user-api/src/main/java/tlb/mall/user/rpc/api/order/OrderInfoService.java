package tlb.mall.user.rpc.api.order;


import tlb.mall.common.util.AjaxResult;
import tlb.mall.entity.order.OrderInfo;
import tlb.mall.user.rpc.api.BaseService;

public interface OrderInfoService extends BaseService<OrderInfo> {

    /**
     * 订单同步
     * 
     * 操作人: zhoubang 日期：2016年10月19日 下午12:52:53
     *
     * @param order
     * @throws Exception
     */
    void orderSync(OrderInfo order, AjaxResult<String> result) throws Exception;
}
