package com.kotori.smarthome.util;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by kotori on 2017/3/9.
 * toast工具类
 */
public class ToastUtil {

    public static void showToast(final Activity context, final String msg){
        if("main".equals(Thread.currentThread().getName())){
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }else{
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
