package net.sinyoo.cooperation.web.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.to.TokenTo;

/**
 * Created by yunpengsha on 2017/4/18.
 */
public class TokenResponse extends ApiResponse{

    @SerializedName("data")
    private TokenTo token;

    public  TokenResponse(){

    }

    public TokenTo getToken() {
        return token;
    }

    public void setToken(TokenTo token) {
        this.token = token;
    }
}
