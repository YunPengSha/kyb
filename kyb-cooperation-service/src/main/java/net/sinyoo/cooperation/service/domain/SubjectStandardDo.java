package net.sinyoo.cooperation.service.domain;

import net.sinyoo.cooperation.comm.base.BaseDomain;
import net.sinyoo.cooperation.core.emnu.SubjectStandardType;
/**
 * 课题纳入标准和排除标准
 * 创建用户:     wangHui
 * 创建时间:     2017-03-16
 * Created by IntelliJ IDEA.
 */
public class SubjectStandardDo extends BaseDomain{

    private Integer subjectStandardId;

    private Integer subjectId;

    private String content;

    private SubjectStandardType subjectStandardType;

    public SubjectStandardDo() {
    }

    public SubjectStandardDo(Integer subjectId, String content, SubjectStandardType subjectStandardType) {
        this.subjectId = subjectId;
        this.content = content;
        this.subjectStandardType = subjectStandardType;
    }

    public Integer getSubjectStandardId() {
        return subjectStandardId;
    }

    public void setSubjectStandardId(Integer subjectStandardId) {
        this.subjectStandardId = subjectStandardId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public SubjectStandardType getSubjectStandardType() {
        return subjectStandardType;
    }

    public void setSubjectStandardType(SubjectStandardType subjectStandardType) {
        this.subjectStandardType = subjectStandardType;
    }
}