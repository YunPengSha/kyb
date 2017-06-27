package net.sinyoo.cooperation.web.backstage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import net.sinyoo.cooperation.comm.model.User;
import net.sinyoo.cooperation.comm.service.CrfService;
import net.sinyoo.cooperation.comm.service.SubjectService;
import net.sinyoo.cooperation.comm.service.UserService;
import net.sinyoo.cooperation.core.base.ResponseErrorCode;
import net.sinyoo.cooperation.core.exception.ApiException;
import net.sinyoo.cooperation.core.util.StringUtils;
import net.sinyoo.cooperation.core.util.UploadUtils;
import net.sinyoo.cooperation.web.backstage.response.UploadFileResponse;
import net.sinyoo.cooperation.web.backstage.to.UploadFileTo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;

/**
 * 文件上传控制器
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/24
 * Time: 上午9:55
 */
@RestController
@RequestMapping("/upload")
public class UploadFileController {

    // 文件允许格式
    private String[] allowFiles = {".png", ".jpg", ".jpeg"};
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

    /**
     * 管理员用户添加头像
     * @param response
     * @param muFile
     * @return
     */
    @RequestMapping(value = "/uploadUserImage", method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseBody
    public String uploadUserImage(UploadFileResponse response, @RequestParam("file") MultipartFile muFile) {

        String fileName = "";
        if (muFile != null) {

            //验证文件名支持
            String originalName = muFile.getOriginalFilename().substring(muFile.getOriginalFilename().lastIndexOf("."));
            if (!this.checkFileType(originalName)) {
                return response.getErrorResponse(new ApiException(502, "不支持的文件类型"));
            }
            //验证文件大小
            if (muFile.getSize() > this.maxSize * 1024) {
                return response.getErrorResponse(new ApiException(503, "文件大小超过限制,头像文件不能大于4M"));
            }

            fileName = "admin_add"+"_head_image_url"+ UUID.randomUUID().toString().replace("-", "").toUpperCase() + originalName ;

            try {
                //更新用户数据
                String url = uploadFile(fileName, muFile);

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
     * 文件类型判断
     *
     * @param fileName
     * @return
     */
    private boolean checkFileType(String fileName) {
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
