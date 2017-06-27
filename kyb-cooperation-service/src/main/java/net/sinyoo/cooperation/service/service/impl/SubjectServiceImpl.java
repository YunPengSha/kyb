package net.sinyoo.cooperation.service.service.impl;

import net.sinyoo.cooperation.comm.model.Subject;
import net.sinyoo.cooperation.comm.service.SubjectService;
import net.sinyoo.cooperation.core.emnu.*;
import net.sinyoo.cooperation.core.exception.ServiceException;
import net.sinyoo.cooperation.core.util.MyBeanUtils;
import net.sinyoo.cooperation.service.domain.SubjectDo;
import net.sinyoo.cooperation.service.domain.SubjectMessageDo;
import net.sinyoo.cooperation.service.domain.SubjectPartInDo;
import net.sinyoo.cooperation.service.domain.SubjectStandardDo;
import net.sinyoo.cooperation.service.mapper.SubjectMapper;
import net.sinyoo.cooperation.service.mapper.SubjectMessageMapper;
import net.sinyoo.cooperation.service.mapper.SubjectPartInMapper;
import net.sinyoo.cooperation.service.mapper.SubjectStandardMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunpengsha on 2017/3/6.
 */
@Component
@Service("subjectService")
public class SubjectServiceImpl implements SubjectService {

    @Resource
    private SubjectMapper subjectMapper;

    @Resource
    private SubjectStandardMapper subjectStandardMapper;

    @Resource
    private SubjectPartInMapper subjectPartInMapper;

    @Resource
    private SubjectMessageMapper subjectMessageMapper;

    @Override
    public Subject addSubject(Subject subject, List<String> subjectStandardIns, List<String> subjectStandardOuts, List<String> doctorIds) throws ServiceException {
        SubjectDo subjectDo = new SubjectDo();
        BeanUtils.copyProperties(subject, subjectDo);
        subjectDo.setSubjectStatus(SubjectStatus.AUDIT);
        subjectMapper.insert(subjectDo);
        SubjectStandardDo subjectStandardDo = null;

        //插入标准
        if (subjectStandardIns != null) {
            for (String in : subjectStandardIns) {
                subjectStandardDo = new SubjectStandardDo(subjectDo.getSubjectId(), in, SubjectStandardType.INCLUDE);
                subjectStandardMapper.insert(subjectStandardDo);
            }
        }

        if (subjectStandardOuts != null) {
            for (String out : subjectStandardOuts) {
                subjectStandardDo = new SubjectStandardDo(subjectDo.getSubjectId(), out, SubjectStandardType.EXCLUDE);
                subjectStandardMapper.insert(subjectStandardDo);
            }
        }

        List<String> partInIds = new ArrayList<>();
        partInIds.add(subject.getUserId() + "");

        //添加参与人
        if (doctorIds != null) {
            for (String doctorId : doctorIds) {
                //不允许重复邀请医生
                if (partInIds.contains(doctorId)) {
                    continue;
                }
                SubjectPartInDo subjectPartInDo = new SubjectPartInDo(Integer.parseInt(doctorId), subjectDo.getSubjectId(), subjectDo.getResearchTitle(), subjectDo.getUserId(),
                        0, SubjectPartInStatus.PENDING, SubjectPartInType.INVITATION);
                subjectPartInMapper.insert(subjectPartInDo);
                partInIds.add(doctorId);
            }
        }
        BeanUtils.copyProperties(subjectDo, subject);
        return subject;
    }

    @Override
    public void modifySubject(int userId, Subject subject, List<String> subjectStandardIns, List<String> subjectStandardOuts, List<String> deleteSubjectStandardIds) throws ServiceException {
        SubjectDo subjectDBDo = subjectMapper.selectByPrimaryKey(subject.getSubjectId());
        if (subjectDBDo == null) {
            throw new ServiceException(601, "课题不存在");
        }
        if (subjectDBDo.getSubjectStatus() != SubjectStatus.PUBLISH || subjectDBDo.getSubjectStatus() != SubjectStatus.AUDIT_FAIL) {
            throw new ServiceException(602, "只能修改发布中和未审核通过的课题");
        }

        if (userId != subjectDBDo.getUserId()) {
            throw new ServiceException(601, "您无权修改");
        }

        SubjectDo subjectDo = new SubjectDo();
        BeanUtils.copyProperties(subject, subjectDo);
        //更新数据
        if (subjectMapper.updateByPrimaryKeySelective(subjectDo) <= 0) {
            throw new ServiceException(603, "课题修改失败");
        }
        SubjectStandardDo subjectStandardDo = null;
        //插入标准
        if (subjectStandardIns != null) {
            for (String in : subjectStandardIns) {
                subjectStandardDo = new SubjectStandardDo(subject.getSubjectId(), in, SubjectStandardType.INCLUDE);
                subjectStandardMapper.insert(subjectStandardDo);
            }
        }
        if (subjectStandardOuts != null) {
            for (String out : subjectStandardOuts) {
                subjectStandardDo = new SubjectStandardDo(subject.getSubjectId(), out, SubjectStandardType.EXCLUDE);
                subjectStandardMapper.insert(subjectStandardDo);
            }
        }
        //删除标准
        if (deleteSubjectStandardIds != null) {
            for (String id : deleteSubjectStandardIds) {
                subjectStandardMapper.deleteByPrimaryKey(Integer.parseInt(id));
            }
        }
    }

    @Override
    public void auditSubject(int subjectId, SubjectStatus subjectStatus, String content) throws ServiceException {
        SubjectDo subjectDo = subjectMapper.selectByPrimaryKey(subjectId);
        if (subjectDo == null) {
            throw new ServiceException(601, "课题不存在");
        }
        if (subjectStatus == SubjectStatus.PUBLISH && subjectDo.getSubjectStatus() != SubjectStatus.AUDIT) {
            throw new ServiceException(602, "课题审核失败");
        }

//        if (userId != subjectDo.getUserId()) {
//            throw new ServiceException(603, "您无权修改");
//        }

        subjectMapper.updateSubjectStatus(subjectId, subjectStatus);

        //审核失败,插入失败消息
        if (subjectStatus == SubjectStatus.AUDIT_FAIL) {
            SubjectMessageType subjectMessageType = SubjectMessageType.APPLY_FAIL;
            SubjectMessageDo subjectMessageDo = new SubjectMessageDo(subjectDo.getSubjectId(), subjectDo.getUserId(), subjectDo.getResearchTitle(), content, subjectMessageType);
            subjectMessageMapper.insert(subjectMessageDo);
        }
    }

    @Override
    public void removeSubject(int userId, int subjectId, SubjectStatus subjectStatus) throws ServiceException {
        SubjectDo subjectDo = subjectMapper.selectByPrimaryKey(subjectId);
        if (subjectDo == null) {
            throw new ServiceException(601, "课题不存在");
        }

        if (userId != subjectDo.getUserId()) {
            throw new ServiceException(603, "您无权修改");
        }


        int result = subjectMapper.updateSubjectStatus(subjectId, subjectStatus);
        if (result <= 0) {
            throw new ServiceException(601, "删除课题失败");
        }
    }

    @Override
    public void completeSubject(int userId, int subjectId, SubjectStatus subjectStatus) throws ServiceException {
        SubjectDo subjectDo = subjectMapper.selectByPrimaryKey(subjectId);
        if (subjectDo == null) {
            throw new ServiceException(601, "课题不存在");
        }
        if (subjectStatus != SubjectStatus.PUBLISH) {
            throw new ServiceException(602, "完成课题失败,状态不匹配");
        }

        if (userId != subjectDo.getUserId()) {
            throw new ServiceException(601, "您无权修改");
        }

        int result = subjectMapper.updateSubjectStatus(subjectId, subjectStatus);
        if (result <= 0) {
            throw new ServiceException(602, "完成课题失败");
        }
    }

    @Override
    public Subject findById(int subjectId) throws ServiceException {
        SubjectDo subjectDo = subjectMapper.selectByPrimaryKey(subjectId);
        if (subjectDo == null) {
            throw new ServiceException(601, "课题不存在");
        }
        Subject subject = new Subject();
        BeanUtils.copyProperties(subjectDo, subject);
        return subject;
    }

    @Override
    public List<Subject> findUserPages(int userId, int pages, int pageSize) {
        return new MyBeanUtils<SubjectDo, Subject>()
                .copyList(subjectMapper.getUserPages(userId, pages * pageSize, pageSize), Subject.class);

    }

    @Override
    public int findUserCount(int userId) {
        return subjectMapper.getUserCount(userId);
    }

    @Override
    public List<Subject> findPages(int userId, String searchKey, SubjectNature subjectNature, SubjectStatus subjectStatus, int pages, int pageSize) {
        return new MyBeanUtils<SubjectDo, Subject>()
                .copyList(subjectMapper.getPages(userId, subjectNature, subjectStatus, searchKey, pages * pageSize, pageSize), Subject.class);
    }

    @Override
    public int findCount(int userId, String searchKey, SubjectNature subjectNature, SubjectStatus subjectStatus) {
        return subjectMapper.getCount(userId, subjectNature, subjectStatus, searchKey);
    }

    @Override
    public List<Subject> findAuditList(int page, int pageSize) {
        List<SubjectDo> auditList = subjectMapper.getAuditList((page - 1) * pageSize, pageSize);
        return new MyBeanUtils<SubjectDo, Subject>().copyList(auditList, Subject.class);
    }

    @Override
    public List<Subject> findPassList(int page, int pageSize) {
        List<SubjectDo> passList = subjectMapper.getPassList((page - 1) * pageSize, pageSize);
        return new MyBeanUtils<SubjectDo, Subject>().copyList(passList, Subject.class);
    }

    @Override
    public List<Subject> findFailList(int page, int pageSize) {
        List<SubjectDo> passList = subjectMapper.getFailList((page - 1) * pageSize, pageSize);
        return new MyBeanUtils<SubjectDo, Subject>().copyList(passList, Subject.class);
    }

    /**
     * 根据一个或多个状态值获取相应状态的课题数量
     *
     * @param a
     * @param b
     * @return
     */
    @Override
    public int findTotalSize(SubjectStatus a, SubjectStatus b) {
        if (b == null) {
            return subjectMapper.getTotalSizeOne(a);
        }
        return subjectMapper.getTotalSizeTwo(a, b);
    }

    /**
     * 获取已通过审核的数量
     *
     * @return
     */
    @Override
    public int findPassListCount() {
        return subjectMapper.getPassListCount();
    }

    @Override
    public void modifySubject(Integer subjectId, String brochureUrl) {
        subjectMapper.updateBrochureUrl(subjectId, brochureUrl);
    }

    /**
     * 验收课题
     *
     * @param userId
     * @param subjectId
     * @throws ServiceException
     */
    @Override
    public void checkSubject(Integer userId, Integer subjectId) throws ServiceException {
        SubjectDo subjectDo = subjectMapper.selectByPrimaryKey(subjectId);

        if (!userId.equals(subjectDo.getUserId())) {
            throw new ServiceException(601, "你无权操作");
        }

        if (subjectDo.getSubjectStatus() == SubjectStatus.WAIT_CHECK) {
            // 确认审核
            subjectMapper.updateSubjectStatus(subjectId, SubjectStatus.HAVE_CHECK);
            // 课题完成
            subjectMapper.updateSubjectStatus(subjectId, SubjectStatus.COMPLETE);
        } else if (subjectDo.getSubjectStatus() == SubjectStatus.HAVE_CHECK || subjectDo.getSubjectStatus() == SubjectStatus.COMPLETE) {
            throw new ServiceException(602, "验收完成，请别重复验收");
        } else {
            throw new ServiceException(603, "当前课题状态有错");
        }
    }

    @Override
    public Integer findOwnerSubjectCount(Integer userId) {
        return subjectMapper.getCount(userId, null, null, null);
    }

}
