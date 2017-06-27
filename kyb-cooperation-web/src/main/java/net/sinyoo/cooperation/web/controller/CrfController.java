package net.sinyoo.cooperation.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import net.sinyoo.cooperation.comm.service.CrfService;
import net.sinyoo.cooperation.core.exception.ApiException;
import net.sinyoo.cooperation.core.exception.ServiceException;
import net.sinyoo.cooperation.web.cache.AccessTokenCache;
import net.sinyoo.cooperation.web.response.NullDataResponse;
import net.sinyoo.cooperation.web.utils.AccessTokenUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;


/**
 * crf记录
 * 创建用户:     wangHui
 * 创建时间:     2017-03-21
 * Created by IntelliJ IDEA.
 */
@RestController
@RequestMapping("/crf")
public class CrfController {

    @Reference
    private CrfService crfService;


    /**
     * 同意报价
     * http://localhost:30001/crf/agree/13
     * <p>header accessToken   623751D996C14763B8D6C9158442C07B</p>
     *
     * @param response {"status":0}
     * @return
     */
    @RequestMapping(value = "/agree/{subjectId}", method = RequestMethod.PUT)
    @ResponseBody
    public String agree(HttpServletRequest httpServletRequest, @PathVariable Integer subjectId, NullDataResponse response) {
        //获取用户
        Integer userId = AccessTokenCache.getUserId(AccessTokenUtil.getAccessToken(httpServletRequest));
        if (userId == null) {
            return response.getErrorResponse(new ApiException(501, "登录信息已过期,未找到用户"));
        }

        try {
            crfService.agreeCrf(userId, subjectId);
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }

        return response.getSuccessResponse();
    }

    /**
     * 拒绝报价
     * http://localhost:30001/crf/refuse/13
     * <p>header accessToken   623751D996C14763B8D6C9158442C07B</p>
     *
     * @param response {"status":0}
     * @return
     */
    @RequestMapping(value = "/refuse/{subjectId}", method = RequestMethod.PUT)
    @ResponseBody
    public String refuse(HttpServletRequest httpServletRequest, @PathVariable Integer subjectId, NullDataResponse response) {
        //获取用户
        Integer userId = AccessTokenCache.getUserId(AccessTokenUtil.getAccessToken(httpServletRequest));
        if (userId == null) {
            return response.getErrorResponse(new ApiException(501, "登录信息已过期,未找到用户"));
        }
        try {
            crfService.refuseCrf(userId, subjectId);
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }

        return response.getSuccessResponse();
    }
}
