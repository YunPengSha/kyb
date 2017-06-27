package net.sinyoo.cooperation.web.backstage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import net.sinyoo.cooperation.comm.service.CrfService;
import net.sinyoo.cooperation.core.emnu.CrfStatus;
import net.sinyoo.cooperation.core.exception.ServiceException;
import net.sinyoo.cooperation.web.backstage.response.CrfCountResponse;
import net.sinyoo.cooperation.web.backstage.response.NullDataResponse;
import net.sinyoo.cooperation.web.backstage.to.CrfCountTo;
import org.springframework.web.bind.annotation.*;

/**
 * crf报价
 * Created by yunpengsha on 2017/4/6.
 */
@RestController
@RequestMapping("/crf")
public class CrfController {

    @Reference
    private CrfService crfService;

    @ResponseBody
    @RequestMapping(value = "/givePrice/{subjectId}/{price}",method = RequestMethod.GET)
    public String CrfPrice(@PathVariable("subjectId")int subjectId, @PathVariable("price")Double price, NullDataResponse response){
        try {
            crfService.addPrice(subjectId,price);
        } catch (ServiceException e) {
            return response.getErrorResponse(e);
        }
        return response.getSuccessResponse();
    }

    /**
     * 得到三个课题列表的数量
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCrfCount",method = RequestMethod.GET)
    public String getCrfCount(CrfCountResponse response){

        CrfCountTo crfCountTo = new CrfCountTo();
        crfCountTo.setHavePriceCount(crfService.findTotalSize(CrfStatus.HAVE_PRICE));
        crfCountTo.setAgreePrice(crfService.findTotalSize(CrfStatus.PRICE_AGREE));
        crfCountTo.setWaitPriceCount(crfService.findTotalSize(CrfStatus.WAIT_PRICE));
        response.setCrfCountTo(crfCountTo);
        return response.getSuccessResponse();

    }

}
