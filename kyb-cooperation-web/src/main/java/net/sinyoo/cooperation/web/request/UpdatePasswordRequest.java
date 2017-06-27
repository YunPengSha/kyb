package net.sinyoo.cooperation.web.request;

import net.sinyoo.cooperation.core.annotation.ApiField;
import net.sinyoo.cooperation.core.annotation.NotEmpty;
import net.sinyoo.cooperation.core.annotation.NotNull;
import net.sinyoo.cooperation.core.api.ApiRequest;

/**
 * Created by yunpengsha on 2017/3/21.
 */
public class UpdatePasswordRequest extends ApiRequest{
    @ApiField
    @NotNull(message = "用户id不能为空")
    private Integer userId;

    @ApiField
    @NotEmpty(message = "旧密码不能为空")
    private String oldPassword;
    @ApiField
    @NotEmpty(message = "新密码不能为空")
    private String newPasswordOne;
    @ApiField
    @NotEmpty(message = "重复密码不能为空")
    private String newPasswordTwo;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPasswordOne() {
        return newPasswordOne;
    }

    public void setNewPasswordOne(String newPasswordOne) {
        this.newPasswordOne = newPasswordOne;
    }

    public String getNewPasswordTwo() {
        return newPasswordTwo;
    }

    public void setNewPasswordTwo(String newPasswordTwo) {
        this.newPasswordTwo = newPasswordTwo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UpdatePasswordRequest{" +
                "userId=" + userId +
                '}';
    }
}
