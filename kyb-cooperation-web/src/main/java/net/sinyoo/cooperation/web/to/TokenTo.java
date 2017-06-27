package net.sinyoo.cooperation.web.to;

import net.sinyoo.cooperation.comm.base.BaseDomain;

/**
 * Created by yunpengsha on 2017/4/18.
 */
public class TokenTo extends BaseDomain{

    private String token;

    public TokenTo(String token){
        this.token=token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
