package com.kotori.smarthome.util;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by kotori on 2017/3/9.
 * 判断服务是否运行的工具类
 */
public class IsServiceRunningUtil {

    public static boolean isServiceRunning(Context mContext, String serviceName) {

        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(200);

        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                Log.v("you","服务存在");
                break;
            }
        }
        return isWork;
    }
}
