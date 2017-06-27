package net.sinyoo.cooperation.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import net.sinyoo.cooperation.comm.model.Subject;
import net.sinyoo.cooperation.comm.model.User;
import net.sinyoo.cooperation.comm.service.CrfService;
import net.sinyoo.cooperation.comm.service.SubjectService;
import net.sinyoo.cooperation.comm.service.UserService;
import net.sinyoo.cooperation.core.base.ResponseErrorCode;
import net.sinyoo.cooperation.core.emnu.SubjectStatus;
import net.sinyoo.cooperation.core.exception.ApiException;
import net.sinyoo.cooperation.core.exception.ServiceException;
import net.sinyoo.cooperation.core.util.StringUtils;
import net.sinyoo.cooperation.core.util.UploadUtils;
import net.sinyoo.cooperation.web.cache.AccessTokenCache;
import net.sinyoo.cooperation.web.cache.UserRegisterCache;
import net.sinyoo.cooperation.web.response.TokenResponse;
import net.sinyoo.cooperation.web.response.UploadFileResponse;
import net.sinyoo.cooperation.web.to.TokenTo;
import net.sinyoo.cooperation.web.to.UploadFileTo;
import net.sinyoo.cooperation.web.utils.AccessTokenUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by yunpengsha on 2017/3/24.
 */
@RestController
@RequestMapping("/uploadFile")
public class UploadFileController {

    // 图片允许格式
    private String[] allowPictures = {".png", ".jpg", ".jpeg"};
    // 文件允许格式
    private String[] allowFiles = {".pdf", ".zip", ".rar", ".doc", ".docx", ".xlsx", ".word"};
    // 文件大小限制，单位KB
    private int maxSize = 4000;


    @Value("${upload.filepath}")
    public String uploadFilePath;

    @Reference
    private UserService userService;

    @Reference
    private SubjectService subjectService;

    @Reference
    private CrfService crfService;

    @RequestMapping(value = "/uploadUserImage", method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseBody
    public String uploadUserImage(UploadFileResponse response, @RequestParam("file") MultipartFile muFile, @RequestParam("userId") Integer userId) {

        String fileName = "";
        if (muFile != null) {

            //验证文件名支持
            String originalName = muFile.getOriginalFilename().substring(muFile.getOriginalFilename().lastIndexOf("."));
            if (!this.checkFileType(originalName,true)) {
                return response.getErrorResponse(new ApiException(502, "不支持的文件类型"));
            }
            //验证文件大小
            if (muFile.getSize() > this.maxSize * 1024) {
                return response.getErrorResponse(new ApiException(503, "文件大小超过限制,头像文件不能大于4M"));
            }
            //验证用户
            User user = userService.getById(userId);
            if (user == null || StringUtils.isEmpty(user.getPassword())) {
                return response.getErrorResponse(ResponseErrorCode.LOGIN, ResponseErrorCode.LOGIN_MESSAGE, "600", "请先登录帐号");
            }

            fileName = userId + "_head_image_url" + originalName;

            try {
                //更新用户数据
                String url = uploadFile(fileName, muFile);
                userService.modifyImageUrl(userId, url);

                //设置返回数据
                UploadFileTo uploadFileTo = new UploadFileTo();
                uploadFileTo.setImageUrl(url);
                response.setUploadFileTo(uploadFileTo);
            } catch (ApiException e) {
                return response.getErrorResponse(e);
            }
        } else {
            //没有找到上传的文件
            return response.getErrorResponse(new ApiException(501, "文件读取错误"));
        }
        return response.getSuccessResponse();
    }


    /**
     * 注册时使用
     * @param response
     * @param muFile
     * @param userId
     * @return
     */
    @RequestMapping(value = "/registerUploadUserImage", method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseBody
    public String registerUploadUserImage(UploadFileResponse response, @RequestParam("userAvatar") MultipartFile muFile, @RequestParam("userId") Integer userId) {

        String fileName = "";
        if (muFile != null) {

            //验证文件名支持
            String originalName = muFile.getOriginalFilename().substring(muFile.getOriginalFilename().lastIndexOf("."));
            if (!this.checkFileType(originalName, true)) {
                return response.getErrorResponse(new ApiException(502, "不支持的文件类型"));
            }
            //验证文件大小
            if (muFile.getSize() > this.maxSize * 1024) {
                return response.getErrorResponse(new ApiException(503, "文件大小超过限制,头像文件不能大于4M"));
            }
            //验证用户
            if( ! UserRegisterCache.hasAccessToken(userId) ){
                return response.getErrorResponse(new ApiException(504,"token 过期"));
            }

            fileName = userId + "_head_image_url" + originalName;

            try {
                //更新用户数据
                String url = uploadFile(fileName, muFile);
                userService.modifyImageUrl(userId, url);

                //设置返回数据
                UploadFileTo uploadFileTo = new UploadFileTo();
                uploadFileTo.setImageUrl(url);
                response.setUploadFileTo(uploadFileTo);
            } catch (ApiException e) {
                return response.getErrorResponse(e);
            }
        } else {
            //没有找到上传的文件
            return response.getErrorResponse(new ApiException(501, "文件读取错误"));
        }
        return response.getSuccessResponse();
    }


    @RequestMapping(value = "/uploadCrf", method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseBody
    public String uploadCrf(HttpServletRequest httpServletRequest, UploadFileResponse response, @RequestParam("file") MultipartFile muFile, @RequestParam("subjectId") Integer subjectId) {

        String fileName = "";
        if (muFile != null) {

            //验证文件名支持
            String originalName = muFile.getOriginalFilename().substring(muFile.getOriginalFilename().lastIndexOf("."));
            if (!this.checkFileType(originalName, false)) {
                return response.getErrorResponse(new ApiException(502, "不支持的文件类型"));
            }
            //验证文件大小
            if (muFile.getSize() > this.maxSize * 1024) {
                return response.getErrorResponse(new ApiException(503, "文件大小超过限制,头像文件不能大于4M"));
            }
            //验证用户  只有创建课题的人才能上传crf
            Integer userId = AccessTokenCache.getUserId(AccessTokenUtil.getAccessToken(httpServletRequest));
            if (userId == null) {
                return response.getErrorResponse(new ApiException(504, "登录信息已过期,未找到用户"));
            }
            try {
                Subject subject = subjectService.findById(subjectId);
                if (!userId.equals(subject.getUserId())) {
                    return response.getErrorResponse(new ApiException(505, "您无权操作"));
                }
                if (subject.getSubjectStatus() != SubjectStatus.PUBLISH) {
                    return response.getErrorResponse(new ApiException(506, "请不要重复上传"));
                }
            } catch (ServiceException e) {
                response.getErrorResponse(e);
            }

            fileName = subjectId + "_crf_" + subjectId + originalName;

            try {
                //更新用户数据
                String url = uploadFile(fileName, muFile);
                //TODO 修改课题,设置已上传Crf

                crfService.uploadCrf(userId, subjectId, url);

                //设置返回数据
                UploadFileTo uploadFileTo = new UploadFileTo();
                uploadFileTo.setImageUrl(url);
                response.setUploadFileTo(uploadFileTo);
            } catch (ApiException e) {
                return response.getErrorResponse(e);
            }
        } else {
            //没有找到上传的文件
            return response.getErrorResponse(new ApiException(501, "文件读取错误"));
        }
        return response.getSuccessResponse();
    }

    /**
     * 上传研究者手册
     * @param httpServletRequest
     * @param response
     * @param muFile
     * @return
     */
    @RequestMapping(value = "/uploadBruchure", method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseBody
    public String uploadBruchure(HttpServletRequest httpServletRequest, TokenResponse response, @RequestParam("file") MultipartFile muFile) {

        String fileName = "";
        if (muFile != null) {

            //验证文件名支持
            String originalName = muFile.getOriginalFilename().substring(muFile.getOriginalFilename().lastIndexOf("."));
            if (!this.checkFileType(originalName, false)) {
                return response.getErrorResponse(new ApiException(502, "不支持的文件类型"));
            }
            //验证文件大小
            if (muFile.getSize() > this.maxSize * 1024) {
                return response.getErrorResponse(new ApiException(503, "文件大小超过限制,头像文件不能大于4M"));
            }

            Integer userId = AccessTokenCache.getUserId(AccessTokenUtil.getAccessToken(httpServletRequest));
            if (userId == null) {
                return response.getErrorResponse(new ApiException(504, "登录信息已过期,未找到用户"));
            }

            fileName = "bruchure_"+ httpServletRequest.getHeader("accessToken") + originalName;

            try {


                File file = new File(uploadFilePath + File.separator + fileName);
                try {
                    if (file.exists()) {
                        file.delete();
                    }
                    boolean isSuccess = file.createNewFile();
                    if (isSuccess) {
                        muFile.transferTo(file);
                    } else {
                        //重新创建新文件失败
                        throw new ApiException(601, "文件读取错误");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new ApiException(601, "文件读取错误");
                }
            } catch (ApiException e) {
                return response.getErrorResponse(e);
            }
        } else {
            //没有找到上传的文件
            return response.getErrorResponse(new ApiException(501, "文件读取错误"));
        }
        String subjectToken = httpServletRequest.getHeader("accessToken");
        TokenTo tokenTo = new TokenTo(subjectToken);
        response.setToken(tokenTo);
        return response.getSuccessResponse();
    }


    /**
     * 上传文件到OSS
     *
     * @param fileName
     * @param muFile
     * @return
     * @throws ApiException
     */
    private String uploadFile(String fileName, MultipartFile muFile) throws ApiException {
        File file = new File(uploadFilePath + File.separator + fileName);
        try {
            if (file.exists()) {
                file.delete();
            }
            boolean isSuccess = file.createNewFile();
            if (isSuccess) {
                muFile.transferTo(file);
            } else {
                //重新创建新文件失败
                throw new ApiException(601, "文件读取错误");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiException(601, "文件读取错误");
        }

        //上传文件到OSS
        String url = new UploadUtils().uploadPicture(file, fileName);

        //删除文件
        file.delete();
        return url;
    }


    /**
     * 文件类型判断  图片和文件类型分开判断
     *
     * @param fileName
     * @param isPicture
     * @return
     */
    private boolean checkFileType(String fileName, boolean isPicture) {

        if( isPicture ){
            Iterator<String> type = Arrays.asList(this.allowPictures).iterator();
            while (type.hasNext()) {
                String ext = type.next();
                if (fileName.toLowerCase().endsWith(ext)) {
                    return true;
                }
            }
            return false;
        }
        else {
            Iterator<String> type = Arrays.asList(this.allowFiles).iterator();
            while (type.hasNext()) {
                String ext = type.next();
                if (fileName.toLowerCase().endsWith(ext)) {
                    return true;
                }
            }
            return false;
        }

    }


}
