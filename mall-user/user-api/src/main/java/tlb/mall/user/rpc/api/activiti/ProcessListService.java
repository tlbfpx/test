package tlb.mall.user.rpc.api.activiti;

import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.web.multipart.MultipartFile;
import tlb.mall.common.util.AjaxResult;
import tlb.mall.common.util.Pager;
import tlb.mall.entity.activiti.qo.ProcessQo;
import tlb.mall.entity.activiti.vo.ProcessVo;


/**
 * 
 * @description 流程定义相关service
 * 
 * @author zhoubang 
 * @date 2017年3月30日 上午9:56:11 
 *
 */
public interface ProcessListService {

    /**
     * 
     * @description 查询已经部署的工作流列表
     * 
     * @author zhoubang
     * @date 2017年3月21日 下午3:41:27
     * 
     * @param pager
     * @param processQo
     * @return
     */
    Pager<ProcessVo> getList(Pager<ProcessVo> pager, ProcessQo processQo);

    /**
     * 
     * @description 删除已部署的工作流实例
     * 
     * @author zhoubang
     * @param result
     * @date 2017年3月21日 下午3:40:35
     * 
     * @throws Exception
     */
    AjaxResult<String> deleteProcess(ProcessQo qo, AjaxResult<String> result) throws Exception;

    /**
     * 
     * @description 上传流程文件
     * 
     * @author zhoubang
     * @date 2017年3月29日 上午11:00:58
     * 
     * @param uploadFile
     * @throws Exception
     */
    void processFileUpload(MultipartFile uploadFile) throws Exception;

    /**
     * 
     * @description 获取流程定义
     * 
     * @author zhoubang
     * @date 2017年3月29日 下午3:16:06
     * 
     * @param processDeploymentId
     * @param resource
     * @return
     * @throws Exception
     */
    ProcessDefinition getProcessDefinition(String processDeploymentId, String resource) throws Exception;

}
