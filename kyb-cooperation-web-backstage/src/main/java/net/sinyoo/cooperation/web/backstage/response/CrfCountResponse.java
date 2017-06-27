package net.sinyoo.cooperation.web.backstage.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.backstage.to.CrfCountTo;

/**
 * Created by yunpengsha on 2017/4/10.
 */
public class CrfCountResponse extends ApiResponse{

    @SerializedName("data")
    private CrfCountTo crfCountTo;

    public CrfCountResponse(){

    }

    public CrfCountTo getCrfCountTo() {
        return crfCountTo;
    }

    public void setCrfCountTo(CrfCountTo crfCountTo) {
        this.crfCountTo = crfCountTo;
    }
}
