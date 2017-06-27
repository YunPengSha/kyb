package net.sinyoo.cooperation.web.backstage.request;

import net.sinyoo.cooperation.comm.model.Subject;
import net.sinyoo.cooperation.core.annotation.ApiField;
import net.sinyoo.cooperation.core.annotation.NotEmpty;
import net.sinyoo.cooperation.core.annotation.NotNull;
import net.sinyoo.cooperation.core.api.ApiRequest;
import net.sinyoo.cooperation.core.emnu.SubjectStatus;

/**
 * 管理员 点击通过或不通过
 * Created by yunpengsha on 2017/4/1.
 */
public class SubjectAuditRequest extends ApiRequest{
    @ApiField
    @NotNull(message = "课题id不能空")
    private int subjectId;

    private SubjectStatus type;

    @ApiField
    @NotEmpty(message = "原因不能为空")
    private String content;

    public SubjectAuditRequest(){

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public SubjectStatus getType() {
        return type;
    }

    public void setType(SubjectStatus type) {
        this.type = type;
    }
}
