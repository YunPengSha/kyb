package net.sinyoo.cooperation.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import net.sinyoo.cooperation.comm.model.Crf;
import net.sinyoo.cooperation.comm.model.Subject;
import net.sinyoo.cooperation.comm.model.SubjectMessage;
import net.sinyoo.cooperation.comm.model.SubjectStandard;
import net.sinyoo.cooperation.comm.service.CrfService;
import net.sinyoo.cooperation.comm.service.SubjectMessageService;
import net.sinyoo.cooperation.comm.service.SubjectService;
import net.sinyoo.cooperation.comm.service.SubjectStandardService;
import net.sinyoo.cooperation.core.base.ResponseErrorCode;
import net.sinyoo.cooperation.core.emnu.SubjectMessageType;
import net.sinyoo.cooperation.core.emnu.SubjectStandardType;
import net.sinyoo.cooperation.core.emnu.SubjectStatus;
import net.sinyoo.cooperation.core.exception.ApiException;
import net.sinyoo.cooperation.core.exception.ServiceException;
import net.sinyoo.cooperation.core.util.ListToString;
import net.sinyoo.cooperation.core.util.MyBeanUtils;
import net.sinyoo.cooperation.core.util.UploadUtils;
import net.sinyoo.cooperation.web.cache.AccessTokenCache;
import net.sinyoo.cooperation.web.controller.base.SubjectBaseController;
import net.sinyoo.cooperation.web.request.SubjectAddRequest;
import net.sinyoo.cooperation.web.request.SubjectAuditRequest;
import net.sinyoo.cooperation.web.request.SubjectModifyRequest;
import net.sinyoo.cooperation.web.response.NullDataResponse;
import net.sinyoo.cooperation.web.response.SubjectAddResponse;
import net.sinyoo.cooperation.web.response.SubjectDetailResponse;
import net.sinyoo.cooperation.web.response.SubjectModifyResponse;
import net.sinyoo.cooperation.web.to.CrfTo;
import net.sinyoo.cooperation.web.to.SubjectStandardTo;
import net.sinyoo.cooperation.web.to.SubjectTo;
import net.sinyoo.cooperation.web.to.query.SubjectDetailTo;
import net.sinyoo.cooperation.web.utils.AccessTokenUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 课题控制器
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/17
 * Time: 上午9:23
 */
@RestController
@RequestMapping("/subject")
public class SubjectController extends SubjectBaseController{


    @Reference
    private SubjectService subjectService;

    @Reference
    private SubjectStandardService subjectStandardService;

    @Reference
    private CrfService crfService;

    @Reference
    private SubjectMessageService subjectMessageService;

    @Value("${upload.filepath}")
    public String uploadFilePath;

    /**
     * 添加课题
     * <p>http://api.kyb.me:30001/subject/addSubject</p>
     * <p>header accessToken   623751D996C14763B8D6C9158442C07B</p>
     *
     * @param subjectAddRequest {"userId":1,"researchTitle":"标题","researchObjective":"描述","researchDrugs":["药物1","药物2"],"regions":["上海区域"],"partInLevel":"三甲医院","minCaseRequire":"10","partInField":"肺癌领域","caseCompleteness":"100","publicStatus":"0","subjectNature":"OBSERVE","subjectStandardIns":["纳入标准1","纳入标准2"],"subjectStandardOuts":["排除标准1","排除标准2"],"doctorIds":[1]}
     * @param response          {"data":{"subjectId":13,"userId":1,"researchTitle":"标题","researchObjective":"描述","researchDrug":"药物1,药物2","region":"上海区域","partInLevel":"三甲医院","minCaseRequire":10,"partInField":"肺癌领域","caseCompleteness":"100","publicStatus":0,"subjectNature":"OBSERVE","subjectStatus":"AUDIT"},"status":0}
     * @return
     */
    @RequestMapping(value = "/addSubject", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String addSubject(@RequestBody SubjectAddRequest subjectAddRequest, SubjectAddResponse response) {

        try {
            subjectAddRequest.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }

/*
        原先是研究者手册，现在删除
        try {
            haveFile(subjectAddRequest.getToken());
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }*/

        Subject subject = new MyBeanUtils<SubjectAddRequest, Subject>().copyBean(subjectAddRequest, Subject.class);
        //转换 区域  数组到string
        subject.setRegion(ListToString.list2String(subjectAddRequest.getRegions()));
        //转换 研究药物 数组到string
        subject.setResearchDrug(ListToString.list2String(subjectAddRequest.getResearchDrugs()));

        try {
            subject = subjectService.addSubject(subject, subjectAddRequest.getSubjectStandardIns(), subjectAddRequest.getSubjectStandardOuts(), subjectAddRequest.getDoctorIds());
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }

/*
        目前不用
        //上传文件，保存url   url后缀加上 subjectId
        String url;
        try {
            url = uploadBrochure(subjectAddRequest.getToken(),subject.getSubjectId());
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
        subjectService.modifySubject(subjectAddRequest.getSubjectId(),url);*/

        //设置返回结果
        response.setSubjectTo(new MyBeanUtils<Subject, SubjectTo>().copyBean(subject, SubjectTo.class));
        return response.getSuccessResponse();
    }

/*    @RequestMapping(value = "/addBrochure",method =RequestMethod.POST,consumes = "application/json")
    @ResponseBody
    public String addBrochureUrl(@RequestBody SubjectAddRequest subjectAddRequest,NullDataResponse response ){
        if( !(subjectAddRequest.getSubjectId() != null && subjectAddRequest.getBrochureUrl() != null) ){
            return response.getErrorResponse(new ApiException(501,"参数有错"));
        }

        subjectService.modifySubject(subjectAddRequest.getSubjectId(),subjectAddRequest.getBrochureUrl());
        return response.getSuccessResponse();
    }*/


    /**
     * 修改课题
     * <p>http://api.kyb.me:30001/subject/modifySubject</p>
     * <p>header accessToken   623751D996C14763B8D6C9158442C07B</p>
     *
     * @param subjectModifyRequest {"subjectId":13,"userId":1,"researchTitle":"标题11111","researchObjective":"描述21111","researchDrugs":["药物11","药物21"],"regions":["上海区域1"],"partInLevel":"三甲医院1","minCaseRequire":"10","partInField":"肺癌领域1","caseCompleteness":"100","publicStatus":"0","subjectNature":"OBSERVE","subjectStandardIns":["纳入标准11","纳入标准21"],"subjectStandardOuts":["排除标准11","排除标准21"],"doctorIds":[1]}
     * @param response             {"status":0}
     * @return
     */
    @RequestMapping(value = "/modifySubject", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String modifySubject(HttpServletRequest httpServletRequest, @RequestBody SubjectModifyRequest subjectModifyRequest, SubjectModifyResponse response) {
        try {
            if (!subjectModifyRequest.checkUpdateParams()) {
                return response.getErrorResponse(ResponseErrorCode.CHECK_ERROR, ResponseErrorCode.CHECK_ERROR_MESSAGE, "701", "请输入更新数据");
            }
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }


        //获取用户
        int userId = 0;
        try {
            userId = getUserId(httpServletRequest);
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }


        Subject subject = new MyBeanUtils<SubjectModifyRequest, Subject>().copyBean(subjectModifyRequest, Subject.class);

        //转换 区域  数组到string
        subject.setRegion(ListToString.list2String(subjectModifyRequest.getRegions()));
        //转换 研究药物 数组到string
        subject.setResearchDrug(ListToString.list2String(subjectModifyRequest.getResearchDrugs()));

        try {
            subjectService.modifySubject(userId, subject, subjectModifyRequest.getSubjectStandardIns(), subjectModifyRequest.getSubjectStandardOuts(), subjectModifyRequest.getDeleteSubjectStandardIds());
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
        //设置返回结果
        return response.getSuccessResponse();
    }


    /**
     * 审核课题
     * http://localhost:30001/subject/auditSubject
     * <p>
     * <p>header accessToken   623751D996C14763B8D6C9158442C07B</p>
     *
     * @param request  {"subjectId":1,"subjectStatus":"PUBLISH","content":"拒绝理由"}
     * @param response {"status":0}
     * @return
     */
    @RequestMapping(value = "/auditSubject", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseBody
    public String auditSubject(HttpServletRequest httpServletRequest, @RequestBody SubjectAuditRequest request, NullDataResponse response) {
        //获取用户
        int userId = 0;
        try {
            userId = getUserId(httpServletRequest);
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }
        try {
            subjectService.auditSubject(request.getSubjectId(), request.getSubjectStatus(), request.getContent());
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
        //设置返回结果
        return response.getSuccessResponse();
    }


    /**
     * 最后一步—— 验收课题
     * @param subjectAuditRequest
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkSubject",method = RequestMethod.POST)
    public String check(HttpServletRequest httpServletRequest,@RequestBody SubjectAuditRequest subjectAuditRequest,NullDataResponse response){
        Integer userId = AccessTokenCache.getUserId(AccessTokenUtil.getAccessToken(httpServletRequest));
        if (userId == null) {
            return response.getErrorResponse(new ApiException(501, "登录信息已过期,未找到用户"));
        }
        Integer subjectId = subjectAuditRequest.getSubjectId();
        if( subjectId == null ){
            return response.getErrorResponse(new ApiException(501,"id不存在"));
        }

        try {
            subjectService.checkSubject(userId,subjectId);
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }

        return response.getSuccessResponse();
    }


    /**
     * 完成课题
     * http://localhost:30001/subject/auditSubject/13/COMPLETE
     * <p>header accessToken   623751D996C14763B8D6C9158442C07B</p>
     *
     * @param subjectId
     * @param subjectStatus
     * @param response      {"status":0}
     * @return
     */
    @RequestMapping(value = "/completeSubject/{subjectId}/{subjectStatus}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseBody
    public String completeSubject(HttpServletRequest httpServletRequest, @PathVariable Integer subjectId, @PathVariable String subjectStatus, NullDataResponse response) {
        //获取用户
        int userId = 0;
        try {
            userId = getUserId(httpServletRequest);
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }
        try {
            subjectService.completeSubject(userId, subjectId, SubjectStatus.valueOf(subjectStatus));
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
        //设置返回结果
        return response.getSuccessResponse();
    }

    /**
     * 删除课题
     * http://localhost:30001/subject/auditSubject/13/DELETED
     * <p>header accessToken   623751D996C14763B8D6C9158442C07B</p>
     *
     * @param subjectId
     * @param subjectStatus
     * @param response      {"status":0}
     * @return
     */
    @RequestMapping(value = "/removeSubject/{subjectId}/{subjectStatus}", method = RequestMethod.DELETE, consumes = "application/json")
    @ResponseBody
    public String removeSubject(HttpServletRequest httpServletRequest, @PathVariable Integer subjectId, @PathVariable String subjectStatus, NullDataResponse response) {

        //获取用户
        int userId = 0;
        try {
            userId = getUserId(httpServletRequest);
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }

        try {
            subjectService.removeSubject(userId, subjectId, SubjectStatus.valueOf(subjectStatus));
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
        //设置返回结果
        return response.getSuccessResponse();
    }


    /**
     * 首页 获取课题详情
     *
     * @param subjectId http://localhost:30001/subject/homeSubjectDetail/1
     * @param response  {"data":{"subjectId":13,"userId":1,"researchTitle":"标题11111","researchObjective":"描述21111","researchDrug":"药物11,药物21","region":"上海区域1","partInLevel":"三甲医院1","minCaseRequire":10,"partInField":"肺癌领域1","caseCompleteness":"100","publicStatus":0,"subjectStatus":"PUBLISH","standardExcludes":[{"subjectStandardId":52,"subjectId":13,"content":"排除标准21","subjectStandardType":"EXCLUDE"},{"subjectStandardId":51,"subjectId":13,"content":"排除标准11","subjectStandardType":"EXCLUDE"},{"subjectStandardId":48,"subjectId":13,"content":"排除标准21","subjectStandardType":"EXCLUDE"},{"subjectStandardId":47,"subjectId":13,"content":"排除标准11","subjectStandardType":"EXCLUDE"}],"standardIncludes":[{"subjectStandardId":50,"subjectId":13,"content":"纳入标准21","subjectStandardType":"INCLUDE"},{"subjectStandardId":49,"subjectId":13,"content":"纳入标准11","subjectStandardType":"INCLUDE"},{"subjectStandardId":46,"subjectId":13,"content":"纳入标准21","subjectStandardType":"INCLUDE"},{"subjectStandardId":45,"subjectId":13,"content":"纳入标准11","subjectStandardType":"INCLUDE"}]},"status":0}
     * @return
     */
    @RequestMapping(value = "/homeSubjectDetail/{subjectId}", method = RequestMethod.GET)
    @ResponseBody
    public String homeSubjectDetail(HttpServletRequest httpServletRequest, @PathVariable Integer subjectId, SubjectDetailResponse response) {
        return subjectDetail(httpServletRequest, subjectId, response);
    }


    /**
     * 获取课题详情
     * <p>header accessToken   623751D996C14763B8D6C9158442C07B</p>
     *
     * @param subjectId http://localhost:30001/subject/subjectDetail/13
     * @param response  {"data":{"subjectId":13,"userId":1,"researchTitle":"标题11111","researchObjective":"描述21111","researchDrug":"药物11,药物21","region":"上海区域1","partInLevel":"三甲医院1","minCaseRequire":10,"partInField":"肺癌领域1","caseCompleteness":"100","publicStatus":0,"subjectStatus":"PUBLISH","standardExcludes":[{"subjectStandardId":52,"subjectId":13,"content":"排除标准21","subjectStandardType":"EXCLUDE"},{"subjectStandardId":51,"subjectId":13,"content":"排除标准11","subjectStandardType":"EXCLUDE"},{"subjectStandardId":48,"subjectId":13,"content":"排除标准21","subjectStandardType":"EXCLUDE"},{"subjectStandardId":47,"subjectId":13,"content":"排除标准11","subjectStandardType":"EXCLUDE"}],"standardIncludes":[{"subjectStandardId":50,"subjectId":13,"content":"纳入标准21","subjectStandardType":"INCLUDE"},{"subjectStandardId":49,"subjectId":13,"content":"纳入标准11","subjectStandardType":"INCLUDE"},{"subjectStandardId":46,"subjectId":13,"content":"纳入标准21","subjectStandardType":"INCLUDE"},{"subjectStandardId":45,"subjectId":13,"content":"纳入标准11","subjectStandardType":"INCLUDE"}]},"status":0}
     * @return
     */
    @RequestMapping(value = "/subjectDetail/{subjectId}", method = RequestMethod.GET)
    @ResponseBody
    public String subjectDetail(HttpServletRequest httpServletRequest, @PathVariable Integer subjectId, SubjectDetailResponse response) {
        SubjectDetailTo to = new SubjectDetailTo();
        try {
            Subject subject = subjectService.findById(subjectId);
            BeanUtils.copyProperties(subject, to);
            //获取课题标准
            List<SubjectStandard> subjectStandards = subjectStandardService.findStandardBySubjectId(subjectId);
            List<SubjectStandardTo> standardExcludes = new ArrayList<>();
            List<SubjectStandardTo> standardIncludes = new ArrayList<>();
            SubjectStandardTo subjectStandardTo = null;
            //分类课题标准
            for (SubjectStandard subjectStandard : subjectStandards) {
                subjectStandardTo = new SubjectStandardTo();
                BeanUtils.copyProperties(subjectStandard, subjectStandardTo);
                if (subjectStandard.getSubjectStandardType() == SubjectStandardType.EXCLUDE) {
                    standardExcludes.add(subjectStandardTo);
                } else {
                    standardIncludes.add(subjectStandardTo);
                }
            }

            //获取用户
            Integer userId = AccessTokenCache.getUserId(AccessTokenUtil.getAccessToken(httpServletRequest));
            if (userId != null && userId > 0) {
                //获取详CRF情
                if (userId.equals(subject.getUserId())) {
                    if (subject.getSubjectStatus() == SubjectStatus.HAVE_PRICE
                            || subject.getSubjectStatus() == SubjectStatus.PRICE_AGREE
                            || subject.getSubjectStatus() == SubjectStatus.PRICE_REFUSE
                            || subject.getSubjectStatus() == SubjectStatus.WAIT_CHECK
                            || subject.getSubjectStatus() == SubjectStatus.HAVE_CHECK
                            || subject.getSubjectStatus() == SubjectStatus.COMPLETE) {
                        Crf crf = crfService.findBySubjectId(subject.getSubjectId());
                        to.setCrfTo(new MyBeanUtils<Crf, CrfTo>().copyBean(crf, CrfTo.class));
                    }
                }
            }
            //查询审核失败原因
            if (subject.getSubjectStatus() == SubjectStatus.AUDIT_FAIL) {
                SubjectMessage sm = subjectMessageService.findMessage(subjectId, SubjectMessageType.AUDIT_FAIL);
                if (sm != null) {
                    to.setAuditMessage(sm.getMessage());
                }
            }

            //设置是否是拥有者
            setSubjectDetailOwn(httpServletRequest,to);
            to.setStandardExcludes(standardExcludes);
            to.setStandardIncludes(standardIncludes);
            response.setSubjectQueryTo(to);
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
        //设置返回结果
        return response.getSuccessResponse();
    }

    //判断文件是否存在
    private void haveFile(String subjectToken) throws ServiceException{
        File file = new File(uploadFilePath + File.separator);
        File[] files = file.listFiles();
        for(File f:files){
            String fileName = f.getName();
            //定位
            if(fileName.contains(subjectToken)){
                return;
            }
        }
        throw  new ServiceException(601,"研究者手册不存在");
    }

    /**
     * 上传本地的研究者手册，并且删除，返回url
     * @param subjectToken
     * @param subjectId
     * @return
     */
    private String uploadBrochure(String subjectToken, Integer subjectId) throws ServiceException{
        File file = new File(uploadFilePath + File.separator);
        File[] files = file.listFiles();
        for(File f:files){
            String fileName = f.getName();
            //定位
            if(fileName.contains(subjectToken)){
                //改名
                fileName.replace(subjectToken,subjectId+"");
                f.renameTo(new File(uploadFilePath + File.separator + fileName));
                //上传到阿里云
                String url = new UploadUtils().uploadPicture(f, fileName);
                //删除
                f.delete();
                return url;
            }
        }
        throw  new ServiceException(602,"上传失败");
    }
}
