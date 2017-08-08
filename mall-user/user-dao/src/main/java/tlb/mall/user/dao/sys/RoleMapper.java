package tlb.mall.user.dao.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import tk.mybatis.mapper.common.Mapper;
import tlb.mall.common.util.Pager;
import tlb.mall.entity.sys.SysRole;
import tlb.mall.entity.sys.vo.RoleVo;

public interface RoleMapper extends Mapper<SysRole> {

    /**
     * 获取用户拥有的角色列表
     * 
     * 日期：2016年8月14日 下午1:20:26
     * 用户：zhoubang
     * 
     * @param userName
     * @return
     */
    List<SysRole> getUserRoles(@Param("userName") String userName);

    /**
     * 分页获取角色列表
     * 
     * 日期：2016年8月14日 下午1:20:18
     * 用户：zhoubang
     * 
     * @param pager
     * @return
     */
    List<RoleVo> getList(@Param("role") SysRole role, @Param("pager") Pager<RoleVo> pager);

}
