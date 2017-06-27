package net.sinyoo.cooperation.service.service.impl;

import net.sinyoo.cooperation.comm.model.SubjectMessage;
import net.sinyoo.cooperation.comm.service.SubjectMessageService;
import net.sinyoo.cooperation.core.emnu.SubjectMessageType;
import net.sinyoo.cooperation.core.util.MyBeanUtils;
import net.sinyoo.cooperation.service.domain.SubjectMessageDo;
import net.sinyoo.cooperation.service.mapper.SubjectMessageMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 课题消息列表服务实现
 * 创建用户:     wangHui
 * 创建时间:     2017-03-29
 * Created by IntelliJ IDEA.
 */
@Component
@Service("subjectMessageService")
public class SubjectMessageServiceImpl implements SubjectMessageService {


    @Resource
    private SubjectMessageMapper subjectMessageMapper;


    @Override
    public SubjectMessage findMessage(int subjectId, SubjectMessageType subjectMessageType) {
        return new MyBeanUtils<SubjectMessageDo,SubjectMessage>().copyBean(subjectMessageMapper.getMessage(subjectId,subjectMessageType),SubjectMessage.class);
    }

    @Override
    public List<SubjectMessage> findMessages(int userId, int pages, int pageSize) {
        return new MyBeanUtils<SubjectMessageDo, SubjectMessage>()
                .copyList(
                        subjectMessageMapper.getMessages(userId, pages * pageSize, pageSize)
                        , SubjectMessage.class
                );
    }

    @Override
    public int findMessageCount(int userId) {
        return subjectMessageMapper.getMessageCount(userId);
    }

    @Override
    public SubjectMessage findMessageBySubjectId(int subjectId) {
        SubjectMessageDo subjectMessageDo = subjectMessageMapper.getMessageBySubjectId(subjectId);
        return new MyBeanUtils<SubjectMessageDo,SubjectMessage>().copyBean(subjectMessageDo,SubjectMessage.class);
    }
}
