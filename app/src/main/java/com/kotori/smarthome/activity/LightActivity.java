package com.kotori.smarthome.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.kotori.smarthome.R;
import com.kotori.smarthome.service.LinkService;
import com.kotori.smarthome.util.IsServiceRunningUtil;
import com.kotori.smarthome.util.ToastUtil;

public class LightActivity extends Activity {

    private ImageView mLightliv;
    private ImageView mLighteat;
    private ImageView mLightroom;
    private Switch mLightSwitch;
    private int mLightId;
    private int mLivId;
    private int mEatId;
    private int mRoomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
        initView();
        setClick();

    }

    // 实例化控件
    private void initView() {
        mLightliv = (ImageView) findViewById(R.id.light_livingroom);
        mLighteat = (ImageView) findViewById(R.id.light_eatingroom);
        mLightroom = (ImageView) findViewById(R.id.light_room);
        mLightSwitch = (Switch) findViewById(R.id.light_switch_all);

        mLightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mLightId == 0) {
                    mLightliv.setImageResource(R.drawable.light_open);
                    mLighteat.setImageResource(R.drawable.light_open);
                    mLightroom.setImageResource(R.drawable.light_open);
                    mLightId = 1;
                    mLivId = 1;
                    mEatId = 1;
                    mRoomId = 1;
                    sendBroadcast("246");
                } else {
                    mLightliv.setImageResource(R.drawable.light_off);
                    mLighteat.setImageResource(R.drawable.light_off);
                    mLightroom.setImageResource(R.drawable.light_off);
                    mLightId = 0;
                    mLivId = 0;
                    mEatId = 0;
                    mRoomId = 0;
                    sendBroadcast("357");
                }

            }
        });
    }

    // 设置点击事件
    private void setClick() {
        mLightliv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRunning = IsServiceRunningUtil.
                        isServiceRunning(LightActivity.this, "com.kotori.smarthome.service.ControlService");
                if (isRunning) {
                    if (mLivId == 0) {
                        mLightliv.setImageResource(R.drawable.light_open);
                        mLivId = 1;
                        sendBroadcast("2");
                    } else {
                        mLightliv.setImageResource(R.drawable.light_off);
                        mLivId = 0;
                        sendBroadcast("3");
                    }
                }
                else {
                    ToastUtil.showToast(LightActivity.this, "启动失败，暂未接入网络");
                }

            }
        });
        mLighteat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRunning = IsServiceRunningUtil.
                        isServiceRunning(LightActivity.this, "com.kotori.smarthome.service.ControlService");
                if (isRunning) {
                    if (mEatId == 0) {
                        mLighteat.setImageResource(R.drawable.light_open);
                        mEatId = 1;
                        sendBroadcast("4");
                    } else {
                        mLighteat.setImageResource(R.drawable.light_off);
                        mEatId = 0;
                        sendBroadcast("5");
                    }
                }
                else {
                    ToastUtil.showToast(LightActivity.this, "启动失败，暂未接入网络");
                }
            }
        });
        mLightroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRunning = IsServiceRunningUtil.
                        isServiceRunning(LightActivity.this, "com.kotori.smarthome.service.ControlService");
                if (isRunning) {
                    if (mRoomId == 0) {
                        mLightroom.setImageResource(R.drawable.light_open);
                        mRoomId = 1;
                        sendBroadcast("6");
                    } else {
                        mLightroom.setImageResource(R.drawable.light_off);
                        mRoomId = 0;
                        sendBroadcast("7");
                    }
                }
                else {
                    ToastUtil.showToast(LightActivity.this, "启动失败，暂未接入网络");
                }
            }
        });
    }

    public void sendBroadcast(String str) {
        Intent intent = new Intent();
        intent.putExtra("result", str);
        intent.setAction("com.kotori.smarthome.ORDER");
        sendBroadcast(intent);
    }


}
