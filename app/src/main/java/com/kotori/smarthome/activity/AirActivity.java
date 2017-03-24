package com.kotori.smarthome.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kotori.smarthome.R;
import com.kotori.smarthome.adapter.MyListAdapter;
import com.kotori.smarthome.bean.Pm25;
import com.kotori.smarthome.interfaces.IRetrofit;
import com.kotori.smarthome.util.ActivityUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AirActivity extends Activity {

    private TextView mAqltv;
    private TextView mAreatv;
    private TextView mPm25tv;
    private TextView mTesttv;
    private TextView mTimetv;
    private TextView mRubbishtv;

    private ListView mAirlv;
    private String [] mData = {"刷新当前城市","获取周边空气质量"};
    private String url ="api/querys/pm2_5.json?city=guangzhou&token=5j1znBVAsnSf5xQyNQyq&stations=no";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_air);
        initView();
        setAdpater();

    }

    // 实例化控件
    private void initView() {
        mAirlv = (ListView) findViewById(R.id.list_air);
        mAqltv = (TextView) findViewById(R.id.air_aqi);
        mAreatv= (TextView) findViewById(R.id.air_area);
        mPm25tv= (TextView) findViewById(R.id.air_pm2_5);
        mTesttv= (TextView) findViewById(R.id.air_test);
        mTimetv= (TextView) findViewById(R.id.air_time);
        mRubbishtv= (TextView) findViewById(R.id.air_rubbish);
    }

    private void setAdpater() {
        MyListAdapter adapter = new MyListAdapter(AirActivity.this,mData);
        mAirlv.setAdapter(adapter);
        mAirlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Log.v("you","点击请求");
                        Retrofit retrofit = new Retrofit.Builder().
                                baseUrl("http://www.pm25.in/")
                                // 使用Gson作为数据转换器
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        IRetrofit myRetrofit = retrofit.create(IRetrofit.class);
                        Call<List<Pm25>> call = myRetrofit.getAir(url);
                        call.enqueue(new Callback<List<Pm25>>() {
                            @Override
                            public void onResponse(Call<List<Pm25>> call, Response<List<Pm25>> response) {
                                Log.v("you","请求成功");
                                List<Pm25> list= response.body();
                                Pm25 p1 = list.get(0);
                                mAqltv.setText(p1.getAqi()+"");
                                mAreatv.setText(p1.getArea());
                                mPm25tv.setText(p1.getPm2_5()+"");
                                mTesttv.setText(p1.getQuality());
                                mRubbishtv.setText(p1.getPrimary_pollutant());
                                mTimetv.setText(p1.getTime_point()+"");
                            }

                            @Override
                            public void onFailure(Call<List<Pm25>> call, Throwable t) {
                                Log.v("you","请求失败"+t);
                                mAqltv.setText("60");
                                mAreatv.setText("广州");
                                mPm25tv.setText("60");
                                mTesttv.setText("良");
                                mRubbishtv.setText("颗粒物(PM2.5)");
                                mTimetv.setText("12:00");
                            }
                        });
                        break;
                    case 1:
                        ActivityUtil.startActivity(AirActivity.this,CityAirActivity.class,false);
                        break;
                }
            }
        });
    }




}
