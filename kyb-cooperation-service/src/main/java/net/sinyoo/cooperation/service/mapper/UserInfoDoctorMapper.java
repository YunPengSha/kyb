package net.sinyoo.cooperation.service.mapper;

import net.sinyoo.cooperation.comm.model.UserInfoDoctor;
import net.sinyoo.cooperation.service.domain.UserInfoDoctorDo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

public interface UserInfoDoctorMapper {
    int deleteByPrimaryKey(Integer doctorId);

    int insert(UserInfoDoctorDo record);

    int insertSelective(UserInfoDoctorDo record);

    UserInfoDoctorDo selectByPrimaryKey(Integer doctorId);

    int updateByPrimaryKeySelective(UserInfoDoctorDo record);

    int updateByPrimaryKey(UserInfoDoctorDo record);

    int updateByUserIdSelective(UserInfoDoctorDo record);

    @Select("select * from user_info_doctor where user_id = #{userId} limit 0,1")
    @ResultMap("BaseResultMap")
    UserInfoDoctorDo getByUserId(Integer userId);

}