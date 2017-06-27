package net.sinyoo.cooperation.web.backstage.request;

import net.sinyoo.cooperation.core.annotation.ApiField;
import net.sinyoo.cooperation.core.annotation.NotEmpty;
import net.sinyoo.cooperation.core.annotation.NotNull;
import net.sinyoo.cooperation.core.api.ApiRequest;

/**
 * 检测是否登录对象
 * Created by yunpengsha on 2017/4/10.
 */
public class CheckLoginRequest extends ApiRequest{

    private String accessToken;

    public CheckLoginRequest(){

    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
