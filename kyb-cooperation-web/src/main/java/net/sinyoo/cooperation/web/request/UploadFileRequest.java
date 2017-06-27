package net.sinyoo.cooperation.web.request;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiRequest;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.to.UploadFileTo;

/**
 * 上传文件请求数据
 * 创建用户:     wangHui
 * 创建时间:     2017-03-14
 * Created by IntelliJ IDEA.
 */
public class UploadFileRequest extends ApiRequest {

    private Integer userId;


    /**
     * 课题编号
     */
    private Integer subjectId;

    /**
     * 0-头像
     * 1-上传crf
     */
    //private Integer type = 0;


    public UploadFileRequest() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

//    public Integer getType() {
//        return type;
//    }
//
//    public void setType(Integer type) {
//        this.type = type;
//    }
}
