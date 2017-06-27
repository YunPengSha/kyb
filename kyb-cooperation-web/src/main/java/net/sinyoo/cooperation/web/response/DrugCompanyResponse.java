package net.sinyoo.cooperation.web.response;

import com.google.gson.annotations.SerializedName;
import net.sinyoo.cooperation.core.api.ApiResponse;
import net.sinyoo.cooperation.web.to.DrugCompanyTo;

/**
 * 药企信息
 * Created by yunpengsha on 2017/4/14.
 */
public class DrugCompanyResponse extends ApiResponse{

    @SerializedName("data")
    private DrugCompanyTo drugCompanyTo;

    public DrugCompanyResponse(){

    }

    public DrugCompanyTo getDrugCompanyTo() {
        return drugCompanyTo;
    }

    public void setDrugCompanyTo(DrugCompanyTo drugCompanyTo) {
        this.drugCompanyTo = drugCompanyTo;
    }
}
