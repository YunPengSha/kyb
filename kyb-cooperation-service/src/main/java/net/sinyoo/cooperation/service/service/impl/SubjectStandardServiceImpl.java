package net.sinyoo.cooperation.service.service.impl;

import net.sinyoo.cooperation.comm.model.SubjectStandard;
import net.sinyoo.cooperation.comm.service.SubjectPartInService;
import net.sinyoo.cooperation.comm.service.SubjectStandardService;
import net.sinyoo.cooperation.core.emnu.SubjectPartInStatus;
import net.sinyoo.cooperation.core.emnu.SubjectPartInType;
import net.sinyoo.cooperation.core.exception.ServiceException;
import net.sinyoo.cooperation.core.util.MyBeanUtils;
import net.sinyoo.cooperation.service.domain.SubjectDo;
import net.sinyoo.cooperation.service.domain.SubjectPartInDo;
import net.sinyoo.cooperation.service.domain.SubjectStandardDo;
import net.sinyoo.cooperation.service.mapper.SubjectMapper;
import net.sinyoo.cooperation.service.mapper.SubjectPartInMapper;
import net.sinyoo.cooperation.service.mapper.SubjectStandardMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 课题标准服务
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/6
 * Time: 下午2:18
 */
@Component
@Service("subjectStandardService")
public class SubjectStandardServiceImpl implements SubjectStandardService {

    @Resource
    private SubjectStandardMapper subjectStandardMapper;


    @Override
    public List<SubjectStandard> findStandardBySubjectId(int subjectId) {
        return new MyBeanUtils<SubjectStandardDo, SubjectStandard>()
                .copyList(subjectStandardMapper.getBySubjectId(subjectId), SubjectStandard.class);
    }
}
