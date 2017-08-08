package tlb.mall.entity.activiti.qo;

import java.io.Serializable;

public class ProcessQo implements Serializable {

    private static final long serialVersionUID = -4815652578119718069L;

    private String name;
    private String deploymentId;// 流程实例id

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
