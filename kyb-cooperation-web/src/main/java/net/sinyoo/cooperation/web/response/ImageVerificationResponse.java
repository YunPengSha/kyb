package net.sinyoo.cooperation.web.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.to.ImageVerificationTo;

/**
 * 用户图片验证码返回
 * Created by yunpengsha on 2017/3/26.
 */
public class ImageVerificationResponse extends ApiResponse{

    @SerializedName("data")
    private ImageVerificationTo imageVerificationTo;

    public ImageVerificationResponse(){

    }

    public ImageVerificationTo getImageVerificationTo() {
        return imageVerificationTo;
    }

    public void setImageVerificationTo(ImageVerificationTo imageVerificationTo) {
        this.imageVerificationTo = imageVerificationTo;
    }
}
