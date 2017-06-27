package net.sinyoo.cooperation.web.request;

import net.sinyoo.cooperation.core.annotation.ApiField;
import net.sinyoo.cooperation.core.annotation.IntegerInit0;
import net.sinyoo.cooperation.core.api.ApiRequest;

/**
 * 获取首页列表
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/17
 * Time: 上午10:44
 */
public class HomeLinkListRequest extends ApiRequest {


    @ApiField
    @IntegerInit0
    private Integer pages;

    @ApiField
    @IntegerInit0
    private Integer totalSize;



    public HomeLinkListRequest() {
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
