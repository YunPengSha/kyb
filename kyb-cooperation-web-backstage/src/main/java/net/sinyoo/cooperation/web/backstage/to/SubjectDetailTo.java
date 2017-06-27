package net.sinyoo.cooperation.web.backstage.to;

import net.sinyoo.cooperation.comm.base.BaseDomain;
import net.sinyoo.cooperation.core.emnu.SubjectStatus;

import java.util.List;

/**
 * 课题详情查询返回   包含课题信息和纳入和排除标准
 */
public class SubjectDetailTo extends BaseDomain {
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

    private SubjectStatus subjectStatus;

    private List<SubjectStandardTo> standardExcludes;

    private List<SubjectStandardTo> standardIncludes;

    private String auditMessage;


    private CrfTo crfTo;

    public SubjectDetailTo() {
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

    public SubjectStatus getSubjectStatus() {
        return subjectStatus;
    }

    public void setSubjectStatus(SubjectStatus subjectStatus) {
        this.subjectStatus = subjectStatus;
    }

    public List<SubjectStandardTo> getStandardExcludes() {
        return standardExcludes;
    }

    public void setStandardExcludes(List<SubjectStandardTo> standardExcludes) {
        this.standardExcludes = standardExcludes;
    }

    public List<SubjectStandardTo> getStandardIncludes() {
        return standardIncludes;
    }

    public void setStandardIncludes(List<SubjectStandardTo> standardIncludes) {
        this.standardIncludes = standardIncludes;
    }

    public String getAuditMessage() {
        return auditMessage;
    }

    public void setAuditMessage(String auditMessage) {
        this.auditMessage = auditMessage;
    }

    public CrfTo getCrfTo() {
        return crfTo;
    }

    public void setCrfTo(CrfTo crfTo) {
        this.crfTo = crfTo;
    }
}
