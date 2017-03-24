package com.kotori.smarthome.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kotori.smarthome.R;
import com.kotori.smarthome.service.LinkService;

public class WarningActivity extends Activity implements ServiceConnection{

    // 初始化控件
    private ImageView warningFire;
    private ImageView warningQuake;
    private ImageView warningTilt;

    private LinearLayout relFire;
    private LinearLayout relQuake;
    private LinearLayout relTilt;


    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            // 当收到火焰预警时
            if(msg.obj.equals("f")) {
                WarningFireUI();
            }
            // 当收到地震预警时
            if(msg.obj.equals("e")) {
                WarningQuakeUI();
            }
            // 当收到倾斜预警时
            if(msg.obj.equals("q")) {
                WarningTiltUI();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_warning);
        bindSer();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initview();
    }

    private void initview() {
        warningFire = (ImageView) findViewById(R.id.img_warning_fire);
        warningQuake = (ImageView) findViewById(R.id.img_warning_quake);
        warningTilt = (ImageView) findViewById(R.id.img_warning_tilt);

        relFire = (LinearLayout) findViewById(R.id.rel_fire);
        relQuake= (LinearLayout) findViewById(R.id.rel_quake);
        relTilt = (LinearLayout) findViewById(R.id.rel_tilt);
    }

    // 绑定服务
    private void bindSer() {
        Intent tempIntent = new Intent(WarningActivity.this, LinkService.class);
        bindService(tempIntent,this,BIND_AUTO_CREATE);
    }

    // 与服务传递参数
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        LinkService.Binder mbinder = (LinkService.Binder) service;
        LinkService linkservice = mbinder.getService();
        linkservice.setCallback(new LinkService.ICallback() {
            @Override
            public void onDataChange(String data) {
                Message msg = new Message();
                msg.obj = data;
                handler.sendMessage(msg);
            }
        });
        sendBroadcast();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    // 倾斜预警
    private void WarningTiltUI(){
        warningTilt.setImageResource(R.mipmap.home_tilt_red);
        relTilt.setBackgroundResource(R.drawable.style_corner_red);
    }
    // 地震预警
    private void WarningQuakeUI(){
        warningQuake.setImageResource(R.mipmap.quake_red);
        relQuake.setBackgroundResource(R.drawable.style_corner_red);
    }
    // 火焰预警
    private void WarningFireUI(){
        warningFire.setImageResource(R.mipmap.fire_red);
        relFire.setBackgroundResource(R.drawable.style_corner_red);
    }

    // 发送广播
    public void sendBroadcast(){
        Intent intent = new Intent();
        intent.putExtra("state","warning");
        intent.setAction("com.kotori.smarthome.ORDER");
        sendBroadcast(intent);
    }




}
