package net.sinyoo.cooperation.web.backstage.cache;

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
     *  token和验证码
     */
    private static Map<String, String> pictureIdCache = new HashMap<String, String>();


    /**
     * 添加缓存
     * @param accessToken
     * @param pCode
     */
    public static void addCache(String accessToken, String pCode) {
        pictureTokenCache.put(accessToken, Calendar.getInstance().getTimeInMillis() + 60 * 1000 );
        pictureIdCache.put(accessToken,pCode);
    }

    /**
     * 获取token的 验证码
     * @param accessToken
     * @return
     */
    public static String getPCode(String accessToken){
        return pictureIdCache.get(accessToken);
    }

    /**
     * 获取是否有缓存信息
     *
     * @param accessToken
     * @return
     */
    public static boolean hasAccessToken(String accessToken) {
        removeToken();
        if (pictureTokenCache.get(accessToken) == null) {
            return false;
        } else {
            long time = pictureTokenCache.get(accessToken);
            if (time <= System.currentTimeMillis()) {
                pictureTokenCache.remove(accessToken);
                pictureIdCache.remove(accessToken);
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
