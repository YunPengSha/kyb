package net.sinyoo.cooperation.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import net.sinyoo.cooperation.comm.model.User;
import net.sinyoo.cooperation.comm.model.UserInfoDepartment;
import net.sinyoo.cooperation.comm.model.UserInfoDoctor;
import net.sinyoo.cooperation.comm.model.UserInfoDrugCompany;
import net.sinyoo.cooperation.comm.service.UserRegisterService;
import net.sinyoo.cooperation.comm.service.UserService;
import net.sinyoo.cooperation.core.exception.ApiException;
import net.sinyoo.cooperation.core.exception.ServiceException;
import net.sinyoo.cooperation.core.util.MD5Util;
import net.sinyoo.cooperation.core.util.MyBeanUtils;
import net.sinyoo.cooperation.web.cache.UserRegisterCache;
import net.sinyoo.cooperation.web.request.*;
import net.sinyoo.cooperation.web.response.*;
import net.sinyoo.cooperation.web.to.DepartmentTo;
import net.sinyoo.cooperation.web.to.DrugCompanyTo;
import net.sinyoo.cooperation.web.to.UserInfoDoctorTo;
import net.sinyoo.cooperation.web.to.query.AllUserInfoTo;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yunpengsha on 2017/3/16.
 */
@RestController
@RequestMapping("/userRegister")
public class UserRegisterController {

    @Reference
    private UserRegisterService userRegisterService;

    @Reference
    private UserService userService;


    /**
     * 用户注册，手机号，密码
     *
     * @param userBaseInfoRequest {"phone":"18848971713","passwordOne":"123","passwordTwo":"123","userType":"DEPARTMENT","smsCode":"123"}
     * @param response            {"data":{"userId":2},"status":0}
     * @return
     */
    @RequestMapping(value = "/tempRegister", method = RequestMethod.POST)
    @ResponseBody
    public String registerUserBaseInfo(@RequestBody UserBaseInfoRequest userBaseInfoRequest, UserInfoGetResponse response) {
        try {
            userBaseInfoRequest.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }
        //检测密码是否相同
        if (!userBaseInfoRequest.getPasswordOne().equals(userBaseInfoRequest.getPasswordTwo())) {
            return response.getErrorResponse(new ServiceException(600, "密码不同"));
        }
        //设置密码属性
        userBaseInfoRequest.setPassword(MD5Util.MD5(userBaseInfoRequest.getPasswordOne()));

        User user = new MyBeanUtils<UserBaseInfoRequest, User>().copyBean(userBaseInfoRequest, User.class);

        //验证短信验证码
        try {

            //olduser  包含手机号和userId
            User oldUser = userService.checkSmsCode(user, userBaseInfoRequest.getSmsCode());
            Integer token = oldUser.getUserId();
            user.setUserId(oldUser.getUserId());
            UserRegisterCache.addCache(token, user);
            AllUserInfoTo allUserInfoTo = new AllUserInfoTo();
            allUserInfoTo.setUserId(oldUser.getUserId());
            response.setAllUserInfoTo(allUserInfoTo);
            return response.getSuccessResponse();
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }

//            int id = userService.addPhone(user.getPhone(), user.getUserType());
//            Integer token = id;
//            user.setUserId(id);
//            UserRegisterCache.addCache(token, user);
//            AllUserInfoTo allUserInfoTo = new AllUserInfoTo();
//            allUserInfoTo.setUserId(id);
//            response.setAllUserInfoTo(allUserInfoTo);
//            return response.getSuccessResponse();
    }

    /**
     * 添加医生用户信息
     * <p>
     * <p>
     * http://api.kyb.me:30001/userRegister/addDoctorDetailInfo
     * </p>
     * <p>
     * header accessToken   623751D996C14763B8D6C9158442C07B
     * <p>
     * new
     * {"userId":"1","userName":"123","title":"123213","hospital":"123123","doctorDetails":"************************************************************"}
     * {"data":{"userId":1,"title":"123213","hospital":"123123","doctorDetails":"************************************************************"},"status":0}
     * <p>
     * old
     *
     * @param request  {"userId":1,"userName":"wanghui","gender":1,"userStatus":0,"title":"主任医师","personalHonor":"个人荣誉","hospital":"上海六院","doctorOutpatientTimes":[{"outpatientTimeId":1,"userId":1,"weekName":"周二","startTime":"09:00","endTime":"00:00"}],"department":"手术外科","clinicalField":"骨科","numberOfBeds":30,"caseNumber":50,"caseDistribution":"10","geneDetectionRatio":"100"}
     * @param response {"data":{"userId":1,"title":"主任医师","personalHonor":"个人荣誉","hospital":"上海六院","department":"手术外科","clinicalField":"骨科","numberOfBeds":30,"caseNumber":50,"caseDistribution":"10","geneDetectionRatio":"100"},"status":0}
     * @return
     */
    @RequestMapping(value = "/addDoctorDetailInfo", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String registerDoctorDetailInfo(@RequestBody DoctorInfoAddRequest request, UserInfoAddResponse response) {
        try {
            request.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }

        if (!UserRegisterCache.hasAccessToken(request.getUserId())) {
            return response.getErrorResponse(new ServiceException(601, "token过期，重新注册"));
        }

        //里面含有手机号 密码 用户类型
        User baseUser = UserRegisterCache.getUserId(request.getUserId());

        UserInfoDoctor userInfoDoctor = new MyBeanUtils<DoctorInfoAddRequest, UserInfoDoctor>().copyBean(request, UserInfoDoctor.class);
        User user = new MyBeanUtils<DoctorInfoAddRequest, User>().copyBean(request, User.class);
        user.setPhone(baseUser.getPhone());
        user.setPassword(baseUser.getPassword());
        user.setUserType(baseUser.getUserType());
        user.setUserStatus(0);
        user.setCreateTime(new Date());
        user.setModifyTime(new Date());
        // 保存图片url或默认图片路径    userAvatar不可能为null
        if (request.getUserAvatar() != null) {
            user.setImageUrl(request.getUserAvatar());
        } else {
            user.setImageUrl("http://sharing-test.oss-cn-hangzhou.aliyuncs.com/%E9%BB%98%E8%AE%A4%E5%8C%BB%E7%94%9F%E5%A4%B4%E5%83%8F.png?Expires=1492988683&OSSAccessKeyId=qelkKL9GgZaH0Ea9&Signature=MGd8TTkPNRhFpbBkWaijTJCF3zU%3D");
        }

//        userInfoDoctor.setDoctorOutpatientTimes(request.getDoctorOutpatientTimes());
        try {
            userInfoDoctor = userService.addDoctorInfo(userInfoDoctor, user);
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
        response.setUserInfoTo(new MyBeanUtils<UserInfoDoctor, UserInfoDoctorTo>().copyBean(userInfoDoctor, UserInfoDoctorTo.class));
        return response.getSuccessResponse();
    }

    /**
     * http://localhost:30001/userRegister/addDepartmentDetailInfo
     * 新增科室信息
     *
     * @param request  {"userId":"9","hospital":"hospital","department":"department","chiefPhysicianNumber":"0","deputyChiefPhysicianNumber":"0","attendingDoctorNumber":0,"clinicalField":"clinicalField"}
     * @param response
     * @return
     */
    @RequestMapping(value = "/addDepartmentDetailInfo", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String registerDepartmentDetailInfo(@RequestBody DepartmentInfoAddRequest request, DepartmentInfoResponse response) {

        try {
            request.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }

        if (!UserRegisterCache.hasAccessToken(request.getUserId())) {
            return response.getErrorResponse(new ServiceException(601, "token过期，重新注册"));
        }

        User user = UserRegisterCache.getUserId(request.getUserId());
        user.setUserStatus(0);

        UserInfoDepartment userInfoDepartment = new MyBeanUtils<DepartmentInfoAddRequest,UserInfoDepartment>().copyBean(request,UserInfoDepartment.class);
        try {
            userService.modifyUserInfo(user);
            userInfoDepartment = userService.addDepartmentDetailInfo(userInfoDepartment);
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
        DepartmentTo departmentTo = new MyBeanUtils<UserInfoDepartment, DepartmentTo>().copyBean(userInfoDepartment, DepartmentTo.class);
        response.setDepartmentTo(departmentTo);
        return response.getSuccessResponse();
    }


    /**
     * 添加药企信息
     * http://localhost:30001/userRegister/addDrugCompanyDetailInfo
     *
     * @param request  {"userId":"2","userName":"123","company":"1263213","hospital":"12312asd3","drugCompanyId":"1"}
     * @param response {"data":{"drugCompanyId":1,"userId":2,"company":"1263213"},"status":0}
     * @return
     */
    @RequestMapping(value = "/addDrugCompanyDetailInfo", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String registerDrugCompanyDetailInfo(@RequestBody DrugCompanyAddRequest request, DrugCompanyResponse response) {

        try {
            request.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }

        if (!UserRegisterCache.hasAccessToken(request.getUserId())) {
            return response.getErrorResponse(new ServiceException(601, "token过期，重新注册"));
        }
        User user = UserRegisterCache.getUserId(request.getUserId());
        user.setUserStatus(0);
        // 如果为空则设置默认医生照片
        if (request.getUserAvatar() != null) {
            //如果是http url 则忽视，已经往数据库中保存过
//            if( !request.getUserAvatar().contains("http") ){
            user.setImageUrl(request.getUserAvatar());
//            }
        } else {
            user.setImageUrl("http://sharing-test.oss-cn-hangzhou.aliyuncs.com/%E9%BB%98%E8%AE%A4%E5%8C%BB%E7%94%9F%E5%A4%B4%E5%83%8F.png?Expires=1492988683&OSSAccessKeyId=qelkKL9GgZaH0Ea9&Signature=MGd8TTkPNRhFpbBkWaijTJCF3zU%3D");
        }
        UserInfoDrugCompany userInfoDrugCompany = new MyBeanUtils<DrugCompanyAddRequest, UserInfoDrugCompany>().copyBean(request, UserInfoDrugCompany.class);

        try {
            userService.modifyUserInfo(user);
            userInfoDrugCompany = userService.addDrugCompanyDetailInfo(userInfoDrugCompany);
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }

        DrugCompanyTo drugCompanyTo = new MyBeanUtils<UserInfoDrugCompany, DrugCompanyTo>().copyBean(userInfoDrugCompany, DrugCompanyTo.class);
        response.setDrugCompanyTo(drugCompanyTo);
        return response.getSuccessResponse();
    }


    /**
     * 修改用户密码
     *
     * @param updatePasswordRequest {"userId":1,"oldPassword":"123123","newPasswordOne":"000","newPasswordTwo":"000"}
     * @param response              {"status":0}
     * @return
     */

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String changePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest, NullDataResponse response) {
        try {
            updatePasswordRequest.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }

        //检测密码是否相同
        if (!updatePasswordRequest.getNewPasswordOne().equals(updatePasswordRequest.getNewPasswordTwo())) {
            return response.getErrorResponse(new ServiceException(600, "密码不同"));
        }

        User user = new User(updatePasswordRequest.getUserId(), updatePasswordRequest.getNewPasswordOne());
        userService.modifyUserPassword(user);
        return response.getSuccessResponse();
    }

    /**
     * 重置密码      http://localhost:30001/userRegister/resetPassword
     *
     * @param resetPasswordRequest {"phone":"18888888888","smsCode":"123456","newPasswordOne":"123","newPasswordTwo":"123","userType":"DOCTOR"}
     * @param response             {"status":0}
     * @return
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest, NullDataResponse response) {
        try {
            resetPasswordRequest.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }
        if (!resetPasswordRequest.getNewPasswordOne().equals(resetPasswordRequest.getNewPasswordTwo())) {
            return response.getErrorResponse(new ServiceException(601, "两次密码不对"));
        }

        User user = new User();
        user.setPassword(resetPasswordRequest.getNewPasswordOne());
        user.setPhone(resetPasswordRequest.getPhone());
        user.setUserType(resetPasswordRequest.getUserType());

        try {
            userService.modifyUserPassword(user, resetPasswordRequest.getSmsCode());
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }

        return response.getSuccessResponse();
    }
}
