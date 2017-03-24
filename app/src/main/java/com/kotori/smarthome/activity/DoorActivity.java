package com.kotori.smarthome.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.kotori.smarthome.R;
import com.kotori.smarthome.util.IsServiceRunningUtil;
import com.kotori.smarthome.util.ToastUtil;

public class DoorActivity extends Activity {

    private TextView tv_door;
    private TextView tv_pwd;
    private ImageView switch_door;
    private EditText etv_pwd;
    private String pwd;
    private int switch_state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door);
        initView();
        getSharepfer();
        setClick();
    }

    // 获取sharePreferences下保存的密码
    private void getSharepfer() {
        SharedPreferences share = getSharedPreferences("door_pwd", Context.MODE_PRIVATE);
        pwd = share.getString("door_pwd","123456");
    }

    // 初始化控件
    private void initView() {
        tv_door = (TextView) findViewById(R.id.door_state);
        tv_pwd = (TextView) findViewById(R.id.door_pwd);
        switch_door = (ImageView) findViewById(R.id.door_switch);
        etv_pwd = (EditText) findViewById(R.id.door_edt);
    }

    // 设置监听事件
    private void setClick() {
        switch_door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isRunning = IsServiceRunningUtil.
                        isServiceRunning(DoorActivity.this, "com.kotori.smarthome.service.ControlService");
                if (isRunning) {
                    pwd = etv_pwd.getText().toString();
                    if(pwd.equals(pwd)) {
                        if (switch_state == 0) {
                            sendBroadcast("k");
                            switch_state = 1;
                            ToastUtil.showToast(DoorActivity.this, "房门已开启");
                            tv_door.setText("开启");
                        } else {
                            sendBroadcast("c");
                            switch_state = 0;
                            ToastUtil.showToast(DoorActivity.this, "房门已关闭");
                            tv_door.setText("关闭");
                        }
                    }
                    else {
                        ToastUtil.showToast(DoorActivity.this, "密码错误");
                    }

                } else {
                    ToastUtil.showToast(DoorActivity.this, "启动失败，暂未接入网络");
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void sendBroadcast(String str) {
        Intent intent = new Intent();
        intent.putExtra("result", str);
        intent.setAction("com.kotori.smarthome.ORDER");
        sendBroadcast(intent);
    }


}
