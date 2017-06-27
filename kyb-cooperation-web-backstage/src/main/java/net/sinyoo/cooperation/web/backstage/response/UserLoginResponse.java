package net.sinyoo.cooperation.web.backstage.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.backstage.request.UserLoginRequest;
import net.sinyoo.cooperation.web.backstage.to.UserLoginTo;

/**
 * Created by yunpengsha on 2017/4/1.
 */
public class UserLoginResponse extends ApiResponse{

    @SerializedName("data")
    private UserLoginTo userLoginTo;

    public UserLoginResponse(){

    }

    public UserLoginTo getUserLoginTo() {
        return userLoginTo;
    }

    public void setUserLoginTo(UserLoginTo userLoginTo) {
        this.userLoginTo = userLoginTo;
    }
}
