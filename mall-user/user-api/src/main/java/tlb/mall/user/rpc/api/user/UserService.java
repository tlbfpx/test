package tlb.mall.user.rpc.api.user;


import tlb.mall.common.util.AjaxResult;
import tlb.mall.common.util.Pager;
import tlb.mall.common.util.annotation.DataSource;
import tlb.mall.common.util.enums.DataSourceEnum;
import tlb.mall.entity.sys.SysUser;
import tlb.mall.entity.sys.qo.UserQo;
import tlb.mall.entity.sys.vo.UserVo;
import tlb.mall.user.rpc.api.BaseService;

public interface UserService extends BaseService<SysUser> {

    @DataSource(DataSourceEnum.Master)
    SysUser getUserByName(String username);

    /**
     * 获取人员列表————分页查询
     *      这里是使用APO注入的数据源。默认是MYSQL数据源。
     *      如果想切换数据源的访问，你可以在这service的接口方法上，使用@DataSource(DataSourceEnum.MYSQL)注解实现数据源动态切换。
     *      DataSourceEnum 是数据源枚举。目前只测试mysql与sqlserver
     * 
     * 创建日期：2016年8月3日 下午3:39:23
     * 操作用户：zhoubang
     * 
     * @param pager
     * @param userQo
     * @return
     */
    @DataSource(DataSourceEnum.Master)
    Pager<UserVo> getList(Pager<UserVo> pager, UserQo userQo);
    
    
    /**
     * 删除用户
     * 
     * 日期：2016年8月20日 下午1:37:15
     * 用户：zhoubang
     * 
     * @return
     */
    AjaxResult<String> deleteUser(Long userId) throws Exception;

    /**
     * 更新用户信息、所属角色
     * 
     * 日期：2016年8月20日 下午3:01:52
     * 用户：zhoubang
     * 
     * @param user
     */
    void updateUser(SysUser user, UserQo qo) throws Exception;

    /**
     * 保存用户、角色
     * 
     * 日期：2016年8月20日 下午4:33:43
     * 用户：zhoubang
     * 
     * @param qo
     * @throws Exception
     */
    void saveUser(UserQo qo) throws Exception;

    /**
     * 批量删除用户
     * @param userIdArr
     * @throws Exception
     */
    AjaxResult<String> deleteUsers(Long[] userIdArr) throws Exception;
}
