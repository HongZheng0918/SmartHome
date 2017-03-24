package com.kotori.smarthome.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kotori.smarthome.R;


/**
 * Created by kotori on 2017/3/15.
 * PM25 数据封装类
 */
public class Pm25ViewHolder extends RecyclerView.ViewHolder{

    public TextView tv_air_local;               // 监测点
    public TextView tv_local_test;              // 空气等级
    public TextView tv_local_aqi;               // 空气指数
    public TextView tv_local_pm25;              // pm2.5指数
    public TextView tv_local_rubbish;           // 首要污染物


    public Pm25ViewHolder(View itemView) {
        super(itemView);
        tv_air_local = (TextView) itemView.findViewById(R.id.tv_air_local);
        tv_local_test = (TextView) itemView.findViewById(R.id.tv_local_test);
        tv_local_aqi = (TextView) itemView.findViewById(R.id.tv_local_aqi);
        tv_local_pm25 = (TextView) itemView.findViewById(R.id.tv_local_pm25);
        tv_local_rubbish = (TextView) itemView.findViewById(R.id.tv_local_rubbish);
    }
}
