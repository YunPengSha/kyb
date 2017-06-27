package net.sinyoo.cooperation.web.to;

/**
 * 科室信息
 */

import net.sinyoo.cooperation.comm.base.BaseDomain;

/**
 * Created by yunpengsha on 2017/4/14.
 */
public class DepartmentTo extends BaseDomain{

    private Integer departmentId;

    private Integer userId;

    private String hospital;

    private String department;

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
     * 临床领域
     */
    private String clinicalField;

    /**
     * 科室介绍
     */
    private String departmentDetails;

    private String imageUrl;

    public DepartmentTo(){

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital == null ? null : hospital.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
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

    public String getClinicalField() {
        return clinicalField;
    }

    public void setClinicalField(String clinicalField) {
        this.clinicalField = clinicalField == null ? null : clinicalField.trim();
    }

    public String getDepartmentDetails() {
        return departmentDetails;
    }

    public void setDepartmentDetails(String departmentDetails) {
        this.departmentDetails = departmentDetails == null ? null : departmentDetails.trim();
    }

}
