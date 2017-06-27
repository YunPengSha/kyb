package net.sinyoo.cooperation.web.request;

import net.sinyoo.cooperation.comm.model.DoctorOutpatientTime;
import net.sinyoo.cooperation.core.annotation.ApiField;
import net.sinyoo.cooperation.core.annotation.NotEmpty;
import net.sinyoo.cooperation.core.annotation.NotNull;
import net.sinyoo.cooperation.core.api.ApiRequest;

import java.util.List;

/**
 * 修改用户信息请求
 * 创建用户:     wangHui
 * 创建时间:     2017-03-22
 * Created by IntelliJ IDEA.
 */
public class UserInfoUpdateRequest extends ApiRequest {
    @ApiField
    @NotNull(message = "用户id不能为空")
    private Integer userId;
    @ApiField
    @NotEmpty(message = "姓名不能为空")
    private String userName;
    /**
     * 0-男 1-女
     */
    @ApiField
    private Integer gender;
    @ApiField
    @NotEmpty(message = "职称不能为空")
    private String title;

    /**
     * 个人荣誉，如**会长
     */
    @ApiField
    private String personalHonor;
    @ApiField
    @NotEmpty(message = "所属医院不能为空")
    private String hospital;
    @ApiField
    /**
     * 门诊时间
     */
    private List<DoctorOutpatientTime> doctorOutpatientTimes;

    /**
     * 临床领域
     */
    @ApiField
    private String clinicalField;
    /**
     * 科室
     */
    @ApiField
    private String department;
    @ApiField
    private Integer numberOfBeds;
    /**
     * 病例数
     */
    @ApiField
    private Integer caseNumber;
    /**
     * 病例病种分布
     */
    @ApiField
    private String caseDistribution;
    /**
     * 基因检测占比
     */
    @ApiField
//    @RangeInteger(message = "基因检测比0-100", max = 100)
    private Integer geneDetectionRatio = 0;

    /**
     * 医师介绍
     */
    private String doctorDetails;

    public UserInfoUpdateRequest() {

    }

    public String getDoctorDetails() {
        return doctorDetails;
    }

    public void setDoctorDetails(String doctorDetails) {
        this.doctorDetails = doctorDetails;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPersonalHonor() {
        return personalHonor;
    }

    public void setPersonalHonor(String personalHonor) {
        this.personalHonor = personalHonor;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
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
        this.department = department;
    }

    public String getClinicalField() {
        return clinicalField;
    }

    public void setClinicalField(String clinicalField) {
        this.clinicalField = clinicalField;
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
        this.caseDistribution = caseDistribution;
    }

    public Integer getGeneDetectionRatio() {
        return geneDetectionRatio;
    }

    public void setGeneDetectionRatio(Integer geneDetectionRatio) {
        this.geneDetectionRatio = geneDetectionRatio;
    }
}
