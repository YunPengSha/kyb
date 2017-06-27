package com.cloopen.rest;

import com.cloopen.rest.config.SMSConfiguration;
import com.cloopen.rest.sdk.CCPRestSDK;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * Created by yunpengsha on 2017/3/16.
 */
public class SendMessage {
    private static Logger logger = Logger.getLogger(SendMessage.class);

    private static volatile CCPRestSDK restAPI;

    private static CCPRestSDK getRestAPI(){
        if(restAPI == null){
            synchronized (SendMessage.class){
                if(restAPI == null){
                    restAPI = new CCPRestSDK();
                    restAPI.init(SMSConfiguration.IP,SMSConfiguration.PORT);
                    // 初始化服务器地址和端口，生产环境配置成app.cloopen.com，端口是8883.
                    restAPI.setAccount(SMSConfiguration.ACCOUNT_SID, SMSConfiguration.ACCOUNT_TOKEN);
                    // 初始化主账号名称和主账号令牌，登陆云通讯网站后，可在控制首页中看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN。
                    restAPI.setAppId(SMSConfiguration.APP_ID);
                }
                return restAPI;
            }
        }
        return restAPI;
    }

    public static boolean send(String phonenumber,String message){
        HashMap<String, Object> result = null;
        CCPRestSDK restAPI = getRestAPI();
        result = restAPI.sendTemplateSMS(phonenumber,SMSConfiguration.TEMPLATE_ID_REGISTER,new String[]{message});
        if("000000".equals(result.get("statusCode"))){
            //验证码发送成功
            return true;
        }else{
            //验证码发送失败
            logger.debug("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
            return false;
        }
    }

//    public static void main(String[] args){
//        send("17602114754","test");
//    }
}
