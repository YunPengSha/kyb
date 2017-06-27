package net.sinyoo.cooperation.web.backstage.request;

import net.sinyoo.cooperation.core.annotation.ApiField;
import net.sinyoo.cooperation.core.annotation.IntegerInit0;
import net.sinyoo.cooperation.core.annotation.NotNull;
import net.sinyoo.cooperation.core.api.ApiRequest;

/**
 * 获取已加入课题用户的列表请求
 */
public class SubjectPartInUserListRequest extends ApiRequest {


    @ApiField
    @NotNull(message = "请选择课题")
    private Integer subjectId;

    @ApiField
    @IntegerInit0
    private Integer pages;

    @ApiField
    @IntegerInit0
    private Integer totalSize;



    public SubjectPartInUserListRequest() {
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
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
}
