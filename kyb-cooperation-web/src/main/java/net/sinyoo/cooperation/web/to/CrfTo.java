package net.sinyoo.cooperation.web.to;

import net.sinyoo.cooperation.comm.base.BaseDomain;
import net.sinyoo.cooperation.core.emnu.CrfStatus;
import net.sinyoo.cooperation.core.emnu.SubjectStatus;

import java.util.Date;

/**
 * crf报表
 * Created by yunpengsha on 2017/4/5.
 */
public class CrfTo extends BaseDomain{

    private int crfId;

    private int subjectId;

    private double price;

    private CrfStatus crfStatus;

    private Date agreeTime;

    public CrfTo(){

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
}
