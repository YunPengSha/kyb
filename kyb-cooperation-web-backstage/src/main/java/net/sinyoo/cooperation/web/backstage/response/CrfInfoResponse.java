package net.sinyoo.cooperation.web.backstage.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.backstage.to.CrfInfoTo;

import java.util.List;

/**
 *
 * Created by yunpengsha on 2017/4/5.
 */
public class CrfInfoResponse extends ApiResponse{

    @SerializedName("data")
    private List<CrfInfoTo> crfList;

    private int totalSize;

    public CrfInfoResponse(){

    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<CrfInfoTo> getCrfList() {
        return crfList;
    }

    public void setCrfList(List<CrfInfoTo> crfList) {
        this.crfList = crfList;
    }

}
