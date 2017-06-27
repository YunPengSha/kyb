package net.sinyoo.cooperation.web.backstage.to;

import net.sinyoo.cooperation.comm.base.BaseDomain;
import net.sinyoo.cooperation.comm.model.DoctorOutpatientTime;
import net.sinyoo.cooperation.core.emnu.UserType;

import java.util.Date;
import java.util.List;

/**
 * 用户信息返回
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/14
 * Time: 下午12:45
 */
public class AllUserInfoTo extends BaseDomain{

    private Integer userId;

//    private String phone;

//    private String password;

    private String userName;

    private String imageUrl;

    private Integer gender;

    private UserType userType;

    private Integer userStatus;

    private Date createTime;

    private Date modifyTime;

    private Integer doctorId;

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
    private List<DoctorOutpatientTime> doctorOutpatientTimes;
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


    private Integer departmentId;

    /**
     * 主任医师数量
     */
    private Integer chiefPhysicianNumber;

    /**
     * 副主任医师数量
     */
    private Integer deputyChiefPhysicianNumber;

    /**
     * 主治医生数量
     */
    private Integer attendingDoctorNumber;


    /**
     * 科室介绍
     */
    private String departmentDetails;

    private Integer drugCompanyId;

    /**
     * 药企名称
     */
    private String company;

    /**
     * 地址
     */
    private String address;

    /**
     * 网站
     */
    private String companyUrl;
    /**
     * 公司介绍
     */
    private String companyDetails;

    public AllUserInfoTo() {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
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

    public String getOfficeTelephone() {
        return officeTelephone;
    }

    public void setOfficeTelephone(String officeTelephone) {
        this.officeTelephone = officeTelephone;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getChiefPhysicianNumber() {
        return chiefPhysicianNumber;
    }

    public void setChiefPhysicianNumber(Integer chiefPhysicianNumber) {
        this.chiefPhysicianNumber = chiefPhysicianNumber;
    }

    public Integer getDeputyChiefPhysicianNumber() {
        return deputyChiefPhysicianNumber;
    }

    public void setDeputyChiefPhysicianNumber(Integer deputyChiefPhysicianNumber) {
        this.deputyChiefPhysicianNumber = deputyChiefPhysicianNumber;
    }

    public Integer getAttendingDoctorNumber() {
        return attendingDoctorNumber;
    }

    public void setAttendingDoctorNumber(Integer attendingDoctorNumber) {
        this.attendingDoctorNumber = attendingDoctorNumber;
    }

    public String getDepartmentDetails() {
        return departmentDetails;
    }

    public void setDepartmentDetails(String departmentDetails) {
        this.departmentDetails = departmentDetails;
    }

    public Integer getDrugCompanyId() {
        return drugCompanyId;
    }

    public void setDrugCompanyId(Integer drugCompanyId) {
        this.drugCompanyId = drugCompanyId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public String getCompanyDetails() {
        return companyDetails;
    }

    public void setCompanyDetails(String companyDetails) {
        this.companyDetails = companyDetails;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }
}
