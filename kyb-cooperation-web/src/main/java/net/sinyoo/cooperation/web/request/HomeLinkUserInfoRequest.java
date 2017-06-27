package net.sinyoo.cooperation.web.request;

import net.sinyoo.cooperation.core.api.ApiRequest;

/**     
 * 首页获取用户信息
 * 创建用户:     wangHui
 * 创建时间:     2017-03-24
 * Created by IntelliJ IDEA.
 */ 
public class HomeLinkUserInfoRequest extends ApiRequest {

    /**
     *  如果获取首页推荐栏的医生详情，则传该值
     */
    private Integer homeLinkId;


    public HomeLinkUserInfoRequest(){}


    public Integer getHomeLinkId() {
        return homeLinkId;
    }

    public void setHomeLinkId(Integer homeLinkId) {
        this.homeLinkId = homeLinkId;
    }

}
