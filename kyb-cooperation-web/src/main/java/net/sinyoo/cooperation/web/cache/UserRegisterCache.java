package net.sinyoo.cooperation.web.cache;

import net.sinyoo.cooperation.comm.model.User;

import java.util.*;

/**
 * Created by yunpengsha on 2017/4/18.
 */
public class UserRegisterCache {

    /**
     * 访问token缓存
     */
    private static Map<Integer, Long> accessTokenCache = new HashMap<>();


    /**
     *  登录用户编号缓存
     */
    private static Map<Integer, User> userRegisterCache = new HashMap<>();


    /**
     * 添加缓存   缓存20分钟
     * @param accessToken
     * @param user
     */
    public static void addCache(Integer accessToken,User user) {
        accessTokenCache.put(accessToken, System.currentTimeMillis() + 20 * 60 * 1000);
        userRegisterCache.put(accessToken,user);
    }

    /**
     * 获取登录用户编号
     * @param accessToken
     * @return
     */
    public static User getUserId(Integer accessToken){
        return userRegisterCache.get(accessToken);
    }

    /**
     * 获取是否有缓存信息
     *
     * @param accessToken
     * @return
     */
    public static boolean hasAccessToken(Integer accessToken) {
        removeToken();
        if (accessTokenCache.get(accessToken) == null) {
            return false;
        } else {
            long time = accessTokenCache.get(accessToken);
            if (time <= System.currentTimeMillis()) {
                accessTokenCache.remove(accessToken);
                userRegisterCache.remove(accessToken);
                return false;
            } else {
                return true;
            }
        }
    }


    private synchronized static void removeToken() {
        List<Integer> needRemove = new ArrayList<>();
        Set<Integer> signSet = accessTokenCache.keySet();
        for (Integer mSign : signSet) {
            Long time = accessTokenCache.get(mSign);
            if (Calendar.getInstance().getTimeInMillis() > time) {
                needRemove.add(mSign);
            }
        }
        if (needRemove.size() > 0) {
            for (Integer signStr : needRemove) {
                accessTokenCache.remove(signStr);
                userRegisterCache.remove(signStr);
            }
        }
    }


}
