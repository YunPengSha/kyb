package net.sinyoo.cooperation.comm.model;

import net.sinyoo.cooperation.comm.base.BaseDomain;
import net.sinyoo.cooperation.core.emnu.SubjectPartInStatus;
import net.sinyoo.cooperation.core.emnu.SubjectPartInType;

import java.util.Date;

/**
 * 课题参与人  邀请和申请都存储在此对象
 * 创建用户:     wangHui
 * 创建时间:     2017-03-16
 * Created by IntelliJ IDEA.
 */
public class SubjectPartIn extends BaseDomain{
    private Integer partInId;

    private Integer userId;

    private Integer subjectId;

    private String subjectName;

    private Integer subjectUserId;

    private Integer caseNumberShare;

    private SubjectPartInStatus subjectPartInStatus;

    private SubjectPartInType subjectPartInType;

    private Date createTime;

    public SubjectPartIn(){}

    public Integer getPartInId() {
        return partInId;
    }

    public void setPartInId(Integer partInId) {
        this.partInId = partInId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName == null ? null : subjectName.trim();
    }

    public Integer getSubjectUserId() {
        return subjectUserId;
    }

    public void setSubjectUserId(Integer subjectUserId) {
        this.subjectUserId = subjectUserId;
    }

    public Integer getCaseNumberShare() {
        return caseNumberShare;
    }

    public void setCaseNumberShare(Integer caseNumberShare) {
        this.caseNumberShare = caseNumberShare;
    }

    public SubjectPartInStatus getSubjectPartInStatus() {
        return subjectPartInStatus;
    }

    public void setSubjectPartInStatus(SubjectPartInStatus subjectPartInStatus) {
        this.subjectPartInStatus = subjectPartInStatus;
    }

    public SubjectPartInType getSubjectPartInType() {
        return subjectPartInType;
    }

    public void setSubjectPartInType(SubjectPartInType subjectPartInType) {
        this.subjectPartInType = subjectPartInType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}