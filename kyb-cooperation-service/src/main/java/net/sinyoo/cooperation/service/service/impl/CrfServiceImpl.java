package net.sinyoo.cooperation.service.service.impl;

import net.sinyoo.cooperation.comm.model.Crf;
import net.sinyoo.cooperation.comm.service.CrfService;
import net.sinyoo.cooperation.core.emnu.CrfStatus;
import net.sinyoo.cooperation.core.emnu.SubjectStatus;
import net.sinyoo.cooperation.core.exception.ServiceException;
import net.sinyoo.cooperation.core.util.MyBeanUtils;
import net.sinyoo.cooperation.service.domain.CrfDo;
import net.sinyoo.cooperation.service.domain.SubjectDo;
import net.sinyoo.cooperation.service.mapper.CrfMapper;
import net.sinyoo.cooperation.service.mapper.SubjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * crf 表单服务
 * Created by yunpengsha on 2017/4/5.
 */
@Component
@Service("crfService")
public class CrfServiceImpl implements CrfService{

    @Resource
    private CrfMapper crfMapper;


    @Resource
    private SubjectMapper subjectMapper;

    @Override
    public Crf findBySubjectId(int subjectId) {
        return new MyBeanUtils<CrfDo,Crf>().copyBean(crfMapper.getBySubjectId(subjectId),Crf.class);
    }

    @Transactional
    @Override
    public void uploadCrf(int userId, int subjectId, String crfDownUrl) {
        CrfDo crfDo = new CrfDo(subjectId,crfDownUrl,0,CrfStatus.WAIT_PRICE);
        crfMapper.insertSelective(crfDo);
        subjectMapper.updateSubjectStatus(subjectId, SubjectStatus.WAIT_PRICE);
    }

    @Transactional
    @Override
    public void agreeCrf(int userId, int subjectId)throws ServiceException {
        SubjectDo subjectDo = subjectMapper.selectByPrimaryKey(subjectId);
        if (userId != subjectDo.getUserId()){
            throw new ServiceException(601,"用户不匹配,无权修改信息");
        }
        subjectMapper.updateSubjectStatus(subjectId, SubjectStatus.WAIT_CHECK);
        crfMapper.updateCrfStatus(subjectId, CrfStatus.PRICE_AGREE);
        crfMapper.addAgreeTime(subjectId,new Date());
    }

    @Transactional
    @Override
    public void refuseCrf(int userId, int subjectId) throws ServiceException{
        SubjectDo subjectDo = subjectMapper.selectByPrimaryKey(subjectId);
        if (userId != subjectDo.getUserId()){
            throw new ServiceException(601,"用户不匹配,无权修改信息");
        }
        subjectMapper.updateSubjectStatus(subjectId, SubjectStatus.PRICE_REFUSE);
        crfMapper.updateCrfStatus(subjectId, CrfStatus.PRICE_REFUSE);
    }


    @Override
    public List<Crf> findWaitPriceList(int page, int pageSize) {
        List<CrfDo> noPriceList = crfMapper.getWaitPriceList((page - 1) * pageSize, pageSize);
        return new MyBeanUtils<CrfDo,Crf>().copyList(noPriceList,Crf.class);
    }

    @Override
    public List<Crf> findHavePriceList(int page, int pageSize) {
        List<CrfDo> havePriceList = crfMapper.getHavePriceList((page - 1) * pageSize, pageSize);
        return new MyBeanUtils<CrfDo,Crf>().copyList(havePriceList,Crf.class);
    }

    @Override
    public List<Crf> findAgreePriceList(int page, int pageSize) {
        List<CrfDo> agreePriceList = crfMapper.getAgreePriceList((page - 1) * pageSize, pageSize);
        return new MyBeanUtils<CrfDo,Crf>().copyList(agreePriceList,Crf.class);
    }

    @Override
    public int findTotalSize(CrfStatus type) {
        return crfMapper.getTotalSize(type);
    }

    @Override
    public void addPrice(int subjectId, Double price) throws ServiceException {
        CrfDo crfDo = crfMapper.getBySubjectId(subjectId);
        if( crfDo.getCrfStatus() ==  CrfStatus.HAVE_PRICE || crfDo.getCrfStatus() == CrfStatus.PRICE_AGREE){
            throw new ServiceException(601,"crf状态异常");
        }
        crfMapper.addCrfPrice(subjectId,price);
        subjectMapper.updateSubjectStatus(subjectId,SubjectStatus.HAVE_PRICE);
    }

}
