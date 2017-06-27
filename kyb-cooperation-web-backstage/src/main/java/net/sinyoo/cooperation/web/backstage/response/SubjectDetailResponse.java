package net.sinyoo.cooperation.web.backstage.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.backstage.to.SubjectDetailTo;

/**
 * 获取课题详情返回
 */
public class SubjectDetailResponse extends ApiResponse {

    @SerializedName("data")
    private SubjectDetailTo subjectQueryTo;


    public SubjectDetailResponse() {
    }

    public SubjectDetailTo getSubjectQueryTo() {
        return subjectQueryTo;
    }

    public void setSubjectQueryTo(SubjectDetailTo subjectQueryTo) {
        this.subjectQueryTo = subjectQueryTo;
    }
}
