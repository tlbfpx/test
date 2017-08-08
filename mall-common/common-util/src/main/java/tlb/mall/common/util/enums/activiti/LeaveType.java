package tlb.mall.common.util.enums.activiti;


import tlb.mall.common.util.enums.entity.BaseEntityEnum;

/**
 * 
 * @description OA请假类型
 * 
 * @author zhoubang
 * @date 2017年3月29日 下午5:31:15
 *
 */
public enum LeaveType implements BaseEntityEnum<LeaveType> {

    THING_LEAVE(0, "事假"), SICK_LEAVE(1, "病假"), ANNUAL_LEAVE(2, "年假"), FUNERAL_LEAVE(3, "丧假"), MATERNITY_LEAVE(4, "产假");

    private int type;
    private String description;

    private LeaveType(int type, String description) {
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

    @Override
    public int getIntValue() {
        return this.type;
    }

}