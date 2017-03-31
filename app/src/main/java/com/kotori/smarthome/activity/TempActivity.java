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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kotori.smarthome.R;
import com.kotori.smarthome.service.LinkService;
import com.kotori.smarthome.util.IsServiceRunningUtil;
import com.kotori.smarthome.util.ToastUtil;

public class TempActivity extends Activity implements ServiceConnection{

    private LinearLayout alcohol;
    private LinearLayout meter;
    private TextView thermo_c;
    private TextView thermo_f;
    private RelativeLayout get_temp;

    public float startTemp;
    public float temp;
    private float temperatureC;		// 摄氏度

    private float mTemp;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if(msg.obj.equals(1)) {
                float temperatureValue = mTemp; // 得到温度
                temperatureC = temperatureValue;// 设置温度
                mUpdateUi();// 更新UI
                mUpdateUi();
            }
            else{
                String data = (String) msg.obj;
                mTemp = Float.parseFloat(data);
            }

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_temp);
        initView();
        bindSer();
        setClick();
    }

    // 绑定服务
    private void bindSer() {
        Intent tempIntent = new Intent(TempActivity.this, LinkService.class);
        bindService(tempIntent,this,BIND_AUTO_CREATE);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        LinkService.Binder binder = (LinkService.Binder) service;
        LinkService linkService = binder.getService();
        linkService.setCallback(new LinkService.ICallback() {
            @Override
            public void onDataChange(String data) {
                Message msg = new Message();
                if(data.equals("f")||data.equals("e")||data.equals("q")){
                    return;
                }
                msg.obj = data;
                handler.sendMessage(msg);
            }
        });
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(this);
    }

    // 初始化view
    private void initView() {
        meter = ((LinearLayout) findViewById(R.id.meter));
        alcohol = ((LinearLayout) findViewById(R.id.alcohol));
        thermo_c = (TextView) findViewById(R.id.thermo_c);
        thermo_f = (TextView) findViewById(R.id.thermo_f);
        get_temp = (RelativeLayout) findViewById(R.id.get_temp);

    }

    // 设置监听事件
    private void setClick() {
        get_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    sendBroadcast("result","w");
                    new Thread(){
                        public void run(){
                            try {
                                Thread.sleep(1000);
                                Message msg = new Message();
                                msg.obj = 1;
                                handler.sendMessage(msg);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
            }
        });
    }


    // 华氏温度
    public float getTemperatureF() {
        float temperatureF = (temperatureC * 9 / 5) + 32;
        return getFloatOne(temperatureF);
    }

    // 摄氏温度
    public float getTemperatureC() {
        return getFloatOne(temperatureC);
    }

    // 保留一位小数点
    public float getFloatOne(float tempFloat) {
        return (float) (Math.round(tempFloat * 10)) / 10;
    }

    // 更新温度计UI
    private void mUpdateUi() {
        ScaleAnimation localScaleAnimation1 = new ScaleAnimation(1.0F, 1.0F, this.startTemp, this.temp, 1, 0.5F, 1,
                1.0F);
        localScaleAnimation1.setDuration(2000L);
        localScaleAnimation1.setFillEnabled(true);
        localScaleAnimation1.setFillAfter(true);
        this.alcohol.startAnimation(localScaleAnimation1);
        this.startTemp = this.temp;

        ScaleAnimation localScaleAnimation2 = new ScaleAnimation(1.0F, 1.0F, 1.0F, 1.0F, 1, 0.5F, 1, 0.5F);
        localScaleAnimation2.setDuration(10L);
        localScaleAnimation2.setFillEnabled(true);
        localScaleAnimation2.setFillAfter(true);
        this.meter.startAnimation(localScaleAnimation2);

        // 把刻度表看出总共700份，如何计算缩放比例。从-20°到50°。
        // 例如，现在温度是30°的话，应该占（30+20）*10=500份 其中20是0到-20°所占有的份
        this.temp = (float) ((20.0F + getTemperatureC()) * 10) / (70.0F * 10);

        thermo_c.setText(getTemperatureC() + "");
        thermo_f.setText(getTemperatureF() + "");
    }

    // 发送广播
    public void sendBroadcast(String isOrder,String str){
        Intent intent = new Intent();
        intent.putExtra(isOrder,str);
        intent.setAction("com.kotori.smarthome.ORDER");
        sendBroadcast(intent);
    }

}
