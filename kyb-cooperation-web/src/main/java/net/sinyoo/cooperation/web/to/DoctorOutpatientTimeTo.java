package net.sinyoo.cooperation.web.to;

import net.sinyoo.cooperation.comm.base.BaseDomain;

/**
 * 医生的门诊时间
 * 创建用户:     wangHui
 * 创建时间:     2017-02-23
 * Created by IntelliJ IDEA.
 */
public class DoctorOutpatientTimeTo extends BaseDomain{
    private Integer outpatientTimeId;

    private Integer userId;

    private String weekName;

    private String startTime;

    private String endTime;

    public DoctorOutpatientTimeTo(){

    }

    public Integer getOutpatientTimeId() {
        return outpatientTimeId;
    }

    public void setOutpatientTimeId(Integer outpatientTimeId) {
        this.outpatientTimeId = outpatientTimeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getWeekName() {
        return weekName;
    }

    public void setWeekName(String weekName) {
        this.weekName = weekName == null ? null : weekName.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }
}