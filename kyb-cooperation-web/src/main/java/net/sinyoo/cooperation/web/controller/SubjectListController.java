package net.sinyoo.cooperation.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import net.sinyoo.cooperation.comm.model.HomeLink;
import net.sinyoo.cooperation.comm.model.Subject;
import net.sinyoo.cooperation.comm.model.User;
import net.sinyoo.cooperation.comm.service.HomeLinkService;
import net.sinyoo.cooperation.comm.service.SubjectService;
import net.sinyoo.cooperation.comm.service.SubjectStandardService;
import net.sinyoo.cooperation.comm.service.UserService;
import net.sinyoo.cooperation.core.emnu.HomeLinkType;
import net.sinyoo.cooperation.core.emnu.SubjectStatus;
import net.sinyoo.cooperation.core.emnu.UserType;
import net.sinyoo.cooperation.core.exception.ApiException;
import net.sinyoo.cooperation.core.util.MyBeanUtils;
import net.sinyoo.cooperation.web.controller.base.SubjectBaseController;
import net.sinyoo.cooperation.web.request.HomeLinkSubjectListRequest;
import net.sinyoo.cooperation.web.request.SubjectListRequest;
import net.sinyoo.cooperation.web.response.HomeLinkSubjectListResponse;
import net.sinyoo.cooperation.web.response.SubjectListResponse;
import net.sinyoo.cooperation.web.to.SubjectTo;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课题控制器
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/17
 * Time: 上午9:23
 */
@RestController
@RequestMapping("/subjectList")
public class SubjectListController extends SubjectBaseController {


    @Reference
    private SubjectService subjectService;

    @Reference
    private UserService userService;

    @Reference
    private SubjectStandardService subjectStandardService;

    @Reference
    private HomeLinkService homeLinkService;

    /**
     * 获取科研课题列表
     * <p>http://api.kyb.me:30001/subjectList/list</p>
     * <p>header accessToken   623751D996C14763B8D6C9158442C07B</p>
     *
     * @param request  <p>{"pages":0,"totalSize":0,"userId":0,"subjectNature":"OBSERVE","searchKey":"标题"}</p>
     * @param response <p>{"pages":0,"totalSize":1,"data":[{"subjectId":13,"userId":1,"researchTitle":"标题11111","researchObjective":"描述21111","researchDrug":"药物11,药物21","region":"上海区域1","partInLevel":"三甲医院1","minCaseRequire":10,"partInField":"肺癌领域1","caseCompleteness":"100","publicStatus":0,"subjectNature":"OBSERVE","subjectStatus":"PUBLISH"}],"status":0}</p>
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String list(HttpServletRequest httpServletRequest, @RequestBody SubjectListRequest request, SubjectListResponse response) {
        try {
            request.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }

        Integer pageSize = request.getPageSize();
        if( pageSize == null ){
            pageSize = 6;
        }

        List<Subject> subjects = subjectService.findPages(-1, request.getSearchKey(), request.getSubjectNature(), SubjectStatus.PUBLISH, request.getPages(), pageSize);

        //设置是否是拥有者
        List<SubjectTo> subjectTos = setUserName(subjects);
        setSubjectOwns(httpServletRequest, subjectTos);

        //设置返回结果
        response.setSubjectTos(subjectTos);

        //查询总数量
        if( request.getTotalSize() == 0 || request.getTotalSize() == null ){
            Integer totalSize = subjectService.findCount(-1, request.getSearchKey(), request.getSubjectNature(), SubjectStatus.PUBLISH);
            if( totalSize == null ){
                totalSize = 0;
            }
            response.setTotalSize(totalSize);
        }
        else {
            response.setTotalSize( request.getTotalSize() );
        }

        response.setPages(request.getPages());
        return response.getSuccessResponse();
    }


    /**
     * 首页进入医生详情页面 获取科研课题列表
     * http://api.kyb.me:30001/subjectList/homeLinkList
     * <br>
     * header accessToken   623751D996C14763B8D6C9158442C07B
     * <br>
     *
     * @param request  {"pages":0,"totalSize":0,"homeLinkId":1}
     *                 <br>
     * @param response {"pages":0,"totalSize":1,"data":[{"subjectId":13,"userId":1,"researchTitle":"标题11111","researchObjective":"描述21111","researchDrug":"药物11,药物21","region":"上海区域1","partInLevel":"三甲医院1","minCaseRequire":10,"partInField":"肺癌领域1","caseCompleteness":"100","publicStatus":0,"subjectNature":"OBSERVE","subjectStatus":"PUBLISH"}],"status":0}
     *                 <br>
     * @return
     */
    @RequestMapping(value = "/homeLinkList", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String homeLinkList(HttpServletRequest httpServletRequest,@RequestBody HomeLinkSubjectListRequest request, HomeLinkSubjectListResponse response) {

        try {
            request.checkUpdateParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }

        // 如果获取首页推荐栏的用户课题详情，则传homeLinkId  ,否则 userId
        if( request.getUserId() == null && request.getHomeLinkId() == null ){
            return response.getErrorResponse( new ApiException(501,"id值不能为空") );
        }

        Integer userId = 0;

        if( request.getHomeLinkId() != null ){
            HomeLink homeLink = homeLinkService.findByLinkId(request.getHomeLinkId());
            if (homeLink == null || (homeLink.getLinkType() != HomeLinkType.DOCTOR && homeLink.getLinkType() != HomeLinkType.DRUG_COMPANY)) {
                return response.getSuccessResponse();
            }
            userId = homeLink.getRefId();
        }
        else {
            userId = request.getUserId();
        }

        List<Subject> subjects = subjectService.findPages(userId, null, null, null, request.getPages(), 20);

        //设置名字
        List<SubjectTo> subjectTos = setUserName(subjects);
        //设置是否是课题创建者
        setSubjectOwnsByUserId(userId, subjectTos);

        //设置返回结果
        response.setSubjectTos(subjectTos);

        //设置页数
        if (request.getTotalSize() <= 0) {
            request.setTotalSize(subjectService.findOwnerSubjectCount(userId));
        }
        response.setPages(request.getPages());
        response.setTotalSize(request.getTotalSize());
        return response.getSuccessResponse();
    }

/*        //设置是否是拥有者
        List<SubjectTo> subjectTos = setUserName(subjects);
        setSubjectOwns(httpServletRequest, subjectTos);

        //设置返回结果
        response.setSubjectTos(subjectTos);

        //设置页数
        if (request.getTotalSize() <= 0) {
            request.setTotalSize(subjectService.findCount(homeLink.getRefId(), null, null, SubjectStatus.PUBLISH));
        }
        response.setPages(request.getPages());
        response.setTotalSize(request.getTotalSize());
        return response.getSuccessResponse();
    }*/

    /**
     * 用户中心 获取科研课题列表
     * http://api.kyb.me:30001/subjectList/userList
     * <br>
     * header accessToken   623751D996C14763B8D6C9158442C07B
     * <br>
     *
     * @param subjectListRequest {"pages":0,"totalSize":0,"userId":1,"subjectNature":"OBSERVE","searchKey":"标题1"}
     *                           <br>
     * @param response           {"pages":0,"totalSize":1,"data":[{"subjectId":13,"userId":1,"researchTitle":"标题11111","researchObjective":"描述21111","researchDrug":"药物11,药物21","region":"上海区域1","partInLevel":"三甲医院1","minCaseRequire":10,"partInField":"肺癌领域1","caseCompleteness":"100","publicStatus":0,"subjectNature":"OBSERVE","subjectStatus":"PUBLISH"}],"status":0}
     *                           <br>
     * @return
     */
    @RequestMapping(value = "/userList", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String userList(@RequestBody SubjectListRequest subjectListRequest, SubjectListResponse response) {
        try {
            subjectListRequest.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }
        //查询推荐列表中是否有信息

        List<Subject> subjects = subjectService.findUserPages(subjectListRequest.getUserId(), subjectListRequest.getPages(), 100);

        //设置名字
        List<SubjectTo> subjectTos = setUserName(subjects);
        //设置是否是课题创建者
        setSubjectOwnsByUserId(subjectListRequest.getUserId(), subjectTos);

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
    private List<SubjectTo> setUserName(List<Subject> subjects) {
        List<SubjectTo> subjectTos = null;
        //查询用户信息  设置用户信息
        User user = null;
        if (subjects != null && subjects.size() > 0) {
            subjectTos = new MyBeanUtils<Subject, SubjectTo>().copyList(subjects, SubjectTo.class);
            SubjectTo subjectTo = null;
            for (int i = 0; i < subjectTos.size(); i++) {
                subjectTo = subjectTos.get(i);
                user = userService.getById(subjectTo.getUserId());
                if (user == null) {
                    subjectTo.setUserName("未知用户");
                    subjectTos.set(i, subjectTo);
                    continue;
                }
                if( user.getUserType() == UserType.DOCTOR ){
                    subjectTo.setUserName(user.getUserName() + " " + user.getUserType().getName());
                    subjectTos.set(i, subjectTo);
                }
                else if( user.getUserType() == UserType.DEPARTMENT || user.getUserType() == UserType.DRUG_COMPANY ){
                    subjectTo.setUserName(user.getUserName());
                    subjectTos.set(i, subjectTo);
                }
/*                subjectTo = subjectTos.get(i);
                //查询map中的用户
                user = UserDoctorCache.hasUser(subjectTo.getUserId());
                if (user == null) {
                    //查询数据库  设置用户信息到map中
                    user = userService.findDoctorInfo(subjectTo.getUserId());
                    if (user == null) {
                        subjectTo.setUserName("未知用户");
                        subjectTos.set(i, subjectTo);
                        continue;
                    }
                    //更新map中的用户数据
                    UserDoctorCache.addCache(user.getUserId(), user);
                }
                subjectTo.setUserName(user.getUserName() + " " + user.getUserType().getName());
                subjectTos.set(i, subjectTo);*/
            }
        }

        return subjectTos;
    }

}
