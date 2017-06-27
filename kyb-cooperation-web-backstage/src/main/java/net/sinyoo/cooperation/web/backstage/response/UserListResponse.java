package net.sinyoo.cooperation.web.backstage.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.backstage.to.AllUserInfoTo;

import java.util.List;

/**
 * Created by yunpengsha on 2017/4/19.
 */
public class UserListResponse extends ApiResponse{

    @SerializedName("data")
    private List<AllUserInfoTo> allUserInfoTos;

    private Integer totalSize;

    public UserListResponse(){}

    public List<AllUserInfoTo> getAllUserInfoTos() {
        return allUserInfoTos;
    }

    public void setAllUserInfoTos(List<AllUserInfoTo> allUserInfoTos) {
        this.allUserInfoTos = allUserInfoTos;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }
}
