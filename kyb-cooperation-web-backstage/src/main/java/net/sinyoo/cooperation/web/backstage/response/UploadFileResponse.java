package net.sinyoo.cooperation.web.backstage.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.backstage.to.UploadFileTo;

/**
 * 上传文件返回数据
 * 创建用户:     wangHui
 * 创建时间:     2017-03-14
 * Created by IntelliJ IDEA.
 */
public class UploadFileResponse extends ApiResponse {

    @SerializedName("data")
    private UploadFileTo uploadFileTo;


    public UploadFileResponse() {
    }

    public UploadFileTo getUploadFileTo() {
        return uploadFileTo;
    }

    public void setUploadFileTo(UploadFileTo uploadFileTo) {
        this.uploadFileTo = uploadFileTo;
    }
}
