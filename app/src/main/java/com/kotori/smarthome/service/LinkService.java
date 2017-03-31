package com.kotori.smarthome.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.kotori.smarthome.R;
import com.kotori.smarthome.activity.SafeActivity;
import com.kotori.smarthome.activity.WarningActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;

public class LinkService extends Service {

    // 定义文件流
    private InputStream mIns;
    private PrintWriter mPwriter;
    private BufferedReader mBufreader;

    private Socket mSocket;                     // 设置socket
    private boolean isConnected = false;        // 连接状态
    private String mIp;                         // ip地址
    private String mPort;                       // 端口地址

    private ICallback callback;                 // 回调接口
    private LinkBroadcastReceiver mReceiver;

    private boolean isFire = false;
    private boolean isQuake = false;
    private boolean isTilt = false;
    private boolean isDoor = false;
    private boolean isWindow = false;

    // 点击查看
    private Intent messageIntent = null;
    private PendingIntent messagePendingIntent = null;

    // 通知栏消息
    private Notification messageNotification = null;
    private NotificationManager messageNotificatioManager = null;

    @Override
    public IBinder onBind(Intent intent) {

        return new Binder();
    }


    // Binder类，返回服务对象
    public class Binder extends android.os.Binder {
        public LinkService getService() {
            return LinkService.this;
        }
    }

    // 服务创建时执行的方法
    @Override
    public void onCreate() {
        super.onCreate();
        // 注册广播接收者
        mReceiver = new LinkBroadcastReceiver();
        IntentFilter filter = new IntentFilter("com.kotori.smarthome.ORDER");
        registerReceiver(mReceiver, filter);
    }

    // 服务启动时执行的方法

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mIp = intent.getStringExtra("ip");          // 将ip与port从传入服务
        mPort = intent.getStringExtra("port");
        Log.v("you", "ip:" + mIp + "port:" + mPort);

        connectServer();                            // 创建socket
        receiveData();                              // 创建接收线程

        return super.onStartCommand(intent, flags, startId);

    }

    // 通知栏推送消息
    private void messageNotification(String str) {

        if(str.equals("房门防盗")||str.equals("窗户防盗")) {
            messageIntent = new Intent(this, SafeActivity.class);
        }
        else{
            messageIntent = new Intent(this, WarningActivity.class);
        }
        messageNotificatioManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        messagePendingIntent = PendingIntent.getActivity(this, 0, messageIntent, 0);
        messageNotification = new Notification.Builder(getApplicationContext())
                .setAutoCancel(true)
                .setContentTitle("警报")
                .setContentText("您的家中发出了" + str + "预警,请注意安全")
                .setContentIntent(messagePendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .build();
        messageNotification.defaults = Notification.DEFAULT_ALL;
        messageNotificatioManager.notify(1, messageNotification);
    }

    // 服务销毁时执行的方法
    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (mSocket != null) {
                mSocket.close();
            }
            isConnected = false;
            if (mIns != null) {
                mIns.close();
            }
            if (mBufreader != null) {
                mBufreader.close();
            }
            if (mPwriter != null) {
                mPwriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 创建socket连接
    private void connectServer() {
        new Thread() {
            public void run() {
                try {
                    Log.v("you", "开始配置socket");
                    mSocket = new Socket(mIp, Integer.parseInt(mPort));
                    Log.v("you", "配置socket完毕");
                    OutputStream outs = mSocket.getOutputStream();  // 打开输出流,用于接收socket传来的数据
                    mPwriter = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(outs, Charset.forName("utf-8"))));
                    mBufreader = new BufferedReader(new InputStreamReader(
                            mSocket.getInputStream()));
                    mIns = mSocket.getInputStream();
                    isConnected = true;


                } catch (IOException e) {
                    isConnected = false;

                    e.printStackTrace();
                }
            }
        }.start();

    }

    // 启动接收线程
    private void receiveData() {
        Thread receiverThread = new Thread(new MyReceiverRunnable());
        receiverThread.start();
        Log.v("you", "接收线程启动完毕");

    }

    // 接收线程
    public class MyReceiverRunnable implements Runnable {

        @Override
        public void run() {
            while (true) {                                   // 无限循环
                if (isConnected) {                           // 判断是否处于连接状态
                    if (mSocket != null && mSocket.isConnected()) {
                        String result = readFromInputStream(mIns);
                        if (result!=null) {            // 判断接收到消息是否为空
                            Log.v("you", result);
                            if (callback != null) {
                                callback.onDataChange(result);
                            }
                            if (result.equals("f")) {        // 收到火灾预警
                                messageNotification("火灾");
                                isFire = true;
                            }
                            if (result.equals("e")) {        // 收到地震预警
                                messageNotification("地震");
                                isQuake = true;
                            }
                            if (result.equals("q")) {        // 收到倾斜预警
                                messageNotification("倾斜");
                                isTilt = true;
                            }
                            if (result.equals("d")) {        // 收到房门防盗预警
                                messageNotification("房门防盗");
                                isDoor = true;
                            }
                            if (result.equals("b")) {        // 收到窗户防盗预警
                                messageNotification("窗户防盗");
                                isWindow = true;
                            }
                        }
                    }
                }
            }
        }
    }

    // 将字节流传化为字符流
    public String readFromInputStream(InputStream in) {
        int count = 0;
        byte[] inDatas = null;
        try {
            while (count == 0) {
                count = in.available();
            }
            inDatas = new byte[count];
            in.read(inDatas);
            return new String(inDatas);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 发送数据
    private void sendData(String data) {
        try {
            String context = data;          //发送内容
            // 判断发送内容为空时，发送失败
            if (mPwriter == null || context == null) {
                if (mPwriter == null) {
                    Log.v("you", "没有消息发送");
                    return;
                }
                if (context == null) {
                    Log.v("you", "没有消息发送");
                    return;
                }
            }
            mPwriter.print(context);        //将发送内容写入流中
            mPwriter.flush();
            Log.v("you", "客户端发送成功");
        } catch (Exception e) {
            Log.v("you", "客户端发送失败");
        }
    }

    // 设置回调接口,当接收到消息后传递给Activity
    public interface ICallback {
        void onDataChange(String data);
    }

    public void setCallback(ICallback callback) {
        this.callback = callback;
    }


    public class LinkBroadcastReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {

            String result = intent.getStringExtra("result");
            if (result != null) {
                sendData(result);
            }
            String str = intent.getStringExtra("state");
            if (str != null) {
                // 判断来自灾难预警的消息
                if (str.equals("warning")) {
                    if (isFire) {
                        callback.onDataChange("f");
                        isFire = false;
                    }
                    if (isQuake) {
                        callback.onDataChange("e");
                        isQuake = false;
                    }
                    if (isTilt) {
                        callback.onDataChange("q");
                        isTilt = false;
                    }
                }
                // 判断来自安防预警的消息
                if(str.equals("safe")){
                    if (isDoor) {
                        callback.onDataChange("f");
                        isDoor = false;
                    }
                    if (isWindow) {
                        callback.onDataChange("e");
                        isWindow = false;
                    }
                }
            }

        }
    }


}
