package net.sinyoo.cooperation.core.emnu;

/**
 * 验证码类型类型
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/16
 * Time: 上午9:47
 */
public enum SmsCodeType {
    REGISTER_CODE("注册验证码");

    private String name;

    private SmsCodeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
