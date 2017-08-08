package tlb.mall.entity.activiti.vo;



import tlb.mall.common.util.enums.activiti.LeaveType;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @description 部门经理审批请假申请VO
 * 
 * @author zhoubang
 * @date 2017年4月1日 上午10:58:35
 *
 */
public class DeptLeaderAuditVo implements Serializable {

    private static final long serialVersionUID = -1519908162453796274L;

    private Long userId;// 申请人id
    private String userName;// 申请人姓名

    private Date startDate;// 开始日期
    private Date endDate;// 结束日期

    private LeaveType type;// 请假类型
    private String typeName;// 请假类型

    private String reason;// 事由

    private String taskId;// 任务ID
    private String taskName;// 任务名称
    private String processInstanceId;// 流程实例ID
    private Date createTime;// 请假申请日期

    
    public LeaveType getType() {
        return type;
    }

    public void setType(LeaveType type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getTypeName() {
        return type.getDescription();
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
