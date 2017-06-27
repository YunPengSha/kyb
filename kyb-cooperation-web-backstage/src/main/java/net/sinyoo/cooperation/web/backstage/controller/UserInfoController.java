package net.sinyoo.cooperation.web.backstage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import net.sinyoo.cooperation.comm.model.*;
import net.sinyoo.cooperation.comm.model.query.AllUserInfo;
import net.sinyoo.cooperation.comm.model.query.UserDoctorQuery;
import net.sinyoo.cooperation.comm.service.UserService;
import net.sinyoo.cooperation.core.base.ResponseErrorCode;
import net.sinyoo.cooperation.core.emnu.UserType;
import net.sinyoo.cooperation.core.exception.ApiException;
import net.sinyoo.cooperation.core.exception.ServiceException;
import net.sinyoo.cooperation.core.util.MyBeanUtils;
import net.sinyoo.cooperation.core.util.StringUtils;
import net.sinyoo.cooperation.core.util.UploadUtils;
import net.sinyoo.cooperation.web.backstage.request.DepartmentInfoAddRequest;
import net.sinyoo.cooperation.web.backstage.request.DrugCompanyAddRequest;
import net.sinyoo.cooperation.web.backstage.request.DoctorAddRequest;
import net.sinyoo.cooperation.web.backstage.request.ListRequest;
import net.sinyoo.cooperation.web.backstage.response.NullDataResponse;
import net.sinyoo.cooperation.web.backstage.response.UserInfoGetResponse;
import net.sinyoo.cooperation.web.backstage.response.UserListResponse;
import net.sinyoo.cooperation.web.backstage.to.AllUserInfoTo;
import net.sinyoo.cooperation.web.backstage.utils.AccessTokenUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yunpengsha on 2017/4/19.
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Value("${upload.filepath}")
    public String uploadFilePath;

    @Reference
    private UserService userService;

    /**
     * \    http://localhost:30002/userInfo/getUserDetailInfo/1
     * 获取用户信息  医生、药企、科室
     *
     * @param userId
     * @param response {"data":{"userId":1,"userName":"123123null","userType":"DOCTOR","userStatus":0,"title":"123213","hospital":"123123","doctorOutpatientTimes":[],"doctorDetails":"********"},"status":0}
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUserDetailInfo/{userId}", method = RequestMethod.GET)
    public String getUserInfo(@PathVariable("userId") Integer userId, UserInfoGetResponse response) {

        if (userId == null) {
            return response.getErrorResponse(new ApiException(501, "用户id不能为空"));
        }

        User user = userService.getById(userId);
        if (user == null) {
            return response.getErrorResponse(new ServiceException(601, "用户不存在"));
        }

        AllUserInfoTo allUserInfoTo;

        if (user.getUserType() == UserType.DOCTOR) {
            UserDoctorQuery doctorInfo = userService.findDoctorInfo(userId);
            allUserInfoTo = new MyBeanUtils<UserDoctorQuery, AllUserInfoTo>().copyBean(doctorInfo, AllUserInfoTo.class);
            //医生需要额外再查询门诊时间
            List<DoctorOutpatientTime> dotDos = userService.findDoctorOutpatientTime(allUserInfoTo.getUserId());
            allUserInfoTo.setDoctorOutpatientTimes(dotDos);
            addBaseUserInfo(allUserInfoTo, user);
        } else if (user.getUserType() == UserType.DEPARTMENT) {
            UserInfoDepartment userInfoDepartment = userService.findDepartmentInfo(userId);
            allUserInfoTo = new MyBeanUtils<UserInfoDepartment, AllUserInfoTo>().copyBean(userInfoDepartment, AllUserInfoTo.class);
            addBaseUserInfo(allUserInfoTo, user);
        } else if (user.getUserType() == UserType.DRUG_COMPANY) {
            UserInfoDrugCompany userInfoDrugCompany = userService.findDrugCompanyInfo(userId);
            allUserInfoTo = new MyBeanUtils<UserInfoDrugCompany, AllUserInfoTo>().copyBean(userInfoDrugCompany, AllUserInfoTo.class);
            addBaseUserInfo(allUserInfoTo, user);
        } else {
            return response.getErrorResponse(new ServiceException(602, "未知错误"));
        }

        response.setAllUserInfoTo(allUserInfoTo);

        return response.getSuccessResponse();
    }

    /**
     * 更新药企信息       http://localhost:30002/userInfo/updateDrugCompanyInfo
     *
     * @param drugCompanyAddRequest {"company":"新药企","userId":"1","companyUrl":"http://baidu.com","drugCompanyId":"1"}
     * @param response              {"status":0}
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateDrugCompanyInfo", method = RequestMethod.POST, consumes = "application/json")
    public String changeDrugCompanyInfo(HttpServletRequest httpServletRequest, @RequestBody DrugCompanyAddRequest drugCompanyAddRequest, NullDataResponse response) {

        try {
            drugCompanyAddRequest.checkParams();
        } catch (ApiException e) {
            response.getErrorResponse(e);
        }

        //获取token
        String accessToken = AccessTokenUtil.getAccessToken(httpServletRequest);
        if (StringUtils.isEmpty(accessToken)) {
            return response.getErrorResponse(ResponseErrorCode.LOGIN, ResponseErrorCode.LOGIN_MESSAGE, "601", "请重新登录");
        }

        if (drugCompanyAddRequest.getUserId() == null) {
            return response.getErrorResponse(new ApiException(501, "缺少userId"));
        }

        drugCompanyAddRequest.setUserId(drugCompanyAddRequest.getUserId());

        try {
            userService.addDrugCompanyDetailInfo(new MyBeanUtils<DrugCompanyAddRequest, UserInfoDrugCompany>().copyBean(drugCompanyAddRequest, UserInfoDrugCompany.class));
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }

        return response.getSuccessResponse();
    }


    /**
     * 修改科室信息        http://localhost:30002/userInfo/updateDepartmentInfo
     *
     * @param departmentInfoAddRequest {"department":"1号科室","hospital":"2医院","userId":"1","departmentId":"1"}
     * @param response                 {"status":0}
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateDepartmentInfo", method = RequestMethod.POST, consumes = "application/json")
    public String changeDepartmentInfo(HttpServletRequest httpServletRequest, @RequestBody DepartmentInfoAddRequest departmentInfoAddRequest, NullDataResponse response) {

        try {
            departmentInfoAddRequest.checkParams();
        } catch (ApiException e) {
            response.getErrorResponse(e);
        }

        //获取token
        String accessToken = AccessTokenUtil.getAccessToken(httpServletRequest);
        if (StringUtils.isEmpty(accessToken)) {
            return response.getErrorResponse(ResponseErrorCode.LOGIN, ResponseErrorCode.LOGIN_MESSAGE, "601", "请重新登录");
        }

        if (departmentInfoAddRequest.getUserId() == null) {
            return response.getErrorResponse(new ApiException(501, "缺少id值"));
        }

        departmentInfoAddRequest.setUserId(departmentInfoAddRequest.getUserId());

        try {
            userService.addDepartmentDetailInfo(new MyBeanUtils<DepartmentInfoAddRequest, UserInfoDepartment>().copyBean(departmentInfoAddRequest, UserInfoDepartment.class));
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }

        return response.getSuccessResponse();
    }

    /**
     * 修改医生信息
     * <p>
     * 请求地址:   http://localhost:30002/userDoctor/updateDoctorInfo
     * </p>
     * <p>
     * header accessToken:   623751D996C14763B8D6C9158442C07B
     * </p>
     *
     * @param request  <p>
     *                 {"userId":1,"userName":"wanghui","gender":1,"userStatus":0,"title":"主任医师","personalHonor":"个人荣誉","hospital":"上海六院","doctorOutpatientTimes":[{"outpatientTimeId":1,"userId":1,"weekName":"周二","startTime":"09:00","endTime":"00:00"}],"department":"手术外科","clinicalField":"骨科","numberOfBeds":30,"caseNumber":50,"caseDistribution":"10","geneDetectionRatio":"100"}
     *                 </p>
     * @param response {"status":0}
     * @return
     */

    @RequestMapping(value = "/updateDoctorInfo", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String changeDoctorInfo(@RequestBody DoctorAddRequest request, NullDataResponse response) {

        try {
            request.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }

        if (request.getUserId() == null) {
            return response.getErrorResponse(new ApiException(503, "userId不能为空"));
        }


        UserInfoDoctor userInfoDoctor = new MyBeanUtils<DoctorAddRequest, UserInfoDoctor>().copyBean(request, UserInfoDoctor.class);
        User user = new MyBeanUtils<DoctorAddRequest, User>().copyBean(request, User.class);
        userInfoDoctor.setDoctorOutpatientTimes(request.getDoctorOutpatientTimes());

        try {
            userService.modifyDoctorInfo(user, userInfoDoctor);
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
        return response.getSuccessResponse();
    }


    /**
     * 获取医生列表            http://localhost:30002/userInfo/getDoctorList/1/2
     *
     * @param page
     * @param pageSize
     * @param response {"data":[{"userId":1,"userName":"wanghui","imageUrl":"https://timgsa.baidu.com/timg?image\u0026quality\u003d80\u0026size\u003db9999_10000\u0026sec\u003d1491481612861\u0026di\u003df30e222ba73ae93e79dd991db2a1c3f3\u0026imgtype\u003d0\u0026src\u003dhttp%3A%2F%2Fimg.25pp.com%2Fuploadfile%2Fsoft%2Fimages%2F2012%2F0409%2F20120409030535743.jpg","gender":0,"userType":"DOCTOR","userStatus":0,"createTime":"2017-03-31 14:38:20","modifyTime":"2017-03-31 14:38:20","doctorId":1,"title":"主任医师","personalHonor":"个人荣誉","hospital":"上海六院","department":"手术外科","clinicalField":"骨科","numberOfBeds":32,"caseNumber":500,"caseDistribution":"100","geneDetectionRatio":"55"},{"userId":2,"userName":"zhangtao","imageUrl":"https://timgsa.baidu.com/timg?image\u0026quality\u003d80\u0026size\u003db9999_10000\u0026sec\u003d1491481612861\u0026di\u003df30e222ba73ae93e79dd991db2a1c3f3\u0026imgtype\u003d0\u0026src\u003dhttp%3A%2F%2Fimg.25pp.com%2Fuploadfile%2Fsoft%2Fimages%2F2012%2F0409%2F20120409030535743.jpg","gender":0,"userType":"DOCTOR","userStatus":0,"createTime":"2017-04-19 13:28:17","doctorId":2,"title":"主任医师"}],"totalSize":7,"status":0}
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getDoctorList/{page}/{pageSize}", method = RequestMethod.GET)
    public String getDoctorList(@PathVariable("page") Integer page, @PathVariable("pageSize") Integer pageSize, UserListResponse response) {
        List<AllUserInfo> list = userService.findUserList(page, pageSize, UserType.DOCTOR);
        response.setAllUserInfoTos(new MyBeanUtils<AllUserInfo, AllUserInfoTo>().copyList(list, AllUserInfoTo.class));
        response.setTotalSize(userService.findUserCountByType(UserType.DOCTOR));
        return response.getSuccessResponse();
    }


    /**
     * 获取科室列表  http://localhost:30002/userInfo/getDepartmentList/1/2
     *
     * @param page
     * @param pageSize
     * @param response {"data":[{"userId":5,"userName":"asd","imageUrl":"https://timgsa.baidu.com/timg?image\u0026quality\u003d80\u0026size\u003db9999_10000\u0026sec\u003d1491481612861\u0026di\u003df30e222ba73ae93e79dd991db2a1c3f3\u0026imgtype\u003d0\u0026src\u003dhttp%3A%2F%2Fimg.25pp.com%2Fuploadfile%2Fsoft%2Fimages%2F2012%2F0409%2F20120409030535743.jpg","gender":0,"userType":"DEPARTMENT","userStatus":0,"hospital":"asdasd","department":"asdasd","clinicalField":"1","departmentId":2,"chiefPhysicianNumber":1,"deputyChiefPhysicianNumber":1,"attendingDoctorNumber":1,"departmentDetails":"asdasdqwdasd"},{"userId":9,"userName":"hospitaldepartment","userType":"DEPARTMENT","userStatus":0,"hospital":"hospital","department":"department","clinicalField":"clinicalField","departmentId":3,"chiefPhysicianNumber":0,"deputyChiefPhysicianNumber":0,"attendingDoctorNumber":0}],"totalSize":2,"status":0}
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getDepartmentList/{page}/{pageSize}", method = RequestMethod.GET)
    public String getDepartmentList(@PathVariable("page") Integer page, @PathVariable("pageSize") Integer pageSize, UserListResponse response) {
        List<AllUserInfo> list = userService.findUserList(page, pageSize, UserType.DEPARTMENT);
        response.setAllUserInfoTos(new MyBeanUtils<AllUserInfo, AllUserInfoTo>().copyList(list, AllUserInfoTo.class));
        response.setTotalSize(userService.findUserCountByType(UserType.DEPARTMENT));
        return response.getSuccessResponse();
    }


    /**
     * 获取药企列表    http://localhost:30002/userInfo/getDrugCompanyList/1/2
     *
     * @param page
     * @param pageSize
     * @param response {"data":[{"userId":6,"userName":"Drug_company","imageUrl":"https://timgsa.baidu.com/timg?image\u0026quality\u003d80\u0026size\u003db9999_10000\u0026sec\u003d1491481612861\u0026di\u003df30e222ba73ae93e79dd991db2a1c3f3\u0026imgtype\u003d0\u0026src\u003dhttp%3A%2F%2Fimg.25pp.com%2Fuploadfile%2Fsoft%2Fimages%2F2012%2F0409%2F20120409030535743.jpg","gender":0,"userType":"DRUG_COMPANY","userStatus":0,"officeTelephone":"021 8694","drugCompanyId":6,"company":"哇哈哈","address":"上海","companyUrl":"http://asdasd","companyDetails":"asdasdasd"}],"totalSize":1,"status":0}
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getDrugCompanyList/{page}/{pageSize}", method = RequestMethod.GET)
    public String getDrugCompanyList(@PathVariable("page") Integer page, @PathVariable("pageSize") Integer pageSize, UserListResponse response) {
        List<AllUserInfo> list = userService.findUserList(page, pageSize, UserType.DRUG_COMPANY);
        response.setAllUserInfoTos(new MyBeanUtils<AllUserInfo, AllUserInfoTo>().copyList(list, AllUserInfoTo.class));
        response.setTotalSize(userService.findUserCountByType(UserType.DRUG_COMPANY));
        return response.getSuccessResponse();
    }


    /**
     * http://localhost:30002/userInfo/addDoctorUser
     * 添加医生用户  默认密码 123456
     *
     * @param doctorAddRequest {"userName":"111","title":"shanghai","hospital":"123123","phone":"333","gender":0,"clinicalField":"clinicalField","department":"beijing"}
     * @param response         {"status":0}
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addDoctorUser", method = RequestMethod.POST,consumes = "application/json")
    public String addDoctorUser(@RequestBody DoctorAddRequest doctorAddRequest, NullDataResponse response) {

        try {
            doctorAddRequest.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }

        if (doctorAddRequest.getPhone() == null) {
            return response.getErrorResponse(new ApiException(503, "手机号不能为空"));
        }

        User user = new MyBeanUtils<DoctorAddRequest, User>().copyBean(doctorAddRequest, User.class);
        user.setImageUrl(doctorAddRequest.getUserAvatar());
        UserInfoDoctor userInfoDoctor = new MyBeanUtils<DoctorAddRequest, UserInfoDoctor>().copyBean(doctorAddRequest, UserInfoDoctor.class);

        try {
            userService.addDoctor(user, userInfoDoctor);
        } catch (ServiceException e) {
            response.getErrorResponse(e);
        }

        return response.getSuccessResponse();
    }


    /**
     * 后台注册科室账号使用          http://localhost:30002/userInfo/addDepartmentUser
     *
     * @param departmentInfoAddRequest {"hospital":"111","department":"shanghai","departmentDetails":"123123","phone":"222"}
     * @param response                 {"status":0}
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addDepartmentUser", method = RequestMethod.POST, consumes = "application/json")
    public String addDepartmentUser(@RequestBody DepartmentInfoAddRequest departmentInfoAddRequest, NullDataResponse response) {

        try {
            departmentInfoAddRequest.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }

        if (departmentInfoAddRequest.getPhone() == null) {
            return response.getErrorResponse(new ApiException(501, "手机号不能为空"));
        }

        User user = new MyBeanUtils<DepartmentInfoAddRequest, User>().copyBean(departmentInfoAddRequest, User.class);
        UserInfoDepartment userInfoDepartment = new MyBeanUtils<DepartmentInfoAddRequest, UserInfoDepartment>().copyBean(departmentInfoAddRequest, UserInfoDepartment.class);

        try {
            userService.addDepartment(user, userInfoDepartment);
        } catch (ServiceException e) {
            response.getErrorResponse(e);
        }

        return response.getSuccessResponse();
    }


    /**
     * http://localhost:30002/userInfo/addDrugCompanyUser
     * 后台添加药企用户
     *
     * @param drugCompanyAddRequest {"company":"111","address":"shanghai","officeTelephone":"123123","companyUrl":"http://localhost","companyDetails":"hahah","phone":"111"}
     * @param response              {"status":0}
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addDrugCompanyUser", method = RequestMethod.POST, consumes = "application/json")
    public String addDrugCompanyUser(@RequestBody DrugCompanyAddRequest drugCompanyAddRequest, NullDataResponse response) {

        try {
            drugCompanyAddRequest.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }

        if (drugCompanyAddRequest.getPhone() == null) {
            return response.getErrorResponse(new ApiException(501, "手机号不能为空"));
        }

        User user = new MyBeanUtils<DrugCompanyAddRequest, User>().copyBean(drugCompanyAddRequest, User.class);
        UserInfoDrugCompany userInfoDrugCompany = new MyBeanUtils<DrugCompanyAddRequest, UserInfoDrugCompany>().copyBean(drugCompanyAddRequest, UserInfoDrugCompany.class);

        try {
            userService.addDrugCompany(user, userInfoDrugCompany);
        } catch (ServiceException e) {
            response.getErrorResponse(e);
        }

        return response.getSuccessResponse();
    }


    /**
     * 删除用户  使用户不能登录，需要重新注册，保留用户所有课题信息记录
     * http://localhost:30002/userInfo/deleteUser
     *
     * @param listRequest {"userIds":[12,13,14,15]}
     * @param response    {"status":0}
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST, consumes = "application/json")
    public String deleteUser(@RequestBody ListRequest listRequest, NullDataResponse response) {

        List<Integer> userIds = listRequest.getUserIds();

        if (userIds == null || userIds.size() <= 0) {
            return response.getErrorResponse(new ApiException(501, "请选择要删除的用户"));
        }

        userService.deleteUsers(userIds);

        return response.getSuccessResponse();

    }


    /**
     * 添加用户基本信息   以后可以重新写个反射 替换掉该方法
     *
     * @param allUserInfoTo
     * @param user
     */
    private void addBaseUserInfo(AllUserInfoTo allUserInfoTo, User user) {

        if (user.getUserType() != null) {
            allUserInfoTo.setUserType(user.getUserType());
        }

        if (user.getUserName() != null) {
            allUserInfoTo.setUserName(user.getUserName());
        }

        if (user.getImageUrl() != null) {
            allUserInfoTo.setImageUrl(user.getImageUrl());
        }

        if (user.getGender() != null) {
            allUserInfoTo.setGender(user.getGender());
        }

    }

}