package net.sinyoo.cooperation.web.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.to.DepartmentTo;

/**
 * 科室信息返回
 * Created by yunpengsha on 2017/4/14.
 */
public class DepartmentInfoResponse extends ApiResponse{

    @SerializedName("data")
    private DepartmentTo departmentTo;

    public DepartmentInfoResponse(){

    }

    public DepartmentTo getDepartmentTo() {
        return departmentTo;
    }

    public void setDepartmentTo(DepartmentTo departmentTo) {
        this.departmentTo = departmentTo;
    }
}
