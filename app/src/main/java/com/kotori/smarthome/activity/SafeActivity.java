package com.kotori.smarthome.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kotori.smarthome.R;
import com.kotori.smarthome.service.LinkService;
import com.kotori.smarthome.util.IsServiceRunningUtil;

public class SafeActivity extends Activity implements ServiceConnection{

    // 初始化控件
    private LinearLayout relDoor;
    private LinearLayout relWindow;

    private ImageView safeDoor;
    private ImageView safeWindow;

    private TextView switchWindow;
    private TextView switchDoor;

    private TextView stateWindow;
    private TextView stateDoor;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            // 当收到房门防盗预警时
            if(msg.obj.equals("d")) {
                SafeDoorUI();
            }
            // 当收到窗户防盗预警时
            if(msg.obj.equals("b")) {
                SafeWindowUI();
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_safe);
        initView();
        changeView();
        bindSer();
        setClick();
    }


    private void changeView() {
        boolean isRunning = IsServiceRunningUtil.
                isServiceRunning(SafeActivity.this, "com.kotori.smarthome.service.LinkService");
        if(isRunning) {
            switchDoor.setText("开启");
            switchDoor.setTextColor(Color.GREEN);
            switchWindow.setText("开启");
            switchWindow.setTextColor(Color.GREEN);
            stateDoor.setText("一切正常");
            stateWindow.setText("一切正常");

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // 实例化控件
    private void initView() {
        relDoor = (LinearLayout) findViewById(R.id.rel_door);
        relWindow = (LinearLayout) findViewById(R.id.rel_window);
        safeDoor = (ImageView) findViewById(R.id.img_safe_door);
        safeWindow = (ImageView) findViewById(R.id.img_safe_window);
        switchDoor = (TextView) findViewById(R.id.tv_door_swtich);
        switchWindow = (TextView) findViewById(R.id.tv_window_switch);
        stateDoor = (TextView) findViewById(R.id.tv_door_state);
        stateWindow = (TextView) findViewById(R.id.tv_window_state);
    }
    // 绑定服务
    private void bindSer() {
        Intent tempIntent = new Intent(SafeActivity.this, LinkService.class);
        bindService(tempIntent,this,BIND_AUTO_CREATE);
    }
    // 设置点击事件
    private void setClick() {
        relDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcast("result","d");
            }
        });

        relWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcast("result","b");
            }
        });
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
        sendBroadcast("state","safe");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    public void sendBroadcast(String isOrder,String str){
        Intent intent = new Intent();
        intent.putExtra(isOrder,str);
        intent.setAction("com.kotori.smarthome.ORDER");
        sendBroadcast(intent);
    }

    // 房门预警
    private void SafeDoorUI() {
        safeDoor.setImageResource(R.mipmap.safe_door_red);
        relDoor.setBackgroundResource(R.drawable.style_corner_red);
        stateDoor.setText("您的房屋的房门被闯入，请速去检查");
        switchDoor.setText("报警");
        switchDoor.setTextColor(Color.RED);
    }

    // 窗户预警
    private void SafeWindowUI() {
        safeWindow.setImageResource(R.mipmap.safe_window_red);
        relWindow.setBackgroundResource(R.drawable.style_corner_red);
        stateWindow.setText("您的房屋的窗户被打开，请速去检查");
        switchWindow.setText("报警");
        switchWindow.setTextColor(Color.RED);
    }
}
