package tlb.mall.common.util.enums.order;


import tlb.mall.common.util.enums.entity.BaseEntityEnum;

public enum OrderSource implements BaseEntityEnum<OrderSource> {

    BACK_SYSTEM(0, "后台"), WECHAT(1, "微信"), IOS(2,"苹果"), ANDROID(3,"安卓");

    private int type;
    private String description;
    
    private OrderSource(int type, String description) {
        this.type = type;
        this.description = description;
    }
    
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 检测是否存在该来源
     * 
     * 操作人: zhoubang 日期：2016年10月19日 下午1:10:35
     *
     * @param orderSource
     * @return
     */
    public static boolean exists(OrderSource orderSource) {
        if(orderSource == null){
            return false;
        }
        boolean bool = false;
        OrderSource[] orderSources = OrderSource.values();
        for (OrderSource source : orderSources) {
            if(source == orderSource){
                bool = true;
                break;
            }
        }
        return bool;
    }

    @Override
    public int getIntValue() {
        return this.type;
    }

    
}