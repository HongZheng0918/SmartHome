package com.kotori.smarthome.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kotori.smarthome.R;
import com.kotori.smarthome.service.LinkService;

public class SafeActivity extends Activity implements ServiceConnection{

    // 初始化控件
    private RelativeLayout relDoor;
    private RelativeLayout relWindow;
    private ImageView safeDoor;
    private ImageView safeWindow;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            // 当收到房门防盗预警时
            if(msg.obj.equals("d")) {
                SafeDoorUI();
            }
            // 当收到窗户防盗预警时
            if(msg.obj.equals("w")) {
                SafeWindowUI();
            }
        }
    };

    private void SafeDoorUI() {
        safeDoor.setImageResource(R.mipmap.safe_door_red);
        relDoor.setBackgroundResource(R.drawable.style_corner_red);
    }

    private void SafeWindowUI() {
        safeWindow.setImageResource(R.mipmap.safe_window_red);
        safeWindow.setBackgroundResource(R.drawable.style_corner_red);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_safe);
        bindSer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // 实例化控件
    private void initView() {
        relDoor = (RelativeLayout) findViewById(R.id.rel_door);
        relWindow = (RelativeLayout) findViewById(R.id.rel_window);
        safeDoor = (ImageView) findViewById(R.id.img_safe_door);
        safeWindow = (ImageView) findViewById(R.id.img_safe_window);
    }
    // 绑定服务
    private void bindSer() {
        Intent tempIntent = new Intent(SafeActivity.this, LinkService.class);
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

    // 发送广播
    public void sendBroadcast(){
        Intent intent = new Intent();
        intent.putExtra("state","safe");
        intent.setAction("com.kotori.smarthome.ORDER");
        sendBroadcast(intent);
    }
}
