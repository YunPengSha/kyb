package net.sinyoo.cooperation.web.backstage.request;

import net.sinyoo.cooperation.core.annotation.ApiField;
import net.sinyoo.cooperation.core.annotation.NotEmpty;
import net.sinyoo.cooperation.core.annotation.NotNull;
import net.sinyoo.cooperation.core.api.ApiRequest;

/**
 * 科室注册详细信息使用
 * Created by yunpengsha on 2017/4/14.
 */
public class DepartmentInfoAddRequest extends ApiRequest{


    private Integer userId;

    private Integer departmentId;
    @ApiField
    @NotEmpty(message = "医院不能为空")
    private String hospital;
    @ApiField
    @NotEmpty(message = "科室不能为空")
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
    @ApiField
    @NotEmpty(message = "科室介绍不能为空")
    private String departmentDetails;

    /**
     * 添加用户时使用该字段
     */
    private String phone;

    public DepartmentInfoAddRequest(){}

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
}
