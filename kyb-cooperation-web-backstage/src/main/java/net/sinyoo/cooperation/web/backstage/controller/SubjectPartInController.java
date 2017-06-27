package net.sinyoo.cooperation.web.backstage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import net.sinyoo.cooperation.comm.model.SubjectPartIn;
import net.sinyoo.cooperation.comm.model.User;
import net.sinyoo.cooperation.comm.model.UserInfoDepartment;
import net.sinyoo.cooperation.comm.model.UserInfoDrugCompany;
import net.sinyoo.cooperation.comm.model.query.UserDoctorQuery;
import net.sinyoo.cooperation.comm.service.SubjectPartInService;
import net.sinyoo.cooperation.comm.service.UserService;
import net.sinyoo.cooperation.core.emnu.UserType;
import net.sinyoo.cooperation.core.exception.ApiException;
import net.sinyoo.cooperation.core.util.MyBeanUtils;
import net.sinyoo.cooperation.web.backstage.request.SubjectPartInUserListRequest;
import net.sinyoo.cooperation.web.backstage.response.SubjectPartInUserListResponse;
import net.sinyoo.cooperation.web.backstage.to.AllUserInfoTo;
import net.sinyoo.cooperation.web.backstage.to.SubjectPartInTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by yunpengsha on 2017/4/6.
 */
@RestController
@RequestMapping("/subjectPartIn")
public class SubjectPartInController {

    @Reference
    private SubjectPartInService subjectPartInService;

    @Reference
    private UserService userService;

    /**
     *
     * 已加入课题用户的列表
     *
     * <p>http://api.kyb.me:30001/subjectPartIn/agreeList</p>
     *
     * <p>accessToken 623751D996C14763B8D6C9158442C07B</p>
     * @param request {"subjectId":1,"pages":0,"totalSize":0}
     * @param response {"pages":0,"pageSize":20,"totalSize":0,"data":[{"partInId":2,"userId":1,"subjectId":1,"subjectName":"课题bane","subjectUserId":1,"caseNumberShare":200,"subjectPartInStatus":"AGREE","subjectPartInType":"APPLY","allUserInfoTo":{"userId":1,"userName":"2医院1号科室","imageUrl":"https://timgsa.baidu.com/timg?image\u0026quality\u003d80\u0026size\u003db9999_10000\u0026sec\u003d1491481612861\u0026di\u003df30e222ba73ae93e79dd991db2a1c3f3\u0026imgtype\u003d0\u0026src\u003dhttp%3A%2F%2Fimg.25pp.com%2Fuploadfile%2Fsoft%2Fimages%2F2012%2F0409%2F20120409030535743.jpg","gender":0,"userType":"DEPARTMENT","hospital":"2医院","department":"1号科室","departmentId":1,"chiefPhysicianNumber":1,"deputyChiefPhysicianNumber":1,"attendingDoctorNumber":1}},{"partInId":1,"userId":1,"subjectId":1,"subjectName":"课题name","subjectUserId":1,"caseNumberShare":100,"subjectPartInStatus":"AGREE","subjectPartInType":"INVITATION","allUserInfoTo":{"userId":1,"userName":"2医院1号科室","imageUrl":"https://timgsa.baidu.com/timg?image\u0026quality\u003d80\u0026size\u003db9999_10000\u0026sec\u003d1491481612861\u0026di\u003df30e222ba73ae93e79dd991db2a1c3f3\u0026imgtype\u003d0\u0026src\u003dhttp%3A%2F%2Fimg.25pp.com%2Fuploadfile%2Fsoft%2Fimages%2F2012%2F0409%2F20120409030535743.jpg","gender":0,"userType":"DEPARTMENT","hospital":"2医院","department":"1号科室","departmentId":1,"chiefPhysicianNumber":1,"deputyChiefPhysicianNumber":1,"attendingDoctorNumber":1}}],"status":0}
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
        List<SubjectPartIn> subjectPartIns = subjectPartInService.findAgreePages(request.getSubjectId(), 0, 20);
        response.setPages(0);
        response.setPageSize(20);
        response.setTotalSize(request.getTotalSize());
        response.setSubjectPartInTos(setUserDoctor(subjectPartIns,false));
        return response.getSuccessResponse();
    }

    /**
     * 设置用户信息返回
     * @param subjectPartIns
     * @param isApply
     * @return
     */
    private List<SubjectPartInTo> setUserDoctor(List<SubjectPartIn> subjectPartIns, boolean isApply){
        int userId = 0;
        List<SubjectPartInTo> subjectPartInTos = new MyBeanUtils<SubjectPartIn, SubjectPartInTo>().copyList(subjectPartIns, SubjectPartInTo.class);
        //设置参与用户
        if (subjectPartInTos != null && subjectPartInTos.size() > 0) {
            for (int i = 0; i < subjectPartInTos.size(); i++) {
                SubjectPartInTo subjectPartInTo = subjectPartInTos.get(i);
                //判断是获取参与人信息还是获取创建者信息
                if (isApply){
                    userId = subjectPartInTo.getUserId();
                }else {
                    userId = subjectPartInTo.getSubjectUserId();
                }

                User user = userService.getById(userId);

                AllUserInfoTo allUserInfoTo ;

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
                else if( user.getUserType() == UserType.DRUG_COMPANY ){
                    UserInfoDrugCompany userInfoDrugCompany = userService.findDrugCompanyInfo(userId);
                    allUserInfoTo = new MyBeanUtils<UserInfoDrugCompany,AllUserInfoTo>().copyBean(userInfoDrugCompany,AllUserInfoTo.class);
                    addBaseUserInfo(allUserInfoTo,user);
                }
                else {
                    continue;
                }

                subjectPartInTo.setAllUserInfoTo(allUserInfoTo);
                subjectPartInTos.set(i,subjectPartInTo);
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
