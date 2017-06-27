package net.sinyoo.cooperation.web.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.to.query.AllUserInfoTo;

/**
 * 医生信息返回
 * 创建用户:     wangHui
 * 创建时间:     2017-03-20
 * Created by IntelliJ IDEA.
 */
public class UserDoctorInfoResponse extends ApiResponse{

    @SerializedName("data")
    private AllUserInfoTo allUserInfoTo;

   public UserDoctorInfoResponse(){

   }

    public AllUserInfoTo getAllUserInfoTo() {
        return allUserInfoTo;
    }

    public void setAllUserInfoTo(AllUserInfoTo allUserInfoTo) {
        this.allUserInfoTo = allUserInfoTo;
    }
}
