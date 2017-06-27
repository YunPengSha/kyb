package net.sinyoo.cooperation.service.domain;

import net.sinyoo.cooperation.comm.base.BaseDomain;
/**
 * 医生用户的信息
 * 创建用户:     wangHui
 * 创建时间:     2017-03-16
 * Created by IntelliJ IDEA.
 */
public class UserInfoDoctorDo extends BaseDomain{
    private Integer doctorId;

    private Integer userId;
    /**
     * 职称
     */
    private String title;
    /**
     * 个人荣誉，如**会长
     */
    private String personalHonor;

    /**
     * 所属医院
     */
    private String hospital;
    /**
     * 门诊时间
     */
//    private String outpatientTime;
    /**
     * 科室
     */
    private String department;
    /**
     * 临床领域
     */
    private String clinicalField;
    /**
     * 病床数
     */
    private Integer numberOfBeds;

    /**
     * 病例数
     */
    private Integer caseNumber;
    /**
     * 病例病种分布
     */
    private String caseDistribution;
    /**
     * 基因检测占比
     */
    private Integer geneDetectionRatio;

    /**
     * 科室电话
     */
    private String officeTelephone;

    /**
     * 医师介绍
     */
    private String doctorDetails;

    public String getDoctorDetails() {
        return doctorDetails;
    }

    public void setDoctorDetails(String doctorDetails) {
        this.doctorDetails = doctorDetails;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPersonalHonor() {
        return personalHonor;
    }

    public void setPersonalHonor(String personalHonor) {
        this.personalHonor = personalHonor == null ? null : personalHonor.trim();
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital == null ? null : hospital.trim();
    }

//    public String getOutpatientTime() {
//        return outpatientTime;
//    }
//
//    public void setOutpatientTime(String outpatientTime) {
//        this.outpatientTime = outpatientTime == null ? null : outpatientTime.trim();
//    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getClinicalField() {
        return clinicalField;
    }

    public void setClinicalField(String clinicalField) {
        this.clinicalField = clinicalField == null ? null : clinicalField.trim();
    }

    public Integer getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(Integer numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public Integer getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(Integer caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getCaseDistribution() {
        return caseDistribution;
    }

    public void setCaseDistribution(String caseDistribution) {
        this.caseDistribution = caseDistribution == null ? null : caseDistribution.trim();
    }

    public Integer getGeneDetectionRatio() {
        return geneDetectionRatio;
    }

    public void setGeneDetectionRatio(Integer geneDetectionRatio) {
        this.geneDetectionRatio = geneDetectionRatio;
    }

    public String getOfficeTelephone() {
        return officeTelephone;
    }

    public void setOfficeTelephone(String officeTelephone) {
        this.officeTelephone = officeTelephone;
    }
}