package net.sinyoo.cooperation.core.emnu;

/**
 * 首页链接类型
 * Created with IntelliJ IDEA.
 * User: mac
 * Date: 17/3/16
 * Time: 上午9:47
 */
public enum HomeLinkType {
    BANNER("banner"), DOCTOR("入驻医生"), DRUG_COMPANY("药企");

    private String name;

    private HomeLinkType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
