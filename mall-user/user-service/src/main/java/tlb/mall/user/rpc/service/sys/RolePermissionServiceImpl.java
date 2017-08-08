package tlb.mall.user.rpc.service.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tlb.mall.entity.sys.SysRole;
import tlb.mall.entity.sys.SysRolePermission;
import tlb.mall.user.dao.sys.RolePermissionMapper;
import tlb.mall.user.rpc.api.sys.RolePermissionService;
import tlb.mall.user.rpc.service.BaseServiceImpl;

import java.util.List;


@Service("rolePermissionServiceImpl")
public class RolePermissionServiceImpl extends BaseServiceImpl<SysRolePermission> implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<SysRolePermission> getPermissionByRoleId(Long roleId) {
        Example example = new Example(SysRolePermission.class);
        example.createCriteria().andEqualTo("roleId", roleId);
        return rolePermissionMapper.selectByExample(example);
    }

    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public void updateRolePermission(SysRole role, Long[] permissionIds) throws Exception {
        //删除角色权限
        deleteRolePermission(role);
        
        //设置角色权限
        for (int i = 0; i < permissionIds.length; i++) {
            SysRolePermission rolePermission = new SysRolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(permissionIds[i]);
            rolePermissionMapper.insert(rolePermission);
        }
    }

    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public void deleteRolePermission(SysRole role) throws Exception {
        //删除角色权限
        Example example = new Example(SysRolePermission.class);
        example.createCriteria().andEqualTo("roleId",role.getId());
        rolePermissionMapper.deleteByExample(example);
    }

    @Override
    public boolean checkPermissionIsBindRole(Long pid) {
        Example example = new Example(SysRolePermission.class);
        example.createCriteria().andEqualTo("permissionId",pid);
        List<SysRolePermission> ls = rolePermissionMapper.selectByExample(example);
        return (ls ==null || ls.size() <= 0) ? false : true;
    }
}
