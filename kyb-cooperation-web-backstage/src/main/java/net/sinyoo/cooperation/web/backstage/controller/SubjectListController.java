package net.sinyoo.cooperation.web.backstage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import net.sinyoo.cooperation.comm.model.Subject;
import net.sinyoo.cooperation.comm.model.SubjectMessage;
import net.sinyoo.cooperation.comm.model.User;
import net.sinyoo.cooperation.comm.model.query.UserDoctorQuery;
import net.sinyoo.cooperation.comm.service.SubjectMessageService;
import net.sinyoo.cooperation.comm.service.SubjectService;
import net.sinyoo.cooperation.comm.service.UserService;
import net.sinyoo.cooperation.core.emnu.SubjectStatus;
import net.sinyoo.cooperation.core.exception.ApiException;
import net.sinyoo.cooperation.core.util.MyBeanUtils;
import net.sinyoo.cooperation.web.backstage.cache.AccessTokenCache;
import net.sinyoo.cooperation.web.backstage.request.SubjectListRequest;
import net.sinyoo.cooperation.web.backstage.response.SubjectInfoResponse;
import net.sinyoo.cooperation.web.backstage.response.SubjectListResponse;
import net.sinyoo.cooperation.web.backstage.to.SubjectInfoTo;
import net.sinyoo.cooperation.web.backstage.to.SubjectTo;
import net.sinyoo.cooperation.web.backstage.utils.AccessTokenUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yunpengsha on 2017/4/1.
 */
@RestController
@RequestMapping("/subjectList")
public class SubjectListController {

    @Reference
    private SubjectService subjectService;

    @Reference
    private UserService userService;

    @Reference
    private SubjectMessageService subjectMessageService;

    /**
     * 获取审核列表
     * @param page
     * @param pageSize
     * @param response
     * @return
     */
    @RequestMapping(value = "/auditList/{page}/{pageSize}",method = RequestMethod.GET)
    public String getAuditList(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, SubjectInfoResponse response){
        List<Subject> auditList = subjectService.findAuditList(page, pageSize);
        List<SubjectInfoTo> subjectList =  addUserInfo(auditList);
        int totalSize = subjectService.findTotalSize(SubjectStatus.AUDIT,null);
        response.setSubjectList(subjectList);
        response.setTotalSize(totalSize);
        return response.getSuccessResponse();
    }
    /**
     * 获取课题通过列表
     * @param page
     * @param pageSize
     * @param response
     * @return
     */
    @RequestMapping(value = "/passList/{page}/{pageSize}",method = RequestMethod.GET)
    public String getPassList(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, SubjectInfoResponse response){
        List<Subject> auditList = subjectService.findPassList(page, pageSize);
        int totalSize = subjectService.findPassListCount();
        List<SubjectInfoTo> subjectList =  addUserInfo(auditList);
        response.setSubjectList(subjectList);
        response.setTotalSize(totalSize);
        return response.getSuccessResponse();
    }
    /**
     * 获取课题失败列表
     * @param page
     * @param pageSize
     * @param response
     * @return
     */
    @RequestMapping(value = "/failList/{page}/{pageSize}",method = RequestMethod.GET)
    public String getFailList(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, SubjectInfoResponse response){
        List<Subject> auditList = subjectService.findFailList(page, pageSize);
        List<SubjectInfoTo> subjectList =  addUserInfo(auditList);
        addFailMessage(subjectList);
        int totalSize = subjectService.findTotalSize(SubjectStatus.AUDIT_FAIL,null);
        response.setSubjectList(subjectList);
        response.setTotalSize(totalSize);
        return response.getSuccessResponse();
    }


    private void addFailMessage(List<SubjectInfoTo> subjectList){
        for( SubjectInfoTo subjectInfoTo : subjectList ){
            Integer subjectId = subjectInfoTo.getSubjectId();
            SubjectMessage subjectMessage = subjectMessageService.findMessageBySubjectId(subjectId);
            if( subjectMessage == null ){
                continue;
            }
            subjectInfoTo.setFailMessage(subjectMessage.getMessage());
        }
    }

    /**
     * 添加用户信息
     * @param auditList
     * @return
     */
    private List<SubjectInfoTo> addUserInfo(List<Subject> auditList) {
        List<SubjectInfoTo> subjectList = new ArrayList<>();
        for(Subject subject:auditList){
            int userId = subject.getUserId();
            //根据userId获取user的基本信息
            User user = userService.getById(userId);
            user.setPassword(null);
            //复制属性 加入集合
            SubjectInfoTo subjectInfoTo = new MyBeanUtils<Subject,SubjectInfoTo>().copyBean(subject,SubjectInfoTo.class);
            subjectInfoTo.setUser(user);
            subjectList.add(subjectInfoTo);
        }
        return subjectList;
    }

    /**
     * 用户中心 获取科研课题列表
     * http://api.kyb.me:30001/subjectList/userList
     * <br>
     * header accessToken   623751D996C14763B8D6C9158442C07B
     * <br>
     *
     * @param subjectListRequest {"pages":0,"totalSize":0,"userId":1}
     *                           <br>
     * @param response           {"pages":0,"totalSize":1,"data":[{"subjectId":13,"userId":1,"researchTitle":"标题11111","researchObjective":"描述21111","researchDrug":"药物11,药物21","region":"上海区域1","partInLevel":"三甲医院1","minCaseRequire":10,"partInField":"肺癌领域1","caseCompleteness":"100","publicStatus":0,"subjectNature":"OBSERVE","subjectStatus":"PUBLISH"}],"status":0}
     *                           <br>
     * @return
     */
    @RequestMapping(value = "/userList", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String userList(HttpServletRequest httpServletRequest, @RequestBody SubjectListRequest subjectListRequest, SubjectListResponse response) {
        try {
            subjectListRequest.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }
        //查询推荐列表中是否有信息

        List<Subject> subjects = subjectService.findUserPages(subjectListRequest.getUserId(), subjectListRequest.getPages(), 100);

        //设置是否是拥有者
        List<SubjectTo> subjectTos = setDoctorName(subjects);
        setSubjectOwns(httpServletRequest, subjectTos);

        //设置返回结果
        response.setSubjectTos(subjectTos);

        //设置页数
        if (subjectListRequest.getTotalSize() <= 0) {
            subjectListRequest.setTotalSize(subjectService.findUserCount(subjectListRequest.getUserId()));
        }
        response.setPages(subjectListRequest.getPages());
        response.setTotalSize(subjectListRequest.getTotalSize());
        return response.getSuccessResponse();
    }


    /**
     * 设置创建课题的用户信息
     *
     * @param subjects 课题列表
     * @return 返回的课题对象
     */
    private List<SubjectTo> setDoctorName(List<Subject> subjects) {
        List<SubjectTo> subjectTos = null;
        //查询用户信息  设置用户信息
        UserDoctorQuery user = null;
        if (subjects != null && subjects.size() > 0) {
            subjectTos = new MyBeanUtils<Subject, SubjectTo>().copyList(subjects, SubjectTo.class);
            SubjectTo subjectTo = null;
            for (int i = 0; i < subjectTos.size(); i++) {
                subjectTo = subjectTos.get(i);
                    user = userService.findDoctorInfo(subjectTo.getUserId());
                    if (user == null) {
                        subjectTo.setUserName("未知用户");
                        subjectTos.set(i, subjectTo);
                        continue;
                    }
                subjectTo.setUserName(user.getUserName() + " " + user.getUserType().getName());
                subjectTos.set(i, subjectTo);
            }
        }

        return subjectTos;
    }

    /**
     * 设置是否是课题拥有着
     * @param httpServletRequest
     * @param subjectTos
     */
    protected void setSubjectOwns(HttpServletRequest httpServletRequest, List<SubjectTo> subjectTos) {
        if(subjectTos == null || subjectTos.size() <= 0){
            return;
        }
        int userId = 0;
        try {
            userId = getUserId(httpServletRequest);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        if (userId > 0) {
            for (int i = 0; i < subjectTos.size(); i++) {
                SubjectTo subjectTo = subjectTos.get(i);
                if (userId == subjectTo.getUserId()) {
                    subjectTo.setSubjectOwn(true);
                    subjectTos.set(i, subjectTo);
                }
            }
        }
    }

    /**
     * 获取缓存中的用户编号
     *
     * @param httpServletRequest
     * @throws ApiException
     */
    protected int getUserId(HttpServletRequest httpServletRequest) throws ApiException {
        //获取用户
        Integer userId = AccessTokenCache.getUserId(AccessTokenUtil.getAccessToken(httpServletRequest));
        if (userId == null || userId <= 0) {
            throw new ApiException(501, "登录信息已过期,未找到用户");
        }
        return userId;
    }

}
