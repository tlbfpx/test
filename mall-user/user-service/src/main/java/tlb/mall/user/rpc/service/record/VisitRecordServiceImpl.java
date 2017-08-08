package tlb.mall.user.rpc.service.record;

import org.springframework.beans.factory.annotation.Autowired;

import tk.mybatis.mapper.entity.Example;
import tlb.mall.entity.record.VisitRecord;
import tlb.mall.user.dao.record.VisitRecordMapper;
import tlb.mall.user.rpc.api.record.VisitRecordService;
import tlb.mall.user.rpc.service.BaseServiceImpl;

import java.util.List;

//@Service("visitRecordServiceImpl")
public class VisitRecordServiceImpl extends BaseServiceImpl<VisitRecord> implements VisitRecordService {

    @Autowired
    private VisitRecordMapper visitRecordMapper;

    @Override
    public VisitRecord selectBySessionId(String sessionId) throws Exception {
        Example example = new Example(VisitRecord.class);
        example.createCriteria().andEqualTo("reqSessionId", sessionId);
        List<VisitRecord> list = visitRecordMapper.selectByExample(example);
        return (list == null || list.size() <= 0) ? null : list.get(0);
    }

    @Override
    public VisitRecord getLastLogin(String userName) throws Exception {
        return visitRecordMapper.getLastLogin(userName);
    }

    @Override
    public Integer getCurUserDayLoginNum(String userName,String day) throws Exception {
        Integer curUserTodayLoginNum = visitRecordMapper.getCurUserDayLoginNum(userName,day);
        return curUserTodayLoginNum;
    }

    
}
