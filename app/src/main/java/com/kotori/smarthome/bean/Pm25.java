package com.kotori.smarthome.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kotori on 2017/3/12.
 * 空气质量数据类
 */
public class Pm25 {

    // 加入注解，方便json解析
    @SerializedName("aqi")
    private int aqi;
    @SerializedName("area")
    private String area;
    @SerializedName("pm2_5")
    private int pm2_5;
    @SerializedName("pm2_5_24h")
    private int pm2_5_24h;
    @SerializedName("quality")
    private String quality;
    @SerializedName("primary_pollutant")
    private String primary_pollutant;
    @SerializedName("time_point")
    private String time_point;


    public Pm25(int aqi, String area, int pm2_5, int pm2_5_24h, String quality,
                String primary_pollutant, String time_point)
    {
        this.aqi = aqi;
        this.area = area;
        this.pm2_5 = pm2_5;
        this.pm2_5_24h = pm2_5_24h;
        this.quality = quality;
        this.primary_pollutant = primary_pollutant;
        this.time_point = time_point;
    }

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getPm2_5() {
        return pm2_5;
    }

    public void setPm2_5(int pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public int getPm2_5_24h() {
        return pm2_5_24h;
    }

    public void setPm2_5_24h(int pm2_5_24h) {
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

    public String getTime_point() {
        return time_point;
    }

    public void setTime_point(String time_point) {
        this.time_point = time_point;
    }




}
