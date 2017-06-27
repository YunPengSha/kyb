package net.sinyoo.cooperation.core.api;

import net.sinyoo.cooperation.core.base.BaseRequest;
import net.sinyoo.cooperation.core.exception.ApiException;
import net.sinyoo.cooperation.core.util.GsonUtil;

/**
 * api 请求规范类
 * 创建用户:     wangHui
 * 创建时间:     2017-03-14
 * Created by IntelliJ IDEA.
 */
public class ApiRequest extends BaseRequest {
    @Override
    public boolean checkUpdateParams() throws ApiException {
        return ApiUtils.checkUpdateParams(this);
    }

    @Override
    public void checkParams() throws ApiException {
        ApiUtils.checkParams(this);
    }

    @Override
    public String toString() {
        return GsonUtil.getGsonInstance().toJson(this);
    }
}
