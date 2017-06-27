package net.sinyoo.cooperation.service.mapper;

import net.sinyoo.cooperation.comm.model.SubjectPartIn;
import net.sinyoo.cooperation.core.annotation.Past;
import net.sinyoo.cooperation.core.emnu.SubjectPartInStatus;
import net.sinyoo.cooperation.core.emnu.SubjectPartInType;
import net.sinyoo.cooperation.service.domain.SubjectPartInDo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

public interface SubjectPartInMapper {
    int deleteByPrimaryKey(Integer partInId);

    @SelectKey(before = false, keyProperty = "partInId", resultType = Integer.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS partInId")
    int insert(SubjectPartInDo record);

    @SelectKey(before = false, keyProperty = "partInId", resultType = Integer.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS partInId")
    int insertSelective(SubjectPartInDo record);

    SubjectPartInDo selectByPrimaryKey(Integer partInId);

    int updateByPrimaryKeySelective(SubjectPartInDo record);

    int updateByPrimaryKey(SubjectPartInDo record);


    /**
     * 获取用户 加入某个课题 的记录
     * @param userId
     * @param subjectId
     * @return
     */
    @Select("select * from subject_part_in where user_id = #{userId} and subject_id = #{subjectId}")
    @ResultMap("BaseResultMap")
    List<SubjectPartInDo> getHasUserPartInSubject(@Param("userId")int userId,@Param("subjectId")int subjectId);

    /**
     * 更新课题参与状态
     * @param partInId
     * @param subjectPartInStatus
     * @return
     */
    @Update("update subject_part_in set subject_part_in_status = #{subjectPartInStatus} where part_in_id = #{partInId}")
    int updatePartInStatus(@Param("partInId") int partInId, @Param("subjectPartInStatus") SubjectPartInStatus subjectPartInStatus);


    /**
     * 更新参与课题提供的病例数
     * @param partInId
     * @param caseNumberShare
     * @return
     */
    @Update("update subject_part_in set case_number_share = #{caseNumberShare} where part_in_id = #{partInId}")
    int updateCaseNumberShare(@Param("partInId") int partInId, @Param("caseNumberShare") int caseNumberShare);

    /**
     * 获取邀请记录
     * @param userId
     * @param offSet
     * @param rows
     * @return
     */
    List<SubjectPartInDo> getInvocationPages(@Param("userId") int userId, @Param("offSet") int offSet, @Param("rows") int rows);

    /**
     * 获取待同意邀请记录数量
     * @param userId
     * @return
     */
    @Select("select count(part_in_id)  from subject_part_in where user_id = #{userId}  AND subject_part_in_type = \"INVITATION\" AND subject_part_in_status = \"PENDING\" ")
    int getInvocationCount(@Param("userId") int userId);

    /**
     * 获取申请记录
     * @param subjectUserId
     * @param offSet
     * @param rows
     * @return
     */
    List<SubjectPartInDo> getApplyPages(@Param("subjectUserId")int subjectUserId, @Param("offSet")int offSet,@Param("rows") int rows);

    /**
     * 获取待同意的申请记录数量
     * @param subjectUserId
     * @return
     */
    @Select("select count(part_in_id)  from subject_part_in where subject_user_id = #{userId} AND subject_part_in_type = \"APPLY\" AND subject_part_in_status = \"PENDING\" ")
    int getApplyCount(int subjectUserId);

    /**
     * 获取已同意加入课题的记录
     * @param subjectId
     * @param offSet
     * @param rows
     * @return
     */
    List<SubjectPartInDo> getAgreePages(@Param("subjectId")int subjectId, @Param("offSet")int offSet,@Param("rows") int rows);
}