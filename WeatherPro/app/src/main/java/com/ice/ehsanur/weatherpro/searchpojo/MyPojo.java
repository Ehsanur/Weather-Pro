
package com.ice.ehsanur.weatherpro.searchpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyPojo {

    @SerializedName("RESULTS")
    @Expose
    private List<RESULT> rESULTS = null;

    public List<RESULT> getRESULTS() {
        return rESULTS;
    }

    public void setRESULTS(List<RESULT> rESULTS) {
        this.rESULTS = rESULTS;
    }

}
