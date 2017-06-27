package net.sinyoo.cooperation.service.service.impl;

import net.sinyoo.cooperation.comm.model.SubjectPartIn;
import net.sinyoo.cooperation.comm.service.SubjectPartInService;
import net.sinyoo.cooperation.core.emnu.SubjectMessageType;
import net.sinyoo.cooperation.core.emnu.SubjectPartInStatus;
import net.sinyoo.cooperation.core.emnu.SubjectPartInType;
import net.sinyoo.cooperation.core.emnu.SubjectStatus;
import net.sinyoo.cooperation.core.exception.ServiceException;
import net.sinyoo.cooperation.core.util.MyBeanUtils;
import net.sinyoo.cooperation.service.domain.SubjectDo;
import net.sinyoo.cooperation.service.domain.SubjectMessageDo;
import net.sinyoo.cooperation.service.domain.SubjectPartInDo;
import net.sinyoo.cooperation.service.mapper.SubjectMapper;
import net.sinyoo.cooperation.service.mapper.SubjectMessageMapper;
import net.sinyoo.cooperation.service.mapper.SubjectPartInMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 课题参与人服务
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/6
 * Time: 下午2:18
 */
@Component
@Service("subjectPartInService")
public class SubjectPartInServiceImpl implements SubjectPartInService {

    @Resource
    private SubjectPartInMapper subjectPartInMapper;
    @Resource
    private SubjectMapper subjectMapper;
    @Resource
    private SubjectMessageMapper subjectMessageMapper;


    @Override
    public void apply(int userId, int subjectId, int caseNumberShare) throws ServiceException {
        if (caseNumberShare <= 0) {
            throw new ServiceException(601, "请填写提供的病例数");
        }
        SubjectDo subjectDo = subjectMapper.selectByPrimaryKey(subjectId);
        if (subjectDo == null) {
            throw new ServiceException(602, "课题不存在");
        }

        List<SubjectPartInDo> subjectPartIns = subjectPartInMapper.getHasUserPartInSubject(userId, subjectId);
        if (subjectPartIns != null){
            for (SubjectPartInDo subjectPartInDo:subjectPartIns){
                if (subjectPartInDo.getSubjectPartInStatus() == SubjectPartInStatus.AGREE ||
                        subjectPartInDo.getSubjectPartInStatus() == SubjectPartInStatus.PENDING){
                    throw new ServiceException(603,"请不要重复申请加入此课题");
                }
            }
        }

//        Integer userId, Integer subjectId, String subjectName, Integer subjectUserId, Integer caseNumberShare, SubjectPartInStatus
//        subjectPartInStatus, SubjectPartInType subjectPartInType
        SubjectPartInDo subjectPartInDo = new SubjectPartInDo(userId, subjectId, subjectDo.getResearchTitle(), subjectDo.getUserId(),
                caseNumberShare, SubjectPartInStatus.PENDING, SubjectPartInType.APPLY);
        subjectPartInMapper.insert(subjectPartInDo);
    }

    @Override
    public void invocation(int userId, int subjectId) throws ServiceException {
        SubjectDo subjectDo = subjectMapper.selectByPrimaryKey(subjectId);
        if (subjectDo == null) {
            throw new ServiceException(601, "课题不存在");
        }

        List<SubjectPartInDo> subjectPartIns = subjectPartInMapper.getHasUserPartInSubject(userId, subjectId);
        if (subjectPartIns != null){
            for (SubjectPartInDo subjectPartInDo:subjectPartIns){
                if (subjectPartInDo.getSubjectPartInStatus() == SubjectPartInStatus.AGREE ||
                        subjectPartInDo.getSubjectPartInStatus() == SubjectPartInStatus.PENDING){
                    throw new ServiceException(603,"请不要重复邀请用户");
                }
            }
        }

        SubjectPartInDo subjectPartInDo = new SubjectPartInDo(userId, subjectId, subjectDo.getResearchTitle(), subjectDo.getUserId(),
                0, SubjectPartInStatus.PENDING, SubjectPartInType.INVITATION);
        subjectPartInMapper.insert(subjectPartInDo);
    }

    @Override
    public void agree(int userId, int partInId) throws ServiceException {
        SubjectPartInDo subjectPartInDo = subjectPartInMapper.selectByPrimaryKey(partInId);
        if (subjectPartInDo == null) {
            throw new ServiceException(601, "课题申请不存在");
        }
        if (subjectPartInDo.getSubjectPartInStatus() == SubjectPartInStatus.DISAGREE) {
            throw new ServiceException(602, "拒绝的申请不能同意");
        }
        if (subjectPartInMapper.updatePartInStatus(partInId, SubjectPartInStatus.AGREE) <= 0) {
            throw new ServiceException(602, "同意申请失败");
        }

        //申请被同意,插入同意申请消息
        SubjectMessageType subjectMessageType = SubjectMessageType.APPLY_SUCCESS;
        SubjectMessageDo subjectMessageDo = new SubjectMessageDo(subjectPartInDo.getSubjectId(), subjectPartInDo.getUserId(), subjectPartInDo.getSubjectName(),"", subjectMessageType);
        subjectMessageMapper.insert(subjectMessageDo);
    }

    @Override
    public void refuse(int userId, int partInId, String content) throws ServiceException {
        SubjectPartInDo subjectPartInDo = subjectPartInMapper.selectByPrimaryKey(partInId);
        if (subjectPartInDo == null) {
            throw new ServiceException(601, "课题申请不存在");
        }
        if (subjectPartInDo.getSubjectPartInStatus() == SubjectPartInStatus.AGREE) {
            throw new ServiceException(602, "同意的申请不能拒绝");
        }
        if (subjectPartInMapper.updatePartInStatus(partInId, SubjectPartInStatus.DISAGREE) <= 0) {
            throw new ServiceException(603, "拒绝申请失败");
        }
        // 插入拒绝理由
        SubjectMessageType subjectMessageType = SubjectMessageType.APPLY_FAIL;
        SubjectMessageDo subjectMessageDo = new SubjectMessageDo(subjectPartInDo.getSubjectId(), subjectPartInDo.getUserId(),subjectPartInDo.getSubjectName(), content, subjectMessageType);
        subjectMessageMapper.insert(subjectMessageDo);
    }

    @Override
    public void accept(int partInId, int caseNumberShare) throws ServiceException {
        if (caseNumberShare <= 0) {
            throw new ServiceException(603, "请填写提供的病例数");
        }
        SubjectPartInDo subjectPartInDo = subjectPartInMapper.selectByPrimaryKey(partInId);
        if (subjectPartInDo == null) {
            throw new ServiceException(601, "课题申请不存在");
        }
        //更新状态
        if (subjectPartInMapper.updatePartInStatus(partInId, SubjectPartInStatus.AGREE) <= 0) {
            throw new ServiceException(602, "接受邀请失败");
        }
        //更新提供的病例数
        subjectPartInMapper.updateCaseNumberShare(partInId, caseNumberShare);
    }

    @Override
    public void ignore(int userId, int partInId) throws ServiceException {
        SubjectPartInDo subjectPartInDo = subjectPartInMapper.selectByPrimaryKey(partInId);
        if (subjectPartInDo == null) {
            throw new ServiceException(601, "课题申请不存在");
        }
        if (subjectPartInDo.getSubjectPartInStatus() == SubjectPartInStatus.AGREE) {
            throw new ServiceException(602, "同意的申请不能忽略");
        }
        if (subjectPartInMapper.updatePartInStatus(partInId, SubjectPartInStatus.DISAGREE) <= 0) {
            throw new ServiceException(603, "忽略申请失败");
        }
    }

    @Override
    public List<SubjectPartIn> findApplyPages(int userId, int pages, int pageSize) {
        List<SubjectPartInDo> list = subjectPartInMapper.getApplyPages(userId, pages * pageSize, pageSize);
        return new MyBeanUtils<SubjectPartInDo, SubjectPartIn>().copyList(list, SubjectPartIn.class);
    }

    @Override
    public int findApplyCount(int userId) {
        return subjectPartInMapper.getApplyCount(userId);
    }

    /**
     * 获得受邀请状态中的课题
     *
     * @param userId
     * @param pages
     * @param pageSize
     * @return
     */
    @Override
    public List<SubjectPartIn> findInvocationPages(int userId, int pages, int pageSize) {
        List<SubjectPartInDo> list = subjectPartInMapper.getInvocationPages(userId, pages * pageSize, pageSize);
        return new MyBeanUtils<SubjectPartInDo, SubjectPartIn>().copyList(list, SubjectPartIn.class);
    }

    /**
     * 获得邀请状态等待做出决定的课题的数量
     *
     * @param userId
     * @return
     */
    @Override
    public int findInvocationCount(int userId) {
        return subjectPartInMapper.getInvocationCount(userId);
    }

    @Override
    public List<SubjectPartIn> findAgreePages(int subjectId,int pages,int pageSize) {
        List<SubjectPartInDo> list = subjectPartInMapper.getAgreePages(subjectId, pages * pageSize, pageSize);
        return new MyBeanUtils<SubjectPartInDo, SubjectPartIn>().copyList(list, SubjectPartIn.class);
    }

}
