package tlb.mall.entity.sys.qo;

import java.io.Serializable;

public class RoleQo implements Serializable{

    private static final long serialVersionUID = 9069414783397826105L;
    
    private Long roleId;// 角色id

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}
