package net.sinyoo.cooperation.comm.service;

import net.sinyoo.cooperation.comm.model.Crf;
import net.sinyoo.cooperation.core.emnu.CrfStatus;
import net.sinyoo.cooperation.core.exception.ServiceException;

import java.util.List;

/**
 * Created by yunpengsha on 2017/4/5.
 */
public interface CrfService {


    /**
     * 根据课题获取报价
     *
     * @param subjectId
     * @return
     */
    Crf findBySubjectId(int subjectId);

    /**
     * 上传crf
     * @param userId
     * @param subjectId
     * @param crfDownUrl
     */
    void uploadCrf(int userId,int subjectId,String crfDownUrl);

    /**
     * 同意报价
     *
     * @param userId
     * @param subjectId
     */
    void agreeCrf(int userId, int subjectId)throws ServiceException;

    /**
     * 拒绝报价
     *
     * @param userId
     * @param subjectId
     */
    void refuseCrf(int userId, int subjectId)throws ServiceException;


    List<Crf> findWaitPriceList(int page, int pageSize);

    List<Crf> findHavePriceList(int page, int pageSize);

    List<Crf> findAgreePriceList(int page, int pageSize);

    int findTotalSize(CrfStatus type);

    void addPrice(int subjectId, Double price) throws ServiceException;
}
