package net.sinyoo.cooperation.web.backstage.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.backstage.to.SubjectTo;

import java.util.List;

/**
 * Created by yunpengsha on 2017/4/6.
 */
public class SubjectListResponse extends ApiResponse {

    private Integer pages;

    private Integer totalSize;

    @SerializedName("data")
    private List<SubjectTo> subjectTos;


    public SubjectListResponse() {
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
