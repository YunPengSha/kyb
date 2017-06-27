package net.sinyoo.cooperation.web.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.to.query.AllUserInfoTo;

import java.util.List;

/**
 * 搜索医生列表返回
 * Created by yunpengsha on 2017/4/17.
 */
public class DoctorListResponse extends ApiResponse{

    @SerializedName("data")
    private List<AllUserInfoTo> allUserInfoTo;

    private Integer totalSize;

    private Integer page;

    public DoctorListResponse(){

    }

    public List<AllUserInfoTo> getAllUserInfoTo() {
        return allUserInfoTo;
    }

    public void setAllUserInfoTo(List<AllUserInfoTo> allUserInfoTo) {
        this.allUserInfoTo = allUserInfoTo;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
