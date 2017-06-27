package net.sinyoo.cooperation.comm.model;

import net.sinyoo.cooperation.comm.base.BaseDomain;
import net.sinyoo.cooperation.core.emnu.SmsCodeType;

/**
 * 手机验证码对象
 * 创建用户:     wangHui
 * 创建时间:     2017-03-16
 * Created by IntelliJ IDEA.
 */
public class SmsCode extends BaseDomain{
    private Integer smsCodeId;

    private Integer refId;

    private String smsCode;

    private SmsCodeType smsCodeType;

    private Long expireTime;

    private Integer smsCodeStatus;

    public SmsCode() {
    }

    public Integer getSmsCodeId() {
        return smsCodeId;
    }

    public void setSmsCodeId(Integer smsCodeId) {
        this.smsCodeId = smsCodeId;
    }

    public Integer getRefId() {
        return refId;
    }

    public void setRefId(Integer refId) {
        this.refId = refId;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode == null ? null : smsCode.trim();
    }

    public SmsCodeType getSmsCodeType() {
        return smsCodeType;
    }

    public void setSmsCodeType(SmsCodeType smsCodeType) {
        this.smsCodeType = smsCodeType;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getSmsCodeStatus() {
        return smsCodeStatus;
    }

    public void setSmsCodeStatus(Integer smsCodeStatus) {
        this.smsCodeStatus = smsCodeStatus;
    }
}