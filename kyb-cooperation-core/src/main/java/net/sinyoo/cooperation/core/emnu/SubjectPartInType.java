package net.sinyoo.cooperation.core.emnu;

/**
 * 参与类别
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/16
 * Time: 上午9:47
 */
public enum SubjectPartInType {
    INVITATION("邀请"), APPLY("申请");

    private String name;

    private SubjectPartInType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
