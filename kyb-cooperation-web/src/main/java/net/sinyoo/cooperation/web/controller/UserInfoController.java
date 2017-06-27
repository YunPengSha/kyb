package net.sinyoo.cooperation.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import net.sinyoo.cooperation.comm.model.*;
import net.sinyoo.cooperation.comm.model.query.UserDoctorQuery;
import net.sinyoo.cooperation.comm.service.HomeLinkService;
import net.sinyoo.cooperation.comm.service.SubjectPartInService;
import net.sinyoo.cooperation.comm.service.UserService;
import net.sinyoo.cooperation.core.base.ResponseErrorCode;
import net.sinyoo.cooperation.core.emnu.HomeLinkType;
import net.sinyoo.cooperation.core.emnu.UserType;
import net.sinyoo.cooperation.core.exception.ApiException;
import net.sinyoo.cooperation.core.exception.ServiceException;
import net.sinyoo.cooperation.core.util.MyBeanUtils;
import net.sinyoo.cooperation.core.util.StringUtils;
import net.sinyoo.cooperation.web.cache.AccessTokenCache;
import net.sinyoo.cooperation.web.request.DepartmentInfoAddRequest;
import net.sinyoo.cooperation.web.request.DrugCompanyAddRequest;
import net.sinyoo.cooperation.web.request.UserInfoGetRequest;
import net.sinyoo.cooperation.web.response.DoctorListResponse;
import net.sinyoo.cooperation.web.response.NullDataResponse;
import net.sinyoo.cooperation.web.response.UserInfoGetResponse;
import net.sinyoo.cooperation.web.to.query.AllUserInfoTo;
import net.sinyoo.cooperation.web.utils.AccessTokenUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 更改，获取用户信息
 * Created by yunpengsha on 2017/4/15.
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Reference
    private UserService userService;

    @Reference
    private SubjectPartInService subjectPartInService;

    @Reference
    private HomeLinkService homeLinkService;

    /**
     * 更新药企信息
     * @param drugCompanyAddRequest
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateDrugCompanyInfo",method = RequestMethod.POST,consumes = "application/json")
    public String changeDrugCompanyInfo(HttpServletRequest httpServletRequest,@RequestBody DrugCompanyAddRequest drugCompanyAddRequest, NullDataResponse response){

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
        //获取userId
        Integer userId = AccessTokenCache.getUserId(accessToken);

        drugCompanyAddRequest.setUserId(userId);

        try {
            userService.addDrugCompanyDetailInfo(new MyBeanUtils<DrugCompanyAddRequest,UserInfoDrugCompany>().copyBean(drugCompanyAddRequest,UserInfoDrugCompany.class));
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }

        return response.getSuccessResponse();
    }


    /**
     * 修改科室信息       http://localhost:30001/userInfo/updateDepartmentInfo
     * @param departmentInfoAddRequest  {"userId":"1","hospital":"2hao","department":"3hao","departmentDetails":"details"}
     * @param response  {"status":0}
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateDepartmentInfo" ,method = RequestMethod.POST,consumes = "application/json")
    public String changeDepartmentInfo(HttpServletRequest httpServletRequest,@RequestBody DepartmentInfoAddRequest departmentInfoAddRequest,NullDataResponse response){

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
        //获取userId
        Integer userId = AccessTokenCache.getUserId(accessToken);

        departmentInfoAddRequest.setUserId(userId);

        try {
            userService.addDepartmentDetailInfo(new MyBeanUtils<DepartmentInfoAddRequest,UserInfoDepartment>().copyBean(departmentInfoAddRequest,UserInfoDepartment.class));
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }

        return response.getSuccessResponse();
    }

    /**\    http://localhost:30001/userInfo/getUserDetailInfo
     * 获取用户信息  医生、药企、科室
     *
     * @param response    {"data":{"userId":1,"userName":"123123null","userType":"DOCTOR","userStatus":0,"title":"123213","hospital":"123123","doctorOutpatientTimes":[],"doctorDetails":"********"},"status":0}
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUserDetailInfo",method = RequestMethod.POST,consumes = "application/json")
    public String getUserInfo(@RequestBody UserInfoGetRequest userInfoGetRequest, UserInfoGetResponse response){

        Integer userId = userInfoGetRequest.getUserId();

        if( userId == null ){
            return response.getErrorResponse(new ApiException(501,"用户id不能为空"));
        }

        User user = userService.getById(userId);
        if( user == null ){
            return response.getErrorResponse(new ServiceException(601,"用户不存在"));
        }

        AllUserInfoTo allUserInfoTo ;

        if( user.getUserType() == UserType.DOCTOR ){
            UserDoctorQuery doctorInfo = userService.findDoctorInfo(userId);
            allUserInfoTo = new MyBeanUtils<UserDoctorQuery,AllUserInfoTo>().copyBean(doctorInfo,AllUserInfoTo.class);
            //医生需要额外再查询门诊时间
            List<DoctorOutpatientTime> dotDos = userService.findDoctorOutpatientTime(allUserInfoTo.getUserId());
            allUserInfoTo.setDoctorOutpatientTimes(dotDos);
            addBaseUserInfo(allUserInfoTo,user);
            allUserInfoTo.setApplyCount( subjectPartInService.findApplyCount(userId) );
            allUserInfoTo.setInvocationCount(subjectPartInService.findInvocationCount(userId));
        }
        else if( user.getUserType() == UserType.DEPARTMENT ){
            UserInfoDepartment userInfoDepartment = userService.findDepartmentInfo(userId);
            allUserInfoTo = new MyBeanUtils<UserInfoDepartment,AllUserInfoTo>().copyBean(userInfoDepartment,AllUserInfoTo.class);
            addBaseUserInfo(allUserInfoTo,user);
            allUserInfoTo.setApplyCount( subjectPartInService.findApplyCount(userId) );
            allUserInfoTo.setInvocationCount(subjectPartInService.findInvocationCount(userId));
        }
        else if( user.getUserType() == UserType.DRUG_COMPANY ){
            UserInfoDrugCompany userInfoDrugCompany = userService.findDrugCompanyInfo(userId);
            allUserInfoTo = new MyBeanUtils<UserInfoDrugCompany,AllUserInfoTo>().copyBean(userInfoDrugCompany,AllUserInfoTo.class);
            addBaseUserInfo(allUserInfoTo,user);
            allUserInfoTo.setApplyCount( subjectPartInService.findApplyCount(userId) );
        }
        else {
            return response.getErrorResponse(new ServiceException(602,"未知错误"));
        }

        response.setAllUserInfoTo(allUserInfoTo);

        return response.getSuccessResponse();
    }


    /**
     * http://localhost:30001/userInfo/getDoctors/0/20/null
     * 首页获取搜索医生列表
     * @param page
     * @param pageSize
     * @param searchKey
     * @param response   {"data":[{"userId":1,"userName":"老二","imageUrl":"https://timgsa.baidu.com/timg?image\u0026quality\u003d80\u0026size\u003db9999_10000\u0026sec\u003d1491481612861\u0026di\u003df30e222ba73ae93e79dd991db2a1c3f3\u0026imgtype\u003d0\u0026src\u003dhttp%3A%2F%2Fimg.25pp.com%2Fuploadfile%2Fsoft%2Fimages%2F2012%2F0409%2F20120409030535743.jpg","gender":0,"userType":"DEPARTMENT","userStatus":0,"createTime":"2017-03-31 14:38:20","modifyTime":"2017-03-31 14:38:20","title":"2","personalHonor":"个人荣誉","hospital":"12","department":"qwe","clinicalField":"123","numberOfBeds":32,"caseNumber":500,"caseDistribution":"100","geneDetectionRatio":"55"},{"userId":2,"userName":"zhangtao","imageUrl":"https://timgsa.baidu.com/timg?image\u0026quality\u003d80\u0026size\u003db9999_10000\u0026sec\u003d1491481612861\u0026di\u003df30e222ba73ae93e79dd991db2a1c3f3\u0026imgtype\u003d0\u0026src\u003dhttp%3A%2F%2Fimg.25pp.com%2Fuploadfile%2Fsoft%2Fimages%2F2012%2F0409%2F20120409030535743.jpg","gender":0,"userType":"DOCTOR","userStatus":0,"createTime":"2017-03-31 14:38:20","title":"主任医师"},{"userId":3,"userName":"wuqixiang","imageUrl":"https://timgsa.baidu.com/timg?image\u0026quality\u003d80\u0026size\u003db9999_10000\u0026sec\u003d1491481612861\u0026di\u003df30e222ba73ae93e79dd991db2a1c3f3\u0026imgtype\u003d0\u0026src\u003dhttp%3A%2F%2Fimg.25pp.com%2Fuploadfile%2Fsoft%2Fimages%2F2012%2F0409%2F20120409030535743.jpg","gender":0,"userType":"DOCTOR","userStatus":0,"createTime":"2017-03-31 14:38:20","modifyTime":"2017-03-31 14:38:20","title":"医师","personalHonor":"中国最牛逼协会会长","hospital":"2333医院","department":"6666科室","clinicalField":"exome","numberOfBeds":10086,"caseNumber":10010,"caseDistribution":"111","geneDetectionRatio":"23"},{"userId":19,"userName":"111","imageUrl":"http://sharing-test.oss-cn-hangzhou.aliyuncs.com/%E9%BB%98%E8%AE%A4%E5%8C%BB%E7%94%9F%E5%A4%B4%E5%83%8F.png?Expires\u003d1492988683\u0026OSSAccessKeyId\u003dqelkKL9GgZaH0Ea9\u0026Signature\u003dMGd8TTkPNRhFpbBkWaijTJCF3zU%3D","gender":0,"userType":"DOCTOR","userStatus":0,"createTime":"2017-04-21 10:52:35","title":"shanghai","hospital":"123123","department":"beijing","clinicalField":"clinicalField"},{"userId":28,"userName":"123","imageUrl":"./dependencies/image/defaultAvatar.png","gender":0,"userType":"DOCTOR","userStatus":0,"title":"医师","personalHonor":"123","hospital":"123","department":"123","clinicalField":"123","caseDistribution":"","geneDetectionRatio":""},{"userId":3,"userName":"wuqixiang","imageUrl":"https://timgsa.baidu.com/timg?image\u0026quality\u003d80\u0026size\u003db9999_10000\u0026sec\u003d1491481612861\u0026di\u003df30e222ba73ae93e79dd991db2a1c3f3\u0026imgtype\u003d0\u0026src\u003dhttp%3A%2F%2Fimg.25pp.com%2Fuploadfile%2Fsoft%2Fimages%2F2012%2F0409%2F20120409030535743.jpg","gender":0,"userType":"DOCTOR","userStatus":0,"createTime":"2017-03-31 14:38:20","modifyTime":"2017-03-31 14:38:20","title":"主任医师","personalHonor":"哈哈","hospital":"上海医院","department":"肿瘤科","clinicalField":"123","numberOfBeds":12,"caseNumber":1,"caseDistribution":"1","geneDetectionRatio":"1","officeTelephone":"1"}],"totalSize":9,"status":0}
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getDoctors/{page}/{pageSize}/{searchkey}",method = RequestMethod.GET)
    public String getDoctors(@PathVariable("page")int page,@PathVariable("pageSize")int pageSize,@PathVariable("searchkey")String searchKey,DoctorListResponse response){

        if( page < 0 ){
            page = 0;
        }

        // 目前获取所有用户信息
        if( pageSize <= 0 ){
            pageSize = 20;
        }

        if( StringUtils.isEmpty(searchKey) || "null".equals(searchKey) ){
            searchKey = null;
        }

        List<HomeLink> homeLinks = homeLinkService.findHomeLinks(HomeLinkType.DOCTOR, page, pageSize, searchKey);
        List<AllUserInfoTo> list = new ArrayList<>();

        for( HomeLink homeLink:homeLinks ){
            UserDoctorQuery userDoctorQuery = userService.findDoctorInfo(homeLink.getRefId());
            AllUserInfoTo allUserInfoTo = new MyBeanUtils<UserDoctorQuery, AllUserInfoTo>().copyBean(userDoctorQuery, AllUserInfoTo.class);
            allUserInfoTo.setLinkId( homeLink.getLinkId() );
            list.add(allUserInfoTo);
        }

        response.setAllUserInfoTo(list);
        response.setPage(page);
        if( searchKey == null ){
            response.setTotalSize( homeLinkService.findHomeLinkCount( HomeLinkType.DOCTOR ) );
        }
        else {
            response.setTotalSize(homeLinkService.findHomeLinkCountBySearchKey(HomeLinkType.DOCTOR, searchKey));
        }
        return response.getSuccessResponse();


/*
        获取user表所有医生信息
        if( page <= 0 ){
           page = 1;
        }

        if( pageSize <= 0 ){
            pageSize = 20;
        }

        if( StringUtils.isEmpty(searchKey) || "null".equals(searchKey) ){
            searchKey = null;
        }

        List<UserDoctorQuery> list = userService.searchDoctorList(page,pageSize,searchKey);

        response.setAllUserInfoTo(new MyBeanUtils<UserDoctorQuery,AllUserInfoTo>().copyList(list,AllUserInfoTo.class));
        response.setTotalSize(userService.findSearchDoctorListCount(searchKey));

        return response.getSuccessResponse();*/
    }

    /**
     * 添加用户基本信息   可以用BeanUtils.copyProperties();代替
     * @param allUserInfoTo
     * @param user
     */
    private void addBaseUserInfo(AllUserInfoTo allUserInfoTo, User user) {

        if( user.getUserType() != null ){
            allUserInfoTo.setUserType(user.getUserType());
        }

        if( user.getUserName() != null ){
            allUserInfoTo.setUserName(user.getUserName());
        }

        if( user.getImageUrl() != null ){
            allUserInfoTo.setImageUrl(user.getImageUrl());
        }

        if( user.getGender() != null ){
            allUserInfoTo.setGender(user.getGender());
        }

    }


}











