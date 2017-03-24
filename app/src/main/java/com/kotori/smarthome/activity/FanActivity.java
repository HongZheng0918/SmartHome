package com.kotori.smarthome.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kotori.smarthome.R;
import com.kotori.smarthome.util.IsServiceRunningUtil;
import com.kotori.smarthome.util.ToastUtil;

public class FanActivity extends Activity {

    private ImageView mFans;
    private ImageView mFansd;
    private ImageView mFansu;
    private TextView mFanState;
    private TextView mFanSwitch;
    private int mFanStateId;            // 风扇风力状态
    private int mFanSwitchId;             // 风扇开关状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan);
        init();
        setClick();
    }
    //实例化控件
    private void init() {
        mFans = (ImageView) findViewById(R.id.fan_switch);
        mFansd = (ImageView) findViewById(R.id.fan_switch_down);
        mFansu = (ImageView) findViewById(R.id.fan_switch_up);
        mFanState = (TextView) findViewById(R.id.fan_state);
        mFanSwitch = (TextView) findViewById(R.id.fan_test);
    }

    // 设置点击事件
    private void setClick() {


        mFans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRunning = IsServiceRunningUtil.
                        isServiceRunning(FanActivity.this, "com.kotori.smarthome.service.LinkService");
                if (isRunning) {
                    if(mFanSwitchId == 0) {
                        mFanSwitch.setText("开启");
                        mFanSwitchId =1;
                        mFanState.setText("1");
                        mFanStateId =1;
                        sendBroadcast("s");
                    }
                    else{
                        mFanSwitch.setText("关闭");
                        mFanSwitchId =0;
                        mFanState.setText("0");
                        mFanStateId =0;
                        sendBroadcast("z");
                    }
                } else {
                    ToastUtil.showToast(FanActivity.this, "启动失败，暂未接入网络");
                }

            }
        });
        mFansd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRunning = IsServiceRunningUtil.
                        isServiceRunning(FanActivity.this, "com.kotori.smarthome.service.LinkService");
                if (isRunning) {
                    if(mFanStateId == 2) {
                        mFanState.setText("1");
                        mFanStateId =1;
                    }
                    else if(mFanStateId == 1) {
                        mFanState.setText("0");
                        mFanSwitch.setText("关闭");
                        mFanSwitchId =0;
                        mFanStateId =0;
                        sendBroadcast("z");
                    }
                    else{
                        mFanStateId =0;
                        mFanSwitchId =0;
                    }
                } else {
                    ToastUtil.showToast(FanActivity.this, "启动失败，暂未接入网络");
                }

            }
        });
        mFansu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRunning = IsServiceRunningUtil.
                        isServiceRunning(FanActivity.this, "com.kotori.smarthome.service.LinkService");
                if (isRunning) {
                    if(mFanStateId == 0) {
                        mFanState.setText("1");
                        mFanSwitch.setText("开启");
                        mFanSwitchId =1;
                        mFanStateId =1;
                    }
                    else if(mFanStateId == 1) {
                        mFanState.setText("2");
                        mFanStateId =2;
                    }
                    else{
                        mFanStateId =2;
                    }
                } else {
                    ToastUtil.showToast(FanActivity.this, "启动失败，暂未接入网络");
                }

            }
        });
    }
    // 发送广播
    public void sendBroadcast(String str){
        Intent intent = new Intent();
        intent.putExtra("result",str);
        intent.setAction("com.kotori.smarthome.ORDER");
        sendBroadcast(intent);
    }




}
