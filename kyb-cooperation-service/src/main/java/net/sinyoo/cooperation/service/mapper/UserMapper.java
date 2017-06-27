package net.sinyoo.cooperation.service.mapper;

import net.sinyoo.cooperation.core.emnu.UserType;
import net.sinyoo.cooperation.service.domain.UserDo;
import net.sinyoo.cooperation.service.domain.query.UserDoctorQueryDo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

public interface UserMapper {

    int deleteByPrimaryKey(Integer userId);

    @SelectKey(before = false, keyProperty = "userId", resultType = Integer.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS userId")
    int insert(UserDo record);

    @SelectKey(before = false, keyProperty = "userId", resultType = Integer.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS userId")
    int insertSelective(UserDo record);

    UserDo selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserDo record);

    int updateByPrimaryKey(UserDo record);

    /**
     * 根据手机号码获取用户
     * @param phone
     * @return
     */
    UserDo selectByPhone(@Param("phone") String phone);

    /**
     * 查询用户列表
     * @param searchKey
     * @return 最多返回20条记录
     */
    List<UserDoctorQueryDo> searchUserDoctorList(@Param("searchKey")String searchKey);

    /**
     * 修改用户的头像地址
     * @param userId
     * @param imageUrl
     * @return
     */
    @Update("update user set image_url = #{imageUrl} where user_id = #{userId}")
    int updateUserImageUrl(@Param("userId")int userId,@Param("imageUrl")String imageUrl);

    /**
     * 根据手机号码修改用户密码
     * @param phone
     * @param password
     * @param userType
     * @return
     */
    @Update("update user set password = #{password} where phone = #{phone} and user_type = #{userType}")
    int updatePasswordByPhone(@Param("phone") String phone, @Param("password") String password,@Param("userType") UserType userType);

    @Update("update user set password = #{password} where user_id = #{userId}")
    int updatePasswordById(@Param("userId") int id,@Param("password") String password);

    /**
     * 根据用户名密码获取管理员用户
     * @param userName
     * @param password
     * @return
     */
    UserDo findAdminByName(@Param("userName") String userName, @Param("password") String password);

    /**
     * 根据手机号和用户类型获取用户
     * @param userName
     * @param userType
     * @return
     */
    UserDo selectByPhoneAndType(@Param("userName") String userName,@Param("userType") UserType userType);

    /**
     * 更新用户名
     * @param userId
     * @param username
     */
    @Update(" update user set user_name = #{username} where user_id = #{userId} ")
    void updateUsername(@Param("userId") Integer userId,@Param("username") String username);

    /**
     * 根据用户名获取所有医生和科室用户
     * @param searchKey
     * @return
     */
    List<UserDo> searchAllUserList(@Param("searchKey") String searchKey);

    /**
     *   根据 searchkey 查找 名字或医院带有searchkey的医生信息
     * @param searchKey
     * @return
     */
    List<UserDoctorQueryDo> searchDoctorListLikeNameOrHospital(@Param("offSet") int offSet, @Param("rows") int rows,@Param("searchKey") String searchKey);

    /**
     * 获取 searchkey 为 医生名字或医生所在医院的医生个数
     * @param searchKey
     * @return
     */
    int getSearchDoctorListCount(@Param("searchKey") String searchKey);

    /**
     * 获取用户列表
     * @param offSet
     * @param rows
     * @param userType
     * @return
     */
    List<UserDo> getUserList(@Param("offSet") int offSet, @Param("rows") int rows, @Param("userType") UserType userType);

    /**
     * 根据类型获取用户数量
     * @param userType
     * @return
     */
    @Select( "select count(user_id) from user where user_type = #{userType} and password != '' " )
    Integer getUserTypeCount(@Param("userType") UserType userType);

}