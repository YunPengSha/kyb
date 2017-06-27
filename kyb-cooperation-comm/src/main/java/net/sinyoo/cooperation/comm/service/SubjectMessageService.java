package net.sinyoo.cooperation.comm.service;

import net.sinyoo.cooperation.comm.model.HomeLink;
import net.sinyoo.cooperation.comm.model.SubjectMessage;
import net.sinyoo.cooperation.core.emnu.HomeLinkType;
import net.sinyoo.cooperation.core.emnu.SubjectMessageType;

import java.util.List;

/**
 * 课题消息列表服务
 * 创建用户:     wangHui
 * 创建时间:     2017-03-29
 * Created by IntelliJ IDEA.
 */
public interface SubjectMessageService {


    /**
     * 根据课题编号和类别获取单条最新记录
     *
     * @param subjectId
     * @param subjectMessageType
     * @return
     */
    SubjectMessage findMessage(int subjectId, SubjectMessageType subjectMessageType);

    /**
     * 获取消息列表
     *
     * @param userId
     * @param pages
     * @param pageSize
     * @return
     */
    List<SubjectMessage> findMessages(int userId, int pages, int pageSize);


    /**
     * 获取消息数量
     *
     * @param userId
     * @return
     */
    int findMessageCount(int userId);

    /**
     * 通过课题id获取
     * @param subjectId
     * @return
     */
    SubjectMessage findMessageBySubjectId(int subjectId);

}
