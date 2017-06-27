package net.sinyoo.cooperation.web.request;

import net.sinyoo.cooperation.core.annotation.ApiField;
import net.sinyoo.cooperation.core.annotation.IntegerInit0;
import net.sinyoo.cooperation.core.api.ApiRequest;

/**
 * 获取课题消息列表
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/29
 * Time: 上午17:44
 */
public class SubjectMessageListRequest extends ApiRequest {

    @ApiField
    @IntegerInit0
    private Integer userId;

    @ApiField
    @IntegerInit0
    private Integer pages;

    @ApiField
    @IntegerInit0
    private Integer totalSize;

    private Integer pageSize;

    public SubjectMessageListRequest() {
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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


}
