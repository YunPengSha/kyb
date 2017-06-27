package net.sinyoo.cooperation.comm.service;

import net.sinyoo.cooperation.comm.model.SubjectPartIn;
import net.sinyoo.cooperation.core.exception.ServiceException;

import java.util.List;

/**
 * 课题参与服务
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/6
 * Time: 下午2:04
 */
public interface SubjectPartInService {


    /**
     * 申请加入课题
     * @param userId
     * @param subjectId
     * @param caseNumberShare 提供简历数
     * @throws ServiceException
     */
    void apply(int userId,int subjectId,int caseNumberShare)throws ServiceException;

    /**
     * 邀请加入课题
     * @param userId
     * @param subjectId
     * @throws ServiceException
     */
    void invocation(int userId,int subjectId)throws ServiceException;

    /**
     * 创建课题的人 同意加入课题
     * @param userId
     * @param partInId
     * @throws ServiceException
     */
    void agree(int userId,int partInId)throws ServiceException;

    /**
     * 创建课题的人 不同意加入课题
     * @param userId
     * @param partInId
     * @param content
     * @throws ServiceException
     */
    void refuse(int userId, int partInId,String content)throws ServiceException;


    /**
     * 被邀请的人 同意加入课题
     * @param partInId
     * @param caseNumberShare
     * @throws ServiceException
     */
    void accept(int partInId,int caseNumberShare)throws ServiceException;

    /**
     * 被邀请加入课题的人 忽略加入课题
     * @param userId
     * @param partInId
     * @throws ServiceException
     */
    void ignore(int userId, int partInId)throws ServiceException;

    /**
     * 获取申请列表
     * @param userId
     * @param pages
     * @param pageSize
     * @return
     */
    List<SubjectPartIn> findApplyPages(int userId,int pages,int pageSize);

    /**
     * 获取申请总数量
     * @param userId
     * @return
     */
    int findApplyCount(int userId);


    /**
     * 获取邀请列表
     * @param userId
     * @param pages
     * @param pageSize
     * @return
     */
    List<SubjectPartIn> findInvocationPages(int userId,int pages,int pageSize);

    /**
     * 获取邀请总数量
     * @param userId
     * @return
     */
    int findInvocationCount(int userId);


    /**
     *  获取已同意加入课题的医生用户
     * @param subjectId
     * @param pages
     * @param pageSize
     * @return
     */
    List<SubjectPartIn> findAgreePages(int subjectId,int pages,int pageSize);

}
