package net.sinyoo.cooperation.web.cache;

import java.util.*;

/**
 * 用户获取短信验证码缓存
 * 创建用户:     wangHui
 * 创建时间:     2017-03-21
 * Created by IntelliJ IDEA.
 */
public class SmsCodeRequestCache {

    /**
     * 获取短信验证码的手机号码缓存
     */
    private static Map<String, Long> smsPhoneCache = new HashMap<>();

    /**
     * 获取短信验证码的ip地址缓存
     */
    private static Map<String, Long> smsIpCache = new HashMap<>();

    /**
     * 添加缓存
     *
     * @param path        url路径标识 例如注册用register
     * @param phoneNumber 手机号码
     * @param ipAddress   ip地址
     */
    public static void addCache(String path, String phoneNumber, String ip) {
        smsPhoneCache.put(path + "_" + phoneNumber, Calendar.getInstance().getTimeInMillis() + 50000);
        smsIpCache.put(path + "_" + ip, Calendar.getInstance().getTimeInMillis() + 50000);
    }

    /**
     * 获取是否可以发短信
     *
     * @param path  url路径标识 例如注册用register
     * @param phone 手机号码
     * @return
     */
    public synchronized static boolean canSendSms(String path, String phone, String ip) {
        removeCache();
        String phoneNumber = path + "_" + phone;
        String ipAddress = path + '_' + ip;
        return checkPhone(phoneNumber) && checkIp(ipAddress);
    }


    /**
     * 检测手机号码
     *
     * @param phoneNumber
     * @return
     */
    private static boolean checkPhone(String phoneNumber) {
        //没有缓存  可以发送短信
        return smsPhoneCache.get(phoneNumber) == null;
    }


    /**
     * 检测ip地址
     *
     * @param ipAddress
     * @return
     */
    private static boolean checkIp(String ipAddress) {
        //没有缓存  可以发送短信
        return smsIpCache.get(ipAddress) == null;
    }


    private static void removeCache() {
        //清除五十秒前的数据
        List<String> needRemove = new ArrayList<>();
        Set<String> signSet = smsPhoneCache.keySet();
        for (String mSign : signSet) {
            Long time = smsPhoneCache.get(mSign);
            if (Calendar.getInstance().getTimeInMillis() > time) {
                needRemove.add(mSign);
            }
        }
        if (needRemove.size() > 0) {
            for (String signStr : needRemove) {
                smsPhoneCache.remove(signStr);
            }
        }


        //清除五十秒前的数据
        needRemove = new ArrayList<>();
        signSet = smsIpCache.keySet();
        for (String mSign : signSet) {
            Long time = smsIpCache.get(mSign);
            if (Calendar.getInstance().getTimeInMillis() > time) {
                needRemove.add(mSign);
            }
        }
        if (needRemove.size() > 0) {
            for (String signStr : needRemove) {
                smsIpCache.remove(signStr);
            }
        }
    }
}
