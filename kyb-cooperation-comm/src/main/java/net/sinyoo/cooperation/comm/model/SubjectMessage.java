package net.sinyoo.cooperation.comm.model;

import net.sinyoo.cooperation.comm.base.BaseDomain;
import net.sinyoo.cooperation.core.emnu.SubjectMessageType;

/**
 * 课题消息
 * 创建用户:     wangHui
 * 创建时间:     2017-03-29
 * Created by IntelliJ IDEA.
 */
public class SubjectMessage extends BaseDomain {
    private Integer id;

    private Integer subjectId;

    private Integer userId;

    private String subjectName;

    private String message;

    private SubjectMessageType subjectMessageType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public SubjectMessageType getSubjectMessageType() {
        return subjectMessageType;
    }

    public void setSubjectMessageType(SubjectMessageType subjectMessageType) {
        this.subjectMessageType = subjectMessageType;
    }
}