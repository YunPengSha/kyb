package net.sinyoo.cooperation.comm.service;

import net.sinyoo.cooperation.comm.model.Subject;
import net.sinyoo.cooperation.core.emnu.SubjectNature;
import net.sinyoo.cooperation.core.emnu.SubjectStatus;
import net.sinyoo.cooperation.core.exception.ServiceException;

import java.util.List;

/**
 * 课题服务
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/6
 * Time: 下午2:04
 */
public interface SubjectService {

    /**
     * 添加课题
     *
     * @param subject
     * @param subjectStandardIns
     * @param subjectStandardOuts
     * @param doctorIds
     * @return
     */
    Subject addSubject(Subject subject, List<String> subjectStandardIns, List<String> subjectStandardOuts, List<String> doctorIds) throws ServiceException;

    /**
     * 修改课题
     *@param userId
     * @param subject
     * @param subjectStandardIns
     * @param subjectStandardOuts
     * @param deleteSubjectStandardIds
     * @throws ServiceException
     */
    void modifySubject(int userId, Subject subject, List<String> subjectStandardIns, List<String> subjectStandardOuts, List<String> deleteSubjectStandardIds) throws ServiceException;


    /**
     * 审核课题,修改课题的状态
     *
     * @param subjectId
     * @param subjectStatus
     * @param content
     * @throws ServiceException
     */
    void auditSubject(int subjectId, SubjectStatus subjectStatus, String content) throws ServiceException;

    /**
     * 删除课题,修改课题的状态
     **@param userId
     * @param subjectId
     * @param subjectStatus
     * @throws ServiceException
     */
    void removeSubject(int userId, int subjectId, SubjectStatus subjectStatus) throws ServiceException;

    /**
     * 完成课题,修改课题的状态
     **@param userId
     * @param subjectId
     * @param subjectStatus
     * @throws ServiceException
     */
    void completeSubject(int userId, int subjectId, SubjectStatus subjectStatus) throws ServiceException;


    Subject findById(int subjectId) throws ServiceException;

    /**
     * 获取用户个人中心的课题  包含自己创建的和自己加入的课题
     * @param userId
     * @param pages
     * @param pageSize
     * @return
     */
    List<Subject> findUserPages(int userId, int pages, int pageSize);

    /**
     * 获取用户个人中心的课题数量  包含自己创建的和自己加入的课题
     * @param userId
     * @return
     */
    int findUserCount(int userId);

    /**
     * 获取课题列表
     *
     * @param userId        获取自己的课题列表的时候传当前登录用户的编号
     * @param searchKey     查询条件
     * @param subjectNature 课题性质
     * @param subjectStatus
     * @param pages
     * @return
     */
    List<Subject> findPages(int userId, String searchKey, SubjectNature subjectNature, SubjectStatus subjectStatus, int pages, int pageSize);

    /**
     * 获取课题列表的时候获取总记录数量
     *
     * @param userId        获取自己的课题列表的时候传当前登录用户的编号
     * @param searchKey     查询条件
     * @param subjectNature 课题性质
     * @param subjectStatus
     * @return
     */
    int findCount(int userId, String searchKey, SubjectNature subjectNature, SubjectStatus subjectStatus);

    /**
     * 获得审核课题列表
     * @param page
     * @param pageSize
     * @return
     */
    List<Subject> findAuditList(int page, int pageSize);
    /**
     * 获得通过课题列表
     * @param page
     * @param pageSize
     * @return
     */
    List<Subject> findPassList(int page, int pageSize);
    /**
     * 获得失败课题列表
     * @param page
     * @param pageSize
     * @return
     */
    List<Subject> findFailList(int page, int pageSize);

    int findTotalSize(SubjectStatus a, SubjectStatus b);

    int findPassListCount();

    /**
     * 更新 研究者手册url
     * @param subjectId
     * @param brochureUrl
     */
    void modifySubject(Integer subjectId, String brochureUrl);

    /**
     * 验收课题，更改状态
     * @param userId
     * @param subjectId
     */
    void checkSubject(Integer userId, Integer subjectId) throws ServiceException;

    /**
     *  获取自己创建的课题数量
     * @param userId
     * @return
     */
    Integer findOwnerSubjectCount(Integer userId);
}
