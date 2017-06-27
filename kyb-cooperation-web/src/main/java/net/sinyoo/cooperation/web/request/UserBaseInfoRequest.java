
package net.sinyoo.cooperation.web.request;

import net.sinyoo.cooperation.core.annotation.ApiField;
import net.sinyoo.cooperation.core.annotation.Mobile;
import net.sinyoo.cooperation.core.annotation.NotEmpty;
import net.sinyoo.cooperation.core.annotation.NotNull;
import net.sinyoo.cooperation.core.api.ApiRequest;
import net.sinyoo.cooperation.core.emnu.UserType;

/**
 * Created by yunpengsha on 2017/3/17.
 */
public class UserBaseInfoRequest extends ApiRequest{

    @ApiField
    @NotEmpty(message = "请输入电话号码")
    @Mobile(message = "手机号码不正确")
    private String phone;

    @ApiField
    @NotEmpty(message = "请输入密码")
    private String passwordOne;

    @ApiField
    @NotEmpty(message = "请输入密码")
    private String passwordTwo;

    private String password;

    @ApiField
    @NotEmpty(message = "请输入短信验证码")
    private String smsCode;

    @ApiField
    @NotNull(message = "请输入注册用户类型")
    private UserType userType;

    public UserBaseInfoRequest(){

    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordOne() {
        return passwordOne;
    }

    public void setPasswordOne(String passwordOne) {
        this.passwordOne = passwordOne;
    }

    public String getPasswordTwo() {
        return passwordTwo;
    }

    public void setPasswordTwo(String passwordTwo) {
        this.passwordTwo = passwordTwo;
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
}
