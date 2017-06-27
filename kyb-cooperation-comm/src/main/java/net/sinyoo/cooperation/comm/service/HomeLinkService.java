package net.sinyoo.cooperation.comm.service;

import net.sinyoo.cooperation.comm.model.HomeLink;
import net.sinyoo.cooperation.core.emnu.HomeLinkType;

import java.util.List;

/**
 * 首页数据服务
 * 创建用户:     wangHui
 * 创建时间:     2017-03-21
 * Created by IntelliJ IDEA.
 */
public interface HomeLinkService {

    /**
     * 获取首页用户数据
     * @param linkType
     * @param pages
     * @param pageSize
     * @param searchKey
     * @return
     */
    List<HomeLink> findHomeLinks(HomeLinkType linkType, int pages, int pageSize, String searchKey);

    /**
     * 获取数据的数量
     * @param linkType
     * @return
     */
    int findHomeLinkCount(HomeLinkType linkType);


    /**
     * 根据Id获取数据
     * @param linkId
     * @return
     */
    HomeLink findByLinkId(int linkId);

    /**
     *  根据类型和条件获取数量
     * @param doctor
     * @param searchKey
     * @return
     */
    int findHomeLinkCountBySearchKey(HomeLinkType type, String searchKey);
}
