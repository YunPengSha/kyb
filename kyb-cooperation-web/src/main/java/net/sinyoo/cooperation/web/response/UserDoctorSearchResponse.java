package net.sinyoo.cooperation.web.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.to.query.AllUserInfoTo;

import java.util.List;

/**
 *  搜索医生列表
 * 创建用户:     wangHui
 * 创建时间:     2017-03-21
 * Created by IntelliJ IDEA.
 */
public class UserDoctorSearchResponse extends ApiResponse {

    @SerializedName("data")
    private List<AllUserInfoTo> allUserInfoTos;


    public UserDoctorSearchResponse() {
    }

    public List<AllUserInfoTo> getAllUserInfoTos() {
        return allUserInfoTos;
    }

    public void setAllUserInfoTos(List<AllUserInfoTo> allUserInfoTos) {
        this.allUserInfoTos = allUserInfoTos;
    }
}
