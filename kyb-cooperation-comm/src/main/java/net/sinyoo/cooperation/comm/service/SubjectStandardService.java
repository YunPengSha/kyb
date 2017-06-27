package net.sinyoo.cooperation.comm.service;

import net.sinyoo.cooperation.comm.model.SubjectStandard;

import java.util.List;

/**
 * 课题标准服务
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/6
 * Time: 下午2:04
 */
public interface SubjectStandardService {


    /**
     * 获取课题标准
     * @param subjectId
     * @return
     */
    List<SubjectStandard> findStandardBySubjectId(int subjectId);




}
