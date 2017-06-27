package net.sinyoo.cooperation.web.backstage.to;

import net.sinyoo.cooperation.comm.base.BaseDomain;
import net.sinyoo.cooperation.comm.model.Subject;

/**
 * Created by yunpengsha on 2017/4/11.
 */
public class SubjectCountTo extends BaseDomain{

    private int waitCount;

    private int successCount;

    private int failCount;

    public SubjectCountTo(){

    }

    public int getWaitCount() {
        return waitCount;
    }

    public void setWaitCount(int waitCount) {
        this.waitCount = waitCount;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }
}
