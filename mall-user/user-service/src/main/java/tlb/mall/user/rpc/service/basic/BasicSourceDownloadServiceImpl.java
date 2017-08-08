package tlb.mall.user.rpc.service.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tlb.mall.entity.basic.BasicSourceDownload;
import tlb.mall.user.dao.basic.BasicSourceDownloadMapper;
import tlb.mall.user.rpc.api.basic.BasicSourceDownloadService;
import tlb.mall.user.rpc.service.BaseServiceImpl;


@Service("basicSourceDownloadServiceImpl")
public class BasicSourceDownloadServiceImpl extends BaseServiceImpl<BasicSourceDownload> implements BasicSourceDownloadService {

    @Autowired
    private BasicSourceDownloadMapper basicSourceDownloadMapper;
}
