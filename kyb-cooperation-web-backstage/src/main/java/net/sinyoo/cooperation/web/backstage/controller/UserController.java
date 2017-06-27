package net.sinyoo.cooperation.web.backstage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import net.sinyoo.cooperation.comm.model.User;
import net.sinyoo.cooperation.comm.model.query.UserDoctorQuery;
import net.sinyoo.cooperation.comm.service.UserService;
import net.sinyoo.cooperation.core.exception.ApiException;
import net.sinyoo.cooperation.core.exception.ServiceException;
import net.sinyoo.cooperation.core.util.MyBeanUtils;
import net.sinyoo.cooperation.core.util.StringUtils;
import net.sinyoo.cooperation.web.backstage.cache.AccessTokenCache;
import net.sinyoo.cooperation.web.backstage.cache.PictureTokenCache;
import net.sinyoo.cooperation.web.backstage.request.CheckLoginRequest;
import net.sinyoo.cooperation.web.backstage.request.UserLoginRequest;
import net.sinyoo.cooperation.web.backstage.response.NullDataResponse;
import net.sinyoo.cooperation.web.backstage.response.UserInfoGetResponse;
import net.sinyoo.cooperation.web.backstage.response.UserLoginResponse;
import net.sinyoo.cooperation.web.backstage.to.UserDoctorTo;
import net.sinyoo.cooperation.web.backstage.to.UserLoginTo;
import net.sinyoo.cooperation.web.backstage.utils.AccessTokenUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by yunpengsha on 2017/4/1.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    /**
     * 管理员用户登录
     * @param httpServletResponse
     * @param request
     * @param response
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST,consumes = "application/json")
    @ResponseBody
    public String login(HttpServletResponse httpServletResponse, @RequestBody UserLoginRequest request, UserLoginResponse response,HttpSession httpSession){

        try {
            request.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }

        //校验图片验证码
        if (!(StringUtils.isEmpty(request.getPictureToken()) && StringUtils.isEmpty(request.getPictureCode()))) {
            if (PictureTokenCache.hasAccessToken(request.getPictureToken())) {
                if (!request.getPictureCode().equalsIgnoreCase(PictureTokenCache.getPCode(request.getPictureToken()))) {
                    return response.getErrorResponse(new ServiceException(603, "验证码错误"));
                }
            } else {
                return response.getErrorResponse(new ServiceException(604, "验证码过时，重新输入"));
            }
        }

        User user ;

        try {
            user = userService.adminLogin(request.getUsername(), request.getPassword());
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }

/*        if (user.getType() != 1){
            return response.getErrorResponse(new ApiException(501,"您没有权限"));
        }*/

        String accessToken = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        AccessTokenCache.addCache(accessToken,user.getUserId());
        UserLoginTo userLoginTo = new UserLoginTo(user.getUserId(),user.getUserName(),accessToken);
        response.setUserLoginTo(userLoginTo);
        AccessTokenUtil.setAccessToken(httpServletResponse, accessToken, user.getUserId());
        return response.getSuccessResponse();

    }


    /**
     *  已经弃用  获取医生用户信息
     * <p>
     * 请求地址: http://api.kyb.me:30001/user/userInfo
     * </p>
     * <p>
     * header accessToken:   623751D996C14763B8D6C9158442C07B
     * </p>
     *
     * @param userId            {}
     * @param response           <p>
     *                           {"data":{"userId":1,"phone":"13585955979","userName":"wanghui","gender":1,"userType":"DOCTOR","userStatus":0,"title":"主任医师","personalHonor":"个人荣誉","hospital":"上海六院","doctorOutpatientTimes":[{"outpatientTimeId":1,"userId":1,"weekName":"周一","startTime":"09:00","endTime":"00:00"}],"department":"手术外科","clinicalField":"骨科","numberOfBeds":30,"caseNumber":500,"caseDistribution":"10","geneDetectionRatio":"10"},"status":0}
     *                           </p>
     * @return
     */
    @RequestMapping(value = "/doctorInfo/{doctorId}", method = RequestMethod.GET)
    @ResponseBody
    @Deprecated
    public String userInfo(@PathVariable("doctorId") int userId, UserInfoGetResponse response) {

         UserDoctorQuery userDoctorQuery = userService.findDoctorInfo(userId);
         UserDoctorTo userDoctorTo = new MyBeanUtils<UserDoctorQuery, UserDoctorTo>()
                .copyBean(userDoctorQuery, UserDoctorTo.class);
        // response.setUserDoctorTo(userDoctorTo);
         return response.getSuccessResponse();
    }

    /**
     * 检测是否登录
     * @param request
     * @param nullDataResponse
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkLogin",method = RequestMethod.POST,consumes = "application/json")
    public String checkLogin(@RequestBody CheckLoginRequest request, NullDataResponse nullDataResponse){

        if( request.getAccessToken() == null ){
            return nullDataResponse.getErrorResponse( new ServiceException(601,"token不能为空") );
        }

        Integer userId = AccessTokenCache.getUserId(request.getAccessToken());
        if( userId == null ){
            return nullDataResponse.getErrorResponse( new ServiceException(602,"用户未登录") );
        }
        return nullDataResponse.getSuccessResponse();
    }


}
