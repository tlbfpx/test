package tlb.mall.entity.order;



import tlb.mall.common.util.enums.order.OrderSource;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 订单
 * 
 * 操作人: zhoubang 日期：2016年10月19日 上午11:58:13
 *
 */
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = -3311932572648905556L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 自增id

    private String orderNo;// 订单编号
    private long totalFee;// 订单总金额
    private String goodName;// 商品名称
    private OrderSource orderSource;// 订单来源

    private Date createTime;
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(long totalFee) {
        this.totalFee = totalFee;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public OrderSource getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(OrderSource orderSource) {
        this.orderSource = orderSource;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}