package net.sinyoo.cooperation.web.request;

import net.sinyoo.cooperation.core.annotation.ApiField;
import net.sinyoo.cooperation.core.annotation.NotEmpty;
import net.sinyoo.cooperation.core.api.ApiRequest;
import net.sinyoo.cooperation.core.emnu.SubjectNature;
import net.sinyoo.cooperation.core.emnu.SubjectStatus;

import java.util.List;

/**
 * 课题审核请求model
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/29
 * Time: 下午18:44
 */
public class SubjectAuditRequest extends ApiRequest {

    private Integer subjectId;

    private String content;

    private SubjectStatus subjectStatus;

    public SubjectAuditRequest() {
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SubjectStatus getSubjectStatus() {
        return subjectStatus;
    }

    public void setSubjectStatus(SubjectStatus subjectStatus) {
        this.subjectStatus = subjectStatus;
    }
}
