package net.sinyoo.cooperation.web.backstage.request;

import net.sinyoo.cooperation.core.api.ApiRequest;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * 需要删除用户的id列表
 * Created by yunpengsha on 2017/4/21.
 */
public class ListRequest extends ApiRequest{

    private List<Integer> userIds;

    public ListRequest(){

    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }
}
