package net.sinyoo.cooperation.service.mapper;

import net.sinyoo.cooperation.service.domain.UserInfoDrugCompanyDo;
import org.apache.ibatis.annotations.Param;

public interface UserInfoDrugCompanyMapper {

    int deleteByPrimaryKey(Integer drugCompanyId);

    int insert(UserInfoDrugCompanyDo record);

    int insertSelective(UserInfoDrugCompanyDo record);

    UserInfoDrugCompanyDo selectByPrimaryKey(Integer drugCompanyId);

    int updateByPrimaryKeySelective(UserInfoDrugCompanyDo record);

    int updateByPrimaryKey(UserInfoDrugCompanyDo record);

    UserInfoDrugCompanyDo selectByUserId(@Param("userId") Integer userId);
}