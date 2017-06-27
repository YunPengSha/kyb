package net.sinyoo.cooperation.web.backstage.request;

import net.sinyoo.cooperation.core.annotation.ApiField;
import net.sinyoo.cooperation.core.annotation.IntegerInit0;
import net.sinyoo.cooperation.core.api.ApiRequest;
import net.sinyoo.cooperation.core.emnu.SubjectNature;

/**
 * 获取课题列表
 * Created by yunpengsha on 2017/4/6.
 */
public class SubjectListRequest extends ApiRequest {

    private Integer userId;

    @ApiField
    @IntegerInit0
    private Integer pages;

    @ApiField
    @IntegerInit0
    private Integer totalSize;

    private SubjectNature subjectNature;

    private String searchKey;


    public SubjectListRequest() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public SubjectNature getSubjectNature() {
        return subjectNature;
    }

    public void setSubjectNature(SubjectNature subjectNature) {
        this.subjectNature = subjectNature;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
}
