package net.sinyoo.cooperation.web.to;

import net.sinyoo.cooperation.core.emnu.UserType;

import java.io.Serializable;
import java.util.Date;

/**
 * 上传文件返回
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/14
 * Time: 下午12:45
 */
public class UploadFileTo implements Serializable{


    private String imageUrl;

    public UploadFileTo(){

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
