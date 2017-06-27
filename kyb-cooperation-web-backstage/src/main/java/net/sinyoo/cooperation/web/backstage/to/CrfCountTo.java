package net.sinyoo.cooperation.web.backstage.to;

import net.sinyoo.cooperation.comm.base.BaseDomain;

/**
 * Created by yunpengsha on 2017/4/11.
 */
public class CrfCountTo extends BaseDomain{

    private int waitPriceCount;

    private int havePriceCount;

    private int agreePrice;

    public CrfCountTo(){

    }

    public int getWaitPriceCount() {
        return waitPriceCount;
    }

    public void setWaitPriceCount(int waitPriceCount) {
        this.waitPriceCount = waitPriceCount;
    }

    public int getHavePriceCount() {
        return havePriceCount;
    }

    public void setHavePriceCount(int havePriceCount) {
        this.havePriceCount = havePriceCount;
    }

    public int getAgreePrice() {
        return agreePrice;
    }

    public void setAgreePrice(int agreePrice) {
        this.agreePrice = agreePrice;
    }
}
