package net.sinyoo.cooperation.web.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.to.query.AllUserInfoTo;

/**
 * 用户登录返回
 * 创建用户:     wangHui
 * 创建时间:     2017-03-14
 * Created by IntelliJ IDEA.
 */
public class UserLoginResponse extends ApiResponse {

    @SerializedName("data")
    private AllUserInfoTo allUserInfoTo;

    private String accessToken;


    public UserLoginResponse() {
    }

    public AllUserInfoTo getAllUserInfoTo() {
        return allUserInfoTo;
    }

    public void setAllUserInfoTo(AllUserInfoTo allUserInfoTo) {
        this.allUserInfoTo = allUserInfoTo;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
