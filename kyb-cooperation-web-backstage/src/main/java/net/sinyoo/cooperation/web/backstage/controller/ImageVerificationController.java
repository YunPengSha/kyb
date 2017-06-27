package net.sinyoo.cooperation.web.backstage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import net.sinyoo.cooperation.comm.service.UserRegisterService;
import net.sinyoo.cooperation.core.util.VerifyCodeUtils;
import net.sinyoo.cooperation.web.backstage.cache.PictureTokenCache;
import net.sinyoo.cooperation.web.backstage.response.ImageVerificationResponse;
import net.sinyoo.cooperation.web.backstage.to.ImageVerificationTo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * 图片验证处理器
 * Created by yunpengsha on 2017/3/23.
 */

@RequestMapping("/imageVerification")
@RestController
public class ImageVerificationController {

    @Reference
    private UserRegisterService userRegisterService;

    /**
     * 验证码图片存放base路径
     */
    @Value("${verify.filepath}")
    public String verifyFilePath;

    /**
     * 验证码图片访问base路径
     */
    @Value("${verify.base.url}")
    public String verifyBaseUrl;

    /**
     * http://localhost:30001/imageVerification/getPicture
     * 获取图片验证码 返回token和图片url
     *
     * @param response {"data":{"imageUrl":"http://120.27.239.67/verifyCode/20170327/4AA7152424F84270985FC56179227D11.jpg","pictureToken":"4AA7152424F84270985FC56179227D11"},"status":0}
     * @return
     */
    @RequestMapping(value = "/getPicture", method = RequestMethod.GET)
    @ResponseBody
    public String getPicture(ImageVerificationResponse response) {
        String pictureToken = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        return getVerifyCode(pictureToken,response);
    }

    /**
     * http://localhost:30001/imageVerification/getPictureAgain/12345
     * 图像验证码 用户点击换一张 调用本接口
     *
     * @param pictureToken 为第一次返回时的token
     * @param response     {"data":{"imageUrl":"http://120.27.239.67/verifyCode/20170327/4AA7152424F84270985FC56179227D11.jpg","pictureToken":"4AA7152424F84270985FC56179227D11"},"status":0}
     * @return
     */
    @RequestMapping(value = "/getPictureAgain/{pictureToken}", method = RequestMethod.GET)
    @ResponseBody
    public String getPictureAgain(@PathVariable String pictureToken, ImageVerificationResponse response) {
        //缓存不存在 需要重新生成token
        if (!PictureTokenCache.hasAccessToken(pictureToken)){
            pictureToken = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        }
        return getVerifyCode(pictureToken,response);
    }

    /**
     * 获取验证码图片   返回客户端验证码的访问地址和token
     *
     * @param pictureToken
     * @param response
     * @return
     */
    private String getVerifyCode(String pictureToken, ImageVerificationResponse response){
        //获取验证码
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);

        //生成验证码
        String picturePath = new VerifyCodeUtils().getPicture(verifyFilePath, pictureToken, verifyCode);

        //添加验证码缓存
        PictureTokenCache.addCache(pictureToken, verifyCode);

        //生成访问路径
        String imageUrl = verifyBaseUrl + picturePath;

        ImageVerificationTo imageVerificationTo = new ImageVerificationTo(pictureToken, imageUrl);

        response.setImageVerificationTo(imageVerificationTo);

        return response.getSuccessResponse();
    }

    /*    *//**
     * 校验图片验证码 http://localhost:30001/imageVerification/validatePictureCode/17602114754/DTLG
     * 校验通过后向用户发送手机短信验证码
     *
     * @param phone            手机号
     * @param code             用户输入的图片验证码    success: {"status":0}
     * @param nullDataResponse error   {"status":1,"errorCode":"1000","errorMessage":"未知错误","subCode":"601","subMessage":"没找到对应验证码"}
     * @return
     *//*
    @RequestMapping(value = "/validatePictureCode/{phone}/{code}", method = RequestMethod.GET)
    @ResponseBody
    public String validatePictureCode(@PathVariable(value = "phone") String phone, @PathVariable(value = "code") String code, NullDataResponse nullDataResponse) {

        if (!PictureTokenCache.hasAccessToken(phone)) {
            return nullDataResponse.getUnKnowErrorResponse("601", "没找到对应验证码");
        }

        //忽视大小写
        if (!PictureTokenCache.getPCode(phone).equalsIgnoreCase(code)) {
            return nullDataResponse.getUnKnowErrorResponse("602", "输入验证码错误");
        }

        //校验是否已经获取过验证码
        if (!SmsCache.hasCache("getSmsCode", phone)) {
            return nullDataResponse.getErrorResponse(ResponseErrorCode.CHECK_ERROR, ResponseErrorCode.CHECK_ERROR_MESSAGE, "501", "获取验证码太快,请稍后重试");
        }

        try {
            userRegisterService.sendRegisterMessage(phone);
            //添加进缓存
            SmsCache.addCache("getSmsCode", phone);
        } catch (ServiceException e) {
            return nullDataResponse.getErrorResponse(e);
        }

        return nullDataResponse.getSuccessResponse();
    }*/
}
