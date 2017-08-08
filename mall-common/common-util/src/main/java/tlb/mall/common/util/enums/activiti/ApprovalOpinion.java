package tlb.mall.common.util.enums.activiti;


import tlb.mall.common.util.enums.entity.BaseEntityEnum;

/**
 * 
 * @description 审批意见
 * 
 * @author zhoubang 
 * @date 2017年4月1日 下午12:58:10 
 *
 */
public enum ApprovalOpinion implements BaseEntityEnum<ApprovalOpinion> {

    AFREE(0, "同意"), REJECT(1, "拒绝");

    private int type;
    private String description;

    private ApprovalOpinion(int type, String description) {
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