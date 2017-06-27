package net.sinyoo.cooperation.core.emnu;

/**
 * 课题状态
 */
public enum SubjectStatus {

  CREATE("创建"),AUDIT("审核中"),AUDIT_FAIL("审核失败"),PUBLISH("发布"),WAIT_PRICE("等待报价"),HAVE_PRICE("已经报价"),PRICE_AGREE("同意报价"),PRICE_REFUSE("拒绝报价"),WAIT_CHECK("等待验收"),HAVE_CHECK("同意验收"),COMPLETE("完成"),DELETED("已删除");

  private String name;

  private SubjectStatus(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}


