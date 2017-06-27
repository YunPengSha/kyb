package net.sinyoo.cooperation.service.mapper;

import net.sinyoo.cooperation.core.emnu.SmsCodeType;
import net.sinyoo.cooperation.service.domain.SmsCodeDo;
import org.apache.ibatis.annotations.*;

public interface SmsCodeMapper {
    int deleteByPrimaryKey(Integer smsCodeId);

    int insert(SmsCodeDo record);

    int insertSelective(SmsCodeDo record);

    SmsCodeDo selectByPrimaryKey(Integer smsCodeId);

    int updateByPrimaryKeySelective(SmsCodeDo record);

    int updateByPrimaryKey(SmsCodeDo record);

    /**
     *
     * @param userId
     * @param smsCodeType
     * @return
     */
    @Update("update sms_code set sms_code_status = 1 where ref_id = #{userId} and sms_code_type = #{smsCodeType}")
    int updateExpire(@Param("userId") int userId,@Param("smsCodeType") SmsCodeType smsCodeType);

    /**
     * 根据用户编号和验证码类别查询短信验证码
     * @param userId
     * @param smsCodeType
     * @return
     */
    @Select("select * from sms_code where ref_id = #{userId} and sms_code_type = #{smsCodeType} and sms_code_status = 0 order by sms_code_id desc limit 0,1")
    @ResultMap("BaseResultMap")
    SmsCodeDo selectByUserId(@Param("userId") int userId,@Param("smsCodeType") SmsCodeType smsCodeType);

    /**
     * 删除旧的验证码
     * @param userId
     */
    @Delete(" delete from sms_code where ref_id = #{userId} ")
    void deleteByUserId( @Param("userId") Integer userId);
}