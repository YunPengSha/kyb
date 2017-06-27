package net.sinyoo.cooperation.service.service.impl;

import net.sinyoo.cooperation.comm.model.HomeLink;
import net.sinyoo.cooperation.comm.service.HomeLinkService;
import net.sinyoo.cooperation.core.emnu.HomeLinkType;
import net.sinyoo.cooperation.core.util.MyBeanUtils;
import net.sinyoo.cooperation.service.domain.HomeLinkDo;
import net.sinyoo.cooperation.service.mapper.HomeLinkMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yunpengsha on 2017/3/13.
 */
@Component
@Service("homeLinkService")
public class HomeLinkServiceImpl implements HomeLinkService {


    @Resource
    private HomeLinkMapper homeLinkMapper;

    @Override
    public List<HomeLink> findHomeLinks(HomeLinkType linkType, int pages, int pageSize, String searchKey) {
        return new MyBeanUtils<HomeLinkDo, HomeLink>().copyList(homeLinkMapper.getHomeLinks(linkType, pages * pageSize, pageSize,searchKey), HomeLink.class);
    }

    @Override
    public int findHomeLinkCount(HomeLinkType linkType) {
        return homeLinkMapper.getHomeLinkCount(linkType);
    }

    @Override
    public HomeLink findByLinkId(int linkId) {
        HomeLinkDo homeLinkDo = homeLinkMapper.getByLinkId(linkId);
        return new MyBeanUtils<HomeLinkDo,HomeLink>().copyBean(homeLinkDo,HomeLink.class);
    }

    @Override
    public int findHomeLinkCountBySearchKey(HomeLinkType type, String searchKey) {
        return homeLinkMapper.getHomeLinkCountBySearchKey( type,searchKey );
    }

}
