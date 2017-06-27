package net.sinyoo.cooperation.web.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.to.query.SubjectDetailTo;

/**
 * 获取课题详情返回
 * 创建用户:     wangHui
 * 创建时间:     2017-03-14
 * Created by IntelliJ IDEA.
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
