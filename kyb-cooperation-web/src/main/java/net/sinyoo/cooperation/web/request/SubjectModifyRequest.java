package net.sinyoo.cooperation.web.request;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.annotation.ApiField;
import net.sinyoo.cooperation.core.annotation.NotEmpty;
import net.sinyoo.cooperation.core.annotation.NotNull;
import net.sinyoo.cooperation.core.api.ApiRequest;

import java.util.List;

/**
 *
 * 修改课题请求model
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/17
 * Time: 上午10:44
 */
public class SubjectModifyRequest extends ApiRequest {


    private Integer subjectId;

    @ApiField
    private String researchTitle;
    @ApiField
    private String researchObjective;
    @ApiField
    private String partInLevel;
    @ApiField
    private Integer minCaseRequire;
    @ApiField
    private String partInField;
    @ApiField
    private String caseCompleteness;
    @ApiField
    private Integer publicStatus;


    //扩展字段
    @ApiField
    private List<String> deleteSubjectStandardIds;//删除标准
    @ApiField
    private List<String> subjectStandardIns;//纳入标准
    @ApiField
    private List<String> subjectStandardOuts;//排除标准
    /**
     * 区域
     */
    @ApiField
    private List<String> regions;

    /**
     * 研究药物
     */
    private List<String> researchDrugs;


    public SubjectModifyRequest(){}

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
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

    public List<String> getSubjectStandardIns() {
        return subjectStandardIns;
    }

    public void setSubjectStandardIns(List<String> subjectStandardIns) {
        this.subjectStandardIns = subjectStandardIns;
    }

    public List<String> getSubjectStandardOuts() {
        return subjectStandardOuts;
    }

    public void setSubjectStandardOuts(List<String> subjectStandardOuts) {
        this.subjectStandardOuts = subjectStandardOuts;
    }

    public List<String> getDeleteSubjectStandardIds() {
        return deleteSubjectStandardIds;
    }

    public void setDeleteSubjectStandardIds(List<String> deleteSubjectStandardIds) {
        this.deleteSubjectStandardIds = deleteSubjectStandardIds;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public List<String> getResearchDrugs() {
        return researchDrugs;
    }

    public void setResearchDrugs(List<String> researchDrugs) {
        this.researchDrugs = researchDrugs;
    }
}
