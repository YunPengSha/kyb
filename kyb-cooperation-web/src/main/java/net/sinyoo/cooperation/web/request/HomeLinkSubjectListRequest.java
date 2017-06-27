package net.sinyoo.cooperation.web.request;

import net.sinyoo.cooperation.core.annotation.ApiField;
import net.sinyoo.cooperation.core.annotation.IntegerInit0;
import net.sinyoo.cooperation.core.annotation.NotEmpty;
import net.sinyoo.cooperation.core.annotation.NotNull;
import net.sinyoo.cooperation.core.api.ApiRequest;

/**
 * 首页进入的请求获取课题列表
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/17
 * Time: 上午10:44
 */
public class HomeLinkSubjectListRequest extends ApiRequest {

    private Integer homeLinkId;

    private Integer userId;

    @ApiField
    @IntegerInit0
    private Integer pages;

    @ApiField
    @IntegerInit0
    private Integer totalSize;


    public HomeLinkSubjectListRequest() {
    }

    public Integer getHomeLinkId() {
        return homeLinkId;
    }

    public void setHomeLinkId(Integer homeLinkId) {
        this.homeLinkId = homeLinkId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
