package net.sinyoo.cooperation.web.request;

import net.sinyoo.cooperation.core.api.ApiRequest;

/**     
 * 获取用户信息
 * 创建用户:     wangHui
 * 创建时间:     2017-03-24
 * Created by IntelliJ IDEA.
 */ 
public class UserInfoGetRequest extends ApiRequest {


    private Integer userId;

    public UserInfoGetRequest(){}


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
