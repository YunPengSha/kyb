package net.sinyoo.cooperation.web.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.to.SubjectPartInTo;

import java.util.List;

/**
 * 获取已加入课题用户的列表返回
 * 创建用户:     wangHui
 * 创建时间:     2017-04-01
 * Created by IntelliJ IDEA.
 */
public class SubjectPartInUserListResponse extends ApiResponse {

    private Integer pages;

    private Integer pageSize;

    private Integer totalSize;

    @SerializedName("data")
    private List<SubjectPartInTo> subjectPartInTos;


    public SubjectPartInUserListResponse() {
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

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<SubjectPartInTo> getSubjectPartInTos() {
        return subjectPartInTos;
    }

    public void setSubjectPartInTos(List<SubjectPartInTo> subjectPartInTos) {
        this.subjectPartInTos = subjectPartInTos;
    }
}
