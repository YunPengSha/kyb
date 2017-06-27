package net.sinyoo.cooperation.web.cache;

import net.sinyoo.cooperation.comm.model.query.UserDoctorQuery;

import java.util.*;

/**
 * 用户缓存
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/21
 * Time: 下午4:34
 */
public class UserDoctorCache {
    /**
     * <p>用户基本信息缓存</p>
     * <p>key 用户编号</p>
     * <p>value 用户对象</p>
     */
    private static Map<Integer, UserDoctorQuery> userMap = new HashMap<>();


    /**
     * <p>用户过期时间</p>
     * <p>key 用户编号</p>
     * <p>value 过期时间</p>
     */
    private static Map<Integer, Long> userExpire = new HashMap<>();


    /**
     * 添加缓存
     * <p>缓存两个小时</p>
     *
     * @param userId 用户编号
     * @param user   用户对象
     */
    public static void addCache(Integer userId, UserDoctorQuery user) {
        userMap.put(userId, user);
        userExpire.put(userId, Calendar.getInstance().getTimeInMillis() + 2 * 60 * 60 * 1000);
    }


    /**
     * 获取是否有缓存信息
     *
     * @param userId 用户编号
     * @return 返回缓存的用户
     */
    public static UserDoctorQuery hasUser(Integer userId) {
        removeUser();
        if (userMap.get(userId) == null) {
            return null;
        } else {
            long time = userExpire.get(userId);
            if (time <= System.currentTimeMillis()) {
                userMap.remove(userId);
                userExpire.remove(userId);
                return null;
            } else {
                userMap.get(userId);
            }
        }
        return null;
    }


    /**
     * 移除过期的用户
     */
    private synchronized static void removeUser() {
        List<Integer> needRemove = new ArrayList<>();
        Set<Integer> userIdSet = userMap.keySet();
        for (Integer userId : userIdSet) {
            Long time = userExpire.get(userId);
            if (Calendar.getInstance().getTimeInMillis() > time) {
                needRemove.add(userId);
            }
        }
        if (needRemove.size() > 0) {
            for (Integer userId : needRemove) {
                userMap.remove(userId);
                userExpire.remove(userId);
            }
        }
    }

}
