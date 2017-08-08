package tlb.mall.user.rpc.api.sys;


import tlb.mall.common.util.AjaxResult;
import tlb.mall.common.util.Pager;
import tlb.mall.entity.sys.SysPermission;
import tlb.mall.entity.sys.vo.PermissionVo;
import tlb.mall.entity.vo.ZtreeVo;
import tlb.mall.user.rpc.api.BaseService;

import java.util.List;


public interface PermissionService extends BaseService<SysPermission> {

    /**
     * 获取用户拥有的权限列表
     * 
     * 日期：2016年8月14日 下午2:04:35
     * 用户：zhoubang
     * 
     * @param userName
     * @return
     */
    List<SysPermission> getUserPermissions(String userName);

    /**
     * 获取所有权限列表tree对象
     * 
     * 日期：2016年8月14日 下午2:04:44
     * 用户：zhoubang
     * 
     * @param isShowTopParent 是否显示顶层父节点
     * @return
     */
    List<ZtreeVo> queryAllFormatWithZtree(boolean isShowTopParent);

    /**
     * 分页获取权限列表
     * 
     * 日期：2016年8月14日 下午7:43:51
     * 用户：zhoubang
     * 
     * @param pager
     * @param permission
     * @return
     */
    Pager<PermissionVo> getList(Pager<PermissionVo> pager, SysPermission permission);

    /**
     * 更新权限
     * 
     * 日期：2016年8月20日 下午6:17:28
     * 用户：zhoubang
     * 
     * @param permission
     * @throws Exception
     */
    void updatePermission(SysPermission permission) throws Exception;

    /**
     * 删除权限
     * 
     * 日期：2016年8月20日 下午6:31:03
     * 用户：zhoubang
     * 
     * @param permission
     * @param result
     * @throws Exception
     */
    AjaxResult<String> deletePermission(Long permissionId) throws Exception;

    /**
     * 根据code获取权限
     * @param code
     * @return
     * @throws Exception
     */
    SysPermission selectByCode(String code) throws Exception;

}
