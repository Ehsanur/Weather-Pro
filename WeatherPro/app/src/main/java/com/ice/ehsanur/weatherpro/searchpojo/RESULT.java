
package com.ice.ehsanur.weatherpro.searchpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RESULT {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("c")
    @Expose
    private String c;
    @SerializedName("zmw")
    @Expose
    private String zmw;
    @SerializedName("tz")
    @Expose
    private String tz;
    @SerializedName("tzs")
    @Expose
    private String tzs;
    @SerializedName("l")
    @Expose
    private String l;
    @SerializedName("ll")
    @Expose
    private String ll;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("airport")
    @Expose
    private String airport;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getZmw() {
        return zmw;
    }

    public void setZmw(String zmw) {
        this.zmw = zmw;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getTzs() {
        return tzs;
    }

    public void setTzs(String tzs) {
        this.tzs = tzs;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getLl() {
        return ll;
    }

    public void setLl(String ll) {
        this.ll = ll;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

}
