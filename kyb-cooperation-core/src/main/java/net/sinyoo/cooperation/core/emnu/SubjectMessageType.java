package net.sinyoo.cooperation.core.emnu;

/**
 * 课题消息类型
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/29
 * Time: 上午16:47
 */
public enum SubjectMessageType {
    APPLY_SUCCESS("申请成功"), APPLY_FAIL("申请失败"), AUDIT_FAIL("审核失败");

    private String name;

    private SubjectMessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
