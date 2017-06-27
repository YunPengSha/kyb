package net.sinyoo.cooperation.service.mapper;

import net.sinyoo.cooperation.core.annotation.Past;
import net.sinyoo.cooperation.service.domain.DoctorOutpatientTimeDo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

public interface DoctorOutpatientTimeMapper {
    int deleteByPrimaryKey(Integer outpatientTimeId);

    @SelectKey(before = false, keyProperty = "outpatientTimeId", resultType = Integer.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS outpatientTimeId")
    int insert(DoctorOutpatientTimeDo record);

    @SelectKey(before = false, keyProperty = "outpatientTimeId", resultType = Integer.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS outpatientTimeId")
    int insertSelective(DoctorOutpatientTimeDo record);

    DoctorOutpatientTimeDo selectByPrimaryKey(Integer outpatientTimeId);

    int updateByPrimaryKeySelective(DoctorOutpatientTimeDo record);

    int updateByPrimaryKey(DoctorOutpatientTimeDo record);

    /**
     * 删除用户的门诊时间
     * @param userId
     * @return
     */
    @Delete("delete from doctor_outpatient_time where user_id = #{userId}")
    int deleteByUserId(@Param("userId")int userId);
    /**
     * 获取用户的门诊时间
     * @param userId
     * @return
     */
    @Select("select * from doctor_outpatient_time where user_id = #{userId} order by week_name asc limit 0,15")
    @ResultMap("BaseResultMap")
    List<DoctorOutpatientTimeDo> getByUserId(@Param("userId")int userId);
}