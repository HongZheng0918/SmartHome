// 欢迎界面
package com.kotori.smarthome.activity;


import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.kotori.smarthome.R;
import com.kotori.smarthome.util.ActivityUtil;

public class WelcomeActivity extends Activity {

    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    ActivityUtil.startActivity(WelcomeActivity.this,MainActivity.class,true);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        alphaAnim();
        startapp();



    }

    private void startapp() {
        new Thread(){
            public void run(){
                SystemClock.sleep(2000);
                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }



    //设置登陆动画
    private void alphaAnim(){
        Animation alaphanim = new AlphaAnimation(0.2f,1.0f);
        // 动画表现时间
        alaphanim.setDuration(2000);
        // 动画结束后是否停留在结束状态
        alaphanim.setFillAfter(true);
        ImageView img = (ImageView) findViewById(R.id.img_welcome);
        img.startAnimation(alaphanim);
    }







}
