package tlb.mall.user.rpc.service.activiti;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tlb.mall.common.util.AjaxResult;
import tlb.mall.common.util.Pager;
import tlb.mall.entity.activiti.qo.ProcessQo;
import tlb.mall.entity.activiti.vo.ProcessVo;
import tlb.mall.user.rpc.api.activiti.ProcessListService;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//@Service("processListServiceImpl")
public class ProcessListServiceImpl implements ProcessListService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RepositoryService repositoryService;

    @Override
    public Pager<ProcessVo> getList(Pager<ProcessVo> pager, ProcessQo processQo) {

        /**
         * 获取已部署的工作流列表
         */
        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().listPage(pager.getOffset(), pager.getLimit());
        int total = repositoryService.createProcessDefinitionQuery().list().size();

        List<ProcessVo> rows = new ArrayList<ProcessVo>();

        for (ProcessDefinition process : processDefinitionList) {
            ProcessVo vo = new ProcessVo();
            vo.setDeploymentId(process.getDeploymentId());
            vo.setId(process.getId());
            vo.setDiagramResourceName(process.getDiagramResourceName());
            vo.setKey(process.getKey());
            vo.setName(process.getName());
            vo.setResourceName(process.getResourceName());
            rows.add(vo);
        }

        pager.setTotal(Long.parseLong(String.valueOf(total)));
        pager.setRows(rows);
        return pager;
    }

    @Transactional
    @Override
    public AjaxResult<String> deleteProcess(ProcessQo qo, AjaxResult<String> result) throws Exception {
        if (qo == null) {
            result.setCode(500);
            result.setMsg("请指定需要删除的流程");
            return result;
        }
        if (StringUtils.isBlank(qo.getDeploymentId())) {
            result.setCode(500);
            result.setMsg("流程id为空");
            return result;
        }
        repositoryService.deleteDeployment(qo.getDeploymentId(), true);
        return result;
    }

    @Transactional
    @Override
    public void processFileUpload(MultipartFile uploadFile) throws Exception {
        MultipartFile multipartFile = uploadFile;
        String fileName = multipartFile.getOriginalFilename();// 获取上传文件名

        InputStream is = multipartFile.getInputStream();
        repositoryService.createDeployment().addInputStream(fileName, is).deploy();// 保存到工作流
    }

    @Override
    public ProcessDefinition getProcessDefinition(String processDeploymentId, String resource) throws Exception {
        ProcessDefinition def = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDeploymentId).singleResult();
        return def;
    }

}
