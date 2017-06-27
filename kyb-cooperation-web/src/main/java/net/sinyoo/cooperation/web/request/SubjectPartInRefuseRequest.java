package net.sinyoo.cooperation.web.request;

import net.sinyoo.cooperation.core.annotation.ApiField;
import net.sinyoo.cooperation.core.annotation.NotEmpty;
import net.sinyoo.cooperation.core.annotation.NotNull;
import net.sinyoo.cooperation.core.api.ApiRequest;
import net.sinyoo.cooperation.core.emnu.SubjectNature;

import java.util.List;

/**
 * 拒绝别人的课题申请请求model
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/17
 * Time: 上午10:44
 */
public class SubjectPartInRefuseRequest extends ApiRequest {

    private Integer userId;

    @ApiField
    @NotNull(message = "请选择拒绝的申请")
    private Integer partInId;

    @ApiField
    @NotEmpty(message = "请输入拒绝理由")
    private String content;


    public SubjectPartInRefuseRequest() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPartInId() {
        return partInId;
    }

    public void setPartInId(Integer partInId) {
        this.partInId = partInId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
