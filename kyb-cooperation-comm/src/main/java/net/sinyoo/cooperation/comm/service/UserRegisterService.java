package net.sinyoo.cooperation.comm.service;

import net.sinyoo.cooperation.core.emnu.UserType;
import net.sinyoo.cooperation.core.exception.ServiceException;

/**
 * Created by yunpengsha on 2017/3/16.
 */
public interface UserRegisterService {
    void sendRegisterMessage(String phoneNumber, UserType userType) throws ServiceException;

    void sendRegisterMessageResetPassword(String phone, UserType userType) throws ServiceException;
}
