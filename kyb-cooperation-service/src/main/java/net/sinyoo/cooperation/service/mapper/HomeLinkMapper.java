package net.sinyoo.cooperation.service.mapper;

import net.sinyoo.cooperation.comm.model.HomeLink;
import net.sinyoo.cooperation.core.annotation.Past;
import net.sinyoo.cooperation.core.emnu.HomeLinkType;
import net.sinyoo.cooperation.service.domain.HomeLinkDo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HomeLinkMapper {
    int deleteByPrimaryKey(Integer linkId);

    int insert(HomeLinkDo record);

    int insertSelective(HomeLinkDo record);

    HomeLinkDo selectByPrimaryKey(Integer linkId);

    int updateByPrimaryKeySelective(HomeLinkDo record);

    int updateByPrimaryKey(HomeLinkDo record);

    /**
     * 根据主键获取
     * @param linkId
     * @return
     */
    @Select("select * from home_link where link_id = #{linkId}")
    @ResultMap("BaseResultMap")
    HomeLinkDo getByLinkId(@Param("linkId")int linkId);

    /**
     * 根据分类获取首页的数据
     * @param linkType
     * @param offSet
     * @param rows
     * @param searchKey
     * @return
     */
    List<HomeLinkDo> getHomeLinks(@Param("linkType") HomeLinkType linkType, @Param("offSet") int offSet, @Param("rows") int rows,@Param("searchKey") String searchKey);

    /**
     * 根据类别获取首页信息的数量
     * @param linkType
     * @return
     */
    @Select("select count(*) from home_link where link_type = #{linkType}")
    int getHomeLinkCount(@Param("linkType")HomeLinkType linkType);

    /**
     * 根据类别和条件获取首页信息的数量
     * @param type
     * @param searchKey
     * @return
     */
    @Select(" select count(link_id) from home_link where link_type = #{linkType}  and ( UPPER(title) like UPPER(CONCAT('%',#{searchKey},'%' )) ) ")
    int getHomeLinkCountBySearchKey( @Param("linkType") HomeLinkType type,@Param("searchKey") String searchKey);
}