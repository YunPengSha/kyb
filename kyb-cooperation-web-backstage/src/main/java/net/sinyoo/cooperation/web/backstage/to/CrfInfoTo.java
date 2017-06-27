package net.sinyoo.cooperation.web.backstage.to;

import net.sinyoo.cooperation.comm.base.BaseDomain;

import java.util.Date;

/**
 * crf信息返回
 * Created by yunpengsha on 2017/4/5.
 */
public class CrfInfoTo extends BaseDomain{

    private int crfId;

    private int subjectId;

    private int userId;

    private String crfDownUrl;

    private double price;
    /**
     * 报价确认时间
     */
    private Date agreeTime;

    private String subjectName;

    public CrfInfoTo() {
    }

    public int getCrfId() {
        return crfId;
    }

    public void setCrfId(int crfId) {
        this.crfId = crfId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getAgreeTime() {
        return agreeTime;
    }

    public void setAgreeTime(Date agreeTime) {
        this.agreeTime = agreeTime;
    }

    public String getCrfDownUrl() {
        return crfDownUrl;
    }

    public void setCrfDownUrl(String crfDownUrl) {
        this.crfDownUrl = crfDownUrl;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
