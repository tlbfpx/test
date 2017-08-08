package tlb.mall.entity.activiti.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @description 请假历史VO
 * 
 * @author zhoubang
 * @date 2017年4月1日 下午3:50:31
 *
 */
public class LeaveApplyHistoryVo implements Serializable {

    private static final long serialVersionUID = 6003545985506055560L;

    private String businessKey;// 业务key，实际上就是请假记录的ID
    private String processInstanceId;// 流程实例ID
    private String userName;// 申请人姓名
    private String typeName;// 请假类型
    private Date createTime;// 申请时间

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
