package net.sinyoo.cooperation.web.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.to.SubjectTo;

import java.util.List;

/**
 * 首页进入的 获取课题列表返回
 * 创建用户:     wangHui
 * 创建时间:     2017-03-14
 * Created by IntelliJ IDEA.
 */
public class HomeLinkSubjectListResponse extends ApiResponse {

    private Integer pages;

    private Integer totalSize;

    @SerializedName("data")
    private List<SubjectTo> subjectTos;


    public HomeLinkSubjectListResponse() {
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

    public List<SubjectTo> getSubjectTos() {
        return subjectTos;
    }

    public void setSubjectTos(List<SubjectTo> subjectTos) {
        this.subjectTos = subjectTos;
    }
}
