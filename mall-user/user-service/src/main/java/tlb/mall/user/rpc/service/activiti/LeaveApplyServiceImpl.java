package tlb.mall.user.rpc.service.activiti;


import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tlb.mall.common.util.AjaxResult;
import tlb.mall.common.util.Pager;
import tlb.mall.common.util.util.DateUtil;
import tlb.mall.entity.activiti.LeaveApply;
import tlb.mall.entity.activiti.qo.LeaveApplyQo;
import tlb.mall.entity.activiti.vo.DeptLeaderAuditVo;
import tlb.mall.entity.activiti.vo.HrAuditVo;
import tlb.mall.entity.activiti.vo.LeaveApplyHistoryVo;
import tlb.mall.entity.activiti.vo.LeaveApplyVo;
import tlb.mall.entity.sys.SysUser;
import tlb.mall.user.dao.activiti.LeaveApplyMapper;
import tlb.mall.user.dao.sys.UserMapper;
import tlb.mall.user.rpc.api.activiti.LeaveApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

//@Service("leaveApplyServiceImpl")
public class LeaveApplyServiceImpl implements LeaveApplyService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IdentityService identityService;
    
    @Autowired
    private RuntimeService runtimeService;
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private HistoryService historyService;
    
    @Autowired
    private LeaveApplyMapper leaveApplyMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Transactional
    @Override
    public String startWorkflow(LeaveApplyQo qo, SysUser user) throws Exception{
        //保存请假申请
        LeaveApply leaveApply = new LeaveApply();
        leaveApply.setCreateTime(new Date());
        leaveApply.setUpdateTime(new Date());
        leaveApply.setStartDate(DateUtil.formatDate(qo.getStartDate(), DateUtil.DATE_DEFAULT_FORMAT));
        leaveApply.setEndDate(DateUtil.formatDate(qo.getEndDate(), DateUtil.DATE_DEFAULT_FORMAT));
        leaveApply.setUserId(user.getId());
        leaveApply.setType(qo.getType());
        leaveApply.setReason(qo.getReason());
        leaveApplyMapper.insert(leaveApply);
        
        //使用leaveapply表的主键作为businesskey,连接业务数据和流程数据
        String businesskey = String.valueOf(leaveApply.getId());
        logger.debug("OA请假申请，业务key(实体id)，businesskey：" + businesskey);
        
        identityService.setAuthenticatedUserId(String.valueOf(user.getId()));
        
        
        
        /** =============================== 启动流程 ===============================  */
        //设置流程所需变量
        Map<String,Object> variables = new HashMap<String, Object>();
        logger.debug("OA请假申请，流程变量设置，applyuserid：" + user.getId());
        variables.put("applyuserid", user.getId());//申请人
        variables.put("approvalRole", "部门经理");//需要部门经理角色才能审批，这里写死了，你们可以根据情况，通过变量赋值
        //启动流程
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("leave",businesskey,variables);
        
        
        /**
         * 启动流程实例之后，需要执行流程task
         */
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(instance.getProcessInstanceId()).active().list();
        for (Task ts : tasks) {
            //获取指定任务
            Task task = taskService.createTaskQuery().taskId(ts.getId()).singleResult();
            if (task == null) continue;
            
            //执行流程里面的步骤。上面已经在variables流程变量中设置了approvalRole属性值，表明接下来是部门经理角色审批
            //如果不执行这一步，当申请请假之后，流程节点始终是“用户请假申请”，部门经理看不到申请记录的。
            taskService.complete(ts.getId(),variables);
        }
        
        //将实例id赋予请假对应的字段
        leaveApply.setProcessInstanceId(instance.getId());
        leaveApplyMapper.updateByPrimaryKey(leaveApply);
        
        return instance.getId();
    }

    @Override
    public Pager<LeaveApplyVo> getMyLeaveApplyList(Pager<LeaveApplyVo> pager, LeaveApplyQo leaveApplyQo) throws Exception {
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().processDefinitionKey("leave").involvedUser(String.valueOf(leaveApplyQo.getUserId()));
        
        int total = query.list().size();
        List<ProcessInstance> processInstanceList = query.listPage(pager.getOffset(), pager.getLimit());
        
        List<LeaveApplyVo> list = new ArrayList<LeaveApplyVo>();
        for (ProcessInstance processInstance : processInstanceList) {
            //根据业务key获取请假申请记录。在该流程中，业务key其实就是请假申请记录的id
            LeaveApply leaveApply = leaveApplyMapper.selectByPrimaryKey(Long.parseLong(processInstance.getBusinessKey()));
            if(leaveApply != null){
                //将请假记录中的申请人id与当前登录的人id进行对比，判断是否属于自己的请假申请
                if(leaveApply.getUserId().longValue() == leaveApplyQo.getUserId().longValue()){
                    Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
                    
                    LeaveApplyVo vo = new LeaveApplyVo();
                    vo.setActivityId(processInstance.getActivityId());
                    vo.setName(task.getName());
                    vo.setBusinessKey(processInstance.getBusinessKey());
                    vo.setExecutionId(processInstance.getId());
                    vo.setProcessInstanceId(processInstance.getProcessInstanceId());
                    list.add(vo);
                }
            }
        }
        pager.setTotal(Long.parseLong(String.valueOf(total)));
        pager.setRows(list);
        return pager;
    }

    @Override
    public List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity, List<HistoricActivityInstance> historicActivityInstances) throws Exception {
        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId

        for (int i = 0; i < historicActivityInstances.size(); i++) {// 对历史流程节点进行遍历
            ActivityImpl activityImpl = processDefinitionEntity.findActivity(historicActivityInstances.get(i).getActivityId());// 得 到节点定义的详细信息
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
            if ((i + 1) >= historicActivityInstances.size()) {
                break;
            }
            ActivityImpl sameActivityImpl1 = processDefinitionEntity.findActivity(historicActivityInstances.get(i + 1).getActivityId());// 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);// 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);// 后续第二个节点
                if (activityImpl1.getStartTime().equals(activityImpl2.getStartTime())) {// 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity.findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {// 有不相同跳出循环
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();// 取出节点的所有出去的线
            for (PvmTransition pvmTransition : pvmTransitions) {// 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition.getDestination();// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }

    @Override
    public Pager<DeptLeaderAuditVo> getDeptLeaderAuditList(Pager<DeptLeaderAuditVo> pager, LeaveApplyQo leaveApplyQo) throws Exception {
        TaskQuery query = taskService.createTaskQuery().taskCandidateGroup("部门经理");//查询项目经理的任务
        List<Task> deptTasks = query.list();//获取部门经理需要审批的所有请假申请列表
        int total = deptTasks.size();//总记录数
        
        List<Task> deptPagerTasks = query.listPage(pager.getOffset(), pager.getLimit());//分页数据
        
        List<DeptLeaderAuditVo> list = new ArrayList<DeptLeaderAuditVo>();
        for(Task task : deptPagerTasks){
            /** 获取请假相关数据 */
            String processInstanceId = task.getProcessInstanceId();//流程实例ID
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            if(processInstance == null){
                continue;
            }
            
            String businessKey = processInstance.getBusinessKey();//业务key，对应的请假记录ID
            if(StringUtils.isBlank(businessKey)){
                continue;
            }
            LeaveApply leaveApply = leaveApplyMapper.selectByPrimaryKey(Long.parseLong(businessKey));//根据ID获取请假记录
            if(leaveApply == null){
                continue;
            }
            
            SysUser user = userMapper.selectByPrimaryKey(leaveApply.getUserId());//获取请假人信息
            
            DeptLeaderAuditVo vo = new DeptLeaderAuditVo();
            vo.setCreateTime(leaveApply.getCreateTime());
            vo.setStartDate(leaveApply.getStartDate());
            vo.setEndDate(leaveApply.getEndDate());
            vo.setProcessInstanceId(processInstanceId);
            vo.setType(leaveApply.getType());
            vo.setUserId(leaveApply.getUserId());
            vo.setUserName(user.getRealName());
            vo.setReason(leaveApply.getReason());
            vo.setTaskId(task.getId());
            vo.setTaskName(task.getName());
            list.add(vo);
        }
        pager.setTotal(Long.parseLong(String.valueOf(total)));
        pager.setRows(list);
        return pager;
    }

    @Override
    public DeptLeaderAuditVo getDeptleaderAuditByTaskId(String taskId) throws Exception {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        ProcessInstance process = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        
        LeaveApply leaveApply = leaveApplyMapper.selectByPrimaryKey(Long.parseLong(process.getBusinessKey()));
        SysUser user = userMapper.selectByPrimaryKey(leaveApply.getUserId());//获取请假人信息
        
        DeptLeaderAuditVo vo = new DeptLeaderAuditVo();
        vo.setCreateTime(leaveApply.getCreateTime());
        vo.setStartDate(leaveApply.getStartDate());
        vo.setEndDate(leaveApply.getEndDate());
        vo.setType(leaveApply.getType());
        vo.setUserId(leaveApply.getUserId());
        vo.setUserName(user.getRealName());
        vo.setReason(leaveApply.getReason());
        
        return vo;
    }

    @Transactional
    @Override
    public AjaxResult<String> deptleaderAuditComplete(String taskId, String approvalResult, Long userId) throws Exception {
        AjaxResult<String> result = new AjaxResult<String>();
        if(StringUtils.isBlank(taskId)){
            result.setCode(10001);
            result.setMsg("任务ID不能为空");
            return result;
        }
        if(userId == null || "".equals(userId.toString())){
            result.setCode(10002);
            result.setMsg("处理人ID不能为空");
            return result;
        }
        try {
            Map<String,Object> variables=new HashMap<String,Object>();
            variables.put("deptleaderapprove", approvalResult);//设置流程所需参数，用于流程跳转
            
            if("true".equals(approvalResult)){//部门经理同意申请，需要设置下一个处理人(当前流程，先一步处理人，是拥有人事角色的人员才能审批)
                variables.put("approvalRole", "人事");//审批人角色，拥有人事角色的人员才能审批
            }
            
            taskService.claim(taskId, String.valueOf(userId));//设置任务处理人
            taskService.complete(taskId, variables);//流程执行
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(500);
            result.setMsg("处理失败，请稍候重试");
            return result;
        }
        return result;
    }

    @Override
    public Pager<DeptLeaderAuditVo> getLeaveApplyTurndownList(Pager<DeptLeaderAuditVo> pager, LeaveApplyQo leaveApplyQo) throws Exception {
        //获取被驳回的需要重新调整的请假申请任务列表
        TaskQuery query = taskService.createTaskQuery().taskCandidateOrAssigned(String.valueOf(leaveApplyQo.getUserId())).taskName("调整申请");

        List<Task> tasks = query.list();//所有任务
        List<Task> pagerTasks = query.listPage(pager.getOffset(), pager.getLimit());//分页
        
        List<DeptLeaderAuditVo> list = new ArrayList<DeptLeaderAuditVo>();
        for(Task task : pagerTasks){
            String processInstanceId = task.getProcessInstanceId();//流程实例ID
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();//获取流程实例
            if(processInstance == null){
                continue;
            }
            
            String businessKey = processInstance.getBusinessKey();//业务key，实际也就是请假申请记录的ID
            LeaveApply leaveApply = leaveApplyMapper.selectByPrimaryKey(Long.parseLong(businessKey));//获取请假申请记录
            if(leaveApply == null){
                continue;
            }
            
            SysUser user = userMapper.selectByPrimaryKey(leaveApply.getUserId());//获取请假人信息
            
            DeptLeaderAuditVo vo = new DeptLeaderAuditVo();
            vo.setCreateTime(leaveApply.getCreateTime());
            vo.setStartDate(leaveApply.getStartDate());
            vo.setEndDate(leaveApply.getEndDate());
            vo.setProcessInstanceId(processInstanceId);
            vo.setType(leaveApply.getType());
            vo.setUserId(leaveApply.getUserId());
            vo.setUserName(user.getRealName());
            vo.setReason(leaveApply.getReason());
            vo.setTaskId(task.getId());
            vo.setTaskName(task.getName());
            list.add(vo);
        }
        
        pager.setTotal(Long.parseLong(String.valueOf(tasks.size())));
        pager.setRows(list);
        return pager;
    }

    @Override
    public AjaxResult<String> leaveapplyTurndownModify(String taskId, String reapply) throws Exception {
        AjaxResult<String> result = new AjaxResult<String>();
        
        if(StringUtils.isBlank(taskId)){
            result.setCode(10001);
            result.setMsg("任务ID不能为空");
            return result;
        }
        if(StringUtils.isBlank(reapply)){
            result.setCode(10002);
            result.setMsg("请确认是否继续申请");
            return result;
        }
        
        /** ==============这里可以对请假记录做相关操作，比如记录日期、更新状态等 ================= */
        /*
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();//获取当前任务
        String processInstanceId = task.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String businesskey = processInstance.getBusinessKey();//获取业务key，实际上就是请假记录的ID
        LeaveApply leaveApply = leaveApplyMapper.selectByPrimaryKey(Long.parseLong(businesskey));//获取请假记录
        */
        
        try {
            Map<String,Object> variables=new HashMap<String,Object>();
            variables.put("reapply", reapply);//设置流程变量
            //variables.put("applyuserid", user.getId());//申请人
            variables.put("approvalRole", "部门经理");//需要部门经理角色才能审批，这里写死了，你们可以根据情况，通过变量赋值
            taskService.complete(taskId, variables);//执行任务
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(500);
            result.setMsg("提交失败，请稍候重试");
            return result;
        }
        return result;
    }

    @Override
    public Pager<LeaveApplyHistoryVo> getLeaveApplyHistoryList(Pager<LeaveApplyHistoryVo> pager, LeaveApplyQo leaveApplyQo) throws Exception {
        //获取请假历史列表
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery().processDefinitionKey("leave").startedBy(String.valueOf(leaveApplyQo.getUserId())).finished();
        int total = Integer.parseInt(String.valueOf(query.count()));//总记录数
        
        List<HistoricProcessInstance> historicProcessInstanceList = query.listPage(pager.getOffset(), pager.getLimit());
        
        List<LeaveApplyHistoryVo> list = new ArrayList<LeaveApplyHistoryVo>();
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstanceList) {
            //根据业务key获取请假申请记录。在该流程中，业务key其实就是请假申请记录的id
            LeaveApply leaveApply = leaveApplyMapper.selectByPrimaryKey(Long.parseLong(historicProcessInstance.getBusinessKey()));
            if(leaveApply == null){
                continue;
            }
            
            SysUser user = userMapper.selectByPrimaryKey(leaveApply.getUserId());//获取请假人信息
            
            LeaveApplyHistoryVo vo = new LeaveApplyHistoryVo();
            vo.setBusinessKey(historicProcessInstance.getBusinessKey());
            vo.setCreateTime(leaveApply.getCreateTime());
            vo.setProcessInstanceId(historicProcessInstance.getId());
            vo.setUserName(user.getRealName());
            vo.setTypeName(leaveApply.getType().getDescription());
            list.add(vo);
        }
        
        pager.setTotal(Long.parseLong(String.valueOf(total)));
        pager.setRows(list);
        return pager;
    }

    @Override
    public AjaxResult<List<HistoricActivityInstance>> leaveApplyHandleRecord(String processInstanceId) throws Exception {
        AjaxResult<List<HistoricActivityInstance>> result = new AjaxResult<List<HistoricActivityInstance>>();
        if(StringUtils.isBlank(processInstanceId)){
            result.setCode(10001);
            result.setMsg("流程实例ID不能为空");
            return result;
        }
        
        //获取处理记录
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().list();
        result.setResult(list);
        return result;
    }

    @Override
    public Pager<HrAuditVo> getHrAuditList(Pager<HrAuditVo> pager, LeaveApplyQo leaveApplyQo) throws Exception {
        TaskQuery query = taskService.createTaskQuery().taskCandidateGroup("人事");//查询项目经理的任务
        List<Task> deptTasks = query.list();//获取部门经理需要审批的所有请假申请列表
        int total = deptTasks.size();//总记录数
        
        List<Task> deptPagerTasks = query.listPage(pager.getOffset(), pager.getLimit());//分页数据
        
        List<HrAuditVo> list = new ArrayList<HrAuditVo>();
        for(Task task : deptPagerTasks){
            /** 获取请假相关数据 */
            String processInstanceId = task.getProcessInstanceId();//流程实例ID
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            if(processInstance == null){
                continue;
            }
            
            String businessKey = processInstance.getBusinessKey();//业务key，对应的请假记录ID
            if(businessKey == null){
                continue;
            }
            
            LeaveApply leaveApply = leaveApplyMapper.selectByPrimaryKey(Long.parseLong(businessKey));//根据ID获取请假记录
            if(leaveApply == null){
                continue;
            }
            
            SysUser user = userMapper.selectByPrimaryKey(leaveApply.getUserId());//获取请假人信息
            if(user == null){
                continue;
            }
            
            HrAuditVo vo = new HrAuditVo();
            vo.setCreateTime(leaveApply.getCreateTime());
            vo.setStartDate(leaveApply.getStartDate());
            vo.setEndDate(leaveApply.getEndDate());
            vo.setProcessInstanceId(processInstanceId);
            vo.setType(leaveApply.getType());
            vo.setUserId(leaveApply.getUserId());
            vo.setUserName(user.getRealName());
            vo.setReason(leaveApply.getReason());
            vo.setTaskId(task.getId());
            vo.setTaskName(task.getName());
            list.add(vo);
        }
        pager.setTotal(Long.parseLong(String.valueOf(total)));
        pager.setRows(list);
        return pager;
    }

    @Override
    public HrAuditVo getHrAuditByTaskId(String taskId) throws Exception {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        ProcessInstance process = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        
        LeaveApply leaveApply = leaveApplyMapper.selectByPrimaryKey(Long.parseLong(process.getBusinessKey()));
        SysUser user = userMapper.selectByPrimaryKey(leaveApply.getUserId());//获取请假人信息
        
        HrAuditVo vo = new HrAuditVo();
        vo.setCreateTime(leaveApply.getCreateTime());
        vo.setStartDate(leaveApply.getStartDate());
        vo.setEndDate(leaveApply.getEndDate());
        vo.setType(leaveApply.getType());
        vo.setUserId(leaveApply.getUserId());
        vo.setUserName(user.getRealName());
        vo.setReason(leaveApply.getReason());
        
        return vo;
    }

    @Transactional
    @Override
    public AjaxResult<String> hrAuditComplete(String taskId, String approveResult, Long userId) throws Exception {
        AjaxResult<String> result = new AjaxResult<String>();
        if(StringUtils.isBlank(taskId)){
            result.setCode(10001);
            result.setMsg("任务ID不能为空");
            return result;
        }
        if(userId == null || "".equals(userId.toString())){
            result.setCode(10002);
            result.setMsg("处理人ID不能为空");
            return result;
        }
        try {
            Map<String,Object> variables=new HashMap<String,Object>();
            variables.put("hrapprove", approveResult);//设置流程所需参数，用于流程跳转
            
            taskService.claim(taskId, String.valueOf(userId));//设置任务处理人
            taskService.complete(taskId, variables);//流程执行
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(500);
            result.setMsg("处理失败，请稍候重试");
            return result;
        }
        return result;
    }

}
