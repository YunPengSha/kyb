package net.sinyoo.cooperation.comm.model;

import net.sinyoo.cooperation.comm.base.BaseDomain;
import net.sinyoo.cooperation.core.emnu.UserType;

import java.util.Date;

/**
 * 系统用户
 * 创建用户:     wangHui
 * 创建时间:     2017-03-16
 * Created by IntelliJ IDEA.
 */
public class User extends BaseDomain {
    private Integer userId;

    private String phone;

    private String password;

    private String userName;

    private String imageUrl;

    private Integer gender;

    private UserType userType;

    private Integer userStatus;

    private Date createTime;

    private Date modifyTime;


    public User(Integer userId , String password ){
        this.userId = userId;
        this.password = password;
        modifyTime = new Date();
    }

    public User() {

    }

    public User(String phone , String password ){
        this.phone = phone;
        this.password = password;
        modifyTime = new Date();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

}