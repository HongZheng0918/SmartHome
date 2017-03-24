package com.kotori.smarthome.interfaces;

import com.kotori.smarthome.bean.Pm25;
import com.kotori.smarthome.bean.Pm25City;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by kotori on 2017/3/12.
 * retrofit的封装类
 */
public interface IRetrofit {
        //  http://www.pm25.in/api/querys/pm2_5.json?city=guangzhou&token=abcdef&stations=no
        //     定义一个请求网络,方法返回Call<Pm25>
        //     通过@Url来指定url

        @GET()
        Call<List<Pm25>> getAir(@Url() String ip);
        @GET()
        Call<List<Pm25City>> getCityAir(@Url() String ip);

}
