package net.sinyoo.cooperation.service.service.impl;

import net.sinyoo.cooperation.comm.model.*;
import net.sinyoo.cooperation.comm.model.query.AllUserInfo;
import net.sinyoo.cooperation.comm.model.query.UserDoctorQuery;
import net.sinyoo.cooperation.comm.service.UserService;
import net.sinyoo.cooperation.core.emnu.SmsCodeType;
import net.sinyoo.cooperation.core.emnu.UserType;
import net.sinyoo.cooperation.core.exception.ServiceException;
import net.sinyoo.cooperation.core.util.MD5Util;
import net.sinyoo.cooperation.core.util.MyBeanUtils;
import net.sinyoo.cooperation.core.util.StringUtils;
import net.sinyoo.cooperation.service.domain.*;
import net.sinyoo.cooperation.service.domain.query.UserDoctorQueryDo;
import net.sinyoo.cooperation.service.mapper.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yunpengsha on 2017/3/6.
 */
@Component
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private SmsCodeMapper smsCodeMapper;

    @Resource
    private UserInfoDoctorMapper userInfoDoctorMapper;

    @Resource
    private DoctorOutpatientTimeMapper doctorOutpatientTimeMapper;

    @Resource
    private UserInfoDepartmentMapper userInfoDepartmentMapper;

    @Resource
    private UserInfoDrugCompanyMapper drugCompanyMapper;

    /**
     * 修改用户密码
     *
     * @param user
     */
    @Override
    public void modifyUserPassword(User user) {
        userMapper.updatePasswordById(user.getUserId(), MD5Util.MD5(user.getPassword()));
    }

    @Override
    public User getById(int id) {

        UserDo userDo = userMapper.selectByPrimaryKey(id);
        if (userDo == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(userDo, user);
        return user;
    }

    @Override
    public User checkSmsCode(User user, String smsCode) throws ServiceException {
        System.out.println(user);
        //获得手机号对应的用户
        UserDo oldUserDo = userMapper.selectByPhoneAndType(user.getPhone(),user.getUserType());
        //根据用户id得到 smsCode验证码 判断验证码是否相同且有效
        if (oldUserDo == null) {
            throw new ServiceException(601, "请先获取短息验证码");
        }
        SmsCodeDo smsCodeDo = smsCodeMapper.selectByUserId(oldUserDo.getUserId(), SmsCodeType.REGISTER_CODE);
        if (smsCodeDo == null) {
            throw new ServiceException(602, "验证码错误");
        }
        if (smsCodeDo.getExpireTime() < System.currentTimeMillis()) {
            smsCodeMapper.deleteByPrimaryKey(smsCodeDo.getSmsCodeId());
            throw new ServiceException(603, "验证码已失效");
        }
        if (StringUtils.isEmpty(smsCode) || !smsCodeDo.getSmsCode().equals(smsCode)) {
            throw new ServiceException(602, "验证码错误");
        }
/*        //验证通过 插入用户信息
        userDo.setUserId(oldUserDo.getUserId());
        //加密密码
        userDo.setPassword(MD5Util.MD5(userDo.getPassword()));

        userDo.setUserType(user.getUserType());

        userMapper.updateByPrimaryKeySelective(userDo);*/
        //删除验证码
        smsCodeMapper.deleteByPrimaryKey(smsCodeDo.getSmsCodeId());
        return new MyBeanUtils<UserDo, User>().copyBean(oldUserDo, User.class);
    }

    @Override
    public UserInfoDoctor addDoctorInfo(UserInfoDoctor userInfoDoctor, User user) throws ServiceException {

        UserDo userDo = new UserDo();
        BeanUtils.copyProperties(user, userDo);
        //插入基本信息,返回id
        userMapper.updateByPrimaryKeySelective(userDo);
        //插入/更新详细信息
        UserInfoDoctorDo userInfoDoctorDo = userInfoDoctorMapper.getByUserId(user.getUserId());
        if (userInfoDoctorDo == null) {
            userInfoDoctorDo = new UserInfoDoctorDo();
            BeanUtils.copyProperties(userInfoDoctor, userInfoDoctorDo);
            userInfoDoctorMapper.insertSelective(userInfoDoctorDo);
        } else {
            userInfoDoctorDo = new UserInfoDoctorDo();
            BeanUtils.copyProperties(userInfoDoctor, userInfoDoctorDo);
            userInfoDoctorMapper.updateByPrimaryKeySelective(userInfoDoctorDo);
        }

        //添加门诊时间
        List<DoctorOutpatientTime> dops = userInfoDoctor.getDoctorOutpatientTimes();
        updateDoctorOutpatientTime(user.getUserId(), dops);
        userInfoDoctor = new MyBeanUtils<UserInfoDoctorDo, UserInfoDoctor>().copyBean(userInfoDoctorDo, UserInfoDoctor.class);
        userInfoDoctor.setDoctorOutpatientTimes(dops);
        return userInfoDoctor;
    }

    @Override
    public void modifyDoctorInfo(User user, UserInfoDoctor userInfoDoctor) throws ServiceException {
        if (user != null) {
            UserDo userDo = new UserDo();
            BeanUtils.copyProperties(user, userDo);
            if (userMapper.updateByPrimaryKeySelective(userDo) <= 0) {
                throw new ServiceException(601, "修改信息失败");
            }
        }

        if (userInfoDoctor != null) {
            //插入/更新详细信息
            UserInfoDoctorDo userInfoDoctorDo = userInfoDoctorMapper.getByUserId(user.getUserId());
            if (userInfoDoctorDo == null) {
                userInfoDoctorDo = new UserInfoDoctorDo();
                BeanUtils.copyProperties(userInfoDoctor, userInfoDoctorDo);
                userInfoDoctorMapper.insertSelective(userInfoDoctorDo);
            } else {
                userInfoDoctorDo = new UserInfoDoctorDo();
                BeanUtils.copyProperties(userInfoDoctor, userInfoDoctorDo);
                if (userInfoDoctorMapper.updateByUserIdSelective(userInfoDoctorDo) <= 0) {
                    throw new ServiceException(602, "修改信息失败");
                }
            }
        }
        if (user != null && userInfoDoctor != null) {
            //添加门诊时间
            updateDoctorOutpatientTime(user.getUserId(), userInfoDoctor.getDoctorOutpatientTimes());
        }

    }

    /**
     *
     * @param userName
     * @param password
     * @param userType
     * @return
     * @throws ServiceException
     */
    @Override
    public User login(String userName, String password, UserType userType) throws ServiceException {

        UserDo userDo = userMapper.selectByPhoneAndType(userName,userType);
        if (userDo == null) {
            throw new ServiceException(601, "用户不存在");
        }
        if (!MD5Util.MD5(password).equals(userDo.getPassword())) {
            throw new ServiceException(602, "帐号密码错误");
        }

        return new MyBeanUtils<UserDo,User>().copyBean(userDo,User.class);


/*        UserDo userDo = userMapper.selectByPhoneAndType(userName,userType);
        if (userDo == null) {
            throw new ServiceException(601, "用户不存在");
        }
        if (!MD5Util.MD5(password).equals(userDo.getPassword())) {
            throw new ServiceException(602, "帐号密码错误");
        }
        if( UserType.DOCTOR == userType ){
            //获取医生的详细信息
            UserInfoDoctorDo userInfoDoctorDo = userInfoDoctorMapper.getByUserId(userDo.getUserId());
            UserDoctorQuery userDoctorQuery = new UserDoctorQuery();
            BeanUtils.copyProperties(userDo, userDoctorQuery);
            if (userInfoDoctorDo != null) {
                BeanUtils.copyProperties(userInfoDoctorDo, userDoctorQuery);
            }
            return userDoctorQuery;
        }
        *//**
         * 后续加入  药企
         *//*
        return null;*/
    }

    @Override
    public UserDoctorQuery findDoctorInfo(int userId) {
        //获取医生帐号信息
        UserDo userDo = userMapper.selectByPrimaryKey(userId);
        if (userDo == null) {
            return null;
        }
        //获取医生的详细信息
        UserInfoDoctorDo userInfoDoctorDo = userInfoDoctorMapper.getByUserId(userId);
        UserDoctorQuery userDoctorQuery = new UserDoctorQuery();
        BeanUtils.copyProperties(userDo, userDoctorQuery);
        if (userInfoDoctorDo != null) {
            BeanUtils.copyProperties(userInfoDoctorDo, userDoctorQuery);
        }
        //获取医生的门诊时间
        List<DoctorOutpatientTimeDo> dotDos = doctorOutpatientTimeMapper.getByUserId(userId);
        userDoctorQuery.setDoctorOutpatientTimes(new MyBeanUtils<DoctorOutpatientTimeDo, DoctorOutpatientTime>().copyList(dotDos, DoctorOutpatientTime.class));
        return userDoctorQuery;
    }

    @Override
    public List<UserDoctorQuery> searchUserDoctorList(String searchKey) {
        List<UserDoctorQuery> userDoctorQueries = new MyBeanUtils<UserDoctorQueryDo, UserDoctorQuery>().copyList(userMapper.searchUserDoctorList(searchKey), UserDoctorQuery.class);
        // 先在获取列表的时候不获取门诊时间 查询用户的门诊时间
//        if (userDoctorQueries != null){
//            for (int i = 0;i< userDoctorQueries.size();i++){
//                UserDoctorQuery userDoctorQuery = userDoctorQueries.get(i);
//                List<DoctorOutpatientTimeDo> dotDos = doctorOutpatientTimeMapper.getByUserId(userDoctorQuery.getUserId());
//                userDoctorQuery.setDoctorOutpatientTimes(new MyBeanUtils<DoctorOutpatientTimeDo,DoctorOutpatientTime>().copyList(dotDos,DoctorOutpatientTime.class));
//
//                userDoctorQueries.set(i,userDoctorQuery);
//            }
//        }
        return userDoctorQueries;
    }

    /**
     * 根据 searchkey 查找 名字或医院带有searchkey的医生信息
     *
     * @param page
     * @param pageSize
     *@param searchKey  @return
     */
    @Override
    public List<UserDoctorQuery> searchDoctorList(int page, int pageSize, String searchKey) {
        List<UserDoctorQuery> userDoctorQueries = new MyBeanUtils<UserDoctorQueryDo, UserDoctorQuery>().copyList(userMapper.searchDoctorListLikeNameOrHospital(page-1,page*pageSize,searchKey), UserDoctorQuery.class);
        return  userDoctorQueries;
    }


    @Override
    public int findSearchDoctorListCount(String searchKey) {
        return userMapper.getSearchDoctorListCount(searchKey);
    }

    @Override
    public int addBaseUserInfo(User baseUser) {
        UserDo userDo = userMapper.selectByPhone(baseUser.getPhone());
        userMapper.updateByPrimaryKeySelective(new MyBeanUtils<User,UserDo>().copyBean(baseUser,UserDo.class));
        return userDo.getUserId();
    }

    @Override
    public void modifyUserInfo(User user) {
        userMapper.updateByPrimaryKey(new MyBeanUtils<User,UserDo>().copyBean(user,UserDo.class));
    }

    @Override
    public List<AllUserInfo> findUserList(Integer page, Integer pageSize, UserType userType) {
        List<UserDo> list = userMapper.getUserList((page-1)*pageSize,pageSize,userType);
        List<AllUserInfo> allUserInfos = new ArrayList<>();
        for( UserDo user : list ){
            AllUserInfo allUserInfo = new AllUserInfo();

            BeanUtils.copyProperties(user,allUserInfo);

            if( user.getUserType() == UserType.DOCTOR ){
                UserInfoDoctorDo userInfoDoctorDo = userInfoDoctorMapper.getByUserId(user.getUserId());
                if( userInfoDoctorDo != null ){
                    BeanUtils.copyProperties(userInfoDoctorDo,allUserInfo);
                }
            }
            else if( user.getUserType() == UserType.DEPARTMENT ){
                UserInfoDepartmentDo userInfoDepartmentDo = userInfoDepartmentMapper.selectByUserId(user.getUserId());
                if( userInfoDepartmentDo != null ){
                    BeanUtils.copyProperties(userInfoDepartmentDo,allUserInfo);
                }
            }
            else if( user.getUserType() == UserType.DRUG_COMPANY ){
                UserInfoDrugCompanyDo userInfoDrugCompanyDo = drugCompanyMapper.selectByPrimaryKey(user.getUserId());
                if( userInfoDrugCompanyDo != null ){
                    BeanUtils.copyProperties(userInfoDrugCompanyDo,allUserInfo);
                }
            }
            allUserInfos.add(allUserInfo);
        }

        return allUserInfos;
    }

    @Override
    public Integer findUserCountByType(UserType userType) {
        Integer count = userMapper.getUserTypeCount(userType);
        return count;
    }

    /**
     * 后台添加医生用户
     * @param user
     * @param userInfoDoctor
     * @throws ServiceException
     */
    @Override
    public void addDoctor(User user, UserInfoDoctor userInfoDoctor) throws ServiceException {

        UserDo u = userMapper.selectByPhoneAndType( user.getPhone() , UserType.DOCTOR );
        //  如果有手机号，没密码则先把原先的记录删除了，再加新的user用户记录
        if( u != null ){
            Integer userId = u.getUserId();
            if( u.getPassword() != null ){
                throw new ServiceException(601,"该手机号已经注册过同种类型的用户");
            }
            userMapper.deleteByPrimaryKey( userId );
        }

        UserDo userDo = new MyBeanUtils<User,UserDo>().copyBean(user,UserDo.class);
        addUserDoInfo(userDo,UserType.DOCTOR);
        userMapper.insertSelective(userDo);
        userInfoDoctor.setUserId(userDo.getUserId());
        UserInfoDoctorDo userInfoDoctorDo = new MyBeanUtils<UserInfoDoctor,UserInfoDoctorDo>().copyBean(userInfoDoctor,UserInfoDoctorDo.class);
        userInfoDoctorMapper.insertSelective(userInfoDoctorDo);

    }

    /**
     * 后台添加科室用户
     * @param user
     * @param userInfoDepartment
     */
    @Override
    public void addDepartment(User user, UserInfoDepartment userInfoDepartment) throws ServiceException{
        UserDo u = userMapper.selectByPhoneAndType( user.getPhone(),UserType.DEPARTMENT );
        if( u != null ){
            if( u.getPassword() != null ){
                throw new ServiceException(601,"该手机号已经注册过同种类型的用户");
            }
        }

        UserDo userDo = new MyBeanUtils<User,UserDo>().copyBean(user,UserDo.class);
        userDo.setUserName(userInfoDepartment.getHospital()+userInfoDepartment.getDepartment());
        addUserDoInfo(userDo,UserType.DEPARTMENT);
        userMapper.insertSelective(userDo);
        userInfoDepartment.setUserId(userDo.getUserId());
        UserInfoDepartmentDo userInfoDepartmentDo = new MyBeanUtils<UserInfoDepartment,UserInfoDepartmentDo>().copyBean(userInfoDepartment,UserInfoDepartmentDo.class);
        userInfoDepartmentMapper.insertSelective(userInfoDepartmentDo);
    }

    /**
     * 后台添加药企用户
     * @param user
     * @param userInfoDrugCompany
     * @throws ServiceException
     */
    @Override
    public void addDrugCompany(User user, UserInfoDrugCompany userInfoDrugCompany) throws ServiceException {

        UserDo u = userMapper.selectByPhoneAndType( user.getPhone(),UserType.DRUG_COMPANY );
        if( u != null ){
            if( u.getUserType() == UserType.DRUG_COMPANY ){
                throw new ServiceException(601,"该手机号已经注册过同种类型的用户");
            }
        }

        UserDo userDo = new MyBeanUtils<User,UserDo>().copyBean(user,UserDo.class);
        userDo.setUserName(userInfoDrugCompany.getCompany());
        addUserDoInfo(userDo,UserType.DRUG_COMPANY);
        userMapper.insertSelective(userDo);
        userInfoDrugCompany.setUserId(userDo.getUserId());
        UserInfoDrugCompanyDo userInfoDrugCompanyDo = new MyBeanUtils<UserInfoDrugCompany,UserInfoDrugCompanyDo>().copyBean(userInfoDrugCompany,UserInfoDrugCompanyDo.class);
        drugCompanyMapper.insertSelective(userInfoDrugCompanyDo);

    }

    /**
     * 管理员删除用户  删除主要逻辑为把用户密码置空
     * @param userIds
     */
    @Override
    public void deleteUsers(List<Integer> userIds) {

        for( Integer id:userIds ){
            userMapper.updatePasswordById(id,null);
        }

    }

    /**
     * 测试使用  录入数据使用
     * @param phone
     * @param userType
     * @return
     */
    @Override
    public int addPhone(String phone, UserType userType) {
        UserDo userDo = new UserDo();
        userDo.setPhone(phone);
        userDo.setUserType(userType);
        userMapper.insertSelective(userDo);
        return userDo.getUserId();
    }

    @Override
    public Integer findUserByPhoneAndType(String phone, UserType userType) {
        UserDo userDo = userMapper.selectByPhoneAndType(phone, userType);
        return userDo.getUserId();
    }

    /**
     * 后台添加用户专用，设置默认密码，状态
     * @param userDo
     */
    private void addUserDoInfo(UserDo userDo,UserType userType) {

        userDo.setPassword(MD5Util.MD5("123456"));
        userDo.setUserType(userType);
        userDo.setUserStatus(0);
        userDo.setCreateTime(new Date());
    }

    @Override
    public void modifyImageUrl(int userId, String imageUrl) {
        UserDo userDo = new UserDo();
        userDo.setUserId(userId);
        userDo.setImageUrl(imageUrl);
        userMapper.updateByPrimaryKeySelective(userDo);
    }

    /**
     * 重置密码
     *
     * @param user
     * @param smsCode
     * @throws ServiceException
     */
    @Override
    public void modifyUserPassword(User user, String smsCode) throws ServiceException {

        //获得手机号对应的用户
        UserDo oldUserDo = userMapper.selectByPhoneAndType(user.getPhone(),user.getUserType());
        //根据用户id得到 smsCode验证码 判断验证码是否相同且有效
        if (oldUserDo == null) {
            throw new ServiceException(602, "你还没有注册过该账户");
        }
        SmsCodeDo smsCodeDo = smsCodeMapper.selectByUserId(oldUserDo.getUserId(), SmsCodeType.REGISTER_CODE);
        if (smsCodeDo == null) {
            throw new ServiceException(604, "验证码错误");
        }
        if (smsCodeDo.getExpireTime() < System.currentTimeMillis()) {
            throw new ServiceException(603, "验证码已失效");
        }
        if (StringUtils.isEmpty(smsCode) || !smsCodeDo.getSmsCode().equals(smsCode)) {
            throw new ServiceException(604, "验证码错误");
        }
        //验证通过 插入用户信息
        userMapper.updatePasswordByPhone(user.getPhone(), MD5Util.MD5(user.getPassword()),user.getUserType());
        //删除验证码
        smsCodeMapper.deleteByPrimaryKey(smsCodeDo.getSmsCodeId());

    }

    /**
     * 添加门诊时间
     *
     * @param userId
     * @param doctorOutpatientTimes
     */
    private void updateDoctorOutpatientTime(int userId, List<DoctorOutpatientTime> doctorOutpatientTimes) {
        if (doctorOutpatientTimes != null) {
            //删除之前的门诊时间
            doctorOutpatientTimeMapper.deleteByUserId(userId);
            //添加新的门诊时间
            DoctorOutpatientTimeDo doctorOutpatientTimeDo = null;
            for (DoctorOutpatientTime doctorOutpatientTime : doctorOutpatientTimes) {
                doctorOutpatientTimeDo = new DoctorOutpatientTimeDo();
                BeanUtils.copyProperties(doctorOutpatientTime, doctorOutpatientTimeDo);
                //补全数据
                doctorOutpatientTimeDo.setUserId(userId);
                if (StringUtils.isEmpty(doctorOutpatientTimeDo.getStartTime())) {
                    doctorOutpatientTimeDo.setStartTime("09:00");
                }
                if (StringUtils.isEmpty(doctorOutpatientTimeDo.getEndTime())) {
                    doctorOutpatientTimeDo.setEndTime("18:00");
                }
                doctorOutpatientTimeMapper.insert(doctorOutpatientTimeDo);
//                doctorOutpatientTime.setOutpatientTimeId(doctorOutpatientTimeDo.getOutpatientTimeId());
            }
        }
    }

    /**
     * 管理员登录
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public User adminLogin(String username, String password) throws ServiceException {

        UserDo userDo = userMapper.findAdminByName(username, MD5Util.MD5(password));
        if (userDo == null) {
            throw new ServiceException(601, "用户不存在，输入错误");
        }

        return new MyBeanUtils<UserDo, User>().copyBean(userDo, User.class);
    }

    /**
     * 添加科室详细信息
     * @param userInfoDepartment
     * @throws ServiceException
     */
    @Override
    public UserInfoDepartment addDepartmentDetailInfo(UserInfoDepartment userInfoDepartment) throws ServiceException {

        UserInfoDepartmentDo userInfoDepartmentDo = userInfoDepartmentMapper.selectByUserId(userInfoDepartment.getUserId());
        Integer departmentId = 0;
        if( userInfoDepartmentDo != null ){
            departmentId = userInfoDepartmentDo.getDepartmentId();
        }
        if( userInfoDepartmentDo == null ){
            userInfoDepartmentDo = new MyBeanUtils<UserInfoDepartment,UserInfoDepartmentDo>().copyBean(userInfoDepartment,UserInfoDepartmentDo.class);
            int i = userInfoDepartmentMapper.insertSelective(userInfoDepartmentDo);
            if ( i <= 0 ){
                throw new ServiceException(601,"信息新增失败");
            }
            //往user 表 插入 username 信息
            String username = userInfoDepartment.getHospital()+userInfoDepartment.getDepartment();
            userMapper.updateUsername(userInfoDepartment.getUserId(),username);
        }
        else {
            userInfoDepartmentDo = new MyBeanUtils<UserInfoDepartment,UserInfoDepartmentDo>().copyBean(userInfoDepartment,UserInfoDepartmentDo.class);
            userInfoDepartmentDo.setDepartmentId(departmentId);
            userInfoDepartmentMapper.updateByPrimaryKeySelective(userInfoDepartmentDo);
            if( userInfoDepartmentDo.getHospital() != null || userInfoDepartmentDo.getDepartment() != null ){
                // 如果更新了医院或科室     往user 表 更新 name 信息
                userInfoDepartmentDo = userInfoDepartmentMapper.selectByUserId(userInfoDepartment.getUserId());
                String username = userInfoDepartment.getHospital()+userInfoDepartment.getDepartment();
                userMapper.updateUsername(userInfoDepartment.getUserId(),username);
            }
        }

        return new MyBeanUtils<UserInfoDepartmentDo,UserInfoDepartment>().copyBean(userInfoDepartmentDo,UserInfoDepartment.class);
    }

    @Override
    public UserInfoDrugCompany addDrugCompanyDetailInfo(UserInfoDrugCompany userInfoDrugCompany) throws ServiceException {

        UserInfoDrugCompanyDo userInfoDrugCompanyDo = drugCompanyMapper.selectByUserId(userInfoDrugCompany.getUserId());
        Integer companyId = 0;
        if( userInfoDrugCompanyDo != null ){
            companyId = userInfoDrugCompanyDo.getDrugCompanyId();
        }

        if( userInfoDrugCompanyDo == null ){
            userInfoDrugCompanyDo = new MyBeanUtils<UserInfoDrugCompany,UserInfoDrugCompanyDo>().copyBean(userInfoDrugCompany,UserInfoDrugCompanyDo.class);
            drugCompanyMapper.insertSelective(userInfoDrugCompanyDo);
            userMapper.updateUsername(userInfoDrugCompany.getUserId(),userInfoDrugCompany.getCompany());
        }
        else {
            userInfoDrugCompanyDo = new MyBeanUtils<UserInfoDrugCompany,UserInfoDrugCompanyDo>().copyBean(userInfoDrugCompany,UserInfoDrugCompanyDo.class);
            userInfoDrugCompanyDo.setDrugCompanyId(companyId);
            drugCompanyMapper.updateByPrimaryKeySelective(userInfoDrugCompanyDo);
            //如果更改了公司名字，同步更新user表
            if( userInfoDrugCompanyDo.getCompany() != null ){
                userMapper.updateUsername(userInfoDrugCompany.getUserId(),userInfoDrugCompany.getCompany());
            }
        }

        return new MyBeanUtils<UserInfoDrugCompanyDo,UserInfoDrugCompany>().copyBean(userInfoDrugCompanyDo,UserInfoDrugCompany.class);
    }

    /**
     * 获取科室信息
     * @param userId
     * @return
     */
    @Override
    public UserInfoDepartment findDepartmentInfo(Integer userId) {
        UserInfoDepartmentDo userInfoDepartmentDo = userInfoDepartmentMapper.selectByUserId(userId);
        return new MyBeanUtils<UserInfoDepartmentDo,UserInfoDepartment>().copyBean(userInfoDepartmentDo,UserInfoDepartment.class);
    }

    /**
     * 获取药企信息
     * @param userId
     * @return
     */
    @Override
    public UserInfoDrugCompany findDrugCompanyInfo(Integer userId) {
        UserInfoDrugCompanyDo userInfoDrugCompanyDo = drugCompanyMapper.selectByUserId(userId);
        return new MyBeanUtils<UserInfoDrugCompanyDo,UserInfoDrugCompany>().copyBean(userInfoDrugCompanyDo,UserInfoDrugCompany.class);
    }

    @Override
    public List<DoctorOutpatientTime> findDoctorOutpatientTime(Integer userId) {
        List<DoctorOutpatientTimeDo> dotDos = doctorOutpatientTimeMapper.getByUserId(userId);
        return new MyBeanUtils<DoctorOutpatientTimeDo,DoctorOutpatientTime>().copyList(dotDos,DoctorOutpatientTime.class);
    }

    @Override
    public List<User> searchUserList(String searchKey) {
        List<UserDo> userDo = userMapper.searchAllUserList(searchKey);
        return new MyBeanUtils<UserDo,User>().copyList(userDo,User.class);
    }

}
