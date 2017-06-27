package net.sinyoo.cooperation.web.backstage.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.comm.service.SubjectMessageService;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.backstage.to.SubjectCountTo;

/**
 * Created by yunpengsha on 2017/4/10.
 */
public class SubjectCountResponse extends ApiResponse{

    @SerializedName("data")
    private SubjectCountTo subjectCountTo;

    public SubjectCountResponse(){

    }

    public SubjectCountTo getSubjectCountTo() {
        return subjectCountTo;
    }

    public void setSubjectCountTo(SubjectCountTo subjectCountTo) {
        this.subjectCountTo = subjectCountTo;
    }
}
