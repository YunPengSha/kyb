package net.sinyoo.cooperation.web.backstage.to;

import net.sinyoo.cooperation.comm.model.User;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.core.emnu.SubjectNature;
import net.sinyoo.cooperation.core.emnu.SubjectStatus;

/**
 * 返回课题信息和课题提出者信息
 * Created by yunpengsha on 2017/4/1.
 */
public class SubjectInfoTo extends ApiResponse{
    private Integer subjectId;

    private Integer userId;

    private String researchTitle;

    private String researchObjective;

    private String researchDrug;

    private String region;

    private String partInLevel;

    private Integer minCaseRequire;

    private String partInField;

    private String caseCompleteness;

    private Integer publicStatus;

    private SubjectNature subjectNature;

    private SubjectStatus subjectStatus;

    private User user;

    private String failMessage;

    public SubjectInfoTo(){

    }

    public String getFailMessage() {
        return failMessage;
    }

    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getResearchTitle() {
        return researchTitle;
    }

    public void setResearchTitle(String researchTitle) {
        this.researchTitle = researchTitle;
    }

    public String getResearchObjective() {
        return researchObjective;
    }

    public void setResearchObjective(String researchObjective) {
        this.researchObjective = researchObjective;
    }

    public String getResearchDrug() {
        return researchDrug;
    }

    public void setResearchDrug(String researchDrug) {
        this.researchDrug = researchDrug;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPartInLevel() {
        return partInLevel;
    }

    public void setPartInLevel(String partInLevel) {
        this.partInLevel = partInLevel;
    }

    public Integer getMinCaseRequire() {
        return minCaseRequire;
    }

    public void setMinCaseRequire(Integer minCaseRequire) {
        this.minCaseRequire = minCaseRequire;
    }

    public String getPartInField() {
        return partInField;
    }

    public void setPartInField(String partInField) {
        this.partInField = partInField;
    }

    public String getCaseCompleteness() {
        return caseCompleteness;
    }

    public void setCaseCompleteness(String caseCompleteness) {
        this.caseCompleteness = caseCompleteness;
    }

    public Integer getPublicStatus() {
        return publicStatus;
    }

    public void setPublicStatus(Integer publicStatus) {
        this.publicStatus = publicStatus;
    }

    public SubjectNature getSubjectNature() {
        return subjectNature;
    }

    public void setSubjectNature(SubjectNature subjectNature) {
        this.subjectNature = subjectNature;
    }

    public SubjectStatus getSubjectStatus() {
        return subjectStatus;
    }

    public void setSubjectStatus(SubjectStatus subjectStatus) {
        this.subjectStatus = subjectStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
