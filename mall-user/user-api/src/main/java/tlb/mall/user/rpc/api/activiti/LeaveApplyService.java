package tlb.mall.user.rpc.api.activiti;


import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import tlb.mall.common.util.AjaxResult;
import tlb.mall.common.util.Pager;
import tlb.mall.entity.activiti.qo.LeaveApplyQo;
import tlb.mall.entity.activiti.vo.DeptLeaderAuditVo;
import tlb.mall.entity.activiti.vo.HrAuditVo;
import tlb.mall.entity.activiti.vo.LeaveApplyHistoryVo;
import tlb.mall.entity.activiti.vo.LeaveApplyVo;
import tlb.mall.entity.sys.SysUser;


import java.util.List;


/**
 * 
 * @description OA请假申请相关service
 * 
 * @author zhoubang 
 * @date 2017年3月30日 上午9:56:23 
 *
 */
public interface LeaveApplyService {

    /**
     * 
     * @description OA请假申请，启动工作流
     * 
     * @author zhoubang 
     * @date 2017年3月30日 上午10:00:18 
     * 
     * @return
     */
    public String startWorkflow(LeaveApplyQo qo, SysUser user) throws Exception;

    /**
     * 
     * @description 获取我发起的请假流程列表
     * 
     * @author zhoubang 
     * @date 2017年3月31日 下午3:05:26 
     * 
     * @param pager
     * @param leaveApplyQo
     * @return
     * @throws Exception
     */
    public Pager<LeaveApplyVo> getMyLeaveApplyList(Pager<LeaveApplyVo> pager, LeaveApplyQo leaveApplyQo) throws Exception;

    /**
     * 计算活动线
     * 
     * @param historicActivityInstances
     * @return
     * @throws Exception
     */
    public List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity,
                                            List<HistoricActivityInstance> historicActivityInstances) throws Exception;

    /**
     * 
     * @description 获取部门领导需要审批的请假流程列表
     * 
     * @author zhoubang 
     * @date 2017年4月1日 上午10:06:27 
     * 
     * @param pager
     * @return
     * @throws Exception
     */
    public Pager<DeptLeaderAuditVo> getDeptLeaderAuditList(Pager<DeptLeaderAuditVo> pager, LeaveApplyQo leaveApplyQo) throws Exception;

    /**
     * 
     * @description 根据任务ID获取详情
     * 
     * @author zhoubang 
     * @date 2017年4月1日 下午12:05:19 
     * 
     * @param taskId
     * @return
     * @throws Exception
     */
    public DeptLeaderAuditVo getDeptleaderAuditByTaskId(String taskId) throws Exception;

    /**
     * 
     * @description 部门经理审批请假申请
     * 
     * @author zhoubang 
     * @date 2017年4月1日 下午1:18:23 
     * 
     * @param taskId 任务ID
     * @param approvalResult 审批结果，true、false
     * @param userId 处理人ID
     * @return
     * @throws Exception
     */
    public AjaxResult<String> deptleaderAuditComplete(String taskId, String approvalResult, Long userId) throws Exception;

    /**
     * 
     * @description 获取被驳回需要重新调整的请假申请列表
     * 
     * @author zhoubang 
     * @date 2017年4月1日 下午2:21:14 
     * 
     * @param pager
     * @param leaveApplyQo
     * @return
     * @throws Exception
     */
    public Pager<DeptLeaderAuditVo> getLeaveApplyTurndownList(Pager<DeptLeaderAuditVo> pager, LeaveApplyQo leaveApplyQo) throws Exception;

    /**
     * 
     * @description 调整请假申请
     * 
     * @author zhoubang 
     * @date 2017年4月1日 下午3:11:07 
     * 
     * @param taskId
     * @param reapply
     * @return
     * @throws Exception
     */
    public AjaxResult<String> leaveapplyTurndownModify(String taskId, String reapply) throws Exception;

    /**
     * 
     * @description 获取我的请假历史列表
     * 
     * @author zhoubang 
     * @date 2017年4月1日 下午3:43:07 
     * 
     * @param pager
     * @param leaveApplyQo
     * @return
     * @throws Exception
     */
    public Pager<LeaveApplyHistoryVo> getLeaveApplyHistoryList(Pager<LeaveApplyHistoryVo> pager, LeaveApplyQo leaveApplyQo) throws Exception;

    /**
     * 
     * @description 获取请假申请处理记录
     * 
     * @author zhoubang 
     * @date 2017年4月1日 下午4:55:49 
     * 
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public AjaxResult<List<HistoricActivityInstance>> leaveApplyHandleRecord(String processInstanceId) throws Exception;

    /**
     * 
     * @description 获取人事需要审批的请假列表
     * 
     * @author zhoubang 
     * @date 2017年5月19日 下午3:07:16 
     * 
     * @param pager
     * @param leaveApplyQo
     * @return
     * @throws Exception
     */
    public Pager<HrAuditVo> getHrAuditList(Pager<HrAuditVo> pager, LeaveApplyQo leaveApplyQo) throws Exception;

    /**
     * 
     * @description 根据任务ID获取详情
     * 
     * @author zhoubang 
     * @date 2017年5月19日 下午3:28:43 
     * 
     * @param taskId
     * @return
     * @throws Exception
     */
    public HrAuditVo getHrAuditByTaskId(String taskId) throws Exception;

    /**
     * 
     * @description 人事审批请假申请
     * 
     * @author zhoubang 
     * @date 2017年5月19日 下午3:31:12 
     * 
     * @param taskId
     * @param approveResult
     * @param userId
     * @return
     * @throws Exception
     */
    public AjaxResult<String> hrAuditComplete(String taskId, String approveResult, Long userId) throws Exception;
}
