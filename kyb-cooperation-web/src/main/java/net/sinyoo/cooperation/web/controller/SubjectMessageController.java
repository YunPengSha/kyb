package net.sinyoo.cooperation.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import net.sinyoo.cooperation.comm.model.SubjectMessage;
import net.sinyoo.cooperation.comm.service.SubjectMessageService;
import net.sinyoo.cooperation.core.exception.ApiException;
import net.sinyoo.cooperation.core.util.MyBeanUtils;
import net.sinyoo.cooperation.web.request.SubjectMessageListRequest;
import net.sinyoo.cooperation.web.response.SubjectMessageListResponse;
import net.sinyoo.cooperation.web.to.SubjectMessageTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by yunpengsha on 2017/3/29.
 */
@RestController
@RequestMapping("/subjectMessage")
public class SubjectMessageController {


    @Reference
    private SubjectMessageService subjectMessageService;


    /**
     * 获取科研课题列表
     * <p>http://api.kyb.me:30001/subjectMessage/list</p>
     * <p>header accessToken   623751D996C14763B8D6C9158442C07B</p>
     *
     * @param request <p>{"pages":0,"totalSize":0,"userId":0}</p>
     * @param response           <p></p>
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String list(@RequestBody SubjectMessageListRequest request, SubjectMessageListResponse response) {
        try {
            request.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }

        Integer pageSize = request.getPageSize();
        if( pageSize == null ){
            pageSize = 20;
        }
        List<SubjectMessage> subjectMessages = subjectMessageService.findMessages(request.getUserId(),request.getPages(),pageSize);
        //设置返回结果
        response.setSubjectMessageTos(new MyBeanUtils<SubjectMessage,SubjectMessageTo>().copyList(subjectMessages,SubjectMessageTo.class));
        //设置页数
        if (request.getTotalSize() <= 0) {
            request.setTotalSize(subjectMessageService.findMessageCount(request.getUserId()));
        }
        response.setPages(request.getPages());
        response.setPageSize(20);
        response.setTotalSize(request.getTotalSize());
        return response.getSuccessResponse();
    }



}
