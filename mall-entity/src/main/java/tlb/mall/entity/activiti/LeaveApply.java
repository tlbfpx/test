package tlb.mall.entity.activiti;



import tlb.mall.common.util.enums.activiti.LeaveType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @description OA请假申请实体
 * 
 * @author zhoubang
 * @date 2017年3月30日 上午10:07:50
 *
 */
public class LeaveApply implements Serializable {

    private static final long serialVersionUID = -3311932572648905556L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 自增id

    private String processInstanceId;// 流程实例id，用于请假申请和工作流之间的关联关系
    private LeaveType type;// 请假类型
    private String reason;// 请假事由
    private Long userId;// 申请人id
    private Date startDate;// 开始日期
    private Date endDate;// 结束日期

    private Date createTime;
    private Date updateTime;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LeaveType getType() {
        return type;
    }

    public void setType(LeaveType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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