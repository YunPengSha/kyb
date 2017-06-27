package net.sinyoo.cooperation.web.backstage.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.backstage.to.SubjectInfoTo;

import java.util.List;

/**
 * 课题列表返回
 * Created by yunpengsha on 2017/4/1.
 */
public class SubjectInfoResponse extends ApiResponse{

    @SerializedName("data")
    private List<SubjectInfoTo> subjectList;

    private int totalSize;

    public SubjectInfoResponse(){

    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<SubjectInfoTo> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<SubjectInfoTo> subjectList) {
        this.subjectList = subjectList;
    }
}
