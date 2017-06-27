package net.sinyoo.cooperation.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import net.sinyoo.cooperation.comm.model.User;
import net.sinyoo.cooperation.comm.model.UserInfoDepartment;
import net.sinyoo.cooperation.comm.model.UserInfoDoctor;
import net.sinyoo.cooperation.comm.model.query.UserDoctorQuery;
import net.sinyoo.cooperation.comm.service.UserService;
import net.sinyoo.cooperation.core.emnu.UserType;
import net.sinyoo.cooperation.core.exception.ApiException;
import net.sinyoo.cooperation.core.exception.ServiceException;
import net.sinyoo.cooperation.core.util.MyBeanUtils;
import net.sinyoo.cooperation.core.util.StringUtils;
import net.sinyoo.cooperation.web.cache.UserDoctorCache;
import net.sinyoo.cooperation.web.request.UserDoctorSearchRequest;
import net.sinyoo.cooperation.web.request.UserInfoUpdateRequest;
import net.sinyoo.cooperation.web.response.NullDataResponse;
import net.sinyoo.cooperation.web.response.UserDoctorInfoResponse;
import net.sinyoo.cooperation.web.response.UserDoctorSearchResponse;
import net.sinyoo.cooperation.web.to.query.AllUserInfoTo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 医生用户控制器
 * 创建用户:     wangHui
 * 创建时间:     2017-03-20
 * Created by IntelliJ IDEA.
 */
@RestController
@RequestMapping("/userDoctor")
public class UserDoctorController {

    @Reference
    private UserService userService;

    /**
     * 获取医生详细信息
     * <p>http://api.kyb.me:30001/userDoctor/info/1</p>
     * <p>header accessToken   623751D996C14763B8D6C9158442C07B</p>
     *
     * @param userId
     * @param response <p>
     *                 {"data":{"userId":1,"phone":"13585955979","userName":"wanghui","gender":1,"userType":"DOCTOR","userStatus":0,"title":"主任医师","personalHonor":"个人荣誉","hospital":"上海六院","doctorOutpatientTimes":[{"outpatientTimeId":2,"userId":1,"weekName":"周二","startTime":"09:00","endTime":"00:00"}],"department":"手术外科","clinicalField":"骨科","numberOfBeds":30,"caseNumber":500,"caseDistribution":"10","geneDetectionRatio":"100"},"status":0}
     *                 </p>
     * @return
     */
    @RequestMapping(value = "/info/{userId}", method = RequestMethod.GET)
    @ResponseBody
    @Deprecated
    public String getUserRegisterMessage(@PathVariable Integer userId, UserDoctorInfoResponse response) {
        //获取缓存数据
        UserDoctorQuery userDoctorQuery = UserDoctorCache.hasUser(userId);
        if (userDoctorQuery == null) {
            //获取用户信息
            userDoctorQuery = userService.findDoctorInfo(userId);
        }

        AllUserInfoTo allUserInfoTo = new MyBeanUtils<UserDoctorQuery, AllUserInfoTo>()
                .copyBean(userDoctorQuery, AllUserInfoTo.class);
        allUserInfoTo.setDoctorOutpatientTimes(userDoctorQuery.getDoctorOutpatientTimes());

        response.setAllUserInfoTo(allUserInfoTo);

        return response.getSuccessResponse();
    }

    /**
     * 创建课题邀请医生,查询医生列表信息  和科室列表
     * <p>
     * http://api.kyb.me:30001/userDoctor/search
     * </p>
     * <p>
     * header accessToken   623751D996C14763B8D6C9158442C07B
     * </p>
     *
     * @param userDoctorSearchRequest <p>
     *                                {"searchKey":"wanghui"}
     *                                </p>
     * @param response                <p>
     *                                {"data":[{"userId":1,"phone":"13585955979","userName":"wanghui","gender":1,"userType":"DOCTOR","userStatus":0,"title":"主任医师","personalHonor":"个人荣誉","hospital":"上海六院","outpatientTime":"周一至周五上午","department":"手术外科","clinicalField":"骨科","numberOfBeds":30,"caseNumber":500,"caseDistribution":"10","geneDetectionRatio":"10"}],"status":0}
     *                                </p>
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String search(@RequestBody UserDoctorSearchRequest userDoctorSearchRequest, UserDoctorSearchResponse response) {

        if(StringUtils.isEmpty(userDoctorSearchRequest.getSearchKey())){
            return response.getErrorResponse(new ApiException(501,"必须要有搜索条件"));
        }

        List<User> users = userService.searchUserList(userDoctorSearchRequest.getSearchKey());
        List<AllUserInfoTo> allUser = new ArrayList<>();
        AllUserInfoTo allUserInfoTo;

        for( User user:users ){
            if( user.getUserType() == UserType.DOCTOR ){
                UserDoctorQuery doctorInfo = userService.findDoctorInfo(user.getUserId());
                allUserInfoTo = new MyBeanUtils<UserDoctorQuery,AllUserInfoTo>().copyBean(doctorInfo,AllUserInfoTo.class);
                if( allUserInfoTo != null ){
                    addBaseUserInfo(allUserInfoTo,user);
                }
                allUser.add(allUserInfoTo);
            }
            else if( user.getUserType() == UserType.DEPARTMENT ){
                UserInfoDepartment userInfoDepartment = userService.findDepartmentInfo(user.getUserId());
                allUserInfoTo = new MyBeanUtils<UserInfoDepartment,AllUserInfoTo>().copyBean(userInfoDepartment,AllUserInfoTo.class);
                if( allUserInfoTo != null){
                    addBaseUserInfo(allUserInfoTo,user);
                }
                allUser.add(allUserInfoTo);
            }
        }

        response.setAllUserInfoTos(allUser);
        return response.getSuccessResponse();


/*        List<UserDoctorQuery> users = userService.searchUserDoctorList(userDoctorSearchRequest.getSearchKey());
        response.setAllUserInfoTos(
                new MyBeanUtils<UserDoctorQuery, AllUserInfoTo>()
                        .copyList(users, AllUserInfoTo.class)
        );
        return response.getSuccessResponse();*/
    }

    /**
     * 修改医生信息
     * <p>
     * 请求地址:   http://localhost:30001/userDoctor/updateDoctorInfo
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

    @RequestMapping(value = "/updateDoctorInfo", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseBody
    public String updateDoctorInfo(@RequestBody UserInfoUpdateRequest request, NullDataResponse response) {
        //@RequestBody UserRequest userRequest , @RequestBody UserInfoDoctorRequest userInfoDoctorRequest
//        UserRequest userRequest = new UserRequest();
//        UserInfoDoctorRequest userInfoDoctorRequest = new UserInfoDoctorRequest();
//        BeanUtils.copyProperties(userUpdateInfoRequest,userRequest);
//        BeanUtils.copyProperties(userUpdateInfoRequest,userInfoDoctorRequest);
//
//        User user = new User();
//        UserInfoDoctor userInfoDoctor = new UserInfoDoctor();
//
//        if( !userRequest.checkUpdateParams() ){
//            user = null;
//        }
//        else{
//            BeanUtils.copyProperties(userRequest, user);
//        }
//        if( !userInfoDoctorRequest.checkUpdateParams() ){
//            userInfoDoctor = null;
//        }
//        else{
//
//        }
        try {
            request.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }

        UserInfoDoctor userInfoDoctor = new MyBeanUtils<UserInfoUpdateRequest, UserInfoDoctor>().copyBean(request, UserInfoDoctor.class);
        User user = new MyBeanUtils<UserInfoUpdateRequest, User>().copyBean(request, User.class);
        userInfoDoctor.setDoctorOutpatientTimes(request.getDoctorOutpatientTimes());

        try {
            userService.modifyDoctorInfo(user, userInfoDoctor);
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
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
