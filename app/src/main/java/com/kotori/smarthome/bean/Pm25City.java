package com.kotori.smarthome.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kotori on 2017/3/12.
 * 附近空气质量数据类
 */
public class Pm25City {

    // 加入注解，方便gson解析
    @SerializedName("aqi")
    private String aqi;
    @SerializedName("area")
    private String area;
    @SerializedName("pm2_5")
    private String pm2_5;
    @SerializedName("pm2_5_24h")
    private String pm2_5_24h;
    @SerializedName("quality")
    private String quality;
    @SerializedName("primary_pollutant")
    private String primary_pollutant;
    @SerializedName("time_poString")
    private String time_poString;
    @SerializedName("station_code")
    private String station_code;
    @SerializedName("position_name")
    private String position_name;


    public Pm25City(String aqi, String area, String pm2_5, String pm2_5_24h, String quality,
                    String primary_pollutant, String time_poString,
                    String station_code, String position_name) {
        this.aqi = aqi;
        this.area = area;
        this.pm2_5 = pm2_5;
        this.pm2_5_24h = pm2_5_24h;
        this.quality = quality;
        this.primary_pollutant = primary_pollutant;
        this.time_poString = time_poString;
        this.station_code = station_code;
        this.position_name = position_name;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPm2_5() {
        return pm2_5;
    }

    public void setPm2_5(String pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public String getPm2_5_24h() {
        return pm2_5_24h;
    }

    public void setPm2_5_24h(String pm2_5_24h) {
        this.pm2_5_24h = pm2_5_24h;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getPrimary_pollutant() {
        return primary_pollutant;
    }

    public void setPrimary_pollutant(String primary_pollutant) {
        this.primary_pollutant = primary_pollutant;
    }

    public String getTime_poString() {
        return time_poString;
    }

    public void setTime_poString(String time_poString) {
        this.time_poString = time_poString;
    }

    public String getStation_code() {
        return station_code;
    }

    public void setStation_code(String station_code) {
        this.station_code = station_code;
    }

    public String getPosition_name() {
        return position_name;
    }

    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }


}
