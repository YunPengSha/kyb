package net.sinyoo.cooperation.core.emnu;

/**
 * 课题参与状态
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/16
 * Time: 上午9:47
 */
public enum SubjectPartInStatus {
    PENDING("待处理"), AGREE("已接受"), DISAGREE("未同意");

    private String name;

    private SubjectPartInStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
