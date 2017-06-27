package net.sinyoo.cooperation.core.emnu;

/**
 * 用户类型
 */
public enum UserType {

  DOCTOR("医生"),ADMIN("管理员"),DRUG_COMPANY("药企"),DEPARTMENT("科室");

  private String name;

  private UserType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
