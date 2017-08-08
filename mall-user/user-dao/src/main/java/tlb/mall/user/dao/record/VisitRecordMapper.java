package tlb.mall.user.dao.record;

import org.apache.ibatis.annotations.Param;


import tk.mybatis.mapper.common.Mapper;
import tlb.mall.entity.record.VisitRecord;

public interface VisitRecordMapper extends Mapper<VisitRecord> {

    /**
     * 获取上次登录的信息
     * @param userName
     * @return
     */
    VisitRecord getLastLogin(@Param("userName") String userName);

    /**
     * 获取用户某日登录次数
     * @param userName
     * @return
     */
    Integer getCurUserDayLoginNum(@Param("userName") String userName, @Param("day") String day);

}
