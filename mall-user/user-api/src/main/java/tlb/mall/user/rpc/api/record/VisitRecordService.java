package tlb.mall.user.rpc.api.record;


import tlb.mall.entity.record.VisitRecord;
import tlb.mall.user.rpc.api.BaseService;

public interface VisitRecordService extends BaseService<VisitRecord> {

    /**
     * 根据sessionId获取用户登录信息
     * @param sessionId
     * @return
     * @throws Exception
     */
    VisitRecord selectBySessionId(String sessionId) throws Exception;

    /**
     * 获取上次登录信息
     * @return
     * @throws Exception
     */
    VisitRecord getLastLogin(String userName) throws Exception;

    /**
     * 获取用户某日登录次数
     * @param userName
     * @return
     * @throws Exception
     */
    Integer getCurUserDayLoginNum(String userName, String day) throws Exception;

}
