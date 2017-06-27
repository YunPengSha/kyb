package net.sinyoo.cooperation.web.backstage.to;

import net.sinyoo.cooperation.core.api.ApiResponse;

/**
 * Created by yunpengsha on 2017/4/1.
 */
public class UserLoginTo extends ApiResponse{

    private int userId;

    private String userName;

    private String accessToken;

    public  UserLoginTo(){

    }

    public UserLoginTo(int adminId, String userName, String accessToken) {
        this.userId = adminId;
        this.userName = userName;
        this.accessToken = accessToken;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
