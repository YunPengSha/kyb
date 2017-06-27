package net.sinyoo.cooperation.web.controller.base;

import net.sinyoo.cooperation.core.exception.ApiException;
import net.sinyoo.cooperation.web.cache.AccessTokenCache;
import net.sinyoo.cooperation.web.utils.AccessTokenUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/4/6
 * Time: 下午1:07
 */
public class BaseController {


    /**
     * 获取缓存中的用户编号
     *
     * @param httpServletRequest
     * @throws ApiException
     */
    protected int getUserId(HttpServletRequest httpServletRequest) throws ApiException {
        //获取用户
        Integer userId = AccessTokenCache.getUserId(AccessTokenUtil.getAccessToken(httpServletRequest));
        if (userId == null || userId <= 0) {
            throw new ApiException(501, "登录信息已过期,未找到用户");
        }
        return userId;
    }
}
