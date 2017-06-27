package net.sinyoo.cooperation.core.emnu;

/**
 * 课题性质
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/16
 * Time: 上午9:47
 */
public enum SubjectNature {
    OBSERVE("观察性"), FORWARD("前瞻性"), RETROSPECTIVE("回顾性"),REGISTRATION("登记性");

    private String name;

    private SubjectNature(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
