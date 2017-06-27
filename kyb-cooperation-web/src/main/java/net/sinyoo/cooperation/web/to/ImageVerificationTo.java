package net.sinyoo.cooperation.web.to;

import java.io.Serializable;

/**
 * 图片验证码返回对象
 * Created by yunpengsha on 2017/3/26.
 */
public class ImageVerificationTo implements Serializable{

    private String imageUrl;

    private String pictureToken;

    public ImageVerificationTo(){

    }

    public ImageVerificationTo(String pictureToken,String imageUrl) {
        this.pictureToken = pictureToken;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPictureToken() {
        return pictureToken;
    }

    public void setPictureToken(String pictureToken) {
        this.pictureToken = pictureToken;
    }
}
