package net.sinyoo.cooperation.web.backstage.controller;

import net.sinyoo.cooperation.web.backstage.response.NullDataResponse;
import net.sinyoo.cooperation.web.backstage.utils.AccessTokenUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户注销
 * Created by yunpengsha on 2017/4/10.
 */
@RestController
public class LoginOutController {

    /**
     * 用户注销
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logOut",method = RequestMethod.GET)
    @ResponseBody
    public String loginOut(HttpServletRequest request, NullDataResponse response){
        AccessTokenUtil.removeAccessToken(request);
        return response.getSuccessResponse();
    }

}
