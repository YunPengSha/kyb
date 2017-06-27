package net.sinyoo.cooperation.web.backstage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import net.sinyoo.cooperation.comm.model.Crf;
import net.sinyoo.cooperation.comm.model.Subject;
import net.sinyoo.cooperation.comm.model.User;
import net.sinyoo.cooperation.comm.service.CrfService;
import net.sinyoo.cooperation.comm.service.SubjectService;
import net.sinyoo.cooperation.comm.service.UserService;
import net.sinyoo.cooperation.core.emnu.CrfStatus;
import net.sinyoo.cooperation.core.exception.ServiceException;
import net.sinyoo.cooperation.core.util.MyBeanUtils;
import net.sinyoo.cooperation.web.backstage.response.CrfInfoResponse;
import net.sinyoo.cooperation.web.backstage.to.CrfInfoTo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取crf列表
 * Created by yunpengsha on 2017/4/5.
 */
@RestController
@RequestMapping("/crfList")
public class CrfListController {

    @Reference
    private CrfService crfService;

    @Reference
    private SubjectService subjectService;

    @Reference
    private UserService userService;

    /**
     * 获取未报价的列表
     * @param page
     * @param pageSize
     * @param response
     * @return
     */
    @RequestMapping(value = "/waitPrice/{page}/{pageSize}",method = RequestMethod.GET)
    @ResponseBody
    public String WaitPriceList(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, CrfInfoResponse response){
        if( page < 0 || pageSize <= 0 ){
            return response.getErrorResponse(new ServiceException(601,"参数错误"));
        }
        List<Crf> list = crfService.findWaitPriceList(page, pageSize);
        int totalSize = crfService.findTotalSize(CrfStatus.WAIT_PRICE);
        try {
            addInfo(list,response);
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
        response.setTotalSize(totalSize);
        return  response.getSuccessResponse();
    }

    /**
     * 获得已报价列表
     * @param page
     * @param pageSize
     * @param response
     * @return
     */
    @RequestMapping(value = "/havePrice/{page}/{pageSize}",method = RequestMethod.GET)
    @ResponseBody
    public String HavePriceList(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, CrfInfoResponse response){
        if( page < 0 || pageSize < 0 ){
            return response.getErrorResponse(new ServiceException(601,"参数错误"));
        }
        List<Crf> list = crfService.findHavePriceList(page, pageSize);
        int totalSize = crfService.findTotalSize(CrfStatus.HAVE_PRICE);
        try {
            addInfo(list,response);
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
        response.setTotalSize(totalSize);
        return  response.getSuccessResponse();
    }

    @RequestMapping(value = "/agreePrice/{page}/{pageSize}",method = RequestMethod.GET)
    @ResponseBody
    public String AgreePriceList(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, CrfInfoResponse response){
        if( page < 0 || pageSize < 0 ){
            return response.getErrorResponse(new ServiceException(601,"参数错误"));
        }
        List<Crf> list = crfService.findAgreePriceList(page, pageSize);
        int totalSize = crfService.findTotalSize(CrfStatus.PRICE_AGREE);
        try {
            addInfo(list,response);
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
        response.setTotalSize(totalSize);
        return  response.getSuccessResponse();
    }

    //获取课题名称，医生名字
    private void addInfo(List<Crf> list, CrfInfoResponse response) throws ServiceException {
//        List<CrfInfoTo> crfList = response.getCrfList();
        List<CrfInfoTo> crfList = new ArrayList<>();
        for(Crf crf : list){
            int subjectId = crf.getSubjectId();
            try {
                Subject subject = subjectService.findById(subjectId);
                User user = userService.getById(subject.getUserId());
                CrfInfoTo crfInfoTo = new MyBeanUtils<Crf,CrfInfoTo>().copyBean(crf,CrfInfoTo.class);
                crfInfoTo.setSubjectName(subject.getResearchTitle());
                crfInfoTo.setUserId(user.getUserId());
                crfList.add(crfInfoTo);
            } catch (ServiceException e) {
                throw e;
            }
        }
        response.setCrfList(crfList);
    }

}
