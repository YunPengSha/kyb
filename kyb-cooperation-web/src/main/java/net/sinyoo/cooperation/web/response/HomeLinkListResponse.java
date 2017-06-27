package net.sinyoo.cooperation.web.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.comm.model.HomeLink;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.to.HomeLinkTo;
import net.sinyoo.cooperation.web.to.SubjectTo;

import java.util.List;

/**
 * 首页数据返回
 * 创建用户:     wangHui
 * 创建时间:     2017-03-14
 * Created by IntelliJ IDEA.
 */
public class HomeLinkListResponse extends ApiResponse {

    private Integer pages;

    private Integer totalSize;

    @SerializedName("data")
    private List<HomeLinkTo> homeLinkTos;


    public HomeLinkListResponse() {
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

    public List<HomeLinkTo> getHomeLinkTos() {
        return homeLinkTos;
    }

    public void setHomeLinkTos(List<HomeLinkTo> homeLinkTos) {
        this.homeLinkTos = homeLinkTos;
    }
}
