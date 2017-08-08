package tlb.mall.entity.sys.vo;



import tlb.mall.common.util.enums.Status;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * 创建日期：2016年8月14日 下午1:50:55
 * 操作用户：zhoubang
 * 
 */
public class RoleVo implements Serializable{
    
    private static final long serialVersionUID = 763228346704155487L;

    private String id;

    private String name;// 名称
    private String description;// 描述
    private Status status;// 状态
    private Date updateTime;// 最后更新时间
    private Date createTime;// 用户创建时间
    
    private String statusName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatusName() {
        return status.getDescription();
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    
    
}



