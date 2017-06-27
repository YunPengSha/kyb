package net.sinyoo.cooperation.web.request;

import net.sinyoo.cooperation.core.annotation.ApiField;
import net.sinyoo.cooperation.core.annotation.NotEmpty;
import net.sinyoo.cooperation.core.annotation.NotNull;
import net.sinyoo.cooperation.core.api.ApiRequest;
import net.sinyoo.cooperation.core.emnu.SubjectNature;

import java.util.List;

/**
 *
 * 创建课题请求model
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/17
 * Time: 上午10:44
 */
public class SubjectAddRequest extends ApiRequest {

    private Integer userId;

    private Integer subjectId;

    @ApiField
    @NotEmpty(message = "课题标题不能为空")
    private String researchTitle;

    @ApiField
    @NotEmpty(message = "请输入课题描述")
    private String researchObjective;


    /**
     * 参与级别
     */
    @ApiField
    @NotEmpty( message = "请输入参与级别")
    private String partInLevel;

    /**
     * 最小样本需求
     */
    @ApiField
    @NotNull( message = "最小样本需求")
    private Integer minCaseRequire;

    /**
     * 参与者领域
     */
    @ApiField
    @NotEmpty( message = "参与者领域")
    private String partInField;

    /**
     *病例完整度
     */
    private String caseCompleteness;

    /**
     * 0-公开  1-不公开
     */
    @ApiField
    @NotNull( message = "请选择是否公开课题")
    private Integer publicStatus;

    /**
     * 课题性质
     */
    @ApiField
    @NotNull( message = "请选择课题性质")
    private SubjectNature subjectNature;

    //扩展字段
    @ApiField
    @NotNull(message = "纳入标准不能为空")
    private List<String> subjectStandardIns;//纳入标准
    @ApiField
    @NotNull(message = "排除标准不能为空")
    private List<String> subjectStandardOuts;//排除标准

    private List<String> doctorIds;//邀请的医生列表

    /**
     * 区域
     */
    @ApiField
    @NotNull(message = "区域不能为空")
    private List<String> regions;

    /**
     * 研究药物
     */
    @ApiField
    @NotNull(message = "研究药物不能为空")
    private List<String> researchDrugs;

    /**
     * 研究者手册token
     */
//    @ApiField
//    @NotEmpty(message = "研究者手册token不能为空")
//    private String token;

    public  SubjectAddRequest(){}

//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }

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

    public List<String> getResearchDrugs() {
        return researchDrugs;
    }

    public void setResearchDrugs(List<String> researchDrugs) {
        this.researchDrugs = researchDrugs;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
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

    public List<String> getDoctorIds() {
        return doctorIds;
    }

    public void setDoctorIds(List<String> doctorIds) {
        this.doctorIds = doctorIds;
    }

    public SubjectNature getSubjectNature() {
        return subjectNature;
    }

    public void setSubjectNature(SubjectNature subjectNature) {
        this.subjectNature = subjectNature;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }
}
