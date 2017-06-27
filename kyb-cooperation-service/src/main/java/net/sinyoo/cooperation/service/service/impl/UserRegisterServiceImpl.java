package net.sinyoo.cooperation.service.service.impl;

import com.cloopen.rest.SendMessage;
import net.sinyoo.cooperation.comm.service.UserRegisterService;
import net.sinyoo.cooperation.core.emnu.SmsCodeType;
import net.sinyoo.cooperation.core.emnu.UserType;
import net.sinyoo.cooperation.core.exception.ServiceException;
import net.sinyoo.cooperation.core.util.Random;
import net.sinyoo.cooperation.core.validator.MyValidator;
import net.sinyoo.cooperation.service.domain.SmsCodeDo;
import net.sinyoo.cooperation.service.domain.UserDo;
import net.sinyoo.cooperation.service.mapper.SmsCodeMapper;
import net.sinyoo.cooperation.service.mapper.UserMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by yunpengsha on 2017/3/16.
 */
@Component
@Service("userRegisterService")
public class UserRegisterServiceImpl implements UserRegisterService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private SmsCodeMapper smsCodeMapper;

    @Override
    public void sendRegisterMessage(String phoneNumber, UserType userType) throws ServiceException {
        if (!MyValidator.isMobile(phoneNumber)) {
            throw new ServiceException(601, "请输入正确的手机号码");
        }
        int userId = 0;
        //判断之前是否注册过
        UserDo user = userMapper.selectByPhoneAndType(phoneNumber,userType);
        if (user != null) {
            if (user.getPassword() != null) {
                throw new ServiceException(603, "用户已经注册过");
            }
        }
        String message = Random.randomKey(6);
        boolean flag = SendMessage.send(phoneNumber, message);
        if (!flag) {
            throw new ServiceException(602, "发送短信验证码失败");
        }
        //添加用户
        if (user == null) {
            UserDo userDo = new UserDo();
            userDo.setPhone(phoneNumber);
            userDo.setUserType(userType);
            userMapper.insertSelective(userDo);
            userId = userDo.getUserId();
        } else {
            userId = user.getUserId();
            //删除旧的验证码
            smsCodeMapper.deleteByUserId(userId);
        }
    //    smsCodeMapper.updateExpire(userId, SmsCodeType.REGISTER_CODE);
        //添加验证码
        SmsCodeDo smsCodeDo = new SmsCodeDo(userId, message, 2 * 60 * 1000);
        smsCodeDo.setSmsCodeType(SmsCodeType.REGISTER_CODE);
        smsCodeMapper.insertSelective(smsCodeDo);
    }

    /**
     * 忘记密码调用本接口
     * @param phoneNumber
     * @param userType
     * @throws ServiceException
     */
    @Override
    public void sendRegisterMessageResetPassword(String phoneNumber, UserType userType) throws ServiceException {

        if (!MyValidator.isMobile(phoneNumber)) {
            throw new ServiceException(601, "请输入正确的手机号码");
    }
        int userId = 0;
        //判断之前是否注册过
        UserDo user = userMapper.selectByPhoneAndType(phoneNumber,userType);
        if (user == null || user.getPassword() != null ) {
                throw new ServiceException(603, "用户还没注册过");
        }
        String message = Random.randomKey(6);
        boolean flag = SendMessage.send(phoneNumber, message);
        if (!flag) {
            throw new ServiceException(602, "发送短信验证码失败");
        }
        //删除旧的验证码
        smsCodeMapper.deleteByUserId(userId);
        //    smsCodeMapper.updateExpire(userId, SmsCodeType.REGISTER_CODE);
        //添加验证码
        SmsCodeDo smsCodeDo = new SmsCodeDo(userId, message, 2 * 60 * 1000);
        smsCodeDo.setSmsCodeType(SmsCodeType.REGISTER_CODE);
        smsCodeMapper.insertSelective(smsCodeDo);

    }
}
