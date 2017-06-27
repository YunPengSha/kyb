package net.sinyoo.cooperation.web.request;

import net.sinyoo.cooperation.core.annotation.ApiField;
import net.sinyoo.cooperation.core.annotation.NotEmpty;
import net.sinyoo.cooperation.core.annotation.NotNull;
import net.sinyoo.cooperation.core.api.ApiRequest;

/**
 * 药企添加修改信息
 * Created by yunpengsha on 2017/4/14.
 */
public class DrugCompanyAddRequest extends ApiRequest{

    private Integer drugCompanyId;

    private Integer userId;

    /**
     * 药企名称
     */
    @ApiField
    @NotEmpty(message = "公司名字不能为空")
    private String company;

    /**
     * 地址
     */
    @ApiField
    @NotEmpty(message = "地址不能为空")
    private String address;

    @ApiField
    @NotEmpty(message = "电话不能为空")
    private String officeTelephone;
    /**
     * 网站
     */
    @ApiField
    @NotEmpty(message = "公司网站不能为空")
    private String companyUrl;
    /**
     * 公司介绍
     */
    @ApiField
    @NotEmpty(message = "公司介绍不能为空")
    private String companyDetails;

    /**
     * 如果为空，则表明用户没上传头像，使用默认
     */
    private String userAvatar;

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public Integer getDrugCompanyId() {
        return drugCompanyId;
    }

    public void setDrugCompanyId(Integer drugCompanyId) {
        this.drugCompanyId = drugCompanyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getOfficeTelephone() {
        return officeTelephone;
    }

    public void setOfficeTelephone(String officeTelephone) {
        this.officeTelephone = officeTelephone == null ? officeTelephone : officeTelephone.trim();
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl == null ? null : companyUrl.trim();
    }

    public String getCompanyDetails() {
        return companyDetails;
    }

    public void setCompanyDetails(String companyDetails) {
        this.companyDetails = companyDetails == null ? null : companyDetails.trim();
    }

}
