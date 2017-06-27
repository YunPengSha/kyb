package net.sinyoo.cooperation.core.emnu;

/**
 * 课题排除 纳入标准
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/16
 * Time: 上午9:47
 */
public enum SubjectStandardType {
    EXCLUDE("排除标准"), INCLUDE("纳入标准");

    private String name;

    private SubjectStandardType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
