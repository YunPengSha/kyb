package net.sinyoo.cooperation.web.backstage.request;

import net.sinyoo.cooperation.core.annotation.ApiField;
import net.sinyoo.cooperation.core.annotation.NotEmpty;
import net.sinyoo.cooperation.core.annotation.NotNull;
import net.sinyoo.cooperation.core.api.ApiRequest;
import net.sinyoo.cooperation.core.emnu.UserType;

/**
 * Created by yunpengsha on 2017/4/1.
 */
public class UserLoginRequest extends ApiRequest{
    @ApiField
    @NotEmpty(message = "请输入用户名")
    private String username;

    @ApiField
    @NotEmpty(message = "请输入密码")
    private String password;

    private UserType userType;


    /**
     * 图片验证码
     */
    @ApiField
    private String pictureCode;

    /**
     * 图片token
     */
    @ApiField
    private String pictureToken;

    public UserLoginRequest(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPictureCode() {
        return pictureCode;
    }

    public void setPictureCode(String pictureCode) {
        this.pictureCode = pictureCode;
    }

    public String getPictureToken() {
        return pictureToken;
    }

    public void setPictureToken(String pictureToken) {
        this.pictureToken = pictureToken;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "UserLoginRequest{" +
                "username='" + username + '\'' +
                ", userType=" + userType +
                ", pictureCode='" + pictureCode + '\'' +
                ", pictureToken='" + pictureToken + '\'' +
                '}';
    }
}
