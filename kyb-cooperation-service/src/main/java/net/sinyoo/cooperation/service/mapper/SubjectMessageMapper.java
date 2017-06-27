package net.sinyoo.cooperation.service.mapper;

import net.sinyoo.cooperation.comm.model.SubjectMessage;
import net.sinyoo.cooperation.core.emnu.SubjectMessageType;
import net.sinyoo.cooperation.service.domain.SubjectMessageDo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * 课题消息
 * 创建用户:     wangHui
 * 创建时间:     2017-03-29
 * Created by IntelliJ IDEA.
 */
public interface SubjectMessageMapper {
    int deleteByPrimaryKey(Integer id);

    @SelectKey(before = false, keyProperty = "id", resultType = Integer.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    int insert(SubjectMessageDo record);

    @SelectKey(before = false, keyProperty = "id", resultType = Integer.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    int insertSelective(SubjectMessageDo record);

    SubjectMessageDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SubjectMessageDo record);

    int updateByPrimaryKey(SubjectMessageDo record);


    /**
     * 根据类别获取单条记录
     *
     * @param subjectId
     * @param subjectMessageType
     * @return
     */
    @Select("select * from subject_message where subject_id = #{subjectId} and subject_message_type = #{subjectMessageType} order by id desc limit 0,1")
    @ResultMap("BaseResultMap")
    SubjectMessageDo getMessage(@Param("subjectId") int subjectId, @Param("subjectMessageType") SubjectMessageType subjectMessageType);


    /**
     * 获取用户的科研课题消息
     *
     * @param userId
     * @param offSet
     * @param rows
     * @return
     */
    @Select("select * from subject_message where user_id = #{userId} order by id desc limit #{offSet},#{rows}")
    @ResultMap("BaseResultMap")
    List<SubjectMessageDo> getMessages(@Param("userId") int userId, @Param("offSet") int offSet, @Param("rows") int rows);

    /**
     * 获取用户的科研课题消息总数量
     *
     * @param userId
     * @return
     */
    @Select("select count(id) from subject_message where user_id = #{userId}")
    int getMessageCount(@Param("userId") int userId);

    /**
     * 通过subjectId获取Message
     * @param subjectId
     * @return
     */
    SubjectMessageDo getMessageBySubjectId(@Param("subjectId") int subjectId);
}