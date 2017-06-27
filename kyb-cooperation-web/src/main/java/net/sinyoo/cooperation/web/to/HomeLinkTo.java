package net.sinyoo.cooperation.web.to;

import net.sinyoo.cooperation.comm.base.BaseDomain;
import net.sinyoo.cooperation.core.emnu.HomeLinkType;

import java.util.Date;

/**
 * 首页连接
 * 创建用户:     wangHui
 * 创建时间:     2017-03-16
 * Created by IntelliJ IDEA.
 */
public class HomeLinkTo extends BaseDomain{
    private Integer linkId;

    private String userName;

    private String imageUrl;

    private String linkUrl;

//    private Integer refId;

    private HomeLinkType linkType;

    private Integer sortNumber;

    private Date createTime;

    private String hospital;

    private String clinicalField;

    private String title;

    public HomeLinkTo() {
    }

    public Integer getLinkId() {
        return linkId;
    }

    public void setLinkId(Integer linkId) {
        this.linkId = linkId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl == null ? null : linkUrl.trim();
    }

//    public Integer getRefId() {
//        return refId;
//    }
//
//    public void setRefId(Integer refId) {
//        this.refId = refId;
//    }

    public HomeLinkType getLinkType() {
        return linkType;
    }

    public void setLinkType(HomeLinkType linkType) {
        this.linkType = linkType;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getClinicalField() {
        return clinicalField;
    }

    public void setClinicalField(String clinicalField) {
        this.clinicalField = clinicalField;
    }
}