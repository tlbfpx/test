package tlb.mall.user.rpc.service.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tlb.mall.common.util.AjaxResult;
import tlb.mall.common.util.Pager;
import tlb.mall.common.util.enums.FromSystemEnum;
import tlb.mall.common.util.util.CryptoUtils;
import tlb.mall.entity.sys.SysUser;
import tlb.mall.entity.sys.qo.UserQo;
import tlb.mall.entity.sys.vo.UserVo;
import tlb.mall.user.dao.sys.UserMapper;
import tlb.mall.user.rpc.api.sys.UserRoleService;
import tlb.mall.user.rpc.api.user.UserService;
import tlb.mall.user.rpc.service.BaseServiceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("userServiceImpl")
public class UserServiceImpl extends BaseServiceImpl<SysUser> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleService userRoleService;
    
    @Override
    public SysUser getUserByName(String username) {
        List<SysUser> list = userMapper.getUserByName(username);
        return (list == null || 0 == list.size()) ? null : list.get(0);
    }

    @Override
    public Pager<UserVo> getList(Pager<UserVo> pager, UserQo userQo) {
        if (pager.getUsePager()) {
            PageHelper.offsetPage(pager.getOffset(), pager.getLimit());
        }
        
        List<UserVo> vos = userMapper.getUserList(userQo, pager);
        pager.setRows(vos);
        PageInfo<UserVo> pageInfo = new PageInfo<UserVo>(vos);
        pager.setTotal(pageInfo.getTotal());
        return pager;
    }

    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public AjaxResult<String> deleteUser(Long userId) throws Exception{
        AjaxResult<String> result = new AjaxResult<String>();
        if (userId == null || "".equals(userId)) {
            result.setCode(10001);
            result.setMsg("userId为空");
            return result;
        }

        SysUser user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            result.setCode(10002);
            result.setMsg("该用户不存在");
            return result;
        }
        
        if("admin".equals(user.getUserName())){//这里直接使用固定字符串比较了，真实项目不建议这种做法！扩展性不好。
            result.setCode(10001);
            result.setMsg("当前用户为管理员角色，不能删除");
            return result;
        }
        //删除用户
        userMapper.delete(user);
        
        //删除用户对于的角色
        userRoleService.deleteByUserId(user.getId());
        return result;
    }

    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public void updateUser(SysUser user, UserQo qo) throws Exception{
        //更新用户
        user.setRealName(qo.getRealName());
        user.setStatus(qo.getStatus());
        user.setUpdateTime(new Date());
        user.setUserName(qo.getUserName());
        userMapper.updateByPrimaryKey(user);
        
        //更新用户角色列表
        userRoleService.updateUserRole(user,qo.getRoleIds());
    }

    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public void saveUser(UserQo qo) throws Exception {
        //新增用户
        SysUser user = new SysUser();
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setRealName(qo.getRealName());
        user.setUserName(qo.getUserName());
        user.setStatus(qo.getStatus());
        user.setPassword(CryptoUtils.encodeMD5(qo.getPassword()));
        user.setFromSystem(FromSystemEnum.DEFAULT_CURRENT_USER_CENTER);
        userMapper.insert(user);
        
        //保存角色
        userRoleService.updateUserRole(user, qo.getRoleIds());
    }

    @Override
    public AjaxResult<String> deleteUsers(Long[] userIdArr) throws Exception {
        AjaxResult<String> result = new AjaxResult<String>();
        if (userIdArr == null || userIdArr.length <= 0) {
            result.setCode(10001);
            result.setMsg("userId为空");
            return result;
        }
        
        //验证选择的用户中是否有管理员角色的用户
        Example exampleQuery = new Example(SysUser.class);
        exampleQuery.createCriteria().andIn("id",Arrays.asList(userIdArr));
        List<SysUser> userList = userMapper.selectByExample(exampleQuery);
        
        boolean bool = false;
        for (SysUser sysUser : userList) {
            if(sysUser != null && "admin".equals(sysUser.getUserName())){
                bool = true;//存在管理员角色的用户
                break;
            }
        }
        if(bool){
            result.setCode(10002);
            result.setMsg("您不能删除拥有管理员角色的用户数据");
            return result;
        }
        
        if(userIdArr != null && userIdArr.length > 0){
            for (int i = 0; i < userIdArr.length; i++) {
                //删除用户
                Example example = new Example(SysUser.class);
                example.createCriteria().andIn("id", Arrays.asList(userIdArr));
                userMapper.deleteByExample(example);
                
                //删除用户对应的角色信息
                userRoleService.deleteByUserId(userIdArr[i]);
            }
        }
        return result;
    }
}
