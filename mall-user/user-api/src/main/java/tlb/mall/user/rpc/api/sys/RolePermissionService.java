package tlb.mall.user.rpc.api.sys;


import tlb.mall.entity.sys.SysRole;
import tlb.mall.entity.sys.SysRolePermission;
import tlb.mall.user.rpc.api.BaseService;

import java.util.List;

public interface RolePermissionService extends BaseService<SysRolePermission> {

    /**
     * 获取角色拥有的权限列表
     * 
     * 日期：2016年8月20日 下午5:00:00
     * 用户：zhoubang
     * 
     * @param roleId
     * @return
     */
    List<SysRolePermission> getPermissionByRoleId(Long roleId);

    /**
     * 更新角色权限
     * 
     * 日期：2016年8月20日 下午5:26:57
     * 用户：zhoubang
     * 
     * @param role
     * @param permissionIds
     * @throws Exception
     */
    void updateRolePermission(SysRole role, Long[] permissionIds) throws Exception ;

    /**
     * 删除角色权限
     * 
     * 日期：2016年8月20日 下午5:56:33
     * 用户：zhoubang
     * 
     * @param role
     * @throws Exception
     */
    void deleteRolePermission(SysRole role) throws Exception ;

    /**
     * 检查该权限是否被分配给角色
     * 
     * 日期：2016年8月20日 下午6:33:42
     * 用户：zhoubang
     * 
     * @param id
     * @return
     */
    boolean checkPermissionIsBindRole(Long id);

}
