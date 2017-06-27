package net.sinyoo.cooperation.service.mapper;

import net.sinyoo.cooperation.core.emnu.CrfStatus;
import net.sinyoo.cooperation.service.domain.CrfDo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.Date;
import java.util.List;

/**
 * Created by yunpengsha on 2017/4/5.
 */
public interface CrfMapper {

    int deleteByPrimaryKey(Integer crfId);

    @SelectKey(before = false, keyProperty = "crfId", resultType = Integer.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS crfId")
    int insert(CrfDo record);

    @SelectKey(before = false, keyProperty = "crfId", resultType = Integer.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS crfId")
    int insertSelective(CrfDo record);

    CrfDo selectByPrimaryKey(Integer crfId);


    /**
     * 获取课题的报价记录
     * @param subjectId
     * @return
     */
    @Select("select * from crf where subject_id = #{subjectId} LIMIT 0,1")
    @ResultMap("BaseResultMap")
    CrfDo getBySubjectId(@Param("subjectId")int subjectId);


    /**
     * 更新crf表状态
     * @param subjectId
     * @param crfStatus
     * @return
     */
    @Update(
            "update crf set crf_status = #{crfStatus} where subject_id = #{subjectId}"
    )
    int updateCrfStatus(@Param("subjectId")int subjectId,@Param("crfStatus")CrfStatus crfStatus);

    void addAgreeTime(@Param("subjectId") int subjectId,@Param("agreeTime") Date date);

    List<CrfDo> getWaitPriceList(@Param("offSet") int offSet,@Param("rows") int rows);

    List<CrfDo> getHavePriceList(@Param("offSet") int offSet,@Param("rows") int rows);

    List<CrfDo> getAgreePriceList(@Param("offSet") int offSet,@Param("rows") int rows);

    @Select("select count(crf_id) from crf where crf_status = #{type}")
    int getTotalSize(@Param("type") CrfStatus type);

    /**
     * 添加价格 修改状态
     * @param subjectId
     * @param price
     */
    @Update(
            "update crf set price = #{price} , crf_status = \"HAVE_PRICE\"  where subject_id = #{subjectId}"
    )
    void addCrfPrice(@Param("subjectId") int subjectId,@Param("price") Double price);
}
