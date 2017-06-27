package net.sinyoo.cooperation.web.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.to.SubjectTo;

/**
 * 用户添加课题返回
 * 创建用户:     wangHui
 * 创建时间:     2017-03-14
 * Created by IntelliJ IDEA.
 */
public class SubjectAddResponse extends ApiResponse {

    @SerializedName("data")
    private SubjectTo subjectTo;


    public SubjectAddResponse() {
    }

    public SubjectTo getSubjectTo() {
        return subjectTo;
    }

    public void setSubjectTo(SubjectTo subjectTo) {
        this.subjectTo = subjectTo;
    }
}
