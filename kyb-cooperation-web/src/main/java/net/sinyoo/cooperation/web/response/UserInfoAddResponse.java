package net.sinyoo.cooperation.web.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.to.UserInfoDoctorTo;

/**
 * 用户添加详细信息返回
 * 创建用户:     wangHui
 * 创建时间:     2017-03-22
 * Created by IntelliJ IDEA.
 */
public class UserInfoAddResponse extends ApiResponse{

    @SerializedName("data")
    private UserInfoDoctorTo userInfoTo;

    public UserInfoAddResponse(){}

    public UserInfoDoctorTo getUserInfoTo() {
        return userInfoTo;
    }

    public void setUserInfoTo(UserInfoDoctorTo userInfoTo) {
        this.userInfoTo = userInfoTo;
    }
}
