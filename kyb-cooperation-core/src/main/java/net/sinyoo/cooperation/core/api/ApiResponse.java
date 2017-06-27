package net.sinyoo.cooperation.core.api;

import net.sinyoo.cooperation.core.base.BaseResponse;
import net.sinyoo.cooperation.core.base.ResponseErrorCode;
import net.sinyoo.cooperation.core.exception.ApiException;
import net.sinyoo.cooperation.core.exception.ServiceException;
import net.sinyoo.cooperation.core.util.GsonUtil;

/**
 * api返回规范类
 * 创建用户:     wangHui
 * 创建时间:     2017-03-14
 * Created by IntelliJ IDEA.
 */
public class ApiResponse extends BaseResponse {

    private Integer status = 0;

    private String errorCode;

    private String errorMessage;

    private String subCode;

    private String subMessage;

    public Integer getStatus() {
        return status;
    }

    private void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    private void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getSubMessage() {
        return subMessage;
    }

    public void setSubMessage(String subMessage) {
        this.subMessage = subMessage;
    }

    @Override
    public String getUnKnowErrorResponse(String subCode, String subMessage) {
        this.status = 1;
        this.errorCode = ResponseErrorCode.UNKNOWN_ERROR;
        this.errorMessage = ResponseErrorCode.UNKNOWN_ERROR_MESSAGE;
        this.subCode = subCode;
        this.subMessage = subMessage;
        return convertJson();
    }

    @Override
    public String getErrorResponse(String errorCode, String errorMessage, String subCode, String subMessage) {
        this.status = 1;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.subCode = subCode;
        this.subMessage = subMessage;
        return convertJson();
    }


    @Override
    public String getErrorResponse(ServiceException e) {
        this.status = 1;
        this.errorCode = ResponseErrorCode.SERVICE_ERROR;
        this.errorMessage = ResponseErrorCode.SERVICE_ERROR_MESSAGE;
        this.subCode = ApiException.getErrorCode(e.getMessage()) + "";
        this.subMessage = ApiException.getErrorMessage(e.getMessage());
        return convertJson();
    }

    @Override
    public String getErrorResponse(ApiException e) {
        this.status = 1;
        this.errorCode = ResponseErrorCode.PARAM_ERROR;
        this.errorMessage = ResponseErrorCode.PARAM_ERROR_MESSAGE;
        this.subCode = ApiException.getErrorCode(e.getMessage()) + "";
        this.subMessage = ApiException.getErrorMessage(e.getMessage());
        return convertJson();
    }

    @Override
    public String getSuccessResponse() {
        return convertJson();
    }

    private String convertJson() {
        return GsonUtil.getGsonInstance().toJson(this);
    }
}
