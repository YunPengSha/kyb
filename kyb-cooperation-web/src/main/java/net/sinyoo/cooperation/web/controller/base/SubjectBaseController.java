package net.sinyoo.cooperation.web.controller.base;

import net.sinyoo.cooperation.comm.model.Subject;
import net.sinyoo.cooperation.core.exception.ApiException;
import net.sinyoo.cooperation.web.cache.AccessTokenCache;
import net.sinyoo.cooperation.web.to.SubjectTo;
import net.sinyoo.cooperation.web.to.query.SubjectDetailTo;
import net.sinyoo.cooperation.web.utils.AccessTokenUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;

import java.util.List;

/**
 * 课题base控制器
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/4/6
 * Time: 下午1:04
 */
public class SubjectBaseController extends BaseController {


    /**
     * 设置是否是课题拥有着
     *
     * @param httpServletRequest
     * @param subjectTo
     */
    protected void setSubjectOwn(HttpServletRequest httpServletRequest, SubjectTo subjectTo) {
        if (subjectTo == null) {
            return;
        }
        int userId = 0;
        try {
            userId = getUserId(httpServletRequest);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        if (userId > 0) {
            if (userId == subjectTo.getUserId()) {
                subjectTo.setSubjectOwn(true);
            }
        }
    }

    /**
     * 设置是否是课题拥有着
     *
     * @param httpServletRequest
     * @param subjectTo
     */
    protected void setSubjectDetailOwn(HttpServletRequest httpServletRequest, SubjectDetailTo subjectTo) {
        if (subjectTo == null) {
            return;
        }
        int userId = 0;
        try {
            userId = getUserId(httpServletRequest);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        if (userId > 0) {
            if (userId == subjectTo.getUserId()) {
                subjectTo.setSubjectOwn(true);
            }
        }
    }

    /**
     * 设置是否是课题拥有着
     *
     * @param httpServletRequest
     * @param subjectTos
     */
    protected void setSubjectOwns(HttpServletRequest httpServletRequest, List<SubjectTo> subjectTos) {
        if (subjectTos == null || subjectTos.size() <= 0) {
            return;
        }
        int userId = 0;
        try {
            userId = getUserId(httpServletRequest);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        if (userId > 0) {
            for (int i = 0; i < subjectTos.size(); i++) {
                SubjectTo subjectTo = subjectTos.get(i);
                if (userId == subjectTo.getUserId()) {
                    subjectTo.setSubjectOwn(true);
                    subjectTos.set(i, subjectTo);
                }
            }
        }
    }

    /**
     * 设置是否是课题拥有着
     *
     * @param httpServletRequest
     * @param subjectTos
     */
    protected void setSubjectDetailOwns(HttpServletRequest httpServletRequest, List<SubjectDetailTo> subjectTos) {
        if (subjectTos == null || subjectTos.size() <= 0) {
            return;
        }

        int userId = 0;
        try {
            userId = getUserId(httpServletRequest);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        if (userId > 0) {
            for (int i = 0; i < subjectTos.size(); i++) {
                SubjectDetailTo subjectTo = subjectTos.get(i);
                if (userId == subjectTo.getUserId()) {
                    subjectTo.setSubjectOwn(true);
                    subjectTos.set(i, subjectTo);
                }
            }
        }
    }

    /**
     * 查看别人主页时候调用此方法
     *
     * @param userId
     * @param subjectTos
     */
    protected void setSubjectOwnsByUserId(Integer userId, List<SubjectTo> subjectTos) {
        if (subjectTos == null || subjectTos.size() <= 0) {
            return;
        }
        for (int i = 0; i < subjectTos.size(); i++) {
            SubjectTo subjectTo = subjectTos.get(i);
            if (userId.equals(subjectTo.getUserId())) {
                subjectTo.setSubjectOwn(true);
                subjectTos.set(i, subjectTo);
            }
        }
    }

}
