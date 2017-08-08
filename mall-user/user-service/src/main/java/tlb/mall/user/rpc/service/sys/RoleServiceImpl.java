package tlb.mall.user.rpc.service.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tlb.mall.common.util.AjaxResult;
import tlb.mall.common.util.Pager;
import tlb.mall.common.util.enums.Status;
import tlb.mall.entity.sys.SysRole;
import tlb.mall.entity.sys.SysRolePermission;
import tlb.mall.entity.sys.vo.RoleVo;
import tlb.mall.entity.vo.ZtreeVo;
import tlb.mall.user.dao.sys.RoleMapper;
import tlb.mall.user.dao.sys.RolePermissionMapper;
import tlb.mall.user.rpc.api.sys.RolePermissionService;
import tlb.mall.user.rpc.api.sys.RoleService;
import tlb.mall.user.rpc.api.sys.UserRoleService;
import tlb.mall.user.rpc.service.BaseServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("roleServiceImpl")
public class RoleServiceImpl extends BaseServiceImpl<SysRole> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    
    @Autowired
    private RolePermissionService rolePermissionService;
    
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public List<SysRole> getUserRoles(String userName) {
        return roleMapper.getUserRoles(userName);
    }

    @Override
    public Pager<RoleVo> getList(Pager<RoleVo> pager, SysRole role) {
        if(pager.getUsePager()){
            PageHelper.offsetPage(pager.getOffset(), pager.getLimit());
        }
        List<RoleVo> vos=roleMapper.getList(role,pager);
        pager.setRows(vos);
        PageInfo<RoleVo> pageInfo=new PageInfo<RoleVo>(vos);
        pager.setTotal(pageInfo.getTotal());
        return pager;
    }

    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public void addRole(SysRole role, Long[] permissionIds) throws Exception{
        //添加角色
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        role.setStatus(Status.ENABLE);//角色状态，0：可用  1：不可用
        roleMapper.insert(role);
        
        //设置权限
        for (int i = 0; i < permissionIds.length; i++) {
            SysRolePermission rolePermission = new SysRolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(permissionIds[i]);
            rolePermissionMapper.insert(rolePermission);
        }
    }

    @Override
    public List<SysRole> getAllRole(boolean haveAdmin) {
        if(haveAdmin){
            return roleMapper.selectAll();
        }
        
        Example example = new Example(SysRole.class);
        example.createCriteria().andNotEqualTo("id", 1);
        return roleMapper.selectByExample(example);
    }

    @Override
    public List<ZtreeVo> queryAllFormatWithZtree(boolean isShowTopParent, boolean haveAdmin) {
        List<ZtreeVo> results = new ArrayList<ZtreeVo>();
        if(isShowTopParent){
            ZtreeVo result = new ZtreeVo();
            result.setId("-1");
            result.setpId("0");
            result.setName("用户所属角色");
            results.add(result);
        }
        List<SysRole> roles = getAllRole(haveAdmin);
        if (CollectionUtils.isNotEmpty(roles)) {
            for (SysRole role : roles) {
                ZtreeVo foo = new ZtreeVo();
                foo.setId(String.valueOf(role.getId()));
                foo.setpId(String.valueOf(-1));
                foo.setName(role.getName());
                results.add(foo);
            }
        }
        return results;
    }

    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public void updateRole(SysRole role, Long[] permissionIds) throws Exception {
        //更新角色
        roleMapper.updateByPrimaryKeySelective(role);
        
        //更新角色权限
        rolePermissionService.updateRolePermission(role,permissionIds);
    }

    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public AjaxResult<String> deleteRole(Long roleId) throws Exception {
        AjaxResult<String> result = new AjaxResult<String>();
        if (roleId == null) {
            result.setCode(10001);
            result.setMsg("角色ID为空");
            return result;
        }
        
        SysRole role = roleMapper.selectByPrimaryKey(roleId);
        if (role == null) {
            result.setCode(10002);
            result.setMsg("角色不存在");
            return result;
        }
        
        
        //校验该角色是否被用户绑定使用
        boolean bool = userRoleService.checkRoleIsBindUser(roleId);
        if(bool){
            result.setCode(10004);
            result.setMsg("该角色已被其他用户所绑定使用，不能删除");
            return result;
        }
        //删除角色
        roleMapper.delete(role);
        
        //删除角色权限
        rolePermissionService.deleteRolePermission(role);
        return result;
    }
}
