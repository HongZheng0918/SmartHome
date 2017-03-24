package com.kotori.smarthome.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kotori.smarthome.R;
import com.kotori.smarthome.util.DateUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class KeepActivity extends Activity implements Runnable{

    private EditText  name_text, password_text;
    private Button load, monitor;
    private String password, ask = "ask", getinfo = "info";
    public static int PORT = 6666;

    Socket socket;
    Thread thread;
    // DataInputStream in;
    // DataOutputStream out;
    boolean flag = false;

    byte[] buffer = new byte[32768];

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep);
        Log.e("you", "init" + 52);

        name_text = (EditText) findViewById(R.id.name);
        password_text = (EditText) findViewById(R.id.passwd);

        load = (Button) this.findViewById(R.id.load);
        monitor = (Button) this.findViewById(R.id.monitor);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
                .build());

        Log.v("you", "init " + 70);
        load.setOnClickListener(new ButtonClickListener());
        monitor.setOnClickListener(new ButtonClickListener());

        Log.v("you", "init " + 90);

        DateUtil.start = true;

    }


    final class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Log.e("you", "button click" + 102);
            switch (v.getId()) {
                case R.id.load:
                    if (flag == true) {
                        Toast.makeText(getApplicationContext(), "已登录",
                                Toast.LENGTH_LONG).show();
                        return;
                    }

                    PORT = Integer.parseInt(name_text.getText().toString());
                    Log.e("=====================", "" + PORT + "," + 112);
                    password = password_text.getText().toString();
                    Log.e("=====================", password + PORT + "," + 114);
                    if (PORT != 0 && password != null) {
                        try {

                            socket = new Socket(password, PORT);

                            Log.v("==================",
                                    "init new socket" + 127);

                            DateUtil.in = new DataInputStream(
                                    socket.getInputStream());

                            DateUtil.out = new DataOutputStream(
                                    socket.getOutputStream());

                            DateUtil.out.write(getinfo.getBytes());

                        } catch (IOException e) {
                            System.out.println("登录失败");
                            Log.e("===================", "链接失败" + 78);
                        }

                        thread = new Thread(KeepActivity.this);
                        {
                            Log.v("==================", "creat thread ok" + "   "
                                    + 117);
                        }

                        thread.start();
                        flag = true;
                        Toast.makeText(getApplicationContext(), "登录成功",
                                Toast.LENGTH_LONG).show();

                    }
                    break;

                case R.id.monitor:
                    flag = true;

                    if (flag == false) {
                        Toast.makeText(getApplicationContext(), "请登录",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    DateUtil.comd = 2;

                    Intent intent = new Intent(KeepActivity.this,
                            monitorActivity.class);
                    KeepActivity.this.startActivity(intent);

                    break;


                default:
                    break;
            }
        }

    }

    public void run() {

        int readlen;
        while (DateUtil.start) {
            try {
                switch (DateUtil.comd) {
                    case 1:
                        readlen = DateUtil.in.read(buffer);
                        Log.v("you", "revc comd, num= " + readlen);
                        break;

                    case 2:
                        if (DateUtil.getmap == 0) {

                            DateUtil.out.write(ask.getBytes());

                            int count = buffer.length;// 1450054;//31000;
                            int index = 0;
                            byte[] buffer0 = new byte[100000];
                            byte[] buffer1 = new byte[100000];

                            readlen = DateUtil.in.read(buffer1);
                            System.arraycopy(buffer1, 0, buffer0, index, readlen);
                            index += readlen;
                            while (index < count) {
                                readlen = DateUtil.in.read(buffer1);
                                Log.v("you", "revc num= "
                                        + readlen);
                                if (readlen > 0) {
                                    System.arraycopy(buffer1, 0, buffer0, index,
                                            readlen);
                                    Log.v("you", "revc num= "
                                            + readlen);
                                    index += readlen;
                                }
                            }

                            DateUtil.bitmap = BitmapFactory.decodeByteArray(
                                    buffer0, 0, count);
                            if (DateUtil.bitmap == null) {
                                Log.e("you", "bitmap=null " + 216);
                                break;
                            }
                            Log.e("you", "getmap ok ");
                            DateUtil.getmap = 1;

                        }
                        break;

                    default:
                        break;

                }

            } catch (IOException e) {
                Log.v("you", "recev failed" + "   "
                        + 210);

            }
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub

        DateUtil.start = false;

        super.onDestroy();
        Log.v("you", "onDestroy---------");
    }
}
