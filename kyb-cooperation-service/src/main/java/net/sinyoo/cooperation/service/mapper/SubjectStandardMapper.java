package net.sinyoo.cooperation.service.mapper;

import net.sinyoo.cooperation.service.domain.SubjectStandardDo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SubjectStandardMapper {
    int deleteByPrimaryKey(Integer subjectStandardId);

    int insert(SubjectStandardDo record);

    int insertSelective(SubjectStandardDo record);

    SubjectStandardDo selectByPrimaryKey(Integer subjectStandardId);

    int updateByPrimaryKeySelective(SubjectStandardDo record);

    int updateByPrimaryKey(SubjectStandardDo record);

    /**
     * 获取课题标准
     * @param subjectId
     * @return
     */
    @Select("select * from subject_standard where subject_id = #{subjectId} order by subject_standard_id desc limit 0,100")
    @ResultMap("BaseResultMap")
    List<SubjectStandardDo> getBySubjectId(@Param("subjectId")int subjectId);
}