package net.sinyoo.cooperation.web.request;

import net.sinyoo.cooperation.core.annotation.ApiField;
import net.sinyoo.cooperation.core.annotation.NotEmpty;
import net.sinyoo.cooperation.core.api.ApiRequest;

/**
 * 搜索医生列表
 * 创建用户:     wangHui
 * 创建时间:     2017-03-21
 * Created by IntelliJ IDEA.
 */
public class UserDoctorSearchRequest extends ApiRequest {
    @ApiField
    @NotEmpty(message = "请输入搜索条件")
    private String searchKey;


    public UserDoctorSearchRequest() {
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
}
