package net.sinyoo.cooperation.web.request;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.annotation.ApiField;
import net.sinyoo.cooperation.core.annotation.NotEmpty;
import net.sinyoo.cooperation.core.annotation.NotNull;
import net.sinyoo.cooperation.core.api.ApiRequest;
import net.sinyoo.cooperation.core.emnu.UserType;

/**
 * 用户登录请求
 * 创建用户:     wangHui
 * 创建时间:     2017-03-21
 * Created by IntelliJ IDEA.
 */
public class UserLoginRequest extends ApiRequest {

    @ApiField
    @NotEmpty(message = "请输入用户名")
    private String userName;
    @ApiField
    @NotEmpty(message = "请输入密码")
    private String password;

    @ApiField
    @NotNull(message = "请输入登录用户类型")
    private UserType userType;

    /**
     *  图片token
     */
    private String pictureToken;

    /**
     * 图片验证码
     */
    private String pictureCode;

   public UserLoginRequest(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPictureToken() {
        return pictureToken;
    }

    public void setPictureToken(String pictureToken) {
        this.pictureToken = pictureToken;
    }

    public String getPictureCode() {
        return pictureCode;
    }

    public void setPictureCode(String pictureCode) {
        this.pictureCode = pictureCode;
    }

    @Override
    public String toString() {
        return "UserLoginRequest{" +
                "userName='" + userName + '\'' +
                ", userType=" + userType +
                ", pictureToken='" + pictureToken + '\'' +
                ", pictureCode='" + pictureCode + '\'' +
                '}';
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
