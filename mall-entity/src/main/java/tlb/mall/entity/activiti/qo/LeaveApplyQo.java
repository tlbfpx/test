package tlb.mall.entity.activiti.qo;



import tlb.mall.common.util.enums.activiti.LeaveType;

import java.io.Serializable;

/**
 * 
 * @description OA请假申请QO
 * 
 * @author zhoubang
 * @date 2017年3月30日 上午9:53:41
 *
 */
public class LeaveApplyQo implements Serializable {

    private static final long serialVersionUID = -6915457227939911169L;

    private Long userId;
    private LeaveType type;
    private String startDate;
    private String endDate;
    private String reason;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LeaveType getType() {
        return type;
    }

    public void setType(LeaveType type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
