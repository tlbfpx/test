package tlb.mall.user.web.controller.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tlb.mall.common.util.AjaxResult;
import tlb.mall.common.util.JQDatatableResult;
import tlb.mall.common.util.Pager;
import tlb.mall.common.util.enums.UserStatus;
import tlb.mall.common.util.util.JsonUtil;
import tlb.mall.entity.sys.SysUser;
import tlb.mall.entity.sys.qo.UserQo;
import tlb.mall.entity.sys.vo.UserVo;
import tlb.mall.entity.vo.ZtreeVo;
import tlb.mall.user.rpc.api.user.UserService;


/**
 * 
 * 用户管理
 * 
 * 创建日期：2016年8月13日 下午4:07:01 操作用户：zhoubang
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

//    @Autowired
//    private RoleService roleService;

//    /**
//     * 进入用户列表页面
//     *
//     * 日期：2016年8月13日 下午4:07:06 用户：zhoubang
//     *
//     * @param request
//     * @param response
//     */
//    @RequestMapping("/toUserListView")
//    public String toUserListView(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
//
//        // 账户状态
//        map.put("status", UserStatus.values());
//
//        // 所有角色
//        List<SysRole> roles = roleService.getAllList();
//        map.put("roles", roles);
//
//        return "/sys/user/list";
//    }

    @RequestMapping("/list")
    @ResponseBody
    public JQDatatableResult<UserVo> list(@ModelAttribute Pager<UserVo> pager, @ModelAttribute UserQo userQo) {
        pager = userService.getList(pager, userQo);

        JQDatatableResult<UserVo> result = new JQDatatableResult<UserVo>();
        result.setData(pager.getRows());
        result.setRecordsTotal(pager.getTotal().intValue());
        result.setDraw(pager.getDraw());
        result.setRecordsFiltered(pager.getTotal().intValue());
        return result;
    }

    /**
     * bootstrap table 列编辑保存
     * 
     * 日期：2016年8月13日 下午11:52:00 用户：zhoubang
     * 
     * @param qo
     * @return
     */
    @RequestMapping("/editTableColumn")
    @ResponseBody
    public AjaxResult<String> editTableColumn(@ModelAttribute UserQo qo) {
        logger.debug("bootstrap table 列编辑保存，qo参数：userId:" + qo.getUserId() + "，realName：" + qo.getRealName());

        AjaxResult<String> result = new AjaxResult<String>();
        try {
            if (qo.getUserId() == null || "".equals(qo.getUserId())) {
                result.setCode(10001);
                result.setMsg("userId为空");
                return result;
            }

            SysUser user = userService.get(qo.getUserId());
            if (user == null) {
                result.setCode(10002);
                result.setMsg("该用户不存在");
                return result;
            }

            try {
                user.setRealName(qo.getRealName());
                user.setUpdateTime(new Date());
                userService.update(user);
            } catch (Exception e) {
                e.printStackTrace();
                result.setCode(10003);
                result.setMsg("保存失败");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除用户
     * 
     * 日期：2016年8月20日 下午1:14:35 用户：zhoubang
     * 
     * @param qo
     * @return
     */
    @RequestMapping("/deleteUser")
    @ResponseBody
    public AjaxResult<String> deleteUser(@ModelAttribute UserQo qo) {
        logger.debug("删除用户，qo参数：userQo:" + JsonUtil.toJson(qo));

        AjaxResult<String> result = new AjaxResult<String>();
        try {
            // 删除用户与角色
            result = userService.deleteUser(qo.getUserId());
        } catch (Exception e) {
            e.printStackTrace();

            result.setCode(10003);
            result.setMsg("删除失败");
            return result;
        }
        return result;
    }
    
    /**
     * 批量删除用户
     * @param qo
     * @return
     */
    @RequestMapping("/deleteUsers")
    @ResponseBody
    public AjaxResult<String> deleteUsers(@ModelAttribute UserQo qo) {
        logger.debug("删除用户，qo参数：userQo:" + JsonUtil.toJson(qo));

        AjaxResult<String> result = new AjaxResult<String>();
        try {
            // 删除用户与角色
            result = userService.deleteUsers(qo.getUserIdArr());
        } catch (Exception e) {
            e.printStackTrace();

            result.setCode(10003);
            result.setMsg("删除失败");
            return result;
        }
        return result;
    }
    
//    /**
//     * 进入用户信息编辑页面
//     *
//     * 日期：2016年8月20日 下午2:00:09 用户：zhoubang
//     *
//     * @param map
//     * @return
//     */
//    @RequestMapping("/toEditView")
//    public String toEditView(ModelMap map, UserQo qo) {
//        if (qo.getUserId() != null) {
//            SysUser user = userService.get(qo.getUserId());
//            map.put("user", user);// 用户信息
//
//            boolean userHaveAdminRole = false;// 标识用户是否拥有管理员角色
//            List<Long> chkDisabledRoleIds = null;// 存放需要禁用ztree节点复选框选中事件的角色ID
//
//            String roleIds = "";
//            // 用户拥有的角色列表
//            List<SysRole> userRoles = roleService.getUserRoles(user.getUserName());
//            for (int i = 0; i < userRoles.size(); i++) {
//                SysRole role = userRoles.get(i);
//
//                if (role.getId().longValue() == 1l) {
//                    userHaveAdminRole = true;
//
//                    chkDisabledRoleIds = new ArrayList<Long>();
//                    chkDisabledRoleIds.add(role.getId().longValue());
//                }
//
//                roleIds += role.getId();
//                if (i < userRoles.size() - 1) {
//                    roleIds += ",";
//                }
//            }
//
//            // tree树，角色数据
//            List<ZtreeVo> allRole = null;
//            if (userHaveAdminRole) {
//                map.put("userHaveAdminRole", userHaveAdminRole);// 账户状态
//                // 角色列表，包含管理员角色
//                allRole = roleService.queryAllFormatWithZtree(false, true);
//            } else {
//                // 角色列表，不包含管理员角色
//                allRole = roleService.queryAllFormatWithZtree(false, false);
//            }
//            // json字符串包含橘色的复选框选中属性设置以及展开属性设置.
//            map.put("allRoles", JsonUtil.toJson(setPZtreeCheck(allRole, userRoles, chkDisabledRoleIds)));
//
//            map.put("roleIds", roleIds);// 角色列表
//        }
//        map.put("userStatus", UserStatus.values());// 账户状态
//        return "/sys/user/edit";
//    }

    /**
//     * 对角色tree进行操作 对用户拥有的角色进行复选框选中的属性设置,并且展开对应的权限tree。
//     *
//     * 作者: zhoubang 日期：2015年4月29日 上午9:52:16
//     *
//     * @param list
//     *            所有角色列表
//     * @param roleList
//     *            用户拥有的角色列表
//     * @param chkDisabledRoleIds
//     * @return
//     */
//    public static List<ZtreeVo> setPZtreeCheck(List<ZtreeVo> list, List<SysRole> roleList,
//                                               List<Long> chkDisabledRoleIds) {
//        for (ZtreeVo ztreeVo : list) {
//            if (roleList != null) {
//                for (SysRole role : roleList) {
//                    if (StringUtils.equals(String.valueOf(role.getId()), ztreeVo.getId())) {
//                        ztreeVo.setChecked(true);
//                    }
//                    // 设置需要禁用check复选框选中的节点
//                    if (null != chkDisabledRoleIds) {
//                        for (Long roleId : chkDisabledRoleIds) {
//                            if (role.getId().longValue() == roleId.longValue()) {
//                                ztreeVo.setChkDisabled(true);
//                            }
//                        }
//                    }
//                }
//            } else {
//                ztreeVo.setChecked(false);
//            }
//
//            ztreeVo.setOpen(true);
//        }
//        return list;
//    }

    /**
     * 更新用户信息
     * 
     * 日期：2016年8月20日 下午3:01:42 用户：zhoubang
     * 
     * @param qo
     * @return
     */
    @RequestMapping("/updateUser")
    @ResponseBody
    public AjaxResult<String> updateUser(@ModelAttribute UserQo qo) {
        logger.debug("更新用户，qo参数：userQo:" + JsonUtil.toJson(qo));

        AjaxResult<String> result = new AjaxResult<String>();

        if (qo.getUserId() == null || "".equals(qo.getUserId())) {
            result.setCode(10001);
            result.setMsg("userId为空");
            return result;
        }

        SysUser user = userService.get(qo.getUserId());
        if (user == null) {
            result.setCode(10002);
            result.setMsg("该用户不存在");
            return result;
        }

        if (StringUtils.isBlank(qo.getRealName())) {
            result.setCode(10003);
            result.setMsg("用户名不能为空");
            return result;
        }

        if (qo.getRoleIds() == null || qo.getRoleIds().length <= 0) {
            result.setCode(10004);
            result.setMsg("请为用户指定角色");
            return result;
        }

        try {
            // 更新用户信息、所属角色
            userService.updateUser(user, qo);
        } catch (Exception e) {
            e.printStackTrace();

            result.setCode(10005);
            result.setMsg("更新失败");
            return result;
        }
        return result;
    }

//    /**
//     * 进入添加用户页面
//     *
//     * 日期：2016年8月20日 下午4:26:07 用户：zhoubang
//     *
//     * @param map
//     * @return
//     */
//    @RequestMapping("/toAddView")
//    public String toAddView(ModelMap map) {
//        // 获取所有角色列表
//        List<ZtreeVo> allRole = roleService.queryAllFormatWithZtree(false, false);
//
//        // json字符串包含角色的复选框选中属性设置以及展开属性设置.
//        map.put("allRoles", JsonUtil.toJson(setPZtreeCheck(allRole, null, null)));
//
//        map.put("userStatus", UserStatus.values());
//        return "/sys/user/add";
//    }

    /**
     * 保存用户
     * 
     * 日期：2016年8月20日 下午4:39:13 用户：zhoubang
     * 
     * @param qo
     * @return
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public AjaxResult<String> addUser(@ModelAttribute UserQo qo) {
        logger.debug("新增用户，qo参数：userQo:" + JsonUtil.toJson(qo));

        AjaxResult<String> result = new AjaxResult<String>();

        if (StringUtils.isBlank(qo.getUserName())) {
            result.setCode(10001);
            result.setMsg("账户名不能为空");
            return result;
        }

        if (StringUtils.isBlank(qo.getRealName())) {
            result.setCode(10002);
            result.setMsg("姓名不能为空");
            return result;
        }

        if (qo.getStatus() == null) {
            result.setCode(10003);
            result.setMsg("请选择账户状态");
            return result;
        }

        if (qo.getRoleIds() == null || qo.getRoleIds().length <= 0) {
            result.setCode(10004);
            result.setMsg("请为用户设置角色");
            return result;
        }
        
        SysUser sysUser = userService.getUserByName(qo.getUserName());
        if(sysUser != null){
            result.setCode(10006);
            result.setMsg("当前账户名已经存在");
            return result;
        }
        // 保存用户、角色
        try {
            userService.saveUser(qo);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(10005);
            result.setMsg("添加失败");
            return result;
        }
        return result;
    }
}
