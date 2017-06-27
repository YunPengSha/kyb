package net.sinyoo.cooperation.comm.service;

import net.sinyoo.cooperation.comm.model.*;
import net.sinyoo.cooperation.comm.model.query.AllUserInfo;
import net.sinyoo.cooperation.comm.model.query.UserDoctorQuery;
import net.sinyoo.cooperation.core.emnu.UserType;
import net.sinyoo.cooperation.core.exception.ServiceException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/6
 * Time: 下午2:04
 */
public interface UserService {
    void modifyUserPassword(User user);

    User getById(int id);

    User checkSmsCode(User user, String smsCode) throws ServiceException;

    UserInfoDoctor addDoctorInfo(UserInfoDoctor userInfoDoctor, User user) throws ServiceException;

    void modifyDoctorInfo(User user,UserInfoDoctor userInfoDoctor) throws ServiceException;

    /**
     * 登录
     * @param userName
     * @param password
     * @param userType
     * @return
     * @throws ServiceException
     */
    User login(String userName, String password, UserType userType)throws ServiceException;
    /**
     * 获取医生信息
     * @param userId
     * @return
     * @throws ServiceException
     */
    UserDoctorQuery findDoctorInfo(int userId);

    /**
     * 搜索用户列表
     * @param searchKey
     * @return
     */
    List<UserDoctorQuery>  searchUserDoctorList(String searchKey);

    /**
     * 修改用户头像
     * @param userId
     * @param imageUrl
     */
    void modifyImageUrl(int userId,String imageUrl);

    void modifyUserPassword(User user, String smsCode) throws ServiceException;

    //后台用户登录
    User adminLogin(String username, String password) throws ServiceException;
    //增加或者修改科室信息
    UserInfoDepartment addDepartmentDetailInfo(UserInfoDepartment userInfoDepartment) throws ServiceException;
    //增加或者修改药企信息
    UserInfoDrugCompany addDrugCompanyDetailInfo(UserInfoDrugCompany userInfoDrugCompany) throws ServiceException;

    UserInfoDepartment findDepartmentInfo(Integer userId);

    UserInfoDrugCompany findDrugCompanyInfo(Integer userId);

    List<DoctorOutpatientTime> findDoctorOutpatientTime(Integer userId);

    List<User> searchUserList(String searchKey);
    //获取医生信息
    List<UserDoctorQuery> searchDoctorList(int page, int pageSize, String searchKey);

    /**
     * 获取 searchkey 为 医生名字或医生所在医院的医生个数
     * @param searchKey
     * @return
     */
    int findSearchDoctorListCount(String searchKey);

    int addBaseUserInfo(User baseUser);

    void modifyUserInfo(User user);

    List<AllUserInfo> findUserList(Integer page, Integer pageSize, UserType userType);

    //根据用户类型获取该类型用户数量
    Integer findUserCountByType(UserType userType);

    /**
     * 后台添加医生用户
     * @param user
     * @param userInfoDoctor
     * @throws ServiceException
     */
    void addDoctor(User user, UserInfoDoctor userInfoDoctor) throws ServiceException;

    /**
     * 后台添加科室用户
     * @param user
     * @param userInfoDepartment
     */
    void addDepartment(User user, UserInfoDepartment userInfoDepartment) throws ServiceException;

    /**
     * 后台添加药企用户
     * @param user
     * @param userInfoDrugCompany
     */
    void addDrugCompany(User user, UserInfoDrugCompany userInfoDrugCompany) throws ServiceException;

    /**
     * 管理员删除用户  删除主要逻辑为把用户密码置空
     * @param userIds
     */
    void deleteUsers(List<Integer> userIds);

    /**
     * 测试使用，添加手机号，类型 返回用户id值
     * @param phone
     * @param userType
     * @return
     */
    int addPhone(String phone, UserType userType);

    /**
     * 根据手机号和用户类型返回userId
     * @param phone
     * @param userType
     * @return
     */
    Integer findUserByPhoneAndType(String phone, UserType userType);
}
