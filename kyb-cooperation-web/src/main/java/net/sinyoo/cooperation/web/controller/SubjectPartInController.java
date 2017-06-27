package net.sinyoo.cooperation.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import net.sinyoo.cooperation.comm.model.DoctorOutpatientTime;
import net.sinyoo.cooperation.comm.model.SubjectPartIn;
import net.sinyoo.cooperation.comm.model.User;
import net.sinyoo.cooperation.comm.model.UserInfoDepartment;
import net.sinyoo.cooperation.comm.model.query.UserDoctorQuery;
import net.sinyoo.cooperation.comm.service.SubjectPartInService;
import net.sinyoo.cooperation.comm.service.UserService;
import net.sinyoo.cooperation.core.emnu.UserType;
import net.sinyoo.cooperation.core.exception.ApiException;
import net.sinyoo.cooperation.core.exception.ServiceException;
import net.sinyoo.cooperation.core.util.MyBeanUtils;
import net.sinyoo.cooperation.web.cache.UserDoctorCache;
import net.sinyoo.cooperation.web.controller.base.BaseController;
import net.sinyoo.cooperation.web.request.SubjectPartInListRequest;
import net.sinyoo.cooperation.web.request.SubjectPartInRefuseRequest;
import net.sinyoo.cooperation.web.request.SubjectPartInUserListRequest;
import net.sinyoo.cooperation.web.response.NullDataResponse;
import net.sinyoo.cooperation.web.response.SubjectPartInListResponse;
import net.sinyoo.cooperation.web.response.SubjectPartInUserListResponse;
import net.sinyoo.cooperation.web.to.SubjectPartInTo;
import net.sinyoo.cooperation.web.to.query.AllUserInfoTo;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 邀请或申请加入课题
 * 创建用户:     wangHui
 * 创建时间:     2017-03-20
 * Created by IntelliJ IDEA.
 */
@RestController
@RequestMapping("/subjectPartIn")
public class SubjectPartInController extends BaseController {

    @Reference
    private SubjectPartInService subjectPartInService;

    @Reference
    private UserService userService;

    /**
     * 申请 加入课题
     * <p>http://api.kyb.me:30001/subjectPartIn/apply/1/13/100</p>
     * <p>header accessToken   623751D996C14763B8D6C9158442C07B</p>
     *
     * @param userId          登录用户的编号
     * @param subjectId       课题编号
     * @param caseNumberShare 共享病例数
     * @param response        {"status":0}
     * @return
     */
    @RequestMapping(value = "/apply/{userId}/{subjectId}/{caseNumberShare}", method = RequestMethod.GET, consumes = "application/json")
    @ResponseBody
    public String apply(HttpServletRequest httpServletRequest, @PathVariable Integer userId, @PathVariable Integer subjectId, @PathVariable Integer caseNumberShare, NullDataResponse response) {
        try {
            int loginUserId = getUserId(httpServletRequest);
            if (loginUserId != userId) {
                return response.getErrorResponse(new ApiException(501, "登录信息已过期,未找到用户"));
            }
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }

        try {
            subjectPartInService.apply(userId, subjectId, caseNumberShare);
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
        return response.getSuccessResponse();
    }


    /**
     * 邀请 加入课题
     * <P>http://api.kyb.me:30001/subjectPartIn/invitation/1/13</P>
     * <p>header accessToken   623751D996C14763B8D6C9158442C07B</p>
     *
     * @param userId    被邀请的用户的编号
     * @param subjectId 课题编号
     * @param response  {"status":0}
     * @return
     */
    @RequestMapping(value = "/invitation/{userId}/{subjectId}", method = RequestMethod.GET, consumes = "application/json")
    @ResponseBody
    public String invitation(@PathVariable Integer userId, @PathVariable Integer subjectId, NullDataResponse response) {
        try {
            subjectPartInService.invocation(userId, subjectId);
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
        return response.getSuccessResponse();
    }

    /**
     * 同意 申请加入课题
     * <p>http://api.kyb.me:30001/subjectPartIn/agree/1/13</p>
     * <p>header accessToken   623751D996C14763B8D6C9158442C07B</p>
     *
     * @param userId   登录账号的编号
     * @param partInId 课题参与编号
     * @param response {"status":0}
     * @return
     */
    @RequestMapping(value = "/agree/{userId}/{partInId}", method = RequestMethod.GET, consumes = "application/json")
    @ResponseBody
    public String agree(@PathVariable Integer userId, @PathVariable Integer partInId, NullDataResponse response) {
        try {
            subjectPartInService.agree(userId, partInId);
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
        return response.getSuccessResponse();
    }


    /**
     * 拒绝 申请加入课题  拒绝别人的课题申请
     * <p>http://api.kyb.me:30001/subjectPartIn/refuse</p>
     * <p>header accessToken   623751D996C14763B8D6C9158442C07B</p>
     *
     * @param request  userId   登录账号的编号
     *                 partInId 课题参与编号
     *                 content 拒绝理由
     * @param response {"status":0}
     * @return
     */
    @RequestMapping(value = "/refuse", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String refuse(@RequestBody SubjectPartInRefuseRequest request, NullDataResponse response) {

        try {
            request.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }
        try {
            subjectPartInService.refuse(request.getUserId(), request.getPartInId(), request.getContent());
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
        return response.getSuccessResponse();
    }

    /**
     * 接受 邀请加入课题
     * <p>http://api.kyb.me:30001/subjectPartIn/accept/13/100</p>
     * <p>header accessToken   623751D996C14763B8D6C9158442C07B</p>
     *
     * @param partInId        课题参与编号
     * @param caseNumberShare 共享病例数
     * @param response        {"status":0}
     * @return
     */
    @RequestMapping(value = "/accept/{partInId}/{caseNumberShare}", method = RequestMethod.GET, consumes = "application/json")
    @ResponseBody
    public String accept(@PathVariable Integer partInId, @PathVariable Integer caseNumberShare, NullDataResponse response) {
        try {
            subjectPartInService.accept(partInId, caseNumberShare);
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
        return response.getSuccessResponse();
    }


    /**
     * 忽略 邀请加入课题
     * <p>http://api.kyb.me:30001/subjectPartIn/disAgree/1/13</p>
     * <p>header accessToken   623751D996C14763B8D6C9158442C07B</p>
     *
     * @param userId   登录账号的编号
     * @param partInId 课题参与编号
     * @param response {"status":0}
     * @return
     */
    @RequestMapping(value = "/ignore/{userId}/{partInId}", method = RequestMethod.GET, consumes = "application/json")
    @ResponseBody
    public String ignore(@PathVariable Integer userId, @PathVariable Integer partInId, NullDataResponse response) {
        try {
            subjectPartInService.ignore(userId, partInId);
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
        return response.getSuccessResponse();
    }

    /**
     * 获取申请加入科研课题列表
     * <p>
     * <p>http://api.kyb.me:30001/subjectPartIn/applyList</p>
     * <p>
     * <p>accessToken 623751D996C14763B8D6C9158442C07B</p>
     *
     * @param request  {"userId":1,"pages":0,"totalSize":0}
     * @param response {"pages":0,"pageSize":20,"totalSize":0,"data":[{"partInId":13,"userId":1,"subjectId":13,"subjectName":"标题11111","subjectUserId":1,"caseNumberShare":0,"subjectPartInStatus":"AGREE","subjectPartInType":"INVITATION","createTime":"2017-03-22 10:01:25","userDoctor":{"userId":1,"userName":"wanghui","gender":0,"userType":"DOCTOR","userStatus":0,"title":"主任医师","personalHonor":"个人荣誉","hospital":"上海六院","doctorOutpatientTimes":[],"department":"手术外科","clinicalField":"骨科","numberOfBeds":30,"caseNumber":500,"caseDistribution":"100","geneDetectionRatio":"55"}},{"partInId":11,"userId":1,"subjectId":13,"subjectName":"标题","subjectUserId":1,"caseNumberShare":0,"subjectPartInStatus":"PENDING","subjectPartInType":"INVITATION","createTime":"2017-03-21 15:49:20","userDoctor":{"userId":1,"userName":"wanghui","gender":0,"userType":"DOCTOR","userStatus":0,"title":"主任医师","personalHonor":"个人荣誉","hospital":"上海六院","doctorOutpatientTimes":[],"department":"手术外科","clinicalField":"骨科","numberOfBeds":30,"caseNumber":500,"caseDistribution":"100","geneDetectionRatio":"55"}}],"status":0}
     * @return
     */
    @RequestMapping(value = "/applyList", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String applyList(HttpServletRequest httpServletRequest, @RequestBody SubjectPartInListRequest request, SubjectPartInListResponse response) {
        int userId = 0;
        try {
            userId = getUserId(httpServletRequest);
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }
        try {
            request.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }

        Integer pageSize = request.getPageSize();
        if( pageSize == null ){
            pageSize = 20;
        }

        List<SubjectPartIn> subjectPartIns = subjectPartInService.findApplyPages(userId, request.getPages(), pageSize);
        if (request.getTotalSize() <= 0) {
            request.setTotalSize(subjectPartInService.findApplyCount(userId));
        }
        response.setPages(request.getPages());
        response.setPageSize(20);
        response.setTotalSize(request.getTotalSize());

        response.setSubjectPartInTos(setUserDoctor(subjectPartIns, true));

        return response.getSuccessResponse();
    }


    /**
     * 获取邀请加入科研课题列表
     * <p>
     * <p>http://api.kyb.me:30001/subjectPartIn/invocationList</p>
     * <p>
     * <p>accessToken 623751D996C14763B8D6C9158442C07B</p>
     *
     * @param request  {"userId":1,"pages":0,"totalSize":0}
     * @param response {"pages":0,"pageSize":20,"totalSize":0,"data":[{"partInId":13,"userId":1,"subjectId":13,"subjectName":"标题11111","subjectUserId":1,"caseNumberShare":0,"subjectPartInStatus":"AGREE","subjectPartInType":"INVITATION","createTime":"2017-03-22 10:01:25","userDoctor":{"userId":1,"userName":"wanghui","gender":0,"userType":"DOCTOR","userStatus":0,"title":"主任医师","personalHonor":"个人荣誉","hospital":"上海六院","doctorOutpatientTimes":[],"department":"手术外科","clinicalField":"骨科","numberOfBeds":30,"caseNumber":500,"caseDistribution":"100","geneDetectionRatio":"55"}},{"partInId":11,"userId":1,"subjectId":13,"subjectName":"标题","subjectUserId":1,"caseNumberShare":0,"subjectPartInStatus":"PENDING","subjectPartInType":"INVITATION","createTime":"2017-03-21 15:49:20","userDoctor":{"userId":1,"userName":"wanghui","gender":0,"userType":"DOCTOR","userStatus":0,"title":"主任医师","personalHonor":"个人荣誉","hospital":"上海六院","doctorOutpatientTimes":[],"department":"手术外科","clinicalField":"骨科","numberOfBeds":30,"caseNumber":500,"caseDistribution":"100","geneDetectionRatio":"55"}}],"status":0}
     * @return
     */
    @RequestMapping(value = "/invocationList", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String invocationList(@RequestBody SubjectPartInListRequest request, SubjectPartInListResponse response) {
        try {
            request.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }

        Integer pageSize = request.getPageSize();
        if( pageSize == null ){
            pageSize = 20;
        }

        List<SubjectPartIn> subjectPartIns = subjectPartInService.findInvocationPages(request.getUserId(), request.getPages(), pageSize);
        if (request.getTotalSize() <= 0) {
            request.setTotalSize(subjectPartInService.findInvocationCount(request.getUserId()));
        }
        response.setPages(request.getPages());
        response.setPageSize(20);
        response.setTotalSize(request.getTotalSize());
        response.setSubjectPartInTos(setUserDoctor(subjectPartIns, false));
        return response.getSuccessResponse();
    }


    /**
     * 已加入课题用户的列表
     * <p>
     * <p>http://api.kyb.me:30001/subjectPartIn/agreeList</p>
     * <p>
     * <p>accessToken 623751D996C14763B8D6C9158442C07B</p>
     *
     * @param request  {"subjectId":1,"pages":0,"totalSize":0}
     * @param response {"pages":0,"pageSize":20,"totalSize":0,"data":[{"partInId":13,"userId":1,"subjectId":13,"subjectName":"标题11111","subjectUserId":1,"caseNumberShare":0,"subjectPartInStatus":"AGREE","subjectPartInType":"INVITATION","createTime":"2017-03-22 10:01:25","userDoctor":{"userId":1,"userName":"wanghui","gender":0,"userType":"DOCTOR","userStatus":0,"title":"主任医师","personalHonor":"个人荣誉","hospital":"上海六院","doctorOutpatientTimes":[],"department":"手术外科","clinicalField":"骨科","numberOfBeds":30,"caseNumber":500,"caseDistribution":"100","geneDetectionRatio":"55"}},{"partInId":11,"userId":1,"subjectId":13,"subjectName":"标题","subjectUserId":1,"caseNumberShare":0,"subjectPartInStatus":"PENDING","subjectPartInType":"INVITATION","createTime":"2017-03-21 15:49:20","userDoctor":{"userId":1,"userName":"wanghui","gender":0,"userType":"DOCTOR","userStatus":0,"title":"主任医师","personalHonor":"个人荣誉","hospital":"上海六院","doctorOutpatientTimes":[],"department":"手术外科","clinicalField":"骨科","numberOfBeds":30,"caseNumber":500,"caseDistribution":"100","geneDetectionRatio":"55"}}],"status":0}
     * @return
     */
    @RequestMapping(value = "/agreeList", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String agreeList(@RequestBody SubjectPartInUserListRequest request, SubjectPartInUserListResponse response) {
        try {
            request.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }

        Integer pageSize = request.getPageSize();
        if( request.getPageSize() == null ){
            pageSize = 20;
        }

        List<SubjectPartIn> subjectPartIns = subjectPartInService.findAgreePages(request.getSubjectId(), 0, pageSize);
//        if (request.getTotalSize() <= 0) {
//            request.setTotalSize(subjectPartInService.findInvocationCount(request.getUserId()));
//        }
        response.setPages(0);
        response.setPageSize(20);
        response.setTotalSize(request.getTotalSize());
        response.setSubjectPartInTos(setUserDoctor(subjectPartIns, true));
        return response.getSuccessResponse();
    }


    /**
     * 首页获取    已加入课题用户的列表
     * <p>
     * <p>http://api.kyb.me:30001/subjectPartIn/homeAgreeList</p>
     *
     * @param request  {"subjectId":1,"pages":0,"totalSize":0}
     * @param response {"pages":0,"pageSize":20,"totalSize":0,"data":[{"partInId":13,"userId":1,"subjectId":13,"subjectName":"标题11111","subjectUserId":1,"caseNumberShare":0,"subjectPartInStatus":"AGREE","subjectPartInType":"INVITATION","createTime":"2017-03-22 10:01:25","userDoctor":{"userId":1,"userName":"wanghui","gender":0,"userType":"DOCTOR","userStatus":0,"title":"主任医师","personalHonor":"个人荣誉","hospital":"上海六院","doctorOutpatientTimes":[],"department":"手术外科","clinicalField":"骨科","numberOfBeds":30,"caseNumber":500,"caseDistribution":"100","geneDetectionRatio":"55"}},{"partInId":11,"userId":1,"subjectId":13,"subjectName":"标题","subjectUserId":1,"caseNumberShare":0,"subjectPartInStatus":"PENDING","subjectPartInType":"INVITATION","createTime":"2017-03-21 15:49:20","userDoctor":{"userId":1,"userName":"wanghui","gender":0,"userType":"DOCTOR","userStatus":0,"title":"主任医师","personalHonor":"个人荣誉","hospital":"上海六院","doctorOutpatientTimes":[],"department":"手术外科","clinicalField":"骨科","numberOfBeds":30,"caseNumber":500,"caseDistribution":"100","geneDetectionRatio":"55"}}],"status":0}
     * @return
     */
    @RequestMapping(value = "/homeAgreeList", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String homeAgreeList(@RequestBody SubjectPartInUserListRequest request, SubjectPartInUserListResponse response) {
        return agreeList(request, response);
    }


    /**
     * 设置医生或者科室信息返回
     *
     * @param subjectPartIns
     * @param isApply
     * @return
     */
    private List<SubjectPartInTo> setUserDoctor(List<SubjectPartIn> subjectPartIns, boolean isApply) {
        int userId = 0;
        List<SubjectPartInTo> subjectPartInTos = new MyBeanUtils<SubjectPartIn, SubjectPartInTo>().copyList(subjectPartIns, SubjectPartInTo.class);
        //设置参与用户
        if (subjectPartInTos != null && subjectPartInTos.size() > 0) {
            AllUserInfoTo allUserInfoTo;
            for (int i = 0; i < subjectPartInTos.size(); i++) {
                allUserInfoTo = null;
                SubjectPartInTo subjectPartInTo = subjectPartInTos.get(i);
                //判断是获取参与人信息还是获取创建者信息
                if (isApply) {
                    userId = subjectPartInTo.getUserId();
                } else {
                    userId = subjectPartInTo.getSubjectUserId();
                }
                User user = userService.getById(userId);

                if( user.getUserType() == UserType.DOCTOR ){
                    UserDoctorQuery doctorInfo = userService.findDoctorInfo(userId);
                    allUserInfoTo = new MyBeanUtils<UserDoctorQuery,AllUserInfoTo>().copyBean(doctorInfo,AllUserInfoTo.class);
                    addBaseUserInfo(allUserInfoTo,user);
                }
                else if( user.getUserType() == UserType.DEPARTMENT ){
                    UserInfoDepartment userInfoDepartment = userService.findDepartmentInfo(userId);
                    allUserInfoTo = new MyBeanUtils<UserInfoDepartment,AllUserInfoTo>().copyBean(userInfoDepartment,AllUserInfoTo.class);
                    addBaseUserInfo(allUserInfoTo,user);
                }

/*                //从缓存获取医生信息
                UserDoctorQuery userDoctorQuery = UserDoctorCache.hasUser(userId);
                if (userDoctorQuery == null) {
                    userDoctorQuery = userService.findDoctorInfo(userId);
                }*/
                subjectPartInTo.setUserDoctor(allUserInfoTo);
                subjectPartInTos.set(i, subjectPartInTo);
            }
        }
        return subjectPartInTos;
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
