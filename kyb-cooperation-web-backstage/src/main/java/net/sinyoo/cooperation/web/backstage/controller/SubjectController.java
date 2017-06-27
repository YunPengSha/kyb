package net.sinyoo.cooperation.web.backstage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import net.sinyoo.cooperation.comm.model.Crf;
import net.sinyoo.cooperation.comm.model.Subject;
import net.sinyoo.cooperation.comm.model.SubjectMessage;
import net.sinyoo.cooperation.comm.model.SubjectStandard;
import net.sinyoo.cooperation.comm.service.CrfService;
import net.sinyoo.cooperation.comm.service.SubjectMessageService;
import net.sinyoo.cooperation.comm.service.SubjectService;
import net.sinyoo.cooperation.comm.service.SubjectStandardService;
import net.sinyoo.cooperation.core.emnu.SubjectMessageType;
import net.sinyoo.cooperation.core.emnu.SubjectStandardType;
import net.sinyoo.cooperation.core.emnu.SubjectStatus;
import net.sinyoo.cooperation.core.exception.ApiException;
import net.sinyoo.cooperation.core.exception.ServiceException;
import net.sinyoo.cooperation.core.util.MyBeanUtils;
import net.sinyoo.cooperation.web.backstage.request.SubjectAuditRequest;
import net.sinyoo.cooperation.web.backstage.response.NullDataResponse;
import net.sinyoo.cooperation.web.backstage.response.SubjectCountResponse;
import net.sinyoo.cooperation.web.backstage.response.SubjectDetailResponse;
import net.sinyoo.cooperation.web.backstage.to.CrfTo;
import net.sinyoo.cooperation.web.backstage.to.SubjectCountTo;
import net.sinyoo.cooperation.web.backstage.to.SubjectDetailTo;
import net.sinyoo.cooperation.web.backstage.to.SubjectStandardTo;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 课题 通过 不通过 controller
 * Created by yunpengsha on 2017/4/1.
 */
@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Reference
    private SubjectService subjectService;

    @Reference
    private SubjectStandardService subjectStandardService;

    @Reference
    private CrfService crfService;

    @Reference
    private SubjectMessageService subjectMessageService;

    /**
     * 审核课题，同意
     * @param subjectId
     * @param response
     * @return
     */
    @RequestMapping(value = "/audit/agree/{subjectId}",method = RequestMethod.GET)
    @ResponseBody
    public String agreeSubject(@PathVariable("subjectId") int subjectId, NullDataResponse response){
        try {
            subjectService.auditSubject(subjectId,SubjectStatus.PUBLISH,null);
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
        return response.getSuccessResponse();
    }

    /**
     * 审核课题，拒绝
     * @param subjectAuditRequest
     * @param response
     * @return
     */
    @RequestMapping(value = "/audit/refuse",method = RequestMethod.POST,consumes = "application/json")
    @ResponseBody
    public String failSubject(@RequestBody SubjectAuditRequest subjectAuditRequest, NullDataResponse response){

        try {
            subjectAuditRequest.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }

        try {
            subjectService.auditSubject(subjectAuditRequest.getSubjectId(),SubjectStatus.AUDIT_FAIL,subjectAuditRequest.getContent());
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
        return response.getSuccessResponse();
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

                    if (subject.getSubjectStatus() == SubjectStatus.HAVE_PRICE
                            || subject.getSubjectStatus() == SubjectStatus.PRICE_AGREE
                            || subject.getSubjectStatus() == SubjectStatus.PRICE_REFUSE
                            || subject.getSubjectStatus() == SubjectStatus.HAVE_CHECK
                            || subject.getSubjectStatus() == SubjectStatus.WAIT_CHECK
                            || subject.getSubjectStatus() == SubjectStatus.COMPLETE) {
                        Crf crf = crfService.findBySubjectId(subject.getSubjectId());
                        to.setCrfTo(new MyBeanUtils<Crf, CrfTo>().copyBean(crf, CrfTo.class));
                    }

            //查询审核失败原因
            if (subject.getSubjectStatus() == SubjectStatus.AUDIT_FAIL) {
                SubjectMessage sm = subjectMessageService.findMessage(subjectId, SubjectMessageType.AUDIT_FAIL);
                if (sm != null) {
                    to.setAuditMessage(sm.getMessage());
                }
            }
            to.setStandardExcludes(standardExcludes);
            to.setStandardIncludes(standardIncludes);
            response.setSubjectQueryTo(to);
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
        //设置返回结果
        return response.getSuccessResponse();
    }

    /**
     * 获得 待审核 已通过 未通过 数量
     * @param subjectCountResponse
     * @return
     */
    @RequestMapping(value = "/getSubjectCount",method = RequestMethod.GET)
    @ResponseBody
    public String subjectCount(SubjectCountResponse subjectCountResponse){

        SubjectCountTo subjectCountTo = new SubjectCountTo();

        subjectCountTo.setFailCount(subjectService.findCount(-5,null,null,SubjectStatus.AUDIT_FAIL));
        subjectCountTo.setSuccessCount(subjectService.findPassListCount());
        subjectCountTo.setWaitCount(subjectService.findCount(-5,null,null,SubjectStatus.AUDIT));

        subjectCountResponse.setSubjectCountTo(subjectCountTo);
        return subjectCountResponse.getSuccessResponse();

    }

}
