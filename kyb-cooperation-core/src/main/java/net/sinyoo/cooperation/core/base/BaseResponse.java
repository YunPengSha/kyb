package net.sinyoo.cooperation.core.base;

import net.sinyoo.cooperation.core.exception.ApiException;
import net.sinyoo.cooperation.core.exception.ServiceException;

import java.io.Serializable;

/**
 * Created by yunpengsha on 2017/3/14.
 */
public abstract class BaseResponse implements Serializable {

    /**
     * 获取失败的json
     * @param subCode
     * @param subMessage
     * @return
     */
    public abstract String getUnKnowErrorResponse(String subCode, String subMessage);

    /**
     * 获取失败的json
     * @param errorCode
     * @param errorMessage
     * @param subCode
     * @param subMessage
     * @return
     */
    public abstract String getErrorResponse(String errorCode,String errorMessage,String subCode,String subMessage);



    /**
     * 参数校验失败错误
     * @param e
     * @return
     */
    public abstract String getErrorResponse(ApiException e);

    /**
     * 服务层抛出错误转换成接口对应的错误信息
     * @param e
     * @return
     */
    public abstract String getErrorResponse(ServiceException e);


    /**
     * 转换成功的json
     * @return
     */
    public abstract String getSuccessResponse();
}
