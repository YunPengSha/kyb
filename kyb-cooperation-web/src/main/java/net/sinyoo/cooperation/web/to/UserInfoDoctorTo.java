package net.sinyoo.cooperation.web.to;

import net.sinyoo.cooperation.comm.base.BaseDomain;
import net.sinyoo.cooperation.comm.model.DoctorOutpatientTime;

import java.util.List;

/**
 * 医生用户的详细信息返回
 * Created by yunpengsha on 2017/3/20.
 *
 * <br>
 * modify:<br/>
 * 2017-03-25 删除 outpatientTime 门诊时间 字段<br/>
 * 2017-03-25 1.添加 doctorOutpatientTimes 门诊时间 字段;2.继承BaseDomain<br/>
 */
public class UserInfoDoctorTo extends BaseDomain{
    private Integer doctorId;

    private Integer userId;

    private String title = "";

    private String personalHonor = "无";

    private String hospital = "";


    private String department = "";

    private String clinicalField = "";

    private Integer numberOfBeds = 0;

    private Integer caseNumber = 0;

    private String caseDistribution = "";

    private Integer geneDetectionRatio = 0;

    /**
     * 科室电话
     */
    private String officeTelephone = "";

    /**
     * 门诊时间
     */
    private List<DoctorOutpatientTime> doctorOutpatientTimes;

    /**
     * 医师介绍
     */
    private String doctorDetails = "";

    public UserInfoDoctorTo(){

    }

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

    public List<DoctorOutpatientTime> getDoctorOutpatientTimes() {
        return doctorOutpatientTimes;
    }

    public void setDoctorOutpatientTimes(List<DoctorOutpatientTime> doctorOutpatientTimes) {
        this.doctorOutpatientTimes = doctorOutpatientTimes;
    }

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
