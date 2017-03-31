package com.kotori.smarthome.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.kotori.smarthome.R;
import com.kotori.smarthome.util.ToastUtil;

public class ParameterActivity extends Activity implements View.OnClickListener{

    private Button ChangeHomeButton;
    private Button ChangeControlButton;
    private Button ChangePasswordButton;
    private LayoutInflater inflater;
    private SharedPreferences sharePre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_parameter);
        initview();

    }

    // 初始化控件
    private void initview() {
        inflater = LayoutInflater.from(ParameterActivity.this);
        ChangeControlButton = (Button) findViewById(R.id.btn_control_parameter);
        ChangeHomeButton = (Button) findViewById(R.id.btn_home_parameter);
        ChangePasswordButton = (Button) findViewById(R.id.btn_door_password);
        ChangeHomeButton.setOnClickListener(this);
        ChangeControlButton.setOnClickListener(this);
        ChangePasswordButton.setOnClickListener(this);
        sharePre = getSharedPreferences("ip_port",MODE_PRIVATE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_control_parameter :
                View controlView = inflater.inflate(R.layout.dialog_edit_ip_view,null);

                AlertDialog controlDialog = new AlertDialog.Builder(ParameterActivity.this).create();
                controlDialog.setTitle("配置控制平台地址");
                controlDialog.setView(controlView);
                final EditText ed_ip = (EditText) controlView.findViewById(R.id.edit_ip);
                final EditText ed_port = (EditText) controlView.findViewById(R.id.edit_port);
                controlDialog.setButton(DialogInterface.BUTTON_POSITIVE,"确定", new DialogInterface.
                        OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v("you","确定修改");
                        SharedPreferences.Editor editor = sharePre.edit();
                        editor.putString("control_ip",ed_ip.getText().toString());
                        editor.putString("control_port",ed_port.getText().toString());
                        editor.commit();
                        ToastUtil.showToast(ParameterActivity.this,"修改完毕");
                    }
                });
                controlDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"取消", new DialogInterface.
                        OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v("you","取消修改");
                    }
                });
                controlDialog.show();
                break;
            case R.id.btn_home_parameter:
                View homeView = inflater.inflate(R.layout.dialog_edit_ip_view,null);
                final EditText home_ip = (EditText) homeView.findViewById(R.id.edit_ip);
                final EditText home_port = (EditText) homeView.findViewById(R.id.edit_port);
                AlertDialog homeDialog = new AlertDialog.Builder(ParameterActivity.this).create();
                homeDialog.setTitle("配置状态监控地址");
                homeDialog.setView(homeView);
                homeDialog.setButton(DialogInterface.BUTTON_POSITIVE,"确定", new DialogInterface.
                        OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v("you","确定修改");
                        SharedPreferences.Editor editor = sharePre.edit();
                        editor.putString("home_ip",home_ip.getText().toString());
                        editor.putString("home_port",home_port.getText().toString());
                        editor.commit();
                        ToastUtil.showToast(ParameterActivity.this,"修改完毕");
                    }
                });
                homeDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"取消", new DialogInterface.
                        OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v("you","取消修改");
                    }
                });
                homeDialog.show();
                break;
            case R.id.btn_door_password:
                View pwdView = inflater.inflate(R.layout.dialog_edit_pwd_view,null);
                final EditText old_pwd = (EditText) pwdView.findViewById(R.id.edit_old_pwd);
                final EditText new_pwd = (EditText) pwdView.findViewById(R.id.edit_new_pwd);
                AlertDialog pwdDialog = new AlertDialog.Builder(ParameterActivity.this).create();
                pwdDialog.setTitle("配置门禁密码");
                pwdDialog.setView(pwdView);
                pwdDialog.setButton(DialogInterface.BUTTON_POSITIVE,"确定", new DialogInterface.
                        OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v("you","确定修改");
                    }
                });
                pwdDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"取消", new DialogInterface.
                        OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v("you","取消修改");
                    }
                });
                pwdDialog.show();
                break;
        }
    }
}
