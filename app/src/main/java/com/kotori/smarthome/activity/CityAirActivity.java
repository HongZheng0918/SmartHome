package com.kotori.smarthome.activity;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.kotori.smarthome.R;
import com.kotori.smarthome.adapter.AirAdapter;
import com.kotori.smarthome.bean.Pm25City;
import com.kotori.smarthome.interfaces.IRetrofit;
import com.kotori.smarthome.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CityAirActivity extends Activity {

    // 空气质量接口
    private String url ="api/querys/pm2_5.json?city=guangzhou&token=5j1znBVAsnSf5xQyNQyq";
    private RecyclerView recyclerView;
    private List<Pm25City> mDataList;
    private List<Pm25City> mNewDataList;
    private AirAdapter mAdapter;
    private TextView mUpdatetv;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what) {
                case  1:
                    updateView();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_city_air);

        initView();
    }


    // 初始化界面
    private void initView() {
        if(mDataList == null) {
            initData();
        }
        mUpdatetv = (TextView) findViewById(R.id.air_update);
        recyclerView = (RecyclerView) findViewById(R.id.air_recy);
        mAdapter = new AirAdapter(this,mDataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        mUpdatetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    private void updateView(){
        mAdapter = new AirAdapter(this,mNewDataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
    }


    // 获取数据
    public void getData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.pm25.in/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        IRetrofit mRetrofit = retrofit.create(IRetrofit.class);
        Call<List<Pm25City>> call = mRetrofit.getCityAir(url);
        call.enqueue(new Callback<List<Pm25City>>() {
            @Override
            public void onResponse(Call<List<Pm25City>> call, Response<List<Pm25City>> response) {
                Log.v("you","请求成功");
                mNewDataList = response.body();
                Log.v("you",mNewDataList.toString());
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);

            }

            @Override
            public void onFailure(Call<List<Pm25City>> call, Throwable t) {
                Log.v("you","请求失败"+t);
                initData();
            }
        });
    }

    // 添加初始数据
    private void initData() {
        Pm25City pm1 = new Pm25City("50","广州","70","60","良","PM2.5颗粒物","9:00","ABC","体育中心");
        Pm25City pm2 = new Pm25City("50","广州","45","40","优","无","9:00","ABC","北京路");
        Pm25City pm3 = new Pm25City("50","广州","30","40","优","无","9:00","ABC","广州塔");
        mDataList = new ArrayList<Pm25City>();
        mDataList.add(pm1);
        mDataList.add(pm2);
        mDataList.add(pm3);

    }

}
