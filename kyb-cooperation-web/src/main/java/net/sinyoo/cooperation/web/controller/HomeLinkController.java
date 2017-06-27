package net.sinyoo.cooperation.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sinyoo.cooperation.comm.model.HomeLink;
import net.sinyoo.cooperation.comm.model.User;
import net.sinyoo.cooperation.comm.model.query.UserDoctorQuery;
import net.sinyoo.cooperation.comm.service.HomeLinkService;
import net.sinyoo.cooperation.comm.service.UserService;
import net.sinyoo.cooperation.core.emnu.HomeLinkType;
import net.sinyoo.cooperation.core.exception.ApiException;
import net.sinyoo.cooperation.core.util.MyBeanUtils;
import net.sinyoo.cooperation.web.request.HomeLinkListRequest;
import net.sinyoo.cooperation.web.response.HomeLinkListResponse;
import net.sinyoo.cooperation.web.to.HomeLinkTo;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by yunpengsha on 2017/3/21.
 */
@RestController
@RequestMapping("/homeLink")
public class HomeLinkController {

    @Reference
    private HomeLinkService homeLinkService;


    @Reference
    private UserService userService;


    /**
     * 获取banner数据
     * @param response
     * @return
     */
    @RequestMapping(value = "/banner", method = RequestMethod.GET)
    @ResponseBody
    public String banner(HomeLinkListResponse response) {
        response.setHomeLinkTos(new MyBeanUtils<HomeLink, HomeLinkTo>().copyList(homeLinkService.findHomeLinks(HomeLinkType.BANNER, 0, 6, null), HomeLinkTo.class));
        return response.getSuccessResponse();
    }

    /**
     *  http://localhost:30001/homeLink/drugCompany
     * 获取药企
     * @param request   {"pages":"0","totalSize":"6"}
     * @param response {"pages":0,"totalSize":6,"data":[{"linkId":8,"title":"合作医生药企","imageUrl":"https://timgsa.baidu.com/timg?image\u0026quality\u003d80\u0026size\u003db9999_10000\u0026sec\u003d1491529246\u0026di\u003d85cf41cbb0650b6b70a9b24e48bc1a79\u0026imgtype\u003djpg\u0026er\u003d1\u0026src\u003dhttp%3A%2F%2Fscimg.jb51.net%2Fallimg%2F160404%2F14-160404103545501.jpg","linkUrl":"https://timgsa.baidu.com/timg?image\u0026quality\u003d80\u0026size\u003db9999_10000\u0026sec\u003d1491529246\u0026di\u003d85cf41cbb0650b6b70a9b24e48bc1a79\u0026imgtype\u003djpg\u0026er\u003d1\u0026src\u003dhttp%3A%2F%2Fscimg.jb51.net%2Fallimg%2F160404%2F14-160404103545501.jpg","linkType":"DRUG_COMPANY","sortNumber":1},{"linkId":9,"title":"合作医生药企2","imageUrl":"https://timgsa.baidu.com/timg?image\u0026quality\u003d80\u0026size\u003db9999_10000\u0026sec\u003d1491529246\u0026di\u003d85cf41cbb0650b6b70a9b24e48bc1a79\u0026imgtype\u003djpg\u0026er\u003d1\u0026src\u003dhttp%3A%2F%2Fscimg.jb51.net%2Fallimg%2F160404%2F14-160404103545501.jpg","linkUrl":"https://timgsa.baidu.com/timg?image\u0026quality\u003d80\u0026size\u003db9999_10000\u0026sec\u003d1491529246\u0026di\u003d85cf41cbb0650b6b70a9b24e48bc1a79\u0026imgtype\u003djpg\u0026er\u003d1\u0026src\u003dhttp%3A%2F%2Fscimg.jb51.net%2Fallimg%2F160404%2F14-160404103545501.jpg","linkType":"DRUG_COMPANY","sortNumber":1},{"linkId":10,"title":"合作医生药企3","imageUrl":"https://timgsa.baidu.com/timg?image\u0026quality\u003d80\u0026size\u003db9999_10000\u0026sec\u003d1491529246\u0026di\u003d85cf41cbb0650b6b70a9b24e48bc1a79\u0026imgtype\u003djpg\u0026er\u003d1\u0026src\u003dhttp%3A%2F%2Fscimg.jb51.net%2Fallimg%2F160404%2F14-160404103545501.jpg","linkUrl":"https://timgsa.baidu.com/timg?image\u0026quality\u003d80\u0026size\u003db9999_10000\u0026sec\u003d1491529246\u0026di\u003d85cf41cbb0650b6b70a9b24e48bc1a79\u0026imgtype\u003djpg\u0026er\u003d1\u0026src\u003dhttp%3A%2F%2Fscimg.jb51.net%2Fallimg%2F160404%2F14-160404103545501.jpg","linkType":"DRUG_COMPANY","sortNumber":1}],"status":0}
     * @return
     */
    @RequestMapping(value = "/drugCompany", method = RequestMethod.POST)
    @ResponseBody
    public String drugCompany(@RequestBody HomeLinkListRequest request, HomeLinkListResponse response) {

        try {
            request.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }
        response.setHomeLinkTos(new MyBeanUtils<HomeLink, HomeLinkTo>().copyList(homeLinkService.findHomeLinks(HomeLinkType.DRUG_COMPANY, request.getPages(), 3, null), HomeLinkTo.class));
        //设置页数
        if (request.getTotalSize() <= 0) {
            request.setTotalSize(homeLinkService.findHomeLinkCount(HomeLinkType.DRUG_COMPANY));
        }
        response.setPages(request.getPages());
        response.setTotalSize(request.getTotalSize());
        return response.getSuccessResponse();
    }


    /**
     * 获取入住医生
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/doctor", method = RequestMethod.POST)
    @ResponseBody
    public String doctor(@RequestBody HomeLinkListRequest request, HomeLinkListResponse response) {
        try {
            request.checkParams();
        } catch (ApiException e) {
            return response.getErrorResponse(e);
        }

        List<HomeLink> homeLinks = homeLinkService.findHomeLinks(HomeLinkType.DOCTOR, request.getPages(), 6, null);
        List< HomeLinkTo > homeLinkTos = new ArrayList<HomeLinkTo>();

        //  加入 医院，临床领域，职称 属性
        for( HomeLink homeLink:homeLinks ){
            HomeLinkTo homeLinkTo = new MyBeanUtils<HomeLink,HomeLinkTo>().copyBean(homeLink,HomeLinkTo.class);
            homeLinkTo.setUserName( homeLink.getTitle() );
            UserDoctorQuery doctorInfo = userService.findDoctorInfo(homeLink.getRefId());
            homeLinkTo.setHospital( doctorInfo.getHospital() );
            homeLinkTo.setTitle( doctorInfo.getTitle() );
            homeLinkTo.setClinicalField( doctorInfo.getClinicalField() );
            homeLinkTos.add( homeLinkTo );
        }

        response.setHomeLinkTos( homeLinkTos );

        //设置页数
        if (request.getTotalSize() <= 0) {
            request.setTotalSize(homeLinkService.findHomeLinkCount(HomeLinkType.DOCTOR));
        }
        response.setPages(request.getPages());
        response.setTotalSize(request.getTotalSize());
        return response.getSuccessResponse();
    }
}
