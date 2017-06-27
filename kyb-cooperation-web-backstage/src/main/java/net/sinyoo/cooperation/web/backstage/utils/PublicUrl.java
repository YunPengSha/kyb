package net.sinyoo.cooperation.web.backstage.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * 添加publicUrl   访问他不需要登录  不需要token
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/14
 * Time: 下午1:39
 */
public class PublicUrl {

    /**
     * 公共的uri不需要权限验证的
     */
    public static final Set<String> publicUris = new HashSet<String>();

    static {
        publicUris.add("/user/login");// 用户登录
        publicUris.add("/imageVerification/getPicture");// 获得图片验证码
    //  publicUris.add("/imageVerification/validatePictureCode");//验证图片验证码
        publicUris.add("/imageVerification/getPictureAgain/");//获得图片验证码
        publicUris.add("/user/checkLogin");//验证用户是否登录

        //获取医生信息和课题信息
        publicUris.add("/user/doctorInfo");
        publicUris.add("/subjectList/userList");
        publicUris.add("/subjectPartIn/agreeList");
        publicUris.add("/subject/subjectDetail");

    }

    /**
     * 判断是否可以直接访问的uri地址<br>
     * 将来应该使用cdn的方式，不能每次请求都进入java，这样效率太低
     *
     * @param uri
     * @return
     */
    public static synchronized boolean isPublicUri(String uri) {
        if (uri.equals("/"))
            return true;
        for (String _uri : publicUris) {
            if (uri.startsWith(_uri)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args){
        System.out.println(isPublicUri("/userRegister/getSmsCode/18888888888"));
    }
}

