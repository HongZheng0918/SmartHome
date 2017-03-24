package com.kotori.smarthome.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.kotori.smarthome.R;
import com.kotori.smarthome.service.LinkService;
import com.kotori.smarthome.util.ToastUtil;

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

public class LinkActivity extends Activity implements View.OnClickListener {

    // 定义控件
    private EditText edtIp;
    private EditText edtPort;
    private EditText edtSend;
    private EditText edtReceiver;
    private Button btnConn;
    private Button btnSend;
    // 定义文件流
    private InputStream mIns;
    private PrintWriter mPwriter;
    private BufferedReader mBufreader;
    // 定义其他
    private Socket mSocket;
    private Thread receiverThread;
    private boolean isConnected = false;        //连接状态
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String result = msg.getData().get("msg").toString();    //接收数据
                    edtReceiver.append(result);
                    break;
                case 2:
                    receiveData();          // 打开接收线程
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_link);
        init();
    }

    // 实例化控件
    private void init() {
        edtIp = (EditText) findViewById(R.id.id_edt_inputIP);
        edtPort = (EditText) findViewById(R.id.id_edt_inputPort);
        edtSend = (EditText) findViewById(R.id.id_edt_send);
        edtReceiver = (EditText) findViewById(R.id.id_edt_receive);
        btnConn = (Button) findViewById(R.id.id_btn_conn);
        btnConn.setOnClickListener(this);
        btnSend = (Button) findViewById(R.id.id_btn_send);
        btnSend.setOnClickListener(this);
    }

    // 设置监听事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_conn:     // 点击连接button
                Log.v("you", "点击成功");
                connectThread();
                break;
            case R.id.id_btn_send:     // 点击发送button
                sendData();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
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

    // 启动 连接/断开的线程
    private void connectThread() {
        if (!isConnected) {      // 判断连接状态，如果处于断开状态，则连接
            new Thread() {
                public void run() {
                    Looper.prepare();
                    Log.v("you", "连接服务器");
                    connectServer(edtIp.getText().toString(), edtPort.getText().toString());
                }
            }.start();
            isConnected = true;
        } else {                   //如果处于连接状态，则断开
            try {
                if (mSocket != null) {
                    mSocket.close();
                    mSocket = null;
                    Log.v("you", "断开服务器");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            btnConn.setText("连接");
            isConnected = false;
        }
    }

    // 发送数据
    private void sendData() {
        try {
            String context = edtSend.getText().toString();         //发送内容
            // 判断发送内容为空时，发送失败
            if (mPwriter == null || context == null) {
                if (mPwriter == null) {
                    ToastUtil.showToast(LinkActivity.this,"发送失败！");
                    return;
                }
                if (context == null) {
                    ToastUtil.showToast(LinkActivity.this,"发送失败！");
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

    // 启动 接收线程
    private void receiveData() {
        receiverThread = new Thread(new MyReceiverRunnable());
        receiverThread.start();

        btnConn.setText("断开");
        isConnected = true;
    }

    // 创建socket连接
    private void connectServer(String ip, String port) {
        try {
            Log.v("you", "开始配置socket");
            mSocket = new Socket(ip, Integer.parseInt(port));
            Log.v("you", "配置socket完毕");
            OutputStream outs = mSocket.getOutputStream();  // 打开输出流,用于接收socket传来的数据
            mPwriter = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(outs, Charset.forName("utf-8"))));
            mBufreader = new BufferedReader(new InputStreamReader(
                    mSocket.getInputStream()));
            mIns = mSocket.getInputStream();
            mHandler.sendEmptyMessage(2);

            ToastUtil.showToast(LinkActivity.this,"连接成功！");
        } catch (IOException e) {
            isConnected = false;
            ToastUtil.showToast(LinkActivity.this,"连接失败！");
            e.printStackTrace();
        }
    }


    // 接收线程
    public class MyReceiverRunnable implements Runnable {

        @Override
        public void run() {
            while (true) {       // 无限循环
                if (isConnected) {   // 判断是否处于连接状态
                    if (mSocket != null && mSocket.isConnected()) {
                        String result = readFromInputStream(mIns);
                        if(result!=null) {
                            if (!result.equals("")) {        //判断接收到消息是否为空
                                Message msg = new Message();
                                msg.what = 1;
                                Bundle data = new Bundle();
                                data.putString("msg", result);
                                msg.setData(data);
                                mHandler.sendMessage(msg);
                            }
                        }
                    }

                }
            }
        }
    }

    // 将字节流传化为字符流的方法
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




}