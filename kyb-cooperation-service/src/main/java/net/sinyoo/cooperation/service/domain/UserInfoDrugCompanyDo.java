package net.sinyoo.cooperation.service.domain;

import net.sinyoo.cooperation.comm.base.BaseDomain;

/**
 * 药企详细信息
 */
public class UserInfoDrugCompanyDo extends BaseDomain{

    private Integer drugCompanyId;

    private Integer userId;

    /**
     * 药企名称
     */
    private String company;

    /**
     * 地址
     */
    private String address;

    private String officeTelephone;
    /**
     * 网站
     */
    private String companyUrl;
    /**
     * 公司介绍
     */
    private String companyDetails;

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