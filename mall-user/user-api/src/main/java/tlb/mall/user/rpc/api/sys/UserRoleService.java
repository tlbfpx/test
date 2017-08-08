package tlb.mall.user.rpc.api.sys;


import tlb.mall.entity.sys.SysUser;
import tlb.mall.entity.sys.SysUserRole;
import tlb.mall.user.rpc.api.BaseService;

public interface UserRoleService extends BaseService<SysUserRole> {

    /**
     * 删除用户角色
     * 
     * 日期：2016年8月20日 下午3:05:47
     * 用户：zhoubang
     * 
     * @param userId
     * @throws Exception
     */
    void deleteByUserId(Long userId) throws Exception;

    /**
     * 更新用户角色
     * 
     * 日期：2016年8月20日 下午3:04:20
     * 用户：zhoubang
     * 
     * @param user
     */
    void updateUserRole(SysUser user, Long[] roleIds)  throws Exception;

    /**
     * 校验该角色是否被用户绑定使用
     * 
     * 日期：2016年8月20日 下午6:03:57
     * 用户：zhoubang
     * 
     * @param roleId
     * @return
     */
    boolean checkRoleIsBindUser(Long roleId);

}
