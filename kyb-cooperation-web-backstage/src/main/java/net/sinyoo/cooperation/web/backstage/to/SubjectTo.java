package net.sinyoo.cooperation.web.backstage.to;

import net.sinyoo.cooperation.comm.base.BaseDomain;
import net.sinyoo.cooperation.core.emnu.SubjectNature;
import net.sinyoo.cooperation.core.emnu.SubjectStatus;

/**
 * 课题信息
 * Created by yunpengsha on 2017/4/6.
 */
public class SubjectTo extends BaseDomain {
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

    /**
     * 扩展字段  某某医生  某某药企
     */
    private String userName;


    private Boolean subjectOwn = false;


    public SubjectTo() {
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
        this.researchTitle = researchTitle == null ? null : researchTitle.trim();
    }

    public String getResearchObjective() {
        return researchObjective;
    }

    public void setResearchObjective(String researchObjective) {
        this.researchObjective = researchObjective == null ? null : researchObjective.trim();
    }

    public String getResearchDrug() {
        return researchDrug;
    }

    public void setResearchDrug(String researchDrug) {
        this.researchDrug = researchDrug == null ? null : researchDrug.trim();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    public String getPartInLevel() {
        return partInLevel;
    }

    public void setPartInLevel(String partInLevel) {
        this.partInLevel = partInLevel == null ? null : partInLevel.trim();
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
        this.partInField = partInField == null ? null : partInField.trim();
    }

    public String getCaseCompleteness() {
        return caseCompleteness;
    }

    public void setCaseCompleteness(String caseCompleteness) {
        this.caseCompleteness = caseCompleteness == null ? null : caseCompleteness.trim();
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getSubjectOwn() {
        return subjectOwn;
    }

    public void setSubjectOwn(Boolean subjectOwn) {
        this.subjectOwn = subjectOwn;
    }

}
