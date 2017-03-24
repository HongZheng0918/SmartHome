package com.kotori.smarthome.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by kotori on 2017/2/20.
 * 跳转Activiy的工具类
 */
public class ActivityUtil {
    public static void startActivity(Context c,Class clz,boolean ifFinish){
        Intent intent = new Intent(c,clz);
        c.startActivity(intent);
        if(ifFinish) {
            ((Activity)c).finish();
        }

    }
}
