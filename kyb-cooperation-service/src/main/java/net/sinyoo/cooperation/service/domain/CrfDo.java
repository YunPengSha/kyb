package net.sinyoo.cooperation.service.domain;

import net.sinyoo.cooperation.comm.base.BaseDomain;
import net.sinyoo.cooperation.core.emnu.CrfStatus;
import net.sinyoo.cooperation.core.emnu.SubjectStatus;

import java.util.Date;

/**
 * crf报表
 * Created by yunpengsha on 2017/4/5.
 */
public class CrfDo extends BaseDomain {
    private int crfId;

    private int subjectId;

    private String crfDownUrl;

    private double price;

    private CrfStatus crfStatus;

    private Date agreeTime;

    public CrfDo(){

    }

    public CrfDo(int subjectId, String crfDownUrl, double price, CrfStatus crfStatus) {
        this.subjectId = subjectId;
        this.crfDownUrl = crfDownUrl;
        this.price = price;
        this.crfStatus = crfStatus;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public CrfStatus getCrfStatus() {
        return crfStatus;
    }

    public void setCrfStatus(CrfStatus crfStatus) {
        this.crfStatus = crfStatus;
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
}
