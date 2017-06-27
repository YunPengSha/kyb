package net.sinyoo.cooperation.core.emnu;

/**
 * crf报价状态
 */
public enum CrfStatus {

  WAIT_PRICE("等待报价"),HAVE_PRICE("已经报价"),PRICE_AGREE("同意报价"),PRICE_REFUSE("拒绝报价");

  private String name;

  private CrfStatus(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}


