package net.sinyoo.cooperation.web.cache;

import net.sinyoo.cooperation.core.util.UploadUtils;

import java.util.*;

/**
 * 图像验证码缓存池
 * Created by yunpengsha on 2017/3/23.
 */
public class PictureTokenCache {
    /**
     * 访问token缓存
     */
    private static Map<String, Long> pictureTokenCache = new HashMap<>();


    /**
     * token和验证码
     */
    private static Map<String, String> pictureIdCache = new HashMap<String, String>();


    /**
     * 添加缓存
     *
     * @param accessToken
     * @param pCode
     */
    public static void addCache(String pictureToken, String pCode) {
        pictureTokenCache.put(pictureToken, Calendar.getInstance().getTimeInMillis() + 60 * 1000);
        pictureIdCache.put(pictureToken, pCode);
    }

    /**
     * 获取token的 验证码
     *
     * @param pictureToken
     * @return
     */
    public static String getPCode(String pictureToken) {
        return pictureIdCache.get(pictureToken);
    }

    /**
     * 获取是否有缓存信息
     *
     * @param pictureToken
     * @return
     */
    public static boolean hasPictureToken(String pictureToken) {
        removeToken();
        if (pictureTokenCache.get(pictureToken) == null) {
            return false;
        } else {
            long time = pictureTokenCache.get(pictureToken);
            if (time <= System.currentTimeMillis()) {
                pictureTokenCache.remove(pictureToken);
                pictureIdCache.remove(pictureToken);
                return false;
            } else {
                return true;
            }
        }
    }


    private synchronized static void removeToken() {
        List<String> needRemove = new ArrayList<>();
        Set<String> signSet = pictureTokenCache.keySet();
        for (String mSign : signSet) {
            Long time = pictureTokenCache.get(mSign);
            if (Calendar.getInstance().getTimeInMillis() > time) {
                needRemove.add(mSign);
            }
        }
        if (needRemove.size() > 0) {
            UploadUtils upload = new UploadUtils();
            for (String signStr : needRemove) {
                pictureTokenCache.remove(signStr);
                pictureIdCache.remove(signStr);
            }
        }
    }

//    public static void main(String[] args){
//        addCache("as","asd");
//        System.out.println(hasAccessToken("as"));
//    }
}
