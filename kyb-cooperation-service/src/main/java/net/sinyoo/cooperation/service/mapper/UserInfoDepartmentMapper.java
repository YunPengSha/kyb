package net.sinyoo.cooperation.service.mapper;

import net.sinyoo.cooperation.service.domain.UserInfoDepartmentDo;
import org.apache.ibatis.annotations.Param;

public interface UserInfoDepartmentMapper {

    int deleteByPrimaryKey(Integer departmentId);

    int insert(UserInfoDepartmentDo record);

    int insertSelective(UserInfoDepartmentDo record);

    UserInfoDepartmentDo selectByPrimaryKey(Integer departmentId);

    int updateByPrimaryKeySelective(UserInfoDepartmentDo record);

    int updateByPrimaryKey(UserInfoDepartmentDo record);

    UserInfoDepartmentDo selectByUserId(@Param("userId") Integer userId);
}