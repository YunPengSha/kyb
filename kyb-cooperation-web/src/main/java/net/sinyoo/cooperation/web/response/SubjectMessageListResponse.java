package net.sinyoo.cooperation.web.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.to.SubjectMessageTo;

import java.util.List;

/**
 * 获取课题消息列表返回
 * 创建用户:     wangHui
 * 创建时间:     2017-03-14
 * Created by IntelliJ IDEA.
 */
public class SubjectMessageListResponse extends ApiResponse {

    private Integer pages;

    private Integer pageSize;

    private Integer totalSize;

    @SerializedName("data")
    private List<SubjectMessageTo> subjectMessageTos;


    public SubjectMessageListResponse() {
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public List<SubjectMessageTo> getSubjectMessageTos() {
        return subjectMessageTos;
    }

    public void setSubjectMessageTos(List<SubjectMessageTo> subjectMessageTos) {
        this.subjectMessageTos = subjectMessageTos;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
