package net.sinyoo.cooperation.service.mapper;

import net.sinyoo.cooperation.comm.model.Subject;
import net.sinyoo.cooperation.core.emnu.SubjectNature;
import net.sinyoo.cooperation.core.emnu.SubjectStatus;
import net.sinyoo.cooperation.service.domain.SubjectDo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

public interface SubjectMapper {
    int deleteByPrimaryKey(Integer subjectId);

    @SelectKey(before = false, keyProperty = "subjectId", resultType = Integer.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS subjectId")
    int insert(SubjectDo record);

    @SelectKey(before = false, keyProperty = "subjectId", resultType = Integer.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS subjectId")
    int insertSelective(SubjectDo record);

    SubjectDo selectByPrimaryKey(Integer subjectId);

    int updateByPrimaryKeySelective(SubjectDo record);

    int updateByPrimaryKey(SubjectDo record);

    /**
     * 获取用户的课题   包含自己创建的和参加的
     * @param userId
     * @param offSet
     * @param rows
     * @return
     */
    List<SubjectDo> getUserPages(@Param("userId")int userId,@Param("offSet") int offSet, @Param("rows") int rows);

    /**
     * 获取用户的课题数量   包含自己创建的和参加的
     * @param userId
     * @return
     */
    int getUserCount(@Param("userId") int userId);


    /**
     * 获取课题列表 userId!=0查询用户的课题,==0获取公开发布的课题
     * @param userId
     * @param subjectNature
     * @param subjectStatus
     * @param searchKey
     * @param offSet
     * @param rows
     * @return
     */
    List<SubjectDo>  getPages(@Param("userId") int userId, @Param("subjectNature")SubjectNature subjectNature, @Param("subjectStatus") SubjectStatus subjectStatus, @Param("searchKey") String searchKey,
                              @Param("offSet") int offSet, @Param("rows") int rows);

    /**
     * 获取课题数 userId!=0查询用户的课题,==0获取公开发布的课题
     * @param userId
     * @param subjectNature
     * @param subjectStatus
     * @param searchKey
     * @return
     */
    int getCount(@Param("userId") int userId,@Param("subjectNature")SubjectNature subjectNature,@Param("subjectStatus") SubjectStatus subjectStatus,@Param("searchKey") String searchKey);

    /**
     * 修改课题的状态
     * @param subjectId
     * @param subjectStatus
     * @return
     */
    @Update("update subject set subject_status = #{subjectStatus} where subject_id = #{subjectId}")
    int updateSubjectStatus(@Param("subjectId")Integer subjectId, @Param("subjectStatus")SubjectStatus subjectStatus);

    /**
     * 获取待审核的课题列表
     * @param offSet
     * @param rows
     * @return
     */
    List<SubjectDo> getAuditList(@Param("offSet") int offSet,@Param("rows") int rows);

    List<SubjectDo> getPassList(@Param("offSet") int offSet,@Param("rows") int rows);

    List<SubjectDo> getFailList(@Param("offSet") int offSet,@Param("rows") int rows);

    /**
     * 根据课题状态获取课题数量
     * @param subjectStatus
     * @return
     */
    @Select("select count(subject_id) from subject where subject_status = #{subjectStatus}")
    int getTotalSizeOne(@Param("subjectStatus") SubjectStatus subjectStatus);

    @Select("select count(subject_id) from subject where subject_status = #{subjectStatus} or subject_status = #{subjectStatus2}")
    int getTotalSizeTwo(@Param("subjectStatus") SubjectStatus subjectStatus, @Param("subjectStatus2") SubjectStatus subjectStatus2);

    /**
     * 获取已通过审核的课题数量
     * @return
     */
    int getPassListCount();

    /**
     * 更新研究者手册 url
     * @param subjectId
     * @param brochureUrl
     */
    @Update("update subject set brochure_url = #{brochureUrl} where subject_id = #{subjectId}")
    void updateBrochureUrl(@Param("subjectId") Integer subjectId,@Param("brochureUrl") String brochureUrl);
}