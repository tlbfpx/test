package tlb.mall.entity.activiti.vo;

import java.io.Serializable;

public class LeaveApplyVo implements Serializable {

    private static final long serialVersionUID = -1519908162453796274L;

    private String activityId;
    private String businessKey;
    private String executionId;
    private String processInstanceId;//流程实例ID
    private String name;//当前任务节点名称
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

}
