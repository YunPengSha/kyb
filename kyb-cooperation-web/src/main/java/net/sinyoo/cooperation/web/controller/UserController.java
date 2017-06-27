package net.sinyoo.cooperation.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import net.sinyoo.cooperation.comm.model.*;
import net.sinyoo.cooperation.comm.model.query.UserDoctorQuery;
import net.sinyoo.cooperation.comm.service.HomeLinkService;
import net.sinyoo.cooperation.comm.service.UserService;
import net.sinyoo.cooperation.core.base.ResponseErrorCode;
import net.sinyoo.cooperation.core.emnu.HomeLinkType;
import net.sinyoo.cooperation.core.emnu.UserType;
import net.sinyoo.cooperation.core.exception.ApiException;
import net.sinyoo.cooperation.core.exception.ServiceException;
import net.sinyoo.cooperation.core.util.MyBeanUtils;
import net.sinyoo.cooperation.core.util.StringUtils;
import net.sinyoo.cooperation.web.cache.AccessTokenCache;
import net.sinyoo.cooperation.web.cache.PictureTokenCache;
import net.sinyoo.cooperation.web.cache.UserDoctorCache;
import net.sinyoo.cooperation.web.request.HomeLinkUserInfoRequest;
import net.sinyoo.cooperation.web.request.UserInfoGetRequest;
import net.sinyoo.cooperation.web.request.UserLoginRequest;
import net.sinyoo.cooperation.web.response.HomeLinkUserInfoResponse;
import net.sinyoo.cooperation.web.response.UserInfoGetResponse;
import net.sinyoo.cooperation.web.response.UserLoginResponse;
import net.sinyoo.cooperation.web.to.query.AllUserInfoTo;
import net.sinyoo.cooperation.web.utils.AccessTokenUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

/**
 * 用户控制器
 * 创建用户:     wangHui
 * 创建时间:     2017-03-21
 * Created by IntelliJ IDEA.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    @Reference
    private HomeLinkService homeLinkService;


    /**
     *
     * @param request  <p>{"userName":"13585955979","password":"123456","userType":"DOCTOR"}</p>
     * @param response <p>
     *                 error: {"status":1,"errorCode":"1004","errorMessage":"服务层抛错","subCode":"501","subMessage":"用户不存在"}
     *                 </p>
     *                 <p>
     *                 success: {"data":{"userId":1,"phone":"13585955979","userName":"wanghui","gender":1,"userType":"DOCTOR","userStatus":0,"title":"主任医师","personalHonor":"个人荣誉","hospital":"上海六院","doctorOutpatientTimes":[{"outpatientTimeId":2,"userId":1,"weekName":"周二","startTime":"09:00","endTime":"00:00"}],"department":"手术外科","clinicalField":"骨科","numberOfBeds":30,"caseNumber":500,"caseDistribution":"10","geneDetectionRatio":"100"},"status":0}
     *                 </p>
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String login(HttpServletResponse httpServletResponse, @RequestBody UserLoginRequest request, UserLoginResponse response, HttpSession httpSession) {

        try {
            request.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }

        //校验图片验证码
        if (!(StringUtils.isEmpty(request.getPictureToken()) && StringUtils.isEmpty(request.getPictureCode()))) {
            if (PictureTokenCache.hasPictureToken(request.getPictureToken())) {
                if (!request.getPictureCode().equalsIgnoreCase(PictureTokenCache.getPCode(request.getPictureToken()))) {
                    return response.getErrorResponse(new ServiceException(603, "验证码错误"));
                }
            } else {
                return response.getErrorResponse(new ServiceException(604, "验证码过时，重新输入"));
            }
        }


        User user;
            try {
                user = userService.login(request.getUserName(), request.getPassword(), request.getUserType());
            } catch (ServiceException e) {
                return response.getErrorResponse(e);
            }


/*        UserDoctorQuery userDoctorQuery = null;
        try {
            userDoctorQuery = userService.login(request.getUserName(), request.getPassword(),request.getUserType());
            if (userDoctorQuery != null){
                UserDoctorCache.addCache(userDoctorQuery.getUserId(),userDoctorQuery);
            }
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }*/
        response.setAllUserInfoTo(
                new MyBeanUtils<User, AllUserInfoTo>()
                        .copyBean(user, AllUserInfoTo.class)
        );
        String accessToken = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        response.setAccessToken(accessToken);
        AccessTokenUtil.setAccessToken(httpServletResponse, accessToken, user.getUserId());
        return response.getSuccessResponse();
    }

    /**
     * 获取用户信息  医生，药企和科室
     * <p>
     * 请求地址: http://api.kyb.me:30001/user/userInfo
     * </p>
     * <p>
     * header accessToken:   623751D996C14763B8D6C9158442C07B
     * </p>
     *
     * @param httpServletRequest
     * @param request            {}
     * @param response           <p>
     *                           {"data":{"userId":1,"phone":"13585955979","userName":"wanghui","gender":1,"userType":"DOCTOR","userStatus":0,"title":"主任医师","personalHonor":"个人荣誉","hospital":"上海六院","doctorOutpatientTimes":[{"outpatientTimeId":1,"userId":1,"weekName":"周一","startTime":"09:00","endTime":"00:00"}],"department":"手术外科","clinicalField":"骨科","numberOfBeds":30,"caseNumber":500,"caseDistribution":"10","geneDetectionRatio":"10"},"status":0}
     *                           </p>
     * @return
     */
    @RequestMapping(value = "/userInfo", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String userInfo(HttpServletRequest httpServletRequest, @RequestBody UserInfoGetRequest request, UserInfoGetResponse response) {
        //获取token
        String accessToken = AccessTokenUtil.getAccessToken(httpServletRequest);
        if (StringUtils.isEmpty(accessToken)) {
            return response.getErrorResponse(ResponseErrorCode.LOGIN, ResponseErrorCode.LOGIN_MESSAGE, "601", "请重新登录");
        }
        //获取userId
        Integer userId = AccessTokenCache.getUserId(accessToken);
        if (userId == null) {
            return response.getErrorResponse(ResponseErrorCode.LOGIN, ResponseErrorCode.LOGIN_MESSAGE, "601", "请重新登录");
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
        }
        else if( user.getUserType() == UserType.DEPARTMENT ){
            UserInfoDepartment userInfoDepartment = userService.findDepartmentInfo(userId);
            allUserInfoTo = new MyBeanUtils<UserInfoDepartment,AllUserInfoTo>().copyBean(userInfoDepartment,AllUserInfoTo.class);
            addBaseUserInfo(allUserInfoTo,user);
        }
        else if( user.getUserType() == UserType.DRUG_COMPANY ){
            UserInfoDrugCompany userInfoDrugCompany = userService.findDrugCompanyInfo(userId);
            allUserInfoTo = new MyBeanUtils<UserInfoDrugCompany,AllUserInfoTo>().copyBean(userInfoDrugCompany,AllUserInfoTo.class);
            addBaseUserInfo(allUserInfoTo,user);
        }
        else {
            return response.getErrorResponse(new ServiceException(602,"未知错误"));
        }

        response.setAllUserInfoTo(allUserInfoTo);

        return response.getSuccessResponse();
    }


    /**
     * 获取用户信息
     * <p>
     * 请求地址: http://api.kyb.me:30001/user/homeLinkDoctorInfo
     * </p>
     * <p>
     * header accessToken:   623751D996C14763B8D6C9158442C07B
     * </p>
     *
     * @param request            {"homeLinkId":1}
     * @param response           <p>
     *                           {"data":{"userId":1,"phone":"13585955979","userName":"wanghui","gender":1,"userType":"DOCTOR","userStatus":0,"title":"主任医师","personalHonor":"个人荣誉","hospital":"上海六院","doctorOutpatientTimes":[{"outpatientTimeId":1,"userId":1,"weekName":"周一","startTime":"09:00","endTime":"00:00"}],"department":"手术外科","clinicalField":"骨科","numberOfBeds":30,"caseNumber":500,"caseDistribution":"10","geneDetectionRatio":"10"},"status":0}
     *                           </p>
     * @return
     */
    @RequestMapping(value = "/homeLinkDoctorInfo", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String homeLinkDoctorInfo(@RequestBody HomeLinkUserInfoRequest request, HomeLinkUserInfoResponse response) {

        if( request.getHomeLinkId() == null ){
            return response.getErrorResponse(new ApiException( 501,"id值不能为空" ));
        }

        //获取userId
        HomeLink homeLink = homeLinkService.findByLinkId(request.getHomeLinkId());
        if (homeLink == null || homeLink.getLinkType() != HomeLinkType.DOCTOR){
            return response.getSuccessResponse();
        }
        Integer userId = homeLink.getRefId();

        //获取缓存数据
        UserDoctorQuery userDoctorQuery = UserDoctorCache.hasUser(userId);

        if (userDoctorQuery == null) {
            //获取用户信息
            userDoctorQuery = userService.findDoctorInfo(userId);
        }
        AllUserInfoTo allUserInfoTo = new MyBeanUtils<UserDoctorQuery, AllUserInfoTo>()
                .copyBean(userDoctorQuery, AllUserInfoTo.class);
        response.setAllUserInfoTo(allUserInfoTo);
        return response.getSuccessResponse();
    }


    /**
     *   首页获取药企详细信息
     *    http://localhost:30001/user/homeLinkDrugCompanyInfo
     * @param request   {"homeLinkId":8}
     * @param response   {"data":{"userId":8,"userName":"正大药企","imageUrl":"http://sharing-test.oss-cn-hangzhou.aliyuncs.com/%E9%BB%98%E8%AE%A4%E5%8C%BB%E7%94%9F%E5%A4%B4%E5%83%8F.png?Expires\u003d1492988683\u0026OSSAccessKeyId\u003dqelkKL9GgZaH0Ea9\u0026Signature\u003dMGd8TTkPNRhFpbBkWaijTJCF3zU%3D","userType":"DRUG_COMPANY","officeTelephone":"110-110","drugCompanyId":8,"company":"正大药企","address":"北京","companyUrl":"http://goudong.com","companyDetails":"to be no.1"},"status":0}
     * @return
     */
    @RequestMapping(value = "/homeLinkDrugCompanyInfo", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String homeLinkDrugCompanyInfo(@RequestBody HomeLinkUserInfoRequest request, HomeLinkUserInfoResponse response) {

        if( request.getHomeLinkId() == null ){
            return response.getErrorResponse(new ApiException( 501,"linkId不能为空" ));
        }

        //获取userId
        HomeLink homeLink = homeLinkService.findByLinkId(request.getHomeLinkId());
        if (homeLink == null || homeLink.getLinkType() != HomeLinkType.DRUG_COMPANY){
            return response.getSuccessResponse();
        }
        Integer userId = homeLink.getRefId();

        UserInfoDrugCompany companyInfo = userService.findDrugCompanyInfo(userId);
        User user = userService.getById(userId);

        AllUserInfoTo allUserInfoTo = new MyBeanUtils<UserInfoDrugCompany, AllUserInfoTo>()
                .copyBean(companyInfo, AllUserInfoTo.class);
        addBaseUserInfo(allUserInfoTo,user);
        response.setAllUserInfoTo(allUserInfoTo);
        return response.getSuccessResponse();
    }

    /**
     * 添加用户基本信息   以后可以重新写个反射 替换掉该方法
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
