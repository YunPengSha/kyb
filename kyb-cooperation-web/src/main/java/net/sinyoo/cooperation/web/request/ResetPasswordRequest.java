package net.sinyoo.cooperation.web.request;

import net.sinyoo.cooperation.core.annotation.ApiField;
import net.sinyoo.cooperation.core.annotation.NotEmpty;
import net.sinyoo.cooperation.core.annotation.NotNull;
import net.sinyoo.cooperation.core.api.ApiRequest;
import net.sinyoo.cooperation.core.emnu.UserType;

/**
 * Created by yunpengsha on 2017/3/31.
 */
public class ResetPasswordRequest extends ApiRequest{
    @ApiField
    @NotNull(message = "手机号不能为空")
    private String phone;
    @ApiField
    @NotEmpty(message = "短信验证码不能为空")
    private String smsCode;
    @ApiField
    @NotEmpty(message = "新密码不能为空")
    private String newPasswordOne;
    @ApiField
    @NotEmpty(message = "重复密码不能为空")
    private String newPasswordTwo;
    @ApiField
    @NotNull(message = "请选择用户类型")
    private UserType userType;

    public ResetPasswordRequest(){}

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getNewPasswordOne() {
        return newPasswordOne;
    }

    public void setNewPasswordOne(String newPasswordOne) {
        this.newPasswordOne = newPasswordOne;
    }

    public String getNewPasswordTwo() {
        return newPasswordTwo;
    }

    public void setNewPasswordTwo(String newPasswordTwo) {
        this.newPasswordTwo = newPasswordTwo;
    }
}
